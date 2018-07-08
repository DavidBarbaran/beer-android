package app.geniuslab.beer.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import app.geniuslab.beer.R;
import app.geniuslab.beer.connection.MyConnection;
import app.geniuslab.beer.model.Beer;
import butterknife.BindView;

public class DetailBeerActivity extends AppCompatActivity {

    @BindView(R.id.name_text)
    EditText nameEdit;

    @BindView(R.id.price_text)
    EditText priceEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_beer);
        String id = getIntent().getExtras().get("beer").toString();


        nameEdit.setText(validation(id).getName());
        priceEdit.setText(validation(id).getPrice());
    }


    public Beer validation(String id){
        Beer bean=null;
        try {
            MyConnection sqlite = new MyConnection(this,null,null,2);
            SQLiteDatabase db = sqlite.getReadableDatabase();

            // Cursor es como un ResultSet
            Cursor cur = db.rawQuery("select * from beer where id= ?",
                    new String[]{id});

            if (cur.moveToNext()) {
                bean = new Beer();
                bean.setName(cur.getString(0));

            }
        }catch (Exception e ){

        }
        return bean;
    }
}
