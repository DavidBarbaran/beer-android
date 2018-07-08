package app.geniuslab.beer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.geniuslab.beer.R;
import app.geniuslab.beer.model.Beer;

public class DetailBeerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_beer);
        String id = getIntent().getExtras().get("beer").toString();

    }
}
