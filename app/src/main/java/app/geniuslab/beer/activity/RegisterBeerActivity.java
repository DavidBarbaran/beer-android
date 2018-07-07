package app.geniuslab.beer.activity;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import app.geniuslab.beer.R;
import app.geniuslab.beer.connection.MyConnection;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterBeerActivity extends AppCompatActivity {

    @BindView(R.id.name_edit)
    EditText nameEdit;

    @BindView(R.id.price_edit)
    EditText priceEdit;

    MyConnection sqlite;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_beer);
        ButterKnife.bind(this);
        sqlite = new MyConnection(this,null,null,2);
        db = sqlite.getWritableDatabase();
    }

    @OnClick(R.id.add_btn)
    public void actionAdd(){
        String name = nameEdit.getText().toString();
        String price = priceEdit.getText().toString();

        sqlite.insertBeer(name,
                price,
                "https://http2.mlstatic.com/D_Q_NP_761521-MPE20797619153_072016-Q.jpg",db);
        finish();
    }
}
