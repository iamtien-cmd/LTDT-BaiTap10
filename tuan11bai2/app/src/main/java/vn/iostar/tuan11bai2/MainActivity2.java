package vn.iostar.tuan11bai2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import vn.iostar.tuan11bai2.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    private FirebaseAuth mAuth;

    @SuppressLint({"SetJavaScriptEnabled", "WebViewApiAvailability"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


         // Khởi tạo Firebase
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }
        mAuth = FirebaseAuth.getInstance();


        // Kiểm tra đăng nhập
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Log.d("Firebase", "Chưa đăng nhập, chuyển đến LoginActivity");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Log thông tin user
        Log.d("Firebase", "User email: " + user.getEmail());

        // Ẩn ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Cài đặt WebView
        binding.webview2.getSettings().setJavaScriptEnabled(true);
        binding.webview2.getSettings().setLoadWithOverviewMode(true);
        binding.webview2.getSettings().setUseWideViewPort(true);
        binding.webview2.getSettings().setBuiltInZoomControls(true);
        binding.webview2.getSettings().setDisplayZoomControls(false);
        binding.webview2.getSettings().setDomStorageEnabled(true);
        binding.webview2.getSettings().setDatabaseEnabled(true);
        binding.webview2.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        // Debug JavaScript
        binding.webview2.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.e("WebView", consoleMessage.message() + " -- From line " +
                        consoleMessage.lineNumber() + " of " + consoleMessage.sourceId());
                return true;
            }
        });

        // Xử lý lỗi và điều hướng
        binding.webview2.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.e("WebView", "Lỗi tải trang: " + description + ", URL: " + failingUrl);
                Toast.makeText(MainActivity2.this, "Lỗi tải trang: " + description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("WebView", "Tải URL: " + url);
                view.loadUrl(url);
                return true;
            }
        });

        // Tải URL
        Log.d("WebView", "Bắt đầu tải http://iotstar.vn");
        binding.webview2.loadUrl("http://iotstar.vn");
    }

    @Override
    public void onBackPressed() {
        if (binding.webview2.canGoBack()) {
            binding.webview2.goBack();
        } else {
            super.onBackPressed();
        }
    }
}