package club.rodong.slitch.POJO;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import lombok.Data;

public @Data class MessageObj {
    private String viewType;

    //PRIVMSG
    private String badges;
    private String bits;//
    private String color;
    private String display_name;
    //private String emotes;//
    private String id;
    private String message;
    private String mod;
    private String room_id;
    private long tmi_send_ts;
    private String user_id;

    private String commandType;
    LinkedHashMap<Bitmap, String> emote_bitmaps = null;
    ArrayList<Bitmap> badge_bitmaps = null;

    private String flags;

    //USERNOTICE
    private String msg_id;
    private String msg_param_months;
    private String msg_param_recipient_display_name;
    private String msg_param_recipient_id;
    private String msg_param_recipient_user_name;
    private String msg_param_sub_plan;
    private String msg_param_sub_plan_name;
    private String system_msg;

    //Deleted
    private boolean isdeleted = false;

    public MessageObj(String viewType, String message, String badges, ArrayList<Bitmap> badge_bitmaps, String bits, String color, String display_name,
                      LinkedHashMap<Bitmap, String> emote_bitmaps, String flags , String id , String mod , String room_id ,
                      long tmi_send_ts , String user_id , String commandType){
        setViewType(viewType);
        setMessage(message);
        setBadges(badges);
        setBadge_bitmaps(badge_bitmaps);
        setBits(bits);
        setColor(color);
        setDisplay_name(display_name);
        //setEmotes(emotes);
        setEmote_bitmaps(emote_bitmaps);
        setFlags(flags);
        setId(id);
        setMod(mod);
        setRoom_id(room_id);
        setTmi_send_ts(tmi_send_ts);
        setUser_id(user_id);
        setCommandType(commandType);
    }
    public MessageObj(String viewType, String message, String badges, ArrayList<Bitmap> badge_bitmaps, String bits, String color, String display_name,
                      LinkedHashMap<Bitmap, String> emote_bitmaps, String flags , String id , String mod , String room_id ,
                      long tmi_send_ts , String user_id , String commandType,
                      String msg_id, String msg_param_months, String msg_param_recipient_display_name, String msg_param_recipient_id, String msg_param_recipient_user_name,
                      String msg_param_sub_plan, String msg_param_sub_plan_name, String system_msg){
        setViewType(viewType);
        setMessage(message);
        setBadges(badges);
        setBadge_bitmaps(badge_bitmaps);
        setBits(bits);
        setColor(color);
        setDisplay_name(display_name);
        //setEmotes(emotes);
        setEmote_bitmaps(emote_bitmaps);
        setFlags(flags);
        setId(id);
        setMod(mod);
        setRoom_id(room_id);
        setTmi_send_ts(tmi_send_ts);
        setUser_id(user_id);
        setCommandType(commandType);
        setMsg_id(msg_id);
        setMsg_param_months(msg_param_months);
        setMsg_param_recipient_display_name(msg_param_recipient_display_name);
        setMsg_param_recipient_id(msg_param_recipient_id);
        setMsg_param_recipient_user_name(msg_param_recipient_user_name);
        setMsg_param_sub_plan(msg_param_sub_plan);
        setMsg_param_sub_plan_name(msg_param_sub_plan_name);
        setSystem_msg(system_msg);
    }
    public MessageObj(String viewType, String message, String CommandType){
        setMessage(message);
        setViewType(viewType);
        setCommandType(CommandType);
    }
}
