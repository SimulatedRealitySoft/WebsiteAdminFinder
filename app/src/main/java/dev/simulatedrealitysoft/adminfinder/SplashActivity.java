package dev.simulatedrealitysoft.adminfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
    private static boolean splashLoaded = false;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!splashLoaded) {
            setContentView(R.layout.splash);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                }
            }, (long) 1500);
            splashLoaded = true;
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
