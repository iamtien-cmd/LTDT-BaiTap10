package vn.iostar.tuan11bai2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import vn.iostar.tuan11bai2.Model.VideoModel;

public interface APIService {

    Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();

    APIService serviceapi = new Retrofit.Builder()
            .baseUrl("http://app.iotstar.vn:8081/appfoods/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    @GET("getvideos.php")
    Call<VideoModel.MessageVideoModel> getVideos();
}