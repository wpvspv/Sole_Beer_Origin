package com.example.mh.sole_beer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

public class Liquor_Store_Makgulri extends Activity {
    String Liquor_kind;
    SQLiteDatabase db;
    DBHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liquorstore_makgulri);
        TextView tx001_0=(TextView) findViewById(R.id.tx001_0);
        TextView tx001_1=(TextView) findViewById(R.id.tx001_1);
        TextView tx001_2=(TextView) findViewById(R.id.tx001_2);
        TextView tx001_3=(TextView) findViewById(R.id.tx001_3);
        TextView tx002_0=(TextView) findViewById(R.id.tx002_0);
        TextView tx002_1=(TextView) findViewById(R.id.tx002_1);
        TextView tx002_2=(TextView) findViewById(R.id.tx002_2);
        TextView tx002_3=(TextView) findViewById(R.id.tx002_3);
        TextView tx003_0=(TextView) findViewById(R.id.tx003_0);
        TextView tx003_1=(TextView) findViewById(R.id.tx003_1);
        TextView tx003_2=(TextView) findViewById(R.id.tx003_2);
        TextView tx003_3=(TextView) findViewById(R.id.tx003_3);
        TextView tx004_0=(TextView) findViewById(R.id.tx004_0);
        TextView tx004_1=(TextView) findViewById(R.id.tx004_1);
        TextView tx004_2=(TextView) findViewById(R.id.tx004_2);
        TextView tx004_3=(TextView) findViewById(R.id.tx004_3);
        TextView tx005_0=(TextView) findViewById(R.id.tx005_0);
        TextView tx005_1=(TextView) findViewById(R.id.tx005_1);
        TextView tx005_2=(TextView) findViewById(R.id.tx005_2);
        TextView tx005_3=(TextView) findViewById(R.id.tx005_3);
        TextView tx006_0=(TextView) findViewById(R.id.tx006_0);
        TextView tx006_1=(TextView) findViewById(R.id.tx006_1);
        TextView tx006_2=(TextView) findViewById(R.id.tx006_2);
        TextView tx006_3=(TextView) findViewById(R.id.tx006_3);
        TextView tx007_0=(TextView) findViewById(R.id.tx007_0);
        TextView tx007_1=(TextView) findViewById(R.id.tx007_1);
        TextView tx007_2=(TextView) findViewById(R.id.tx007_2);
        TextView tx007_3=(TextView) findViewById(R.id.tx007_3);
        TextView tx008_0=(TextView) findViewById(R.id.tx008_0);
        TextView tx008_1=(TextView) findViewById(R.id.tx008_1);
        TextView tx008_2=(TextView) findViewById(R.id.tx008_2);
        TextView tx008_3=(TextView) findViewById(R.id.tx008_3);
        TextView tx009_0=(TextView) findViewById(R.id.tx009_0);
        TextView tx009_1=(TextView) findViewById(R.id.tx009_1);
        TextView tx009_2=(TextView) findViewById(R.id.tx009_2);
        TextView tx009_3=(TextView) findViewById(R.id.tx009_3);
        TextView tx010_0=(TextView) findViewById(R.id.tx010_0);
        TextView tx010_1=(TextView) findViewById(R.id.tx010_1);
        TextView tx010_2=(TextView) findViewById(R.id.tx010_2);
        TextView tx010_3=(TextView) findViewById(R.id.tx010_3);
        Intent intent = getIntent();
        Liquor_kind = intent.getStringExtra("Liquor_kind");
        Toast.makeText(this, Liquor_kind, Toast.LENGTH_SHORT).show();

        helper = new DBHelper(this);
        try {
            db = helper.getWritableDatabase(); //데이터베이스 객체를 얻음
        } catch (SQLiteException ex) {
            db = helper.getReadableDatabase();
        }

        Cursor cursor = db.rawQuery("SELECT * FROM contacts WHERE kind='막걸리'",null);
        cursor.moveToFirst();
        tx001_0.setText(cursor.getString(2));
        tx001_1.setText(cursor.getString(3));
        tx001_2.setText(cursor.getString(4));
        tx001_3.setText(cursor.getString(5));
        cursor.moveToNext();
        tx002_0.setText(cursor.getString(2));
        tx002_1.setText(cursor.getString(3));
        tx002_2.setText(cursor.getString(4));
        tx002_3.setText(cursor.getString(5));
        cursor.moveToNext();
        tx003_0.setText(cursor.getString(2));
        tx003_1.setText(cursor.getString(3));
        tx003_2.setText(cursor.getString(4));
        tx003_3.setText(cursor.getString(5));
        cursor.moveToNext();
        tx004_0.setText(cursor.getString(2));
        tx004_1.setText(cursor.getString(3));
        tx004_2.setText(cursor.getString(4));
        tx004_3.setText(cursor.getString(5));
        cursor.moveToNext();
        tx005_0.setText(cursor.getString(2));
        tx005_1.setText(cursor.getString(3));
        tx005_2.setText(cursor.getString(4));
        tx005_3.setText(cursor.getString(5));
        cursor.moveToNext();
        tx006_0.setText(cursor.getString(2));
        tx006_1.setText(cursor.getString(3));
        tx006_2.setText(cursor.getString(4));
        tx006_3.setText(cursor.getString(5));
        cursor.moveToNext();
        tx007_0.setText(cursor.getString(2));
        tx007_1.setText(cursor.getString(3));
        tx007_2.setText(cursor.getString(4));
        tx007_3.setText(cursor.getString(5));
        cursor.moveToNext();
        tx008_0.setText(cursor.getString(2));
        tx008_1.setText(cursor.getString(3));
        tx008_2.setText(cursor.getString(4));
        tx008_3.setText(cursor.getString(5));
        cursor.moveToNext();
        tx009_0.setText(cursor.getString(2));
        tx009_1.setText(cursor.getString(3));
        tx009_2.setText(cursor.getString(4));
        tx009_3.setText(cursor.getString(5));
        cursor.moveToNext();
        tx010_0.setText(cursor.getString(2));
        tx010_1.setText(cursor.getString(3));
        tx010_2.setText(cursor.getString(4));
        tx010_3.setText(cursor.getString(5));
    }
}
