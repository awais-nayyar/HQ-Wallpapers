package com.example.hqwallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        toolbar = findViewById(R.id.toolBar);
        webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String url;
        url = getIntent().getStringExtra("URL");
        webView.loadUrl(url);
    }
}