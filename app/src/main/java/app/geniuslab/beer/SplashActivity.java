package app.geniuslab.beer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

import app.geniuslab.beer.activity.HomeActivity;
import app.geniuslab.beer.activity.LoginActivity;
import app.geniuslab.beer.session.Preference;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.animation_view)
    LottieAnimationView animationView;

    private Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        preference = Preference.getIntance(this);

        animationView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //finish();
                //startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        });
        finish();
        if (preference.isLogin()){
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }

    }
}
