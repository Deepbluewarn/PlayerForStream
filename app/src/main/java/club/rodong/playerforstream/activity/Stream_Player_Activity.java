
package club.rodong.playerforstream.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.pircbotx.Configuration;
import org.pircbotx.MultiBotManager;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.EnableCapHandler;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.NoticeEvent;
import org.pircbotx.hooks.events.OutputEvent;
import org.pircbotx.hooks.managers.BackgroundListenerManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import club.rodong.playerforstream.Fragment.EmoteFragment;
import club.rodong.playerforstream.GlideApp;
import club.rodong.playerforstream.JavascriptBridge;
import club.rodong.playerforstream.POJO.Twitch_New_Get_Streams;
import club.rodong.playerforstream.POJO.Twitch_Webhook_Body_Object;
import club.rodong.playerforstream.POJO.Twitch_v5_Get_Stream_By_User;
import club.rodong.playerforstream.R;
import club.rodong.playerforstream.RetrofitHelper;
import club.rodong.playerforstream.SharedPreferenceHelper;
import club.rodong.playerforstream.TwitchListener;
import club.rodong.playerforstream.WebServer;
import club.rodong.playerforstream.adapter.EmoteAdapter;
import club.rodong.playerforstream.adapter.EmotePagerAdapter;
import club.rodong.playerforstream.adapter.MessageAdapter;
import club.rodong.playerforstream.databinding.ActivityPlayerBinding;
import club.rodong.playerforstream.interfaces.Twitch_API;
import club.rodong.playerforstream.interfaces.Twitch_Badges;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Stream_Player_Activity extends AppCompatActivity {
    private static final String TAG = "Stream_Player_Activity";
    ActivityPlayerBinding mBinding;

    final int ANIM_DURATION = 250;
    private static final String DESKTOP_USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36";

    String channel = null;
    int ID = 0;
    int EmotePage;

    ConstraintLayout player_container;
    ConstraintLayout chat_layout;
    ConstraintLayout webview_overlay;
    ConstraintLayout stream_detail;

    ProgressBar progressBar;

    Button playorpause_btn;
    Button fullscreen_btn;
    Button setting_btn;
    Button back_btn;
    Button chat_onoff_btn;
    TextView streamer_name_overlay_tv;
    TextView streamer_name_tv;
    TextView title_tv;
    TextView stream_category_tv;
    ImageView streamer_icon_iv;
    ImageButton emotebtn;
    ImageButton sendbtn;
    boolean overlay = false;
    boolean isFullScreen = false;
    boolean isdetail_show = false;
    boolean isemotefrag_show = false;
    boolean ischat_show = true;
    boolean isBotStarted = false;

    ConstraintSet constraintSet = new ConstraintSet();

    WebView player_webview;
    RecyclerView MessageList;
    EditText chat_edittext;
    FrameLayout emote_container;
    MessageAdapter messageAdapter;
    LinearLayoutManager linearLayoutManager;
    EmotePagerAdapter pagerAdapter;

    final MultiBotManager manager = new MultiBotManager();
    TwitchListener myListener;
    ViewPager viewPager;

    SharedPreferenceHelper SPH;

    Handler handler;
    BroadcastReceiver mMessageReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SPH = new SharedPreferenceHelper(this);
        switch (SPH.getTheme()){
            case 0:
                setTheme(R.style.CustomTheme_1);
                break;
            case 1:
                setTheme(R.style.CustomTheme_2);
                break;
            case 2:
                setTheme(R.style.CustomTheme_3);
                break;
        }
        super.onCreate(savedInstanceState);
        Log.i("onCreate", "Stream_Player_Activity: 실행");
        Fabric.with(this, new Crashlytics());
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_player);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("ACTION_STRING_ACTIVITY"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler = new Handler();
                Looper.loop();
            }
        }).start();

        //스크린 회전상태 확인.
        if(getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE){
            setFullScreen();
        }else if(getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT){
            isFullScreen = false;
        }
        player_container = mBinding.playerContainer;
        chat_layout = mBinding.chatLayout;
        chat_edittext = mBinding.chatEdittext;
        emote_container = mBinding.emoteSelector;
        player_webview = mBinding.player;
        webview_overlay = mBinding.webviewOverlay;
        stream_detail = mBinding.detail;
        progressBar = mBinding.progressBar;
        playorpause_btn = mBinding.playorpause;
        fullscreen_btn = mBinding.rotate;
        setting_btn = mBinding.settingBtn;
        back_btn = mBinding.backBtn;
        chat_onoff_btn = mBinding.chatOnoff;
        streamer_name_overlay_tv = mBinding.streamerName;
        streamer_name_tv = mBinding.detailStreamerName;
        title_tv = mBinding.detailStreamTitle;
        stream_category_tv = mBinding.detailStreamCategory;
        streamer_icon_iv = mBinding.streamerIcon;
        emotebtn = mBinding.emotebtn;
        sendbtn = mBinding.sendbtn;
        MessageList = mBinding.messageList;

        linearLayoutManager = new LinearLayoutManager(this);
        messageAdapter = new MessageAdapter(this, MessageList, SPH);
        viewPager = findViewById(R.id.EmoteviewPager);
        MessageList.setAdapter(messageAdapter);
        MessageList.setLayoutManager(linearLayoutManager);
        //RecyclerView 추가 설정..
        //MessageList.setHasFixedSize(true);
        MessageList.setItemViewCacheSize(1000);
        MessageList.setDrawingCacheEnabled(true);
        MessageList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        MessageList.setItemAnimator(null);

        if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {
            Uri uri = getIntent().getData();
            channel = uri.getQueryParameter("channel");
            ID = Integer.valueOf(uri.getQueryParameter("id"));
        }

        OverlayToDelayInvisible();
        webview_overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v != null){
                    if(isemotefrag_show){
                        hideEmoteFragment();
                    }
                    if (overlay) {
                        OverlayToInvisible();
                    } else {
                        OverlayToVisible();
                        OverlayToDelayInvisible();
                    }
                    player_webview.loadUrl("javascript:player.setMuted(false)");
                }
            }
        });
        init_WebServer();

        fullscreen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v != null && overlay){
                    if(isFullScreen){
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
                        setNonFullScreen();
                    }else{
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
                        setFullScreen();
                    }
                }
            }
        });
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v != null && overlay){
                    List<String> listItems = new ArrayList<>();

                    JsonParser jsonParser = new JsonParser();
                    JsonElement element = jsonParser.parse(SPH.getQualities());
                    final JsonArray array = element.getAsJsonArray();
                    for(int i = 0; i < array.size(); i++){
                        listItems.add(array.get(i).getAsJsonObject().get("name").getAsString());
                    }
                    final CharSequence[] items = listItems.toArray(new CharSequence[0]);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Stream_Player_Activity.this);
                    builder
                            .setTitle("화질을 선택하세요")
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialogInterface) {
                                    if(isFullScreen){
                                        setFullScreen();
                                    }
                                }
                            })
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    if(isFullScreen){
                                        setFullScreen();
                                    }
                                }
                            })
                            .setItems(items, new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int index) {
                                    //Toast.makeText(Stream_Player_Activity.this, array.get(index).getAsJsonObject().get("group").getAsString(), Toast.LENGTH_LONG).show();
                                    player_webview.loadUrl("javascript:player.setQuality('" + array.get(index).getAsJsonObject().get("group").getAsString() +"')");
                                    if(isFullScreen){
                                        setFullScreen();
                                    }

                                }
                            });
                    AlertDialog dialog = builder.create();    // 알림창 객체 생성
                    dialog.show();    // 알림창 띄우기
                }
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        emotebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                if(isemotefrag_show){
                    hideEmoteFragment();
                }else{
                    showEmoteFragment();
                }
            }
        });

        chat_onoff_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getVisibility() == View.VISIBLE){
                    if(ischat_show){
                        hideChat();
                    }else{
                        showChat();
                    }
                }
            }
        });

        //Firebase FCM Data 수신
        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra("msg");
                Log.i("Stream_Player_Activity", "FCM onReceive: " + bundle.get("msgBody"));
            }
        };

        myListener = new TwitchListener(messageAdapter, SPH, GlideApp.with(this), this);

        //WebView 세팅
        WebSettings webSettings = player_webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        Map<String, String> extraHeaders = new HashMap<>();
        if(channel != null){
            extraHeaders.put("channel", channel);
        }
        WebView.setWebContentsDebuggingEnabled(true);
        player_webview.addJavascriptInterface(new JavascriptBridge(player_webview, webview_overlay, progressBar, SPH, this), "TwitchPlayer");
        player_webview.getSettings().setUserAgentString(DESKTOP_USER_AGENT);
        player_webview.loadUrl("http://localhost:8800", extraHeaders);

        playorpause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v != null && v.getTag() != null && overlay){
                    if (v.getTag().equals("play")) {
                        player_webview.loadUrl("javascript:play()");
                    } else if (v.getTag().equals("pause")) {
                        player_webview.loadUrl("javascript:pause()");
                    }
                }
            }
        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = chat_edittext.getText().toString();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(channel != null && !text.equals("")){
                            manager.getBots().asList().get(1).send().message("#" + channel, text);
                        }
                    }
                });
                chat_edittext.setText("");
            }
        });

        chat_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Stream_Player_Activity", "onFocusChange: isemoteFrag_show : " + isemotefrag_show);
                if(isemotefrag_show){
                    hideEmoteFragment();
                }
            }
        });

        chat_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
        chat_edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    final String text = v.getText().toString();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(channel != null && !text.equals("")){
                                manager.getBots().asList().get(1).send().message("#" + channel, text);
                            }
                        }
                    });
                    chat_edittext.setText("");
                }
                return false;
            }
        });
        if(ID != 0){
            //final DBHelper db = new DBHelper(this);
            final Twitch_API twitch_API = RetrofitHelper.getRetrofit_Json(getString(R.string.twitch_BaseUrl)).create(Twitch_API.class);
            Call<Twitch_v5_Get_Stream_By_User> getStreamByUserCall = twitch_API.Twitch_v5_Get_Stream_By_User("application/vnd.twitchtv.v5+json", getResources().getString(R.string.twitch_client_id), ID);
            getStreamByUserCall.enqueue(new Callback<Twitch_v5_Get_Stream_By_User>() {
                @Override
                public void onResponse(Call<Twitch_v5_Get_Stream_By_User> call, Response<Twitch_v5_Get_Stream_By_User> response) {
                    Twitch_v5_Get_Stream_By_User twitch = response.body();
                    if(twitch != null && twitch.getStream() != null){
                        String title = twitch.getStream().getChannel().getStatus();
                        String iconUrl = twitch.getStream().getChannel().getLogo();
                        String name = twitch.getStream().getChannel().getDisplayName();
                        String category = twitch.getStream().getChannel().getGame();
                        title_tv.setText(title);
                        GlideApp.with(Stream_Player_Activity.this)
                                .load(iconUrl)
                                .into(streamer_icon_iv);
                        streamer_name_overlay_tv.setText(name);
                        streamer_name_tv.setText(name);

                        stream_category_tv.setText(category);
                    }else{
                        Toast.makeText(Stream_Player_Activity.this, getResources().getString(R.string.E_404), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Twitch_v5_Get_Stream_By_User> call, Throwable t) {
                    Log.i(TAG, "onFailure: Twitch_v5_Get_Stream_By_User 실패 " + t.getLocalizedMessage());
                }
            });
            final Twitch_Badges twitch_badges = RetrofitHelper.getRetrofit_String(getString(R.string.twitch_subscriber_badges)).create(Twitch_Badges.class);
            Call<String> subscriber_badges_call = twitch_badges.Get_Badges(ID);
            subscriber_badges_call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String twitch = response.body();
                    writeFile(twitch, "twitch_channel_badges.json");
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("twitch_badges", "onFailure: " + t.getLocalizedMessage());
                }
            });

            Twitch_API twitch_api = RetrofitHelper.getRetrofit_String(getString(R.string.twitch_BaseUrl)).create(Twitch_API.class);
            Call<String> user_emotes = twitch_api.Twitch_v5_Get_User_Emotes("application/vnd.twitchtv.v5+json",
                    "5s8icgxpodxmo6oajt6nk20x2q6yrc","OAuth " + SPH.get_access_token(), SPH.getTwitchid());
            user_emotes.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    int emote_sets_count;
                    String twitch = response.body();
                    writeFile(twitch, "twitch_user_emotes.json");
                    JsonParser jsonParser = new JsonParser();
                    if (twitch != null) {
                        JsonObject object = jsonParser.parse(twitch).getAsJsonObject();//UserEmotes
                        Set<Map.Entry<String, JsonElement>> entries = object.get("emoticon_sets").getAsJsonObject().entrySet();
                        emote_sets_count = entries.size();
                        pagerAdapter = new EmotePagerAdapter(getSupportFragmentManager(), emote_sets_count);
                        viewPager.setAdapter(pagerAdapter);

                        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.EmoteviewPager + ":" + 0);
                        EmoteFragment fragment = (EmoteFragment) page;
                        fragment.addEmotes(0);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
            viewPager.setOffscreenPageLimit(5);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    EmotePage = position;
                }
                @Override
                public void onPageSelected(int position) {}
                @Override
                public void onPageScrollStateChanged(int state) {
                    if(state == 0){
                        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.EmoteviewPager + ":" + EmotePage);
                        EmoteFragment fragment = (EmoteFragment) page;
                        fragment.addEmotes(EmotePage);
                    }
                }
            });

            //Twitch Webhook Subscribe POST Request..
            Twitch_Webhook_Body_Object object = new Twitch_Webhook_Body_Object("https://us-central1-twitchwebhook.cloudfunctions.net/SimpleTwitchEndPoint",
                    "subscribe","https://api.twitch.tv/helix/streams?user_id=" + ID,864000);

            Call<String> webhook_subscribe = twitch_API.Request_Subscribe("application/json","5s8icgxpodxmo6oajt6nk20x2q6yrc", object);
            webhook_subscribe.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {}
                @Override
                public void onFailure(Call<String> call, Throwable t) {}
            });

            //Firebase 구독은 한 Topic 에만..
            //Firebase 구독 전체 해제.
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        FirebaseInstanceId.getInstance().deleteInstanceId();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            //Firebase 구독.
            FirebaseMessaging.getInstance().subscribeToTopic(String.valueOf(ID)).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    //Toast.makeText(getApplicationContext(), "Firebase Topic : " + ID, Toast.LENGTH_SHORT).show();
                }
            });
        }
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( Stream_Player_Activity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);
            }
        });
    }
    public void appendEditText(String text){
        chat_edittext.append(text);
    }

    @Override
    public void onBackPressed(){
        if(isemotefrag_show){
            hideEmoteFragment();
        }else{
            super.onBackPressed();
        }
    }

    private void setFullScreen() {
        isFullScreen = true;
        int uiOption;
        View decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOption);
    }

    private void setNonFullScreen() {
        isFullScreen = false;
        int uiOption;
        View decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        uiOption &= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        uiOption &= ~View.SYSTEM_UI_FLAG_FULLSCREEN;
        uiOption &= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOption);
    }

    private void OverlayToDelayInvisible() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (overlay) {
                    Log.i("OverlayToDelayInvisible", "onClick: 실행");

                    AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.0f);
                    animation1.setDuration(ANIM_DURATION);
                    animation1.setFillAfter(true);
                    webview_overlay.startAnimation(animation1);

                    hideComponents();

                    overlay = false;
                }
            }
        }, 10000);// 10초 정도 딜레이를 준 후 시작
    }

    private void OverlayToInvisible() {
        AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.0f);
        animation1.setDuration(ANIM_DURATION);
        animation1.setFillAfter(true);
        webview_overlay.startAnimation(animation1);
        hideComponents();
        overlay = false;
    }

    private void OverlayToVisible() {
        AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(ANIM_DURATION);
        animation1.setFillAfter(true);
        webview_overlay.startAnimation(animation1);
        showComponents();
        overlay = true;
    }

    private void showChat(){
        ischat_show = true;
        constraintSet.clone(player_container);

        Transition transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        transition.setDuration(250);

        TransitionManager.beginDelayedTransition(player_container,transition);

        constraintSet.setDimensionRatio(chat_layout.getId(), "H,16:9");
        constraintSet.applyTo(player_container);
    }
    private void hideChat(){
        ischat_show = false;
        constraintSet.clone(player_container);

        Transition transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        transition.setDuration(250);

        TransitionManager.beginDelayedTransition(player_container,transition);

        constraintSet.setDimensionRatio(chat_layout.getId(), "H,16:0");
        constraintSet.applyTo(player_container);
    }


    private void showEmoteFragment(){
        isemotefrag_show = true;

        constraintSet.clone(player_container);

        Transition transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        transition.setDuration(250);

        TransitionManager.beginDelayedTransition(player_container,transition);

        constraintSet.constrainHeight(emote_container.getId(), 600);
        constraintSet.applyTo(player_container);
    }
    private void hideEmoteFragment(){
        isemotefrag_show = false;
        constraintSet.clone(player_container);

        Transition transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        transition.setDuration(250);

        TransitionManager.beginDelayedTransition(player_container,transition);

        constraintSet.constrainHeight(emote_container.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.applyTo(player_container);
    }

    private void showComponents(){
        isdetail_show = true;
        constraintSet.clone(player_container);

        Transition transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        transition.setDuration(250);

        TransitionManager.beginDelayedTransition(player_container,transition);

        constraintSet.constrainHeight(stream_detail.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.applyTo(player_container);
    }

    private void hideComponents(){
        isdetail_show = false;
        constraintSet.clone(player_container);

        Transition transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        transition.setDuration(250);

        TransitionManager.beginDelayedTransition(player_container,transition);

        constraintSet.constrainHeight(stream_detail.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.applyTo(player_container);
    }

    /**
     * 레이아웃 설정.
     * @param orientation true : landscape, false portrait.
     */
    private void init_layout(Boolean orientation){
        ConstraintLayout.LayoutParams layoutParams_webview;
        ConstraintLayout.LayoutParams layoutParams_chat_layout;
        ConstraintLayout.LayoutParams layoutParams_edit_text;
        ConstraintLayout.LayoutParams layoutParams_webview_overlay;
        ConstraintLayout.LayoutParams layoutParams_detail;
        ConstraintLayout.LayoutParams layoutParams_emote_selector;

        if(orientation){
            //landscape
            chat_onoff_btn.setVisibility(View.VISIBLE);
            layoutParams_webview = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT);
            layoutParams_webview.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_webview.endToStart = R.id.chat_layout;
            layoutParams_webview.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_webview.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            player_webview.setLayoutParams(layoutParams_webview);

            layoutParams_webview_overlay = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT);
            layoutParams_webview_overlay.bottomToTop = R.id.detail;
            layoutParams_webview_overlay.endToStart = R.id.chat_layout;
            layoutParams_webview_overlay.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_webview_overlay.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            webview_overlay.setLayoutParams(layoutParams_webview_overlay);

            layoutParams_chat_layout = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT);
            layoutParams_chat_layout.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_chat_layout.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_chat_layout.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_chat_layout.dimensionRatio = "H,16:9";
            chat_layout.setLayoutParams(layoutParams_chat_layout);

            layoutParams_detail = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT);
            layoutParams_detail.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_detail.endToStart = R.id.chat_layout;
            layoutParams_detail.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            stream_detail.setLayoutParams(layoutParams_detail);

            layoutParams_edit_text = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT);
            layoutParams_edit_text.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_edit_text.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_edit_text.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_edit_text.topToBottom = R.id.message_list;
            chat_edittext.setLayoutParams(layoutParams_edit_text);

            layoutParams_emote_selector = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT);
            layoutParams_emote_selector.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_emote_selector.endToStart = R.id.chat_layout;
            layoutParams_emote_selector.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            emote_container.setLayoutParams(layoutParams_emote_selector);

        }else{
            //portraite
            chat_onoff_btn.setVisibility(View.GONE);
            layoutParams_webview = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT);
            layoutParams_webview.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_webview.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_webview.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_webview.dimensionRatio = "H,16:9";
            player_webview.setLayoutParams(layoutParams_webview);

            layoutParams_webview_overlay = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT);
            layoutParams_webview_overlay.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_webview_overlay.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_webview_overlay.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_webview_overlay.dimensionRatio = "H,16:9";
            webview_overlay.setLayoutParams(layoutParams_webview_overlay);

            layoutParams_chat_layout = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT);
            layoutParams_chat_layout.bottomToTop = R.id.emote_selector;
            layoutParams_chat_layout.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_chat_layout.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_chat_layout.topToBottom = R.id.player;
            chat_layout.setLayoutParams(layoutParams_chat_layout);

            layoutParams_detail = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT);
            layoutParams_detail.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_detail.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_detail.topToBottom = R.id.webview_overlay;
            stream_detail.setLayoutParams(layoutParams_detail);

            layoutParams_emote_selector = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT);
            layoutParams_emote_selector.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_emote_selector.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams_emote_selector.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            emote_container.setLayoutParams(layoutParams_emote_selector);
        }
    }
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void connectIRC() {
        String name = SPH.getTusername();
        String token = SPH.get_access_token();
        if(name != null && token != null && !name.equals("") && !token.equals("") && channel != null){
            String chl = "#" + channel;
            BackgroundListenerManager backgroundListenerManager = new BackgroundListenerManager();
            backgroundListenerManager.addListener(myListener, true);
            Configuration ReceiverBot_conf = new Configuration.Builder()
                    .setCapEnabled(true)
                    .setOnJoinWhoEnabled(false)
                    .addCapHandler(new EnableCapHandler("twitch.tv/tags"))
                    .addCapHandler(new EnableCapHandler("twitch.tv/membership"))
                    .addCapHandler(new EnableCapHandler("twitch.tv/commands"))
                    .setServerPassword("oauth:" + token)
                    .setName(name)
                    .addServer(getString(R.string.twitch_irc))
                    .addAutoJoinChannel(chl)
                    .setListenerManager(backgroundListenerManager)
                    .buildConfiguration();
            Configuration SenderBot_conf = new Configuration.Builder()
                    .setCapEnabled(true)
                    .setOnJoinWhoEnabled(false)
                    .addCapHandler(new EnableCapHandler("twitch.tv/tags"))
                    .addCapHandler(new EnableCapHandler("twitch.tv/membership"))
                    .addCapHandler(new EnableCapHandler("twitch.tv/commands"))
                    .setServerPassword("oauth:" + token)
                    .setName(name)
                    .addServer(getString(R.string.twitch_irc))
                    .addAutoJoinChannel(chl)
                    .setListenerManager(new BackgroundListenerManager())
                    .addListener(new ListenerAdapter() {
                        @Override
                        public void onOutput(OutputEvent event) throws Exception {
                            super.onOutput(event);
                            Log.i("sendIRC Output", event.getRawLine());
                        }

                        @Override
                        public void onMessage(MessageEvent event) {
                            Log.i("sendIRC onMessage", event.toString());
                        }
                        @Override
                        public void onNotice(NoticeEvent event) {
                            Log.i("sendIRC onNotice ", " Tags : " + event.getTags() + ", Message : " + event.getMessage());
                            messageAdapter.add_chat_event(getString(R.string.Alert_Chat), getString(R.string.COMMAND_NOTICE), event.getMessage());
                        }
                    })
                    .buildConfiguration();
            manager.addBot(ReceiverBot_conf);
            manager.addBot(SenderBot_conf);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(!isBotStarted){
                        manager.start();
                        isBotStarted = true;
                    }
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "채팅 연결 실패. 로그인이 되어 있는지 확인해보세요!", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        boolean isconnected = false;
        for(PircBotX botX : manager.getBots().asList()){
            if(botX.isConnected()){
                isconnected = true;
            }
        }
        if(!isconnected){
            connectIRC();
        }
        final Twitch_API twitch_API = RetrofitHelper.getRetrofit_Json(getString(R.string.twitch_BaseUrl)).create(Twitch_API.class);
        Call<Twitch_New_Get_Streams> newGetStreamsCall = twitch_API.Get_Stream("5s8icgxpodxmo6oajt6nk20x2q6yrc",ID,1);
        newGetStreamsCall.enqueue(new Callback<Twitch_New_Get_Streams>() {
            @Override
            public void onResponse(Call<Twitch_New_Get_Streams> call, Response<Twitch_New_Get_Streams> response) {
                Twitch_New_Get_Streams twitch = response.body();
                if(twitch != null && twitch.getData().size() == 0){
                    Toast.makeText(getApplicationContext(), "방송이 오프라인 상태입니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Twitch_New_Get_Streams> call, Throwable t) {

            }
        });
    }
    @Override
    public void onPause(){
        super.onPause();
    }

    private void init_WebServer() {
        WebServer server;
        server = new WebServer();
        try {
            server.start();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.post(new Runnable() {
            @Override
            public void run() {
                for(PircBotX botX : manager.getBots().asList()){
                    botX.close();
                }
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        //화면 회전시 레이아웃 재구성.
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            init_layout(true);
        } else if (newConfig.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
            init_layout(false);
        }
    }
    private void writeFile(String c, String file_name){
        File file = new File(getFilesDir(), file_name);
        FileWriter fw = null;
        BufferedWriter bufwr = null;
        try {
            fw = new FileWriter(file);
            bufwr = new BufferedWriter(fw);
            bufwr.write(c);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (bufwr != null)
                bufwr.close();

            if (fw != null)
                fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

