package com.example.crudapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHelperClass extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "employee_db";
    private static final String TABLE_NAME = "employee";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";

    private static final String KEY_IMAGE = "image";
    private SQLiteDatabase sqLiteDatabase;

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT NOT NULL," + EMAIL + " TEXT NOT NULL,"+ KEY_IMAGE + " TEXT);";

    public DatabaseHelperClass(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addEmployee(EmployeeModelClass employeeModelClass){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperClass.NAME,employeeModelClass.getName());
        contentValues.put(DatabaseHelperClass.EMAIL,employeeModelClass.getEmail());
        contentValues.put(DatabaseHelperClass.KEY_IMAGE, employeeModelClass.getImage());
        Log.d("imagename",employeeModelClass.getImage());
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(DatabaseHelperClass.TABLE_NAME,null,contentValues);
    }

    public ArrayList<HashMap<String,String>> getEmployeeList(){
        String sql = " SELECT * from " + TABLE_NAME;
        sqLiteDatabase = this.getReadableDatabase();
        ArrayList<HashMap<String,String>> storeEmployee = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do {
                HashMap<String,String> user = new HashMap<>();
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String email = cursor.getString(2);
                String image = cursor.getString(3);

                user.put("name",name);
                user.put("email",email);
                user.put("image",image);
                storeEmployee.add(user);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeEmployee;
    }
}
