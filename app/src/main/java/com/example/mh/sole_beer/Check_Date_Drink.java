package com.example.mh.sole_beer;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.Calendar;
import java.util.Date;

public class Check_Date_Drink extends AppCompatActivity {

    SQLiteDatabase storedb;
    DB_Date helper;

    int[] volumes = new int[6];
    String[] name = new String[6];
    Button store;
    MaterialCalendarView cal;
    String year, month, day;
    String text1, text2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_date);
        helper = new DB_Date(this);

        Intent intent = getIntent();
        volumes = intent.getIntArrayExtra("Drinks_Volumes");
        name = intent.getStringArrayExtra("Drinks_Names");
        Log.e("dddd", "volumes" + volumes[0] + volumes[1] + volumes[2] + volumes[3] + volumes[4] + volumes[5]);
        Log.e("ddddd 에러1", "name" + name[0] + name[1] + name[2] + name[3] + name[4] + name[5]);


        try {
            storedb = helper.getWritableDatabase(); //데이터베이스 객체를 얻음
        } catch (SQLiteException ex) {
            storedb = helper.getReadableDatabase();
        }

        store = (Button)findViewById(R.id.store);
        cal = (MaterialCalendarView)findViewById(R.id.calendar);

        cal.addDecorators(
                new Decorator_SunDay(),
                new Decorator_Saturday(),
                new Decorator_Today());
        year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        month = Integer.toString(Calendar.getInstance().get(Calendar.MONTH)+1);
        day = Integer.toString(Calendar.getInstance().get(Calendar.DATE));

        cal.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView materialCalendarView, CalendarDay calendarDay, boolean b) {
                year = Integer.toString(calendarDay.getYear());
                month = Integer.toString(calendarDay.getMonth()+1);
                day = Integer.toString(calendarDay.getDay());

                Cursor cursor;
                cursor = storedb.rawQuery("SELECT * FROM contacts WHERE day='"
                        + day + "';",  null);
                while (cursor.moveToNext()) {
                    if(cursor.getString(1).equals(year) && cursor.getString(2).equals(month)) {
                        text1 = cursor.getString(4);
                        text2 = cursor.getString(5);
                    }
                    else text1 = text2 = null;
                }

            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Soju , Beer, Makgulli, Coktail, Liquor, Wine;
                Soju = Beer= Makgulli= Coktail= Liquor= Wine= 0;
                String[] volume = new String[6];
                for(int i=0; i<6; i++){
                    if(name[i] ==null)  continue;
                    else if(name[i].equals("Soju")) Soju += volumes[i];
                    else if(name[i].equals("Beer")) Beer += volumes[i];
                    else if(name[i].equals("Liquor")) Liquor += volumes[i];
                    else if(name[i].equals("Makgulli")) Makgulli += volumes[i];
                    else if(name[i].equals("Caktail")) Coktail += volumes[i];
                    else if(name[i].equals("Wine")) Wine += volumes[i];
                }
                volume[0] = Integer.toString(Soju);
                volume[1] = Integer.toString(Beer);
                volume[2] = Integer.toString(Liquor);
                volume[3] = Integer.toString(Makgulli);
                volume[4] = Integer.toString(Coktail);
                volume[5] = Integer.toString(Wine);

                storedb.execSQL("INSERT INTO contacts VALUES (null, '" + year + "', '" + month + "','" + day + "','"
                        + volume[0] + "','" + volume[1] + "','" + volume[2] + "','" + volume[3] + "','" + volume[4] + "','" + volume[5] + "');");
                Toast.makeText(getApplicationContext(), "저장 완료", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}