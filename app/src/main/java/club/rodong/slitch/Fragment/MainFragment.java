/*
package club.rodong.playerforstream.Fragment;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import club.rodong.playerforstream.FullscreenableChromeClient;
import club.rodong.playerforstream.JavascriptBridge;
import club.rodong.playerforstream.R;
import club.rodong.playerforstream.SharedPreferenceHelper;
import club.rodong.playerforstream.VideoEnabledWebChromeClient;
import club.rodong.playerforstream.VideoEnabledWebView;
import club.rodong.playerforstream.WebServer;

public class MainFragment extends Fragment {
    Map<String, String> extraHeaders;
    SharedPreferenceHelper SPH;
    private static final String DESKTOP_USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36";

    private ViewGroup sOldRootView = null;
    //private WebView player_webview;

    private WebView player_webview;
    //private VideoEnabledWebView player_webview;
    //private VideoEnabledWebChromeClient webChromeClient;

    private View decorView;
    private int uiOption;

    public MainFragment() {
        //setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreate", "MainFragment");
        init_WebServer();
        SPH = new SharedPreferenceHelper(getActivity());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("onSaveInstanceState()", "MainFragment outState : " + outState.toString());
        player_webview.saveState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("onActivityCreated", "MainFragment");


    }

    private void init_WebServer() {
        WebServer server;
        server = new WebServer();
        try {
            server.start();
        } catch (IOException ioe) {
            Log.w("Httpd", ioe.getLocalizedMessage());
            Toast.makeText(getActivity(), getString(R.string.WebServer_err), Toast.LENGTH_SHORT).show();
        }
        Log.w("Httpd", "Web server initialized.");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.twitch_video_one, container, false);
        WebView webView = v.findViewById(R.id.player);
        Button fullscreen_btn = v.findViewById(R.id.fsbtn);
        Button chat_btn = v.findViewById(R.id.chat);

        ImageView user_icon = v.findViewById(R.id.icon);
        TextView disp_name = v.findViewById(R.id.name);
        TextView title_tv = v.findViewById(R.id.title);

        String name = getArguments().getString("name");
        String title = getArguments().getString("title");
        //Bitmap icon = getArguments().getParcelable("icon");

        //user_icon.setImageBitmap(icon);
        disp_name.setText(name);
        title_tv.setText(title);

        fullscreen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE) {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                } else {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                }
            }
        });
        chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (savedInstanceState == null) {
            WebView.setWebContentsDebuggingEnabled(true);
            player_webview = webView;
            WebSettings webSettings = player_webview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSupportZoom(false);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            extraHeaders = new HashMap<>();
            String channel = getArguments().getString("channel");
            extraHeaders.put("channel", channel);
            //player_webview.setWebChromeClient(new FullscreenableChromeClient(getActivity()));
            player_webview.addJavascriptInterface(new JavascriptBridge(player_webview), "TwitchPlayer");
            player_webview.getSettings().setUserAgentString(DESKTOP_USER_AGENT);
            player_webview.loadUrl("http://localhost:8800", extraHeaders);
            //player_webview.loadUrl("https://player.twitch.tv/?channel=lucia94&muted=true");
        } else {
            ViewGroup mainLayout = (ViewGroup) v;
            mainLayout.removeView(webView);
            if (sOldRootView != null) {
                sOldRootView.removeView(player_webview);
            }
            mainLayout.addView(player_webview);
        }
        sOldRootView = (ViewGroup) v;
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //setFullScreen();
    }

    */
/**
     * WebView 에 있는 Twitch Player 의 채널을 JavaScript 를 통해 변경.
     *//*

    public void resetChannel(String Channel) {
        player_webview.loadUrl("javascript:SetChannel('" + Channel + "')");
    }

    private void setFullScreen() {
        Log.i("setFullScreen", "실행");
        if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT) {

        } else {


             = getActivity().getWindow().getDecorView();
            uiOption = getActivity().getWindow().getDecorView().getSystemUiVisibility();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

            decorView.setSystemUiVisibility(uiOption);
        }
    }
}*/
