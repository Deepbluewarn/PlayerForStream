package club.rodong.playerforstream.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;

import club.rodong.playerforstream.R;
import club.rodong.playerforstream.SharedPreferenceHelper;
import io.fabric.sdk.android.Fabric;

public class SettingActivity extends AppCompatActivity {
    protected ConstraintLayout background;
    private SharedPreferenceHelper SPH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_setting);
        background = findViewById(R.id.background);
        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.SettingActivityTitle));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
