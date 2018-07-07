package app.geniuslab.beer.connection;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import app.geniuslab.beer.model.User;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {

    String BASE_URL = "https://clothes-dbab1.firebaseio.com/";

    OkHttpClient okHttpClient = new OkHttpClient
            .Builder().readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build();

    Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

    @GET("user.json?")
    Call<JsonObject> getUser(@Query("orderBy") String order, @Query("equalTo") String username);

    @GET("drinks.json")
    Call<JsonArray> getdrink();
}
