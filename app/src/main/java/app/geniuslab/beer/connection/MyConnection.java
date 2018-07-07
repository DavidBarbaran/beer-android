package app.geniuslab.beer.connection;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyConnection extends SQLiteOpenHelper {
    public static final String DATA_BASE="beer.db";
    public static final String TABLE_NAME="beer";
    public static final String col_id="id";
    public static final String col_name="name";
    public static final String col_image="image";
    public static final String col_price="price";

    public MyConnection(Context context, String name , SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATA_BASE, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME +"("+
                col_id+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                col_name + " TEXT," +
                col_image + " TEXT," +
                col_price + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertBeer( String name, String price,String image ,SQLiteDatabase db)
    {
        Log.i("SQLite", "INSERT: " + name + ","+image+"," + price );
        ContentValues contentValues = new ContentValues();
        contentValues.put( col_name , name);
        contentValues.put( col_image , image);
        contentValues.put( col_price , price);

        return db.insert( TABLE_NAME , null, contentValues );
    }
}
