package club.rodong.slitch.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

import club.rodong.slitch.POJO.EmoteObj;
import club.rodong.slitch.R;
import club.rodong.slitch.activity.Stream_Player_Activity;
import club.rodong.slitch.interfaces.ItemClickListener;

public class EmoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemClickListener {
    private final static String TAG = "EmoteAdapter";
    private ArrayList<EmoteObj> Items = new ArrayList<>();
    public static final int EmoteViewType = 0;
    public static final int DividerViewType = 1;

    private RequestManager glide;
    private Context context;
    public EmoteAdapter(RequestManager glide, Context context){
        this.glide = glide;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == DividerViewType){
            return new EmoteAdapter.DividerViewHolder(
                    layoutInflater.inflate(R.layout.emote_divider, parent, false));
        }else{
            return new EmoteAdapter.EmoteViewHolder(
                    layoutInflater.inflate(R.layout.emote, parent, false),this);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == EmoteViewType){
            EmoteViewHolder emoteViewHolder = (EmoteViewHolder)holder;
            ImageView emoteview = emoteViewHolder.emoteview;
            glide.load(Items.get(position).getEmote_url()).into(emoteview);
        }else{
            DividerViewHolder dividerViewHolder = (DividerViewHolder)holder;
        }

    }

    @Override
    public int getItemCount() {
        return Items.size();
    }
    public ArrayList<EmoteObj> getItemList() {
        return Items;
    }
    @Override
    public int getItemViewType(int position){
        if(Items.get(position).isDivider()){
            return DividerViewType;
        }else{
            return EmoteViewType;
        }

    }

    @Override
    public void onItemClick(View v, int position) {
        String emote_code = Items.get(position).getEmote_code();
        ((Stream_Player_Activity)context).appendEditText(" " + emote_code + " ");
    }

    @Override
    public void onItemLongClick(View v, int position) {

    }

    static class EmoteViewHolder extends RecyclerView.ViewHolder {
        ImageView emoteview;
        public EmoteViewHolder(View itemView, final ItemClickListener itemClickListener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        itemClickListener.onItemClick(v,getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        itemClickListener.onItemLongClick(v,getAdapterPosition());
                    }
                    return true;
                }
            });
            emoteview = itemView.findViewById(R.id.emoteImageView);
        }
    }
    static class DividerViewHolder extends RecyclerView.ViewHolder{
        View line;
        public DividerViewHolder(View itemView) {
            super(itemView);
            line = itemView.findViewById(R.id.divider);
        }
    }
    public void add_emote(int emoticon_sets, String emote_url, String emote_code, int emote_id, boolean isdivider){
        Log.i(TAG, "add_emote: " + emote_url);
        EmoteObj emoteObj = new EmoteObj();
        emoteObj.setEmoticon_sets(emoticon_sets);
        emoteObj.setEmote_url(emote_url);
        emoteObj.setEmote_id(emote_id);
        emoteObj.setEmote_code(emote_code);
        emoteObj.setDivider(isdivider);
        Items.add(emoteObj);
    }
    public void clear() {
        Items.clear();
        notifyDataSetChanged();
    }
}
