package dev.simulatedrealitysoft.adminfinder.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import dev.simulatedrealitysoft.adminfinder.R;

public class EulaTerms {
    private String EULA_PREFIX = "eula_";
    public Activity mActivity;

    public EulaTerms(Activity activity) {
        mActivity = activity;
    }

    private PackageInfo getPackageInfo() {
        try {
            return mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void show() {
        PackageInfo packageInfo = getPackageInfo();
        final String str = EULA_PREFIX + packageInfo.versionCode;
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
        if (!defaultSharedPreferences.getBoolean(str, false)) {
            new AlertDialog.Builder(mActivity).setTitle(mActivity.getString(R.string.srs_title)).setMessage(mActivity.getString(R.string.use)).setPositiveButton(mActivity.getString(R.string.srs_agree), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences.Editor edit = defaultSharedPreferences.edit();
                    edit.putBoolean(str, true);
                    edit.commit();
                    dialogInterface.dismiss();
                }
                }).setNegativeButton(mActivity.getString(R.string.srs_decline), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    EulaTerms.this.mActivity.finish();
                }
            }).create().show();
        }
    }
}
