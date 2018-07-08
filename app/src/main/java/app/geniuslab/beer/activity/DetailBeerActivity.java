package app.geniuslab.beer.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.geniuslab.beer.R;
import app.geniuslab.beer.connection.MyConnection;
import app.geniuslab.beer.model.Beer;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailBeerActivity extends AppCompatActivity {

    @BindView(R.id.name_text)
    TextView nameEdit;

    @BindView(R.id.price_text)
    TextView priceEdit;

    public static final String col_id="id";
    public static final String col_name="name";
    public static final String col_image="image";
    public static final String col_price="price";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_beer);
        ButterKnife.bind(this);
        String id = getIntent().getExtras().get("beer").toString();

        Beer beer = validation(id);

        if (beer == null){
            finish();
            Toast.makeText(this,"No existe la bebida en tu base de datos local",Toast.LENGTH_SHORT).show();
        } else {
            nameEdit.setText(beer.getName());
            priceEdit.setText(beer.getPrice());
        }


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
                bean = new Beer(
                        cur.getInt(cur.getColumnIndex(col_id)),
                        cur.getString(cur.getColumnIndex(col_name)),
                        cur.getString(cur.getColumnIndex(col_image)),
                        cur.getString(cur.getColumnIndex(col_price))
                );

            }
        }catch (Exception e ){

        }
        return bean;
    }
}
