package vn.iostar.tuan11bai2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.iostar.tuan11bai2.Model.VideoModel;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.MyViewHolder> {

    private Context context;
    private List<VideoModel> videoList;
    private List<Boolean> isFavList;  // Track favorites per video


    public VideosAdapter(Context context, List<VideoModel> videoList) {
        this.context = context;
        this.videoList = videoList;
        this.isFavList = new ArrayList<>(Collections.nCopies(videoList.size(), false)); // Initialize favorites list
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VideoModel videoModel = videoList.get(position);
        String url = videoModel.getUrl();

        // Lấy ID video từ URL YouTube (Sử dụng một cách tiếp cận tốt hơn cho việc tìm ID video)
        String videoId = extractVideoIdFromUrl(url);
        if (holder.webView != null) {
            // Cấu hình WebView và load video YouTube
            String iframe = "<html><body style='margin:0'><iframe width=\"100%\" height=\"100%\" " +
                    "src=\"https://www.youtube.com/embed/" + videoId + "\" " +
                    "frameborder=\"0\" allowfullscreen></iframe></body></html>";

            WebSettings webSettings = holder.webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            holder.webView.loadData(iframe, "text/html", "utf-8");
        } else {
            Log.e("VideosAdapter", "WebView is null at position " + position);
        }

        // Set up the onClickListener for imMore
        holder.imMore.setOnClickListener(view -> {
            // Start the UploadActivity when imMore is clicked
            Intent intent = new Intent(context, UploadActivity.class);
            context.startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return videoList != null ? videoList.size() : 0;
    }

    private String extractVideoIdFromUrl(String url) {
        String videoId = "";
        if (url.contains("v=")) {
            videoId = url.substring(url.indexOf("v=") + 2, url.indexOf("v=") + 13);  // Get video ID
        }
        return videoId;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        WebView webView;
        VideoView videoView;
        ProgressBar videoProgressBar;
        TextView textVideoTitle;
        TextView textVideoDescription;
        ImageView imPerson, favorites, imShare, imMore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.webView);
            videoView = itemView.findViewById(R.id.videoView);
            videoProgressBar = itemView.findViewById(R.id.videoProgressBar);
            textVideoTitle = itemView.findViewById(R.id.textVideoTitle);
            textVideoDescription = itemView.findViewById(R.id.textVideoDescription);
            imPerson = itemView.findViewById(R.id.imPerson);
            favorites = itemView.findViewById(R.id.imFavorite);
            imShare = itemView.findViewById(R.id.imShare);
            imMore = itemView.findViewById(R.id.imMore);
        }
    }
}
