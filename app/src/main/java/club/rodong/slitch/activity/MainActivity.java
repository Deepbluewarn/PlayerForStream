package club.rodong.slitch.activity;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import club.rodong.slitch.BuildConfig;
import club.rodong.slitch.GlideApp;
import club.rodong.slitch.POJO.LiveDetail;
import club.rodong.slitch.POJO.Twitch_v5_Get_Followed_Stream;
import club.rodong.slitch.R;
import club.rodong.slitch.RetrofitHelper;
import club.rodong.slitch.adapter.RV_Adapter;
import club.rodong.slitch.SharedPreferenceHelper;
import club.rodong.slitch.databinding.ActivityMainBinding;
import club.rodong.slitch.interfaces.Twitch_API;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RV_Adapter rvadapter;
    LinearLayoutManager mLayoutManager;
    ActivityMainBinding mBinding;
    private long pressedTime;
    SharedPreferenceHelper SPH;

    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
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
        Fabric.with(this, new Crashlytics());
        Log.i("onCreate", "진입");
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        swipeRefreshLayout = mBinding.refresh;
        toolbar = mBinding.toolbar;

        rvadapter = new RV_Adapter(GlideApp.with(this),this);//메인 화면 리스트뷰 어댑터 초기화
        rvadapter.clear();
        mLayoutManager = new LinearLayoutManager(this);
        final RecyclerView recyclerView = mBinding.mainRecyclerView;
        recyclerView.setAdapter(rvadapter);
        recyclerView.setLayoutManager(mLayoutManager);

        init_list();

        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light,
                android.R.color.background_dark
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init_list();
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SIMPLE TWITCH");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivityForResult(intent,25);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(SPH.get_start_first()){
            //앱 실행이 처음인 경우.
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.first_demo, null);
            TextView textView = layout.findViewById(R.id.firstdemo_textview);
            String demostr =
                    "  앱을 다운로드 해주셔서 감사합니다. \n\n" +
                    "  방송 미리보기를 한번 터치하면 새로고침 할 수 있습니다. \n\n" +
                    "  방송 미리보기를 길게 터치하면 라이브 스트리밍을 시청할 수 있습니다.\n\n" +
                    "  미완성 앱이므로 만약 앱 이용 중 오류 발생이나 기타 개선사항 등이 있으시면 앱 리뷰나 이메일을 통해 피드백 부탁드립니다.";
            textView.setText(demostr);
            AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
            dialog.setTitle("Simple Player For Twitch")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setView(layout)
                    .create().show();
            SPH.set_start_first(false);
        }else{

        }
    }

    @Override
    public void onBackPressed() {
        if (pressedTime == 0) {
            Toast.makeText(getApplicationContext(), getString(R.string.BackbuttonMsg), Toast.LENGTH_SHORT).show();
            pressedTime = System.currentTimeMillis();//현재 시간 저장
        }else {
            int second = (int) (System.currentTimeMillis() - pressedTime);//현재 시스템 시간에서 저장된 시간(뒤로가기 버튼을 눌렀던 시간)을 뺀 값.
            if (second > 2000) {                                          //이 2초를 넘긴 경우
                pressedTime = 0;                                          //어플이 뒤로가기에 의해 종료되지 않게 0 으로 초기화.
                Toast.makeText(getApplicationContext(), getString(R.string.BackbuttonMsg), Toast.LENGTH_SHORT).show();
            } else {
                super.onBackPressed();                                    //2초를 넘기지 않은 경우 뒤로가기 실행하여 종료.
            }
        }
    }

    //썸네일 리스트뷰를 전부 삭제한 뒤, 새로운 정보로 리스트뷰 재구성
    //Delete Every Stream Preview Items, and replace it to new Stream Info
    private void init_list(){
        rvadapter.clear();
        if(SPH.getLogin() && SPH.get_access_token() != null){
            final Twitch_API twitch_api = RetrofitHelper.getRetrofit_Json(getString(R.string.twitch_BaseUrl)).create(Twitch_API.class);
            Call<Twitch_v5_Get_Followed_Stream> users_followsCall = twitch_api.Get_Followed_Streams(
                    "application/vnd.twitchtv.v5+json",
                    BuildConfig.TWITCH_CLIENT_ID,
                    "OAuth " + SPH.get_access_token(),100);
            users_followsCall.enqueue(new Callback<Twitch_v5_Get_Followed_Stream>() {
                @Override
                public void onResponse(Call<Twitch_v5_Get_Followed_Stream> call, Response<Twitch_v5_Get_Followed_Stream> response) {
                    Log.i("init_list", "onResponse: 진입");
                    Twitch_v5_Get_Followed_Stream twitch = response.body();
                    if (twitch != null) {
                        int width = SPH.get_t_width();
                        int height = SPH.get_t_height();
                        for(int i = 0; i < twitch.getTotal(); i++){
                            long stid = twitch.getStreams().get(i).getChannel().getId();
                            String display_name = twitch.getStreams().get(i).getChannel().getDisplayName();
                            String name = twitch.getStreams().get(i).getChannel().getName();
                            String profileimgurl = twitch.getStreams().get(i).getChannel().getLogo();
                            String thumbUri = twitch.getStreams().get(i).getPreview().getTemplate()
                                    .replace("{width}",String.valueOf(width))
                                    .replace("{height}",String.valueOf(height));
                            String title = twitch.getStreams().get(i).getChannel().getStatus();
                            long viewer = twitch.getStreams().get(i).getViewers();
                            Log.i("init_list", "onResponse: display_name" + display_name);
                            rvadapter.addItem(getString(R.string.TWITCH),name, display_name, thumbUri, profileimgurl, title, viewer ,0 , stid);
                            SortList();
                        }
                    }else{
                        rvadapter.add_no_live();
                    }
                    rvadapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<Twitch_v5_Get_Followed_Stream> call, Throwable t) {
                    Log.i("init_list", "onFailure: " + t.getLocalizedMessage());
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "로그인하세요", Toast.LENGTH_SHORT).show();
            rvadapter.add_no_live();
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 25) {
            if(resultCode == Activity.RESULT_OK){
                recreate();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }//onActivityResult
    //ID 값을 기준으로 리스트뷰 내림차순 정렬.
    private void SortList() {
        ArrayList<LiveDetail> list = rvadapter.getItemList();
        Comparator<LiveDetail> noDesc = new Comparator<LiveDetail>() {
            @Override
            public int compare(LiveDetail item1, LiveDetail item2) {
                return Long.compare(item2.getId(),item1.getId());
            }
        };
        Collections.sort(list, noDesc);
        for(int i = 0; i < list.size(); i++){
            rvadapter.notifyItemInserted(i);
        }
    }
}

