package app.geniuslab.beer.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import app.geniuslab.beer.R;
import app.geniuslab.beer.connection.MyConnection;
import app.geniuslab.beer.model.Beer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditBeerActivity extends AppCompatActivity {

    @BindView(R.id.name_edit)
    EditText nameEdit;

    @BindView(R.id.price_edit)
    EditText priceEdit;

    MyConnection sqlite;
    SQLiteDatabase db;
    Beer beer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_beer);
        ButterKnife.bind(this);
        sqlite = new MyConnection(this,null,null,2);
        db = sqlite.getWritableDatabase();

         beer = (Beer) getIntent().getExtras().get("beer");
        Log.e("id_beer","" + beer.getId());
        nameEdit.setText(beer.getName());
        priceEdit.setText(beer.getPrice());

    }

    @OnClick(R.id.add_btn)
    public void actionUpdate(){
        String name = nameEdit.getText().toString();
        String price = priceEdit.getText().toString();
        beer.setName(name);
        beer.setPrice(price);
        sqlite.updateBeer(beer, db);
        finish();
    }
}
