package vn.iostar.tuan11bai2;
import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iostar.tuan11bai2.Model.VideoModel;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private VideosAdapter videosAdapter;
    private List<VideoModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Yêu cầu ẩn thanh tiêu đề và chế độ toàn màn hình
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // 1. Khởi tạo list rỗng
        list = new ArrayList<>();

        // 2. Tìm ViewPager2 và gán adapter ngay
        viewPager2 = findViewById(R.id.vpager);
        videosAdapter = new VideosAdapter(this, list); // dùng `this` thay vì getApplicationContext()
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setAdapter(videosAdapter); // gắn adapter NGAY ở đây

        // 3. Sau đó mới gọi API
        getVideos();
        // Kiểm tra và yêu cầu quyền camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Nếu chưa cấp quyền, yêu cầu người dùng cấp quyền
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {  // Mã yêu cầu quyền là 1
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền được cấp, thực hiện hành động sử dụng camera
                Toast.makeText(this, "Quyền camera đã được cấp", Toast.LENGTH_SHORT).show();
            } else {
                // Quyền bị từ chối, thông báo cho người dùng
                Toast.makeText(this, "Cần cấp quyền camera để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Hàm gọi API để lấy danh sách video
    private void getVideos() {
        APIService.serviceapi.getVideos().enqueue(new Callback<VideoModel.MessageVideoModel>() {
            @Override
            public void onResponse(Call<VideoModel.MessageVideoModel> call, Response<VideoModel.MessageVideoModel> response) {
                // Kiểm tra phản hồi từ API
                if (response.isSuccessful() && response.body() != null) {
                    List<VideoModel> fetchedList = response.body().getResult();
                    Log.d("API_RESPONSE", "Fetched videos: " + fetchedList); // In log để kiểm tra dữ liệu

                    // Nếu dữ liệu trả về hợp lệ
                    if (fetchedList != null && !fetchedList.isEmpty()) {
                        list.clear(); // Xóa danh sách cũ
                        list.addAll(fetchedList); // Cập nhật danh sách mới
                        videosAdapter.notifyDataSetChanged(); // Thông báo adapter cập nhật UI
                    } else {
                        Log.d("API_RESPONSE", "No videos found");
                        // Thông báo khi không có video nào
                    }
                } else {
                    Log.d("API_ERROR", "API response is not successful or empty");
                }
            }

            @Override
            public void onFailure(Call<VideoModel.MessageVideoModel> call, Throwable t) {
                Log.d("API_ERROR", "API call failed: " + t.getMessage());
            }
        });
    }
}
