package app.geniuslab.beer.connection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import app.geniuslab.beer.model.Beer;

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
    public List<Beer> getList(SQLiteDatabase db){
        List<Beer> beers = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME,null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Beer beer = new Beer(
                        cursor.getInt(cursor.getColumnIndex(col_id)),
                        cursor.getString(cursor.getColumnIndex(col_name)),
                        cursor.getString(cursor.getColumnIndex(col_image)),
                        cursor.getString(cursor.getColumnIndex(col_price))
                );

                beers.add(beer);
                cursor.moveToNext();
            }
        }

        return beers;
    }

    public void updateBeer(Beer beer,SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put(col_name,beer.getName()); //These Fields  be your String values of actual column nam
        cv.put(col_price,beer.getPrice());
        db.update(TABLE_NAME, cv, "id="+beer.getId(), null);

    }

}
