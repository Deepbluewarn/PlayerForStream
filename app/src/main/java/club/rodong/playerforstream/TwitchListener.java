package club.rodong.playerforstream;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.ChannelInfoEvent;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.ExceptionEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.ListenerExceptionEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.ModeEvent;
import org.pircbotx.hooks.events.MotdEvent;
import org.pircbotx.hooks.events.NoticeEvent;
import org.pircbotx.hooks.events.OutputEvent;
import org.pircbotx.hooks.events.ServerResponseEvent;
import org.pircbotx.hooks.events.TopicEvent;
import org.pircbotx.hooks.events.UnknownEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import club.rodong.playerforstream.POJO.MessageObj;
import club.rodong.playerforstream.adapter.MessageAdapter;

public class TwitchListener extends ListenerAdapter {
    private MessageAdapter messageAdapter;
    //private Handler handler;
    private SharedPreferenceHelper SPH;
    private Context context;
    private RequestManager glide;
    private HashMap<String,String> MessageColor = new HashMap<>();

    public TwitchListener(MessageAdapter messageAdapter, SharedPreferenceHelper SPH, RequestManager glide, Context context) {
        this.messageAdapter = messageAdapter;
        //this.handler = handler;
        this.SPH = SPH;
        this.context = context;
        this.glide = glide;
    }

    @Override
    public void onMessage(final MessageEvent event) {
        Log.i("TwitchListener 메시지 수신", "onMessage: Tags : " + event.getTags() + ", Message : " + event.getMessage());
        String color = event.getTags().get("color");
        String UserNick = event.getUser().getNick();
        String emotes = event.getTags().get("emotes");
        String User_id = event.getTags().get("user-id");
        String id = event.getTags().get("id");
        String room_id = event.getTags().get("room-id");
        long tmi_send_ts = Long.parseLong(event.getTags().get("tmi-sent-ts"));
        String disp_name = event.getTags().get("display-name");
        String badges = event.getTags().get("badges");
        String bits = event.getTags().get("bits");
        String mod = event.getTags().get("mod");
        String message = event.getMessage();

        LinkedHashMap<Bitmap, String> emote_bitmaps = null;
        ArrayList<Bitmap> badge_bitmaps = null;
        if(emotes != null && !emotes.equals("")){
            emote_bitmaps = getEmoteBitmaps(emotes);
        }
        if(badges != null && !badges.equals("")){
            badge_bitmaps = getBadgeBitmaps(badges);
        }
        if(color != null && color.equals("")){
            color = getRandomColor(User_id);
        }


        if (!UserNick.equals("")) {
            if (!UserNick.equals(disp_name)) {
                final MessageObj messageObj = new MessageObj(
                        context.getString(R.string.Main_Chat), message, badges, badge_bitmaps, bits, color, disp_name + "(" + UserNick + ")" , emote_bitmaps, "", id, mod, room_id,tmi_send_ts,User_id,context.getString(R.string.COMMAND_PRIVMSG)
                );
                messageAdapter.add_chat(messageObj);
            } else {
                final MessageObj messageObj = new MessageObj(
                        context.getString(R.string.Main_Chat), message, badges, badge_bitmaps, bits, color, disp_name , emote_bitmaps, "", id, mod, room_id,tmi_send_ts,User_id,context.getString(R.string.COMMAND_PRIVMSG)
                );
                messageAdapter.add_chat(messageObj);
            }
        } else {
            MessageObj messageObj = new MessageObj(
                    context.getString(R.string.Main_Chat), message, badges, badge_bitmaps, bits, color, disp_name , emote_bitmaps, "", id, mod, room_id,tmi_send_ts,User_id,context.getString(R.string.COMMAND_PRIVMSG)
            );
            messageAdapter.add_chat(messageObj);
        }
    }

    @Override
    public void onAction(final ActionEvent event) {
        Log.i("TwitchListener 메시지 수신", "onAction : Tags : " + event.getTags() + ", Message : " + event.getMessage());

        String Msg_Color = event.getTags().get("color");
        String UserNick = event.getUser().getNick();
        String emotes = event.getTags().get("emotes");
        String User_id = event.getTags().get("user-id");
        String id = event.getTags().get("id");
        String room_id = event.getTags().get("room-id");
        long tmi_send_ts = Long.parseLong(event.getTags().get("tmi-sent-ts"));
        String disp_name = event.getTags().get("display-name");
        String badges = event.getTags().get("badges");
        String bits = event.getTags().get("bits");
        String mod = event.getTags().get("mod");
        String Msg = event.getMessage();

        LinkedHashMap<Bitmap, String> emote_bitmaps = null;
        ArrayList<Bitmap> badge_bitmaps = null;
        if(emotes != null && !emotes.equals("")){
            emote_bitmaps = getEmoteBitmaps(emotes);
        }
        if(badges != null && !badges.equals("")){
            badge_bitmaps = getBadgeBitmaps(badges);
        }

        MessageObj messageObj = new MessageObj(
                context.getString(R.string.Main_Chat), Msg, badges, badge_bitmaps, bits, Msg_Color, disp_name + "(" + UserNick + ")"  , emote_bitmaps, "", id, mod, room_id,tmi_send_ts,User_id,context.getString(R.string.COMMAND_ACTION)
        );
        messageAdapter.add_chat(messageObj);
    }

    @Override
    public void onServerResponse(ServerResponseEvent event) {
        Log.i("TwitchListener 메시지 수신", "RawLine : " + event.getRawLine());
    }

    @Override
    public void onMotd(MotdEvent event) {
        Log.i("bot onMotd()", event.getMotd());
    }

    @Override
    public void onJoin(JoinEvent event) {
        Log.i("TwitchListener 메시지 수신", "onJoin : toString : " + event.toString());
        if (event.getUserHostmask().getNick().equals(SPH.getTusername())) {
            String Channel = event.getChannel().getName();// ex ) #bluewarn
            String channel = event.getChannel().getName().substring(1, Channel.length());// remove "#"
            if(("#"+SPH.getLastJoinChannel()).equals(event.getChannel().getName())){
                messageAdapter.add_chat_event(context.getString(R.string.Alert_Chat),context.getString(R.string.COMMAND_PRIVMSG), event.getChannel().getName() + " 님 " + context.getString(R.string.Connected));
                SPH.setLastJoinChannel(channel);
            }else{
                messageAdapter.clear();
                messageAdapter.add_chat_event(context.getString(R.string.Alert_Chat), context.getString(R.string.COMMAND_PRIVMSG),event.getChannel().getName() + " 님 " + context.getString(R.string.Connected));
                SPH.setLastJoinChannel(channel);
            }
        }
    }

    @Override
    public void onMode(ModeEvent event) {
        Log.i("bot onMode()", event.getMode());
    }

    @Override
    public void onNotice(NoticeEvent event) {
        Log.i("TwitchListener 메시지 수신", "onNotice : Tags : " + event.getTags() + ", Message : " + event.getMessage());
    }

    @Override
    public void onOutput(OutputEvent event) {
        Log.i("bot onOutput()", event.getRawLine());
    }

    @Override
    public void onTopic(TopicEvent event) {
        Log.i("bot onTopic()", event.getTopic());
    }

    @Override
    public void onUnknown(UnknownEvent event) {
        String Command = event.getCommand();
        Log.i("TwitchListener 메시지 수신", "onUnknown : Command : " + event.getCommand() + ", toString : " + event.toString());
        if (Command.equals("ROOMSTATE")) {
            if (event.getTags().size() == 8) {
                //유저가 처음 채팅방에 들어온 경우. 채팅방의 전반적인 상태를 전달.
            }else if(event.getTags().containsKey("followers-only")){
                int followers_only = Integer.valueOf(event.getTags().get("followers-only"));
                if(followers_only == 10){
                    messageAdapter.add_chat_event(context.getString(R.string.Alert_Chat),
                            context.getString(R.string.COMMAND_ROOMSTATE),
                            context.getString(R.string.Followers_Only_1) + " 10분 " + context.getString(R.string.Followers_Only_2));
                }else if(followers_only == 30){
                    messageAdapter.add_chat_event(context.getString(R.string.Alert_Chat),
                            context.getString(R.string.COMMAND_ROOMSTATE),
                            context.getString(R.string.Followers_Only_1) + " 30분 " + context.getString(R.string.Followers_Only_2));
                }else if(followers_only == 60){
                    messageAdapter.add_chat_event(context.getString(R.string.Alert_Chat),
                            context.getString(R.string.COMMAND_ROOMSTATE),
                            context.getString(R.string.Followers_Only_1) + " 1시간 " + context.getString(R.string.Followers_Only_2));
                }else if(followers_only == 1440){
                    messageAdapter.add_chat_event(context.getString(R.string.Alert_Chat),
                            context.getString(R.string.COMMAND_ROOMSTATE),
                            context.getString(R.string.Followers_Only_1) + " 1일 " + context.getString(R.string.Followers_Only_2));
                }else if(followers_only == 10080){
                    messageAdapter.add_chat_event(context.getString(R.string.Alert_Chat),
                            context.getString(R.string.COMMAND_ROOMSTATE),
                            context.getString(R.string.Followers_Only_1) + " 1주일 " + context.getString(R.string.Followers_Only_2));
                }else if(followers_only == 43200){
                    messageAdapter.add_chat_event(context.getString(R.string.Alert_Chat),
                            context.getString(R.string.COMMAND_ROOMSTATE),
                            context.getString(R.string.Followers_Only_1) + " 1개월 " + context.getString(R.string.Followers_Only_2));
                }else if(followers_only == 129600){
                    messageAdapter.add_chat_event(context.getString(R.string.Alert_Chat),
                            context.getString(R.string.COMMAND_ROOMSTATE),
                            context.getString(R.string.Followers_Only_1) + " 3개월 " + context.getString(R.string.Followers_Only_2));
                }
            }else if(event.getTags().containsKey("slow")){
                //슬로우 모드..
                if(Integer.valueOf(event.getTags().get("slow")) == 0) {
                    messageAdapter.add_chat_event(context.getString(R.string.Alert_Chat),
                            context.getString(R.string.COMMAND_ROOMSTATE),
                            context.getString(R.string.Slow_Deactive));
                }else{
                    messageAdapter.add_chat_event(context.getString(R.string.Alert_Chat),
                            context.getString(R.string.COMMAND_ROOMSTATE),
                            context.getString(R.string.Slow_Mode_1) + " " + event.getTags().get("slow") + context.getString(R.string.Slow_Mode_2));
                }
            }
        }else if(Command.equals("CLEARCHAT")){
            String ban_duration_tag = event.getTags().get("ban-duration");
            String ban_reason_tag = event.getTags().get("ban-reason");
            String target_user_id_tag = event.getTags().get("target-user-id");
            if(target_user_id_tag != null) {
                messageAdapter.DeleteBanMsg(target_user_id_tag, context.getString(R.string.Chat_Deleted));
            }else if(ban_duration_tag == null && ban_reason_tag == null){
                //채팅 전체 삭제
                messageAdapter.clear();
                messageAdapter.add_chat_event(context.getString(R.string.Alert_Chat),
                        context.getString(R.string.COMMAND_CLEARCHAT), "매니저가 채팅을 삭제했습니다.");
            }
        }else if(Command.equals("HOSTTARGET")){

        }else if(Command.equals("RECONNECT")){

        }else if(Command.equals("USERNOTICE")){
            //구독, 구독 선물 등..
            ImmutableMap<String, String> tags = event.getTags();
            String badges = tags.get("badges");
            String color = tags.get("color");
            String message = "";
            if(event.getParsedLine().size() == 2){
                //구독 유저 메시지
                message = event.getParsedLine().get(1);
            }
            String display_name = tags.get("display-name");
            String emotes = tags.get("emotes");
            String id = tags.get("id");

            String mod = tags.get("mod");
            String msg_id = tags.get("msg-id");
            String msg_param_months = tags.get("msg-param-months");
            String msg_param_recipient_display_name = tags.get("msg-param-recipient-display-name");
            String msg_param_recipient_id = tags.get("msg-param-recipient-id");
            String msg_param_recipient_user_name = tags.get("msg-param-recipient-user-name");
            String msg_param_sub_plan = tags.get("msg-param-sub-plan");
            String msg_param_sub_plan_name = tags.get("msg-param-sub-plan-name");
            String room_id = tags.get("room-id");
            String system_msg = tags.get("system-msg");
            String tmi_send_ts = tags.get("tmi-sent-ts");
            String user_id = tags.get("user-id");

            LinkedHashMap<Bitmap, String> emote_bitmaps = null;
            ArrayList<Bitmap> badge_bitmaps = null;
            if(emotes != null && !emotes.equals("")){
                emote_bitmaps = getEmoteBitmaps(emotes);
            }
            if(badges != null && !badges.equals("")){
                badge_bitmaps = getBadgeBitmaps(badges);
            }
            if(color != null && color.equals("")){
                color = getRandomColor(user_id);
            }


            final MessageObj messageObj = new MessageObj(
                    context.getString(R.string.SubScribe_Chat), message, badges, badge_bitmaps, "", color, display_name ,
                    emote_bitmaps, "", id, mod, room_id,Long.valueOf(tmi_send_ts),
                    user_id,context.getString(R.string.COMMAND_USERNOTICE), msg_id,msg_param_months,msg_param_recipient_display_name,msg_param_recipient_id,
                    msg_param_recipient_user_name,msg_param_sub_plan,msg_param_sub_plan_name,system_msg
            );
            messageAdapter.add_chat(messageObj);
        }else if(Command.equals("USERSTATE")){

        }
    }
    @Override
    public void onException(ExceptionEvent event){
        //Log.i("bot onException()",event.getException().getLocalizedMessage());
        event.getException().printStackTrace();
    }
    @Override
    public void onListenerException(ListenerExceptionEvent event){
        Log.i("bot onListenerExcept()",event.getMessage());
    }


    /**
     * Twitch API 로 부터 배지를 받아온다.
     * Get Badges from Twitch API.
     * @param badges Twitch API 에서 받은 Tag 정보.
     * @return 비트맵 형식의 배지.
     */
    private ArrayList<Bitmap> getBadgeBitmaps(String badges){
        Log.i("getBadgeBitmaps", "실행됨");
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        String ChannelBadges = readJsonFromFile(context);//채널 배지
        String JsonAssetString = loadJSONFromAsset(context);//글로벌 배지
        JsonParser jsonParser = new JsonParser();
        JsonObject object = jsonParser.parse(JsonAssetString).getAsJsonObject();//Global Badges
        JsonObject CB_object = jsonParser.parse(ChannelBadges).getAsJsonObject();//Channel Badges
        String[] badge = badges.split(",");
        for(int i = 0; i < badge.length; i++) {
            final String[] a = badge[i].split("/");
            if(a[0].equals("subscriber")){
                String url = getBadgeUrl(CB_object,a[0],a[1]);
                try {
                    bitmaps.add(glide.applyDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)).asBitmap().load(url).submit().get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }else if(a[0].equals("bits")){
                Log.i("getBadgeBitmaps", "비트 배지 있음");
                //비트 배지의 경우, 채널 커스텀이 가능함. ChannelBadges 에 bits 객체가 있는지 확인.
                //Bits badges can be custom by Streamer. Check badge is custom.
                String url = "";
                JsonElement element = CB_object.get("badge_sets").getAsJsonObject().get(a[0]);
                if(element != null && element.getAsJsonObject().get("versions").getAsJsonObject().get(a[1]) != null){
                    //bits 객체에서 현재 번호에 해당하는 커스텀 배지가 있는지 확인.
                    //커스텀이 안되어있는 배지인 경우 기본 배지로 넘어감.
                    Log.i("getBadgeBitmaps custom", " a[0]: " + a[0] + " a[1] : " + a[1]);
                    url = getBadgeUrl(CB_object,a[0],a[1]);
                }else{
                    //커스텀 bits 배지가 아닌 경우.(기본 bits 배지)
                    //In case badges is not custom
                    Log.i("getBadgeBitmaps not cus", " a[0]: " + a[0] + " a[1] : " + a[1]);
                    url = getBadgeUrl(object,a[0],a[1]);
                }
                //Save it to ArrayList..
                try {
                    Log.i("getBadgeBitmaps", "requested url: " + url);
                    bitmaps.add(glide.applyDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)).asBitmap().load(url).submit().get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }else{
                //the other cases..
                String url = getBadgeUrl(object,a[0],a[1]);
                try {
                    bitmaps.add(glide.applyDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)).asBitmap().load(url).submit().get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.i("getBadgeBitmaps", " return bitmaps");
        return bitmaps;
    }
    private LinkedHashMap<Bitmap, String> getEmoteBitmaps(String emote_str){
        LinkedHashMap<Bitmap, String> map = new LinkedHashMap<>();
        if(!emote_str.equals("")){
            String[] emote_index = emote_str.split("/");
            for(int i = 0; i < emote_index.length; i++) {
                String [] emote = emote_index[i].split(":");
                String url = "http://static-cdn.jtvnw.net/emoticons/v1/" + emote[0] + "/3.0";
                try {
                    map.put(
                            glide.applyDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)).asBitmap().load(url).submit().get(),
                            emote[1]
                    );
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            return map;
        }else{
            return null;
        }
    }

    /**
     *
     * @param object 배지 목록이 있는 Json 객체
     * @param k 배지 유형
     * @param n 배지 넘버
     * @return 배지 url
     */
    private String getBadgeUrl(JsonObject object, String k, String n){
        return object
                .get("badge_sets").getAsJsonObject()
                .get(k).getAsJsonObject()
                .get("versions").getAsJsonObject()
                .get(n).getAsJsonObject()
                .get("image_url_2x").getAsString();
    }
    private String loadJSONFromAsset(Context context) {
        String json = "";
        try {
            InputStream is = context.getAssets().open("BadgeAPI_BETA.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    private String readJsonFromFile(Context context) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput("twitch_channel_badges.json");
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
    private String getRandomColor(String user_id){
        String[] color_arr = context.getResources().getStringArray(R.array.chat_name_color);
        String return_color = MessageColor.get(user_id);
        if(return_color != null){
            return return_color;
        }else{
            Random random = new Random();
            int r = random.nextInt(color_arr.length - 1);
            MessageColor.put(user_id,color_arr[r]);
            return color_arr[r];
        }
    }
}
