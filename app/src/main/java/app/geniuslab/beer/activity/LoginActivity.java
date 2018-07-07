package app.geniuslab.beer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.Set;

import app.geniuslab.beer.R;
import app.geniuslab.beer.SplashActivity;
import app.geniuslab.beer.connection.RestApi;
import app.geniuslab.beer.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username_edit)
    EditText usernameEdit;

    @BindView(R.id.password_edit)
    EditText passwordEdit;

    private RestApi restApi = RestApi.RETROFIT.create(RestApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void actionLogin(){
        restApi.getUser("\"username\"", "\""+usernameEdit.getText().toString() + "\"").enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("response", response.body() + "");
                if (response.isSuccessful() && !response.body().isJsonNull() && !response.body().toString().equals("{}")){
                    Set<Map.Entry<String, JsonElement>> entries = response.body().entrySet();
                    User user = new Gson().fromJson(
                            response.body().get(entries.iterator().next().getKey()).getAsJsonObject(),
                            User.class);
                    Log.e("keys", entries.iterator().next().getKey());

                    if (user.getPassword().equals(passwordEdit.getText().toString())){
                        finish();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this,"Password incorrecto",Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(LoginActivity.this,"El usuario no existe",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}
