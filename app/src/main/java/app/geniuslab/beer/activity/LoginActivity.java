package app.geniuslab.beer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLHandshakeException;

import app.geniuslab.beer.R;
import app.geniuslab.beer.connection.RestApi;
import app.geniuslab.beer.dialog.LoadingDialog;
import app.geniuslab.beer.model.User;
import app.geniuslab.beer.session.Preference;
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
    private LoadingDialog loadingDialog;
    private Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        preference = Preference.getIntance(this);
        loadingDialog = new LoadingDialog(LoginActivity.this, getString(R.string.app_name),getString(R.string.process_message));

    }

    @OnClick(R.id.btn_login)
    public void actionLogin(){
        if (validateLogin()){
            loadingDialog = new LoadingDialog(LoginActivity.this, getString(R.string.app_name),getString(R.string.process_message));
            loadingDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    loginUser();
                }
            }).start();
        }
    }

    private boolean validateLogin(){
        boolean isValid = true;
        if (usernameEdit.getText().toString().isEmpty()){
            loadingDialog.showMessage("El campo username no debe estar vacio");
            isValid =  false;
        }
        else if (passwordEdit.getText().toString().isEmpty()){
            loadingDialog.showMessage("El campo password no debe estar vacio");
            isValid = false;
        }

        if (!isValid){
            loadingDialog.show();
        }

        return isValid;
    }

    private void loginUser(){
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
                        preference.setLogin(true);
                        preference.setName(user.getName());
                        preference.setUsername(user.getUsername());
                        preference.setProfilePicture(user.getProfilePicture());
                        finish();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    } else {
                        loadingDialog.showMessage("Password incorrecto");
                    }
                } else {
                    loadingDialog.showMessage("El usuario no existe");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loadingDialog.showMessage(evaluateFailure(t));
            }
        });
    }

    private String evaluateFailure(Throwable t) {
        if (t instanceof UnknownHostException) {
            return getString(R.string.connection_message);
        } else if (t instanceof SocketTimeoutException) {
            return getString(R.string.time_out_message);
        } else if (t instanceof SSLHandshakeException) {
            return getString(R.string.connection_lost_message);
        } else {
            return getString(R.string.default_error_message);
        }
    }
}
