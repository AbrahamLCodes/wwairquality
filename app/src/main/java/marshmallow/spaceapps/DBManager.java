package marshmallow.spaceapps;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {

    private static final String nombre = "WWAQ.bd";
    private static final int version = 1;


    //private static final String create = "CREATE TABLE PLACEMARKS(ID INTEGER PRIMARY KEY, LAT REAL, LON REAL, REGION TEXT, DENSITY REAL, MONC REAL, DION REAL, IMAGE BLOB)";
    private static final String create = "CREATE TABLE PLACEMARKS(ID INTEGER PRIMARY KEY, LAT REAL, LON REAL, REGION TEXT, DENSITY REAL, MONC REAL, DION REAL, IMAGE BLOB)";

    public DBManager(@Nullable Context context) {
        super(context, nombre, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + create);
        db.execSQL(create);
    }

    public void insert(int id, double lat, double lon, String region, double density,
                       double monc, double dion, Bitmap img) {

        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {

            byte[] data = getBitmapAsByteArray(img);

            bd.execSQL("INSERT INTO PLACEMARKS VALUES ('" + id + "','" + lat + "','" + lon + "'" +
                    ", '" + region + "', '" + density + "', '" + monc + "', '" + dion + "', '" + data + "')");

            bd.close();
        }
    }

    public long getCount() {
        SQLiteDatabase db = getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, "PLACEMARKS");
        db.close();
        return count;
    }

    public long getColumnCount() {
        SQLiteDatabase bd = getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM PLACEMARKS", null);
        for (int i = 0; i < 7; i++) {
            Log.d("AAAAAAA", cursor.getColumnName(i));
        }
        long count = cursor.getColumnCount();
        return count;
    }


    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }


    public void initList() {
        SQLiteDatabase bd = getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM PLACEMARKS", null);

        if (cursor.moveToFirst()) {
            do {
                //int, double, double, String, double, double, double, Bitmap
                Places.getLocations().add
                        (new Location(
                                cursor.getInt(0)
                                , cursor.getDouble(1)
                                , cursor.getDouble(2)
                                , cursor.getString(3)
                                , cursor.getDouble(4)
                                , cursor.getDouble(5)
                                , cursor.getDouble(6)
                                , getImage(cursor.getInt(0))));
            } while (cursor.moveToNext());
        }
    }


    public Bitmap getImage(int i) {

        SQLiteDatabase bd = getReadableDatabase();

        String query = "SELECT IMAGE FROM PLACEMARKS WHERE ID = " + i;
        Cursor cursor = bd.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            byte[] imgByte = cursor.getBlob(0);
            cursor.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return null;
    }


}
