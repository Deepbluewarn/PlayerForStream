/*
package club.rodong.playerforstream.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.collect.ImmutableSortedSet;

import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.EnableCapHandler;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.OutputEvent;

import java.io.IOException;

import club.rodong.playerforstream.R;
import club.rodong.playerforstream.SharedPreferenceHelper;
import club.rodong.playerforstream.TwitchListener;
import club.rodong.playerforstream.adapter.MessageAdapter;

public class SubFragment extends Fragment {
    private RecyclerView MessageList;
    private EditText editText;
    private MessageAdapter messageAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferenceHelper SPH;
    private PircBotX bot;
    private PircBotX SenderBot;
    private TwitchListener myListener;
    private String channel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreate","SubFragment");
        SPH = new SharedPreferenceHelper(getActivity());
        //channel = SPH.getLastJoinChannel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("onCreateView","SubFragment");
        View v = inflater.inflate(R.layout.twitch_video_two, container, false);
        final Button hide_btn = v.findViewById(R.id.hide);
        final View recyclerview = v.findViewById(R.id.message_list);
        final View edittext = v.findViewById(R.id.editText2);
        hide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide_btn.setVisibility(View.GONE);
                recyclerview.setVisibility(View.GONE);
                edittext.setVisibility(View.GONE);
            }
        });
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        channel = getArguments().getString("channel");
        Log.i("onActivityCreated","SubFragment");
        MessageList = getView().findViewById(R.id.message_list);
        editText = getView().findViewById(R.id.editText2);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        messageAdapter = new MessageAdapter(getActivity(),MessageList);
        MessageList.setAdapter(messageAdapter);
        MessageList.setLayoutManager(linearLayoutManager);

        myListener = new TwitchListener(messageAdapter, SPH, getActivity());

        connectIRC();
        connectIRC_Send();
        */
/*ConnectChat();*//*


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    final String text = v.getText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(channel.equals("null")){

                            }else{
                                SenderBot.send().message("#" + channel, text);
                            }

                        }
                    }).start();
                    editText.setText("");
                }
                return false;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("onAttach","SubFragment");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("onStart","SubFragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("onResume","SubFragment");
        if(channel != null){
            if(bot.isConnected() && SenderBot.isConnected()){
                Log.i("onResume","SubFragment : 채팅 서버에 연결되어 있는 상태.");
                if(!bot.getUserChannelDao().containsChannel(channel)){
                    Log.i("onResume","SubFragment : 채널에 연결되어 있지 않음.");
                    ConnectChat(channel);
                }
            }else{
                Log.i("onResume","SubFragment : 채팅 서버에 연결되어 있지 않은 상태. 재연결.");
                connectIRC();
                connectIRC_Send();
                ConnectChat(channel);
            }
        }else{
            Log.d("onResume()", "onResume: channel is null!");
        }
    }

    */
/*@Override
    public void onPause() {
        super.onPause();
        Log.i("onPause","SubFragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("onStop","SubFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("onDestroy","SubFragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("onDestroyView","SubFragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("onDetach","SubFragment");
    }*//*


    private void connectIRC(){
        Configuration configuration = new Configuration.Builder()
                .setCapEnabled(true)
                .setOnJoinWhoEnabled(false)
                .addCapHandler(new EnableCapHandler("twitch.tv/tags"))
                .addCapHandler(new EnableCapHandler("twitch.tv/membership"))
                .addCapHandler(new EnableCapHandler("twitch.tv/commands"))
                .setServerPassword(getString(R.string.twitch_oauth))
                .setName(SPH.getTusername())
                .addServer(getString(R.string.twitch_irc))
                */
/*.addAutoJoinChannel("#" + channel)*//*

                .addListener(myListener)
                .buildConfiguration();
        bot = new PircBotX(configuration);//Create our bot with the configuration
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Connect to the server
                try {
                    bot.startBot();
                    Log.i("bot", "run: 연결됨");
                } catch (IOException | IrcException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void connectIRC_Send(){
        Configuration configuration = new Configuration.Builder()
                .setCapEnabled(true)
                .setOnJoinWhoEnabled(false)
                .addCapHandler(new EnableCapHandler("twitch.tv/tags"))
                .addCapHandler(new EnableCapHandler("twitch.tv/membership"))
                .addCapHandler(new EnableCapHandler("twitch.tv/commands"))
                .setServerPassword(getString(R.string.twitch_oauth))
                .setName(SPH.getTusername())
                .addServer(getString(R.string.twitch_irc))
                */
/*.addAutoJoinChannel("#" + channel)*//*

                .addListener(new ListenerAdapter() {
                    @Override
                    public void onOutput(OutputEvent event) throws Exception {
                        super.onOutput(event);
                        //Log.i("sendIRC Output",event.getRawLine());
                    }
                    @Override
                    public void onMessage(MessageEvent event){
                        //Log.i("sendIRC onMessage",event.toString());
                    }
                })
                .buildConfiguration();
        SenderBot = new PircBotX(configuration);//Create our bot with the configuration
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Connect to the server
                try {
                    SenderBot.startBot();
                    Log.i("SenderBot", "run: 연결됨");
                } catch (IOException | org.pircbotx.exception.IrcException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    */
/**
     * 기존에 연결된 채널에서 나가고, 새로운 채널에 연결.
     * @param Channel 새로 연결할 채널
     *//*


    public void ConnectChat(final String Channel){
        this.channel = Channel;
        bot.getUserChannelDao().getAllChannels().asList();
        final ImmutableSortedSet<org.pircbotx.Channel> Channels = bot.getUserChannelDao().getAllChannels();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(bot.isConnected() && SenderBot.isConnected()){
                    if(Channels.size() == 0){
                        Log.i("ConnectChat()", "run: 연결된 채널 없음.");
                        bot.send().joinChannel("#"+Channel);
                        SenderBot.send().joinChannel("#"+Channel);
                    }else{
                        Log.i("ConnectChat()", "run: 연결된 채널 있음.");
                        for(int i = 0; i < Channels.size(); i++){
                            String channel = Channels.asList().get(i).getName();
                            Log.i("ConnectChat()", "run: 연결 해제할 채널 : " + channel);
                            bot.sendRaw().rawLine("PART " + channel);
                            SenderBot.sendRaw().rawLine("PART " + channel);
                        }
                        bot.send().joinChannel("#"+Channel);
                        SenderBot.send().joinChannel("#"+Channel);
                    }
                }
            }
        }).start();
    }
}
*/
