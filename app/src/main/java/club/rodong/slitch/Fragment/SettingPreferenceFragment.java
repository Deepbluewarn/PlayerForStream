package club.rodong.slitch.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import club.rodong.slitch.R;
import club.rodong.slitch.SharedPreferenceHelper;
import club.rodong.slitch.activity.WebViewActivity;

/**
 * Created by Bluewarn on 2018-03-13.
 */

public class SettingPreferenceFragment extends PreferenceFragment {
    PreferenceScreen tLogin;
    PreferenceScreen tsize;
    PreferenceScreen set_theme;
    PreferenceScreen lisence;
    PreferenceScreen iconinfo;
    PreferenceScreen version_info;
    Boolean isLogIn = false;

    //DBHelper DBHelper;
    SharedPreferenceHelper SPH;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);
        SPH = new SharedPreferenceHelper(getActivity());
        //DBHelper = new DBHelper(getContext());

        tLogin = (PreferenceScreen)findPreference("Twitch_login");
        tsize = (PreferenceScreen)findPreference("Twitch_thumb_size");
        set_theme = (PreferenceScreen)findPreference("Theme_setting");
        lisence = (PreferenceScreen)findPreference("OpenSources");
        iconinfo = (PreferenceScreen)findPreference("icon_info");
        version_info = (PreferenceScreen)findPreference("version_info");
        //로그인 여부 가져오기
        isLogIn = SPH.getLogin();

        set_prefText(isLogIn);

        try {
            PackageInfo pi = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            version_info.setSummary(pi.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tLogin.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                isLogIn = SPH.getLogin();
                if(isLogIn){
                    //todo 로그아웃 Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(getResources().getString(R.string.pref_Logout_title));
                    builder.setMessage(getResources().getString(R.string.Logout));
                    builder.setPositiveButton("예",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which){
                                    setLoginStatus(false);
                                }
                            });
                    builder.setNegativeButton("아니오",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();
                }else{
                    Intent intent = new Intent(getActivity(),WebViewActivity.class);
                    startActivityForResult(intent,1);
                }
                return false;
            }
        });
        tsize.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                final String items[] = { "640 × 360", "960 × 540", "1280 × 720","1600 × 900","1920 x 1080 (느림)" };
                final int[] selectedIndex = {0};

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle(getString(R.string.change_size))
                        .setSingleChoiceItems(items, SPH.get_t_size_num(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                selectedIndex[0] = i;
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch(selectedIndex[0]){
                                    case 0:
                                        SPH.set_t_width(640);
                                        SPH.set_t_height(360);
                                        SPH.set_t_size_num(0);
                                        break;
                                    case 1:
                                        SPH.set_t_width(960);
                                        SPH.set_t_height(540);
                                        SPH.set_t_size_num(1);
                                        break;
                                    case 2:
                                        SPH.set_t_width(1280);
                                        SPH.set_t_height(720);
                                        SPH.set_t_size_num(2);
                                        break;
                                    case 3:
                                        SPH.set_t_width(1600);
                                        SPH.set_t_height(900);
                                        SPH.set_t_size_num(3);
                                        break;
                                    case 4:
                                        SPH.set_t_width(1920);
                                        SPH.set_t_height(1080);
                                        SPH.set_t_size_num(4);
                                        break;
                                }
                            }
                        }).create().show();
                return false;
            }
        });
        set_theme.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(getActivity().getApplicationContext(), "테마를 변경합니다.", Toast.LENGTH_SHORT).show();
                final String items[] = {"Blue & Green", "Black & Brown", "Yellow & White"};
                final int[] selectedIndex = {0};

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle(getString(R.string.change_size))
                        .setSingleChoiceItems(items, SPH.getTheme(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                selectedIndex[0] = i;
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SPH.setTheme(selectedIndex[0]);
                                getActivity().setResult(Activity.RESULT_OK);
                                getActivity().finish();
                            }
                        }).create().show();
                return false;
            }
        });
        lisence.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View layout = inflater.inflate(R.layout.lisence, null);
                TextView textView = layout.findViewById(R.id.license_textview);
                String lisence_str = loadLisenseFromAsset(getActivity());
                textView.setText(lisence_str);
                dialog.setTitle("LICENSE")
                        .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setView(layout).create().show();
                return false;
            }
        });
        iconinfo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View layout = inflater.inflate(R.layout.iconinfo, null);
                TextView textView = layout.findViewById(R.id.iconinfo_textview);
                String html =
                        "<div>Icons made by <a href=\"https://www.flaticon.com/authors/smashicons\" title=\"Smashicons\">Smashicons</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div> \n" +
                        "<div>Icons made by <a href=\"https://www.flaticon.com/authors/gregor-cresnar\" title=\"Gregor Cresnar\">Gregor Cresnar</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div> \n" +
                        "<div>Icons made by <a href=\"https://www.flaticon.com/authors/vitaly-gorbachev\" title=\"Vitaly Gorbachev\">Vitaly Gorbachev</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div> \n" +
                        "<div>Icons made by <a href=\"http://www.freepik.com\" title=\"Freepik\">Freepik</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>";
                textView.setText(Html.fromHtml(html));

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("ICON INFO")
                        .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setView(layout)
                        .create().show();
                return false;
            }
        });
    }// onCreate
    private String loadLisenseFromAsset(Context context) {
        String txt = "";
        try {
            InputStream is = context.getAssets().open("LICENSE.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            txt = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return txt;
    }
    @Override
    public void onResume(){
        isLogIn = SPH.getLogin();
        set_prefText(isLogIn);
        super.onResume();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                set_prefText(true);
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }//onActivityResult
    @Override
    public void onPause(){
        super.onPause();
    }
    private void setLoginStatus(Boolean isLogIn){
        if(isLogIn){
            SPH.setLogin(isLogIn);
            tLogin.setTitle(getResources().getString(R.string.pref_Logout_title));
            tLogin.setSummary(getResources().getString(R.string.pref_Login_yes));
        }else{
            SPH.setLogin(isLogIn);
            SPH.setTwitchid(0);
            SPH.setTusername(null);
            SPH.set_access_token(null);
            tLogin.setTitle(getResources().getString(R.string.pref_Login_title));
            tLogin.setSummary(getResources().getString(R.string.pref_Login_no));
        }
    }
    private void set_prefText(Boolean Tl){
        if(Tl){
            tLogin.setTitle(getString(R.string.pref_Logout_title));
            tLogin.setSummary(getString(R.string.pref_Login_yes));
        }else{
            tLogin.setTitle(getString(R.string.pref_Login_title));
            tLogin.setSummary(getString(R.string.pref_Login_no));
        }
    }
}
