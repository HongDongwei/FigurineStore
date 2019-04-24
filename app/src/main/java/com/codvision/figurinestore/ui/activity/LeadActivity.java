package com.codvision.figurinestore.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.codvision.figurinestore.R;

public class LeadActivity extends Activity {
    int time = 3;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        textView = findViewById(R.id.tv_lead_time);
        handler.postDelayed(runnable, 1000);

    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time--;
            textView.setText(String.valueOf(time));
            handler.postDelayed(this, 1000);
            if (time == 0) {
                Intent intent = new Intent(LeadActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}

