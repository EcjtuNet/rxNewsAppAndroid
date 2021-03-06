package ecjtu.net.demon.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.nostra13.universalimageloader.core.ImageLoader;

import ecjtu.net.demon.R;

/**
 * 最顶层的用于被继承的activity
 * Created by Shakugan on 15/12/8.
 */
public class NoGestureBaseActivity extends AppCompatActivity {

    public static int themeID = -1;
    public static final int DEFAULT_THEME = 0;
    public static final int DARK_THEME = 1;
    public static final int RED_THEME = 2;
    public static final String DEFUALT_COLOR = "#009688";
    public static final String DEFUALT_COLOR_DARK = "#00796b";
    public static final String BLACK_COLOR = "#424242";
    public static final String BLACK_COLOR_DARK = "#212121";
    public static final String RED_COLOR = "#c41411";
    public static final String RED_COLOR_DARK = "#b0120a";
    public static int themeColor;
    public static int themeColorDark;

    public SharedPreferences preferences;
    public NavigationView drawer;
    public DrawerLayout drawerLayout;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (themeID == -1) {
            preferences = getSharedPreferences("phone", Context.MODE_PRIVATE);
            themeID = preferences.getInt("theme", 0);
        }
        switch (themeID) {
            case DEFAULT_THEME: {
                themeColor = Color.parseColor(DEFUALT_COLOR);
                themeColorDark = Color.parseColor(DEFUALT_COLOR_DARK);
                setTheme(R.style.AppTheme);
                break;
            }
            case DARK_THEME: {
                themeColor = Color.parseColor(BLACK_COLOR);
                themeColorDark = Color.parseColor(BLACK_COLOR_DARK);
                setTheme(R.style.AppThemeDark);
                break;
            }
            case RED_THEME: {
                themeColor = Color.parseColor(RED_COLOR);
                themeColorDark = Color.parseColor(RED_COLOR_DARK);
                setTheme(R.style.AppThemeRed);
                break;
            }
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setBackgroundColor(themeColor);
        CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.main_container);
        if (layout != null) {
            layout.setStatusBarBackgroundColor(themeColorDark);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }
        return super.onOptionsItemSelected(item);
    }

    protected void initActionBar() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i("lowmemory", "clearMemoryCache0-----------");
        ImageLoader.getInstance().clearMemoryCache();
    }

    public void turn2Activity(Context context,Class dest) {
        Intent intent = new Intent(context,dest);
        startActivity(intent);
    }

    public void turn2Activity(Context context,Class dest,Bundle bundle) {
        Intent intent = new Intent(context,dest);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void turn2ActivityWithUrl(Context context,Class dest, String url) {
        Intent intent = new Intent(context, dest);
        if (url != null) {
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
