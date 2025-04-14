package vn.iostar.tuan11bai2;

import android.view.View;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideoViewHolder extends RecyclerView.ViewHolder {
    WebView webView;

    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        webView = itemView.findViewById(R.id.webView);
    }
}
