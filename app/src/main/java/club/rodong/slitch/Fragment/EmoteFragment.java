package club.rodong.slitch.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import club.rodong.slitch.GlideApp;
//import club.rodong.playerforstream.ItemOffsetDecoration;
import club.rodong.slitch.POJO.EmoteObj;
import club.rodong.slitch.R;
import club.rodong.slitch.SharedPreferenceHelper;
import club.rodong.slitch.adapter.EmoteAdapter;

public class EmoteFragment extends Fragment {
    RecyclerView emote_list;
    EmoteAdapter adapter;
    SharedPreferenceHelper SPH;

    int page;
    int recyclerview_width;
    String title;
    public static EmoteFragment newInstance(int page, String title){
        EmoteFragment fragmentFirst = new EmoteFragment();
        Bundle args = new Bundle();
        args.putInt("pageInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_emote_selector, container, false);
        emote_list = v.findViewById(R.id.emote_list);
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SPH = new SharedPreferenceHelper(getContext());

        recyclerview_width = emote_list.getMeasuredWidth();
        adapter = new EmoteAdapter(GlideApp.with(getContext()), getContext());
        emote_list.setAdapter(adapter);

        emote_list.setItemViewCacheSize(1000);
        emote_list.setDrawingCacheEnabled(true);
        emote_list.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        emote_list.setItemAnimator(null);

        adapter.notifyDataSetChanged();
    }
    public void addEmotes(int position){
        adapter.clear();
        JsonParser jsonParser = new JsonParser();
        JsonObject object = jsonParser.parse(readJsonFromFile(getContext(), "twitch_user_emotes.json")).getAsJsonObject();//UserEmotes
        Set<Map.Entry<String, JsonElement>> entries = object.get("emoticon_sets").getAsJsonObject().entrySet();
        ArrayList<String> setsArr = new ArrayList<>();
        for(Map.Entry<String, JsonElement> entry: entries){
            setsArr.add(entry.getKey());
        }
        for (Map.Entry<String, JsonElement> entry: entries) {
            if(entry.getKey().equals(setsArr.get(position))){
                int emote_sets = Integer.valueOf(entry.getKey());
                for(int i = 0; i < entry.getValue().getAsJsonArray().size(); i++){
                    JsonElement e = entry.getValue().getAsJsonArray().get(i).getAsJsonObject();
                    int emote_id =  ((JsonObject) e).get("id").getAsInt();
                    String emote_code = ((JsonObject) e).get("code").getAsString();
                    String url = "https://static-cdn.jtvnw.net/emoticons/v1/" + emote_id + "/3.0";
                    adapter.add_emote(emote_sets, url, emote_code, emote_id, false);
                }
            }
        }
        SortList();
        adapter.notifyDataSetChanged();

    }
    private void SortList() {
        ArrayList<EmoteObj> list = adapter.getItemList();
        Comparator<EmoteObj> noDesc = new Comparator<EmoteObj>() {
            @Override
            public int compare(EmoteObj item1, EmoteObj item2) {
                return (item1.getEmoticon_sets() - item2.getEmoticon_sets());
            }
        };
        Collections.sort(list, noDesc);
        for(int i = 0; i < list.size(); i++){
            adapter.notifyItemInserted(i);
        }
    }
    private String readJsonFromFile(Context context, String filename) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(filename);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("MessageAdapter", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("MessageAdapter", "Can not read file: " + e.toString());
        }
        return ret;
    }
}
