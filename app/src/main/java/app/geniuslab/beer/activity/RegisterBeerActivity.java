package app.geniuslab.beer.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import app.geniuslab.beer.R;
import app.geniuslab.beer.connection.MyConnection;
import app.geniuslab.beer.dialog.LoadingDialog;
import app.geniuslab.beer.model.Beer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterBeerActivity extends AppCompatActivity {

    @BindView(R.id.name_edit)
    EditText nameEdit;

    @BindView(R.id.price_edit)
    EditText priceEdit;
    LoadingDialog loadingDialog;
    MyConnection sqlite;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_beer);
        ButterKnife.bind(this);
        sqlite = new MyConnection(this,null,null,2);
        loadingDialog = new LoadingDialog(RegisterBeerActivity.this, getString(R.string.app_name),getString(R.string.process_message));

        db = sqlite.getWritableDatabase();
    }

    @OnClick(R.id.back_button)
    public void actionBack() {
        onBackPressed();
    }

    @OnClick(R.id.add_btn)
    public void actionAdd(){
        String name = nameEdit.getText().toString();
        String price = priceEdit.getText().toString();
        if(validateinputs()){
            if (validation(name) == null) {
                sqlite.insertBeer(name,
                        price,
                        "https://http2.mlstatic.com/D_Q_NP_761521-MPE20797619153_072016-Q.jpg", db);
                finish();
            } else {
                Toast.makeText(this, "Producto ya existe", Toast.LENGTH_LONG).show();
            }
        }
    }

    public Beer validation(String name){
        Beer bean=null;
        try {
            SQLiteDatabase db = sqlite.getReadableDatabase();

            // Cursor es como un ResultSet
            Cursor cur = db.rawQuery("select name from beer where name= ?",
                    new String[]{name});

            if (cur.moveToNext()) {
                bean = new Beer();
                bean.setName(cur.getString(0));

            }
        }catch (Exception e ){

        }
        return bean;
    }

    private boolean validateinputs(){
        boolean isValid = true;
        if (nameEdit.getText().toString().isEmpty()){
            loadingDialog.showMessage("El campo Nombre no debe estar vacio");
            isValid =  false;
        }
        else if (priceEdit.getText().toString().isEmpty()){
            loadingDialog.showMessage("El campo Precio no debe estar vacio");
            isValid = false;
        }

        if (!isValid){
            loadingDialog.show();
        }

        return isValid;
    }
}
