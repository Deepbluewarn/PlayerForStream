package club.rodong.playerforstream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

public class SharedPreferenceHelper {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;
    private Resources resources;
    public SharedPreferenceHelper(Context c) {
        prefs = PreferenceManager.getDefaultSharedPreferences(c);
        context = c;
        resources = context.getResources();
    }

    /**
     * true if app first started, false if app is not first start.
     * @return
     */
    public boolean get_start_first(){
        return prefs.getBoolean(resources.getString(R.string.first_start), true);
    }
    public void set_start_first(boolean isfirst){
        editor = prefs.edit();
        editor.putBoolean(resources.getString(R.string.first_start), isfirst);
        editor.apply();
    }

    /**
     * get Login status
     * @return
     */
    public boolean getLogin(){
        return prefs.getBoolean(resources.getString(R.string.t_islogin), false);
    }
    public void setLogin(boolean login){
        editor = prefs.edit();
        editor.putBoolean(resources.getString(R.string.t_islogin),login);
        editor.apply();
    }
    /**
     * Twitch Username
     * @return
     */
    public String getTusername(){
        return prefs.getString(resources.getString(R.string.t_username),"");
    }
    public void setTusername(String username){
        editor = prefs.edit();
        editor.putString(resources.getString(R.string.t_username),username);
        editor.apply();
    }

    /**
     * Twitch User ID.
     * @return int ID
     */
    public int getTwitchid(){
        return prefs.getInt(resources.getString(R.string.t_id),0);
    }

    /**
     * Twitch User ID
     * @param id
     */
    public void setTwitchid(int id){
        editor = prefs.edit();
        editor.putInt(resources.getString(R.string.t_id),id);
        editor.apply();
    }

    /**
     * Get Twitch API access token
     * @return access token
     */
    public String get_access_token(){
        return prefs.getString(resources.getString(R.string.access_token),"");
    }

    /**
     * Set Twitch API access token
     * @param token access token
     */
    public void set_access_token(String token){
        editor = prefs.edit();
        editor.putString(resources.getString(R.string.access_token),token);
        editor.apply();
    }

    /**
     * Using for AlertDialog to mark last user choice
     * @return
     */
    public int get_t_size_num(){
        return prefs.getInt(resources.getString(R.string.t_size_num),0);
    }
    public void set_t_size_num(int num){
        editor = prefs.edit();
        editor.putInt(resources.getString(R.string.t_size_num),num);
        editor.apply();
    }

    /**
     * 트위치 썸네일 가로 크기를 가져온다.
     * @return Thumbnail width.
     */
    public int get_t_width(){
        return prefs.getInt(resources.getString(R.string.t_width),0);
    }

    /**
     * 트위치 썸네일 세로 크기를 설정한다.
     * set Twitch Thumbnail width
     * @param width Thumbnail width.
     */
    public void set_t_width(int width){
        editor = prefs.edit();
        editor.putInt(resources.getString(R.string.t_width),width);
        editor.apply();
    }
    /**
     * 트위치 썸네일 세로 크기를 가져온다.
     * @return Thumbnail height.
    */
    public int get_t_height(){
        return prefs.getInt(resources.getString(R.string.t_height),0);
    }
    /**
     * 트위치 썸네일 세로 크기를 설정한다.
     * set Twitch Thumbnail height
     * @param height Thumbnail height.
     */
    public void set_t_height(int height){
        editor = prefs.edit();
        editor.putInt(resources.getString(R.string.t_height),height);
        editor.apply();
    }

    /**
     * 마지막으로 시청한 트위치 방송 채널 이름.
     * Last played channel name
     * TwitchListener 의 onJoin() 메소드에서 설정 됨.
     * @return String Channel Name.
     */
    public String getLastJoinChannel(){
        return prefs.getString(resources.getString(R.string.t_last_join_channel),"");
    }
    public void setLastJoinChannel(String channel){
        editor = prefs.edit();
        editor.putString(resources.getString(R.string.t_last_join_channel),channel);
        editor.apply();
    }

    /**
     * Get Stream Qualities List by String. From Twitch Embed Player Javascript API
     * @return
     */
    public String getQualities(){
        return prefs.getString(resources.getString(R.string.t_stream_qualities),"");
    }
    public void setQualities(String json){
        editor = prefs.edit();
        editor.putString(resources.getString(R.string.t_stream_qualities),json);
        editor.apply();
    }
    public int getTheme(){
        return prefs.getInt(resources.getString(R.string.theme_num),2);
    }
    public void setTheme(int theme_num){
        editor = prefs.edit();
        editor.putInt(resources.getString(R.string.theme_num),theme_num);
        editor.apply();
    }

}
