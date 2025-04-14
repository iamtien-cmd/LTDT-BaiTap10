package vn.iostar.tuan11bai2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;



import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

public class UploadActivity extends AppCompatActivity {

    private static final int PICK_VIDEO_REQUEST = 1;
    private Uri videoUri;

    private VideoView videoView;
    private Button btnChooseVideo, btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        videoView = findViewById(R.id.videoPreview);
        btnChooseVideo = findViewById(R.id.btnChooseVideo);
        btnUpload = findViewById(R.id.btnUpload);

        btnChooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVideoChooser();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadVideoToFirebase();
            }
        });

    }

    private void openVideoChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        startActivityForResult(Intent.createChooser(intent, "Chọn video"), PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.start();
        }
    }

    private void uploadVideoToFirebase() {
        if (videoUri == null) {
            Toast.makeText(this, "Vui lòng chọn video trước", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String fileName = UUID.randomUUID().toString() + ".mp4";
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("videos/" + userId + "/" + fileName);

        storageRef.putFile(videoUri)
                .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    Toast.makeText(UploadActivity.this, "Upload thành công!", Toast.LENGTH_SHORT).show();
                    Log.d("VIDEO_URL", uri.toString());
                    // TODO: lưu URL vào Firestore nếu muốn
                }))
                .addOnFailureListener(e -> {
                    Toast.makeText(UploadActivity.this, "Upload thất bại!", Toast.LENGTH_SHORT).show();
                    Log.e("Upload", "Lỗi: " + e.getMessage());
                });
    }
}

