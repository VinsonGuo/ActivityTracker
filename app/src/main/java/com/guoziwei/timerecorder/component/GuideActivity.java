package com.guoziwei.timerecorder.component;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.guoziwei.timerecorder.R;
import com.guoziwei.timerecorder.utils.AccessibilityUtil;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

//        checkOverlayPermission();
        findViewById(R.id.btn).setOnClickListener(v -> AccessibilityUtil.checkAccessibility(GuideActivity.this));
        toMain();

    }

    private void toMain() {
        if (AccessibilityUtil.isAccessibilitySettingsOn(this)) {
            startService(
                    new Intent(GuideActivity.this, TrackerService.class)
            );
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        toMain();
    }

}
