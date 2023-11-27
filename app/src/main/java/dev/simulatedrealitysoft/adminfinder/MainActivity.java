package dev.simulatedrealitysoft.adminfinder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import dev.simulatedrealitysoft.adminfinder.results.Frg_Results;
import dev.simulatedrealitysoft.adminfinder.scanner.Frg_Scanner;
import dev.simulatedrealitysoft.adminfinder.settings.Frg_Settings;
import dev.simulatedrealitysoft.adminfinder.utils.EulaTerms;
import dev.simulatedrealitysoft.adminfinder.utils.Utils;
import android.widget.Toast;
import dev.simulatedrealitysoft.adminfinder.about.Frg_About;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    CheckBoxPreference CPUwakelock;
    CheckBoxPreference lckscreen;
    CheckBoxPreference lckwifi;
    String[] permissions = {"android.permission.INTERNET", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.WAKE_LOCK"};
    SharedPreferences sharedPrefs;
    CheckBoxPreference timestamp;
    private static final int PERMISO_ALMACENAMIENTO_REQUEST_CODE = 100;
    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        drawerLayout.openDrawer(GravityCompat.START);
        
        new EulaTerms(this).show();
        verificarPermisoAlmacenamiento(MainActivity.this);
        CheckApplyConfiguration();
    }

    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.scanner) {
            getFragmentManager().beginTransaction().replace(R.id.frame, new Frg_Scanner()).commit();
        } else if (itemId == R.id.results) {
            getFragmentManager().beginTransaction().replace(R.id.frame, new Frg_Results()).commit();
        } else if (itemId == R.id.settings) {
            getFragmentManager().beginTransaction().replace(R.id.frame, new Frg_Settings()).commit();
        } else if (itemId == R.id.about) {
            getFragmentManager().beginTransaction().replace(R.id.frame, new Frg_About()).commit();
        }
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer((int) GravityCompat.START);
        return true;
    }

    public void verificarPermisoAlmacenamiento(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            // permission granted
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
				
            } else {
                abrirConfiguracion(context);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            solicitarPermisoScopedStorage(context);
        }
    }

    private void abrirConfiguracion(Context context) {
        Toast.makeText(getApplicationContext(), getString(R.string.grant_permission), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void solicitarPermisoScopedStorage(Context context) {
        // permission granted
        if (Environment.isExternalStorageManager()) {
            
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.grant_permission), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, Context context) {
        if (requestCode == PERMISO_ALMACENAMIENTO_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted
            } else {
                abrirConfiguracion(context);
            }
        }
    }
    


    public void CheckApplyConfiguration() {
        this.sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (Boolean.valueOf(sharedPrefs.getBoolean("lockScreen", false)).booleanValue()) {
            getWindow().addFlags(128);
            Log.d("locscreen------>", "true");
        } else {
            getWindow().clearFlags(128);
            Log.d("locscreen------>", "false");
        }
        if (Boolean.valueOf(sharedPrefs.getBoolean("lockWifi", false)).booleanValue()) {
            Utils.keepWiFiOn(getApplicationContext(), true);
            Log.d("lockwifi------>", "true");
        } else {
            Utils.keepWiFiOn(getApplicationContext(), false);
            Log.d("lockwifi------>", "false");
        }
        if (Boolean.valueOf(sharedPrefs.getBoolean("wakeLock", false)).booleanValue()) {
            Utils.keepCPUOn(getApplicationContext(), true);
            Log.d("lockcpu------>", "true");
            return;
        }
        Utils.keepCPUOn(getApplicationContext(), false);
        Log.d("lockcpu------>", "false");
    }
}
