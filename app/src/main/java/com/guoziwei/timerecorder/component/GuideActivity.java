package com.guoziwei.timerecorder.component;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.guoziwei.timerecorder.R;
import com.guoziwei.timerecorder.utils.AccessibilityUtil;

public class GuideActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

//        checkOverlayPermission();

        if (AccessibilityUtil.checkAccessibility(GuideActivity.this)) {
            startService(
                    new Intent(GuideActivity.this, TrackerService.class)
                            .putExtra(TrackerService.COMMAND, TrackerService.COMMAND_OPEN)
            );
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }


    }

    private void checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                startActivityForResult(
                        new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()))
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                        REQUEST_CODE
                );
                Toast.makeText(this, "请先授予 \"Activity 栈\" 悬浮窗权限", Toast.LENGTH_LONG).show();
            }
        }
    }
}
