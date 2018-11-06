package club.rodong.playerforstream.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

//import com.crashlytics.android.Crashlytics;

import com.crashlytics.android.Crashlytics;

import club.rodong.playerforstream.R;
import club.rodong.playerforstream.RetrofitHelper;
import club.rodong.playerforstream.SharedPreferenceHelper;
import club.rodong.playerforstream.interfaces.Twitch_API;
import club.rodong.playerforstream.POJO.Twitch_New_Get_Users;
//import io.fabric.sdk.android.Fabric;
//import io.userhabit.service.Userhabit;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    WebSettings webSettings;
    boolean TisLogIn;
    SharedPreferenceHelper SPH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SPH = new SharedPreferenceHelper(getBaseContext());
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
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.Klogin);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //로그인 상태 불러오기
        TisLogIn = SPH.getLogin();

        if(TisLogIn){
            finish();
        }else{
            webView.loadUrl("https://id.twitch.tv/oauth2/authorize" +
                    "?client_id=5s8icgxpodxmo6oajt6nk20x2q6yrc" +
                    "&redirect_uri=login://simpletwitch.com" +
                    "&response_type=token" +
                    "&scope=user_read+openid+chat:read+channel:moderate+chat:edit+user_subscriptions" +
                    "&state=c3ab8aa609ea11e793ae92361f002671" +
                    "&force_verify=true"
            );
        }
        webView.setWebViewClient(new WebViewClientClass());
    }
    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.startsWith("login:")){
                //hash to Query String
                String URL_temp = url.replace("#","?");
                Uri uri = Uri.parse(URL_temp);
                String token = uri.getQueryParameter("access_token");
                //받아온 토큰 저장.
                //Save access token
                SPH.set_access_token(token);
                if(token != null){
                    //앱 사용자 유저정보 가져오기
                    Twitch_API twitch_API = RetrofitHelper.getRetrofit_Json(getString(R.string.twitch_BaseUrl)).create(Twitch_API.class);

                    Call<Twitch_New_Get_Users> call = twitch_API.Get_Users("Bearer "+token);
                    call.enqueue(new Callback<Twitch_New_Get_Users>() {
                        @Override
                        public void onResponse(Call<Twitch_New_Get_Users> call, Response<Twitch_New_Get_Users> response) {
                            Twitch_New_Get_Users twitch_new1 = response.body();
                            if (twitch_new1 != null) {
                                // User Info
                                SPH.setTwitchid(Integer.valueOf(twitch_new1.getData().get(0).getId()));
                                SPH.setTusername(twitch_new1.getData().get(0).getLogin());
                                SPH.setLogin(true);
                                Toast.makeText(getApplicationContext(), twitch_new1.getData().get(0).getLogin() + " 님 반갑습니다!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Twitch_New_Get_Users> call, Throwable t) {
                        }
                    });
                }
                setResult(Activity.RESULT_OK);
                finish();
            }
            view.loadUrl(url);
            return true;
        }
    }
}

