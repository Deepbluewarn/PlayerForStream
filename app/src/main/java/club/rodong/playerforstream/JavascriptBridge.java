package club.rodong.playerforstream;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;

import club.rodong.playerforstream.activity.Stream_Player_Activity;

/**
 * Communicate with Twitch Embed Javascript API
 */
public class JavascriptBridge {
    private WebView webView;
    private ConstraintLayout overlay;
    private ProgressBar progressBar;
    private Button play_or_pause_btn;
    private Button setting_btn;
    private Context context;

    private SharedPreferenceHelper SPH;

    public JavascriptBridge(WebView webview, ConstraintLayout overlay, ProgressBar progressBar, SharedPreferenceHelper SPH, Context context) {
        this.webView = webview;
        this.overlay = overlay;
        this.progressBar = progressBar;
        this.play_or_pause_btn = overlay.findViewById(R.id.playorpause);
        this.setting_btn = overlay.findViewById(R.id.setting_btn);
        this.context = context;
        this.SPH = SPH;
    }
    @JavascriptInterface
    public void playerEnded(){
        Log.i("playerEnded", "실행 JavascriptBridge");
    }
    @JavascriptInterface
    public void playerPause(){
        ((Stream_Player_Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //icon 설정 (pause -> play)
                play_or_pause_btn.setBackground(ContextCompat.getDrawable(context, R.drawable.t_playbtn));
                play_or_pause_btn.setTag("play");
            }
        });
        Log.i("playerPause", "실행 JavascriptBridge");
    }
    @JavascriptInterface
    public void playerPlay(){
        progressBar.setVisibility(View.VISIBLE);
    }
    @JavascriptInterface
    public void playerPlayback_Blocked(){

    }
    @JavascriptInterface
    public void playerPlaying(final String quality){
        ((Stream_Player_Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                webView.setVisibility(View.VISIBLE);
                overlay.setVisibility(View.VISIBLE);
                play_or_pause_btn.setBackground(ContextCompat.getDrawable(context, R.drawable.t_pausebtn));
                play_or_pause_btn.setTag("pause");
            }
        });
    }
    @JavascriptInterface
    public void playerOffline(){

    }
    @JavascriptInterface
    public void playerOnline(){

    }
    @JavascriptInterface
    public void playerReady(){

    }
    @JavascriptInterface
    public void setQualities(String json) {
        SPH.setQualities(json);
        ((Stream_Player_Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setting_btn.setVisibility(View.VISIBLE);
            }
        });
    }
}
