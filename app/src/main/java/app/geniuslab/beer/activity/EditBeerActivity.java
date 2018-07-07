package app.geniuslab.beer.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import app.geniuslab.beer.R;
import app.geniuslab.beer.connection.MyConnection;
import app.geniuslab.beer.dialog.LoadingDialog;
import app.geniuslab.beer.model.Beer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditBeerActivity extends AppCompatActivity {

    @BindView(R.id.name_edit)
    EditText nameEdit;

    @BindView(R.id.price_edit)
    EditText priceEdit;
    LoadingDialog loadingDialog;
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
        loadingDialog = new LoadingDialog(EditBeerActivity.this, getString(R.string.app_name),getString(R.string.process_message));

         beer = (Beer) getIntent().getExtras().get("beer");
        Log.e("id_beer","" + beer.getId());
        nameEdit.setText(beer.getName());
        priceEdit.setText(beer.getPrice());

    }

    @OnClick(R.id.add_btn)
    public void actionUpdate(){
        String name = nameEdit.getText().toString();
        String price = priceEdit.getText().toString();
        if(validateinputs()){
            beer.setName(name);
            beer.setPrice(price);
            sqlite.updateBeer(beer, db);
            finish();
        }

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

    @OnClick(R.id.deleten_btn)
    public void delete(){
        String name = nameEdit.getText().toString();
        Eliminar(name);
        finish();
    }


    public String Eliminar(String name){
        String result="";
        try {
            MyConnection cn=new MyConnection(this,null,null,2);
            SQLiteDatabase db=cn.getWritableDatabase();

            db.delete("beer",
                    "name=?",
                    new String[]{name});

            result="Eliminado";
        }catch(Exception ex){
            result= ex.getMessage();
        }
        return result;
    }
}
