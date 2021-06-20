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
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.Calendar;
import java.util.Date;

public class Check_Date_Record extends AppCompatActivity {

    SQLiteDatabase storedb;
    DB_Date helper;
    int total=0, sojuml, beerml, liquorml, makgulliml, coktailml, wineml;

    TextView sojudate, beerdate, liquordate, makgullidate, coktaildate, winedate, totaldate, totalmonth, monthml, masim;
    TextView sojudateml, beerdateml, liquordateml, makgullidateml, coktaildateml, winedateml;
    MaterialCalendarView cal;
    String year, month, day;
    String soju, beer, liquor, makgulli, coktail, wine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_date_record);
        helper = new DB_Date(this);

        Intent intent = getIntent();

        try {
            storedb = helper.getWritableDatabase(); //데이터베이스 객체를 얻음
        } catch (SQLiteException ex) {
            storedb = helper.getReadableDatabase();
        }
        sojudate = (TextView)findViewById(R.id.sojudate);
        sojudateml = (TextView)findViewById(R.id.sojudateml);

        beerdate = (TextView)findViewById(R.id.beerdate);
        beerdateml = (TextView)findViewById(R.id.beerdateml);

        liquordate = (TextView)findViewById(R.id.liquordate);
        liquordateml = (TextView)findViewById(R.id.liquordateml);

        makgullidate = (TextView)findViewById(R.id.makgullidate);
        makgullidateml = (TextView)findViewById(R.id.makgullidateml);

        coktaildate = (TextView)findViewById(R.id.coktaildate);
        coktaildateml = (TextView)findViewById(R.id.coktaildateml);

        winedate = (TextView)findViewById(R.id.winedate);
        winedateml = (TextView)findViewById(R.id.winedateml);

        totaldate = (TextView)findViewById(R.id.totaldate);
        totalmonth = (TextView)findViewById(R.id.totalmonth);
        monthml = (TextView)findViewById(R.id.monthml);
        masim = (TextView)findViewById(R.id.masim);

        cal = (MaterialCalendarView)findViewById(R.id.calendar);

        cal.addDecorators(
                new Decorator_Today(),
                new Decorator_SunDay(),
                new Decorator_Saturday());
        year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        month = Integer.toString(Calendar.getInstance().get(Calendar.MONTH)+1);
        day = Integer.toString(Calendar.getInstance().get(Calendar.DATE));

        Cursor cursor;
        cursor = storedb.rawQuery("SELECT * FROM contacts WHERE month='"
                + month + "';",  null);
        while(cursor.moveToNext()){
            if(cursor.getString(4).equals("0")) sojuml = 0;
            else    sojuml=Integer.parseInt(cursor.getString(4));
            if(cursor.getString(5).equals("0")) beerml = 0;
            else    beerml=Integer.parseInt(cursor.getString(
                    5));
            if(cursor.getString(6).equals("0")) liquorml = 0;
            else    liquorml=Integer.parseInt(cursor.getString(6));
            if(cursor.getString(7).equals("0")) makgulliml = 0;
            else    makgulliml=Integer.parseInt(cursor.getString(7));
            if(cursor.getString(8).equals("0")) coktailml = 0;
            else    coktailml=Integer.parseInt(cursor.getString(8));
            if(cursor.getString(9).equals("0")) wineml = 0;
            else    wineml=Integer.parseInt(cursor.getString(9));

            total = total+sojuml+beerml+liquorml+makgulliml+coktailml+wineml;
        }
        totalmonth.setText(month+"월 통계");
        if(total != 0)monthml.setText(total+"ml");
        else monthml.setText("기록 없음");


        cal.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                int total=0, sojuml, beerml, liquorml, makgulliml, coktailml, wineml;
                month = Integer.toString(date.getMonth()+1);
                Cursor cursor;
                cursor = storedb.rawQuery("SELECT * FROM contacts WHERE month='"
                        + month + "';",  null);
                while(cursor.moveToNext()){
                    if(cursor.getString(4).equals("0")) sojuml = 0;
                    else    sojuml=Integer.parseInt(cursor.getString(4));
                    if(cursor.getString(5).equals("0")) beerml = 0;
                    else    beerml=Integer.parseInt(cursor.getString(5));
                    if(cursor.getString(6).equals("0")) liquorml = 0;
                    else    liquorml=Integer.parseInt(cursor.getString(6));
                    if(cursor.getString(7).equals("0")) makgulliml = 0;
                    else    makgulliml=Integer.parseInt(cursor.getString(7));
                    if(cursor.getString(8).equals("0")) coktailml = 0;
                    else    coktailml=Integer.parseInt(cursor.getString(8));
                    if(cursor.getString(9).equals("0")) wineml = 0;
                    else    wineml=Integer.parseInt(cursor.getString(9));

                    total = total+sojuml+beerml+liquorml+makgulliml+coktailml+wineml;
                }
                totalmonth.setText(month+"월 통계");
                if(total != 0)monthml.setText(total+"ml");
                else monthml.setText("기록 없음");

                totaldate.setVisibility(View.GONE);
                masim.setVisibility(View.GONE);
                sojudate.setVisibility(View.GONE);
                sojudateml.setVisibility(View.GONE);
                beerdate.setVisibility(View.GONE);
                beerdateml.setVisibility(View.GONE);
                liquordate.setVisibility(View.GONE);
                liquordateml.setVisibility(View.GONE);
                makgullidate.setVisibility(View.GONE);
                makgullidateml.setVisibility(View.GONE);
                coktaildate.setVisibility(View.GONE);
                coktaildateml.setVisibility(View.GONE);
                winedate.setVisibility(View.GONE);
                winedateml.setVisibility(View.GONE);
            }
        });
        cal.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView materialCalendarView, CalendarDay calendarDay, boolean b) {
                year = Integer.toString(calendarDay.getYear());
                month = Integer.toString(calendarDay.getMonth()+1);
                day = Integer.toString(calendarDay.getDay());
                Cursor cursor;
                cursor = storedb.rawQuery("SELECT * FROM contacts WHERE year='"
                        + year + "';",  null);
                totaldate.setText(day+"일");

                while (cursor.moveToNext()) {
                    totaldate.setVisibility(View.VISIBLE);
                    if(cursor.getString(3).equals(day) && cursor.getString(2).equals(month)) {
                        masim.setText("");
                        masim.setVisibility(View.VISIBLE);
                        if(cursor.getString(4).equals("0")) {
                            sojudate.setVisibility(View.GONE);
                            sojudateml.setVisibility(View.GONE);
                        }
                        else {
                            soju = cursor.getString(4);
                            sojudateml.setText(soju+"ml");
                            sojudate.setVisibility(View.VISIBLE);
                            sojudateml.setVisibility(View.VISIBLE);
                        }

                        if(cursor.getString(5).equals("0")) {
                            beerdate.setVisibility(View.GONE);
                            beerdateml.setVisibility(View.GONE);
                        }
                        else {
                            beer = cursor.getString(5);
                            beerdateml.setText(beer+"cc");
                            beerdate.setVisibility(View.VISIBLE);
                            beerdateml.setVisibility(View.VISIBLE);
                        }

                        if(cursor.getString(6).equals("0")) {
                            liquordate.setVisibility(View.GONE);
                            liquordateml.setVisibility(View.GONE);
                        }
                        else {
                            liquor = cursor.getString(6);
                            liquordateml.setText(liquor+"ml");
                            liquordate.setVisibility(View.VISIBLE);
                            liquordateml.setVisibility(View.VISIBLE);
                        }

                        if(cursor.getString(7).equals("0")) {
                            makgullidate.setVisibility(View.GONE);
                            makgullidateml.setVisibility(View.GONE);
                        }
                        else {
                            makgulli = cursor.getString(7);
                            makgullidateml.setText(makgulli+"ml");
                            makgullidate.setVisibility(View.VISIBLE);
                            makgullidateml.setVisibility(View.VISIBLE);
                        }

                        if(cursor.getString(8).equals("0")) {
                            coktaildate.setVisibility(View.GONE);
                            coktaildateml.setVisibility(View.GONE);
                        }
                        else {
                            coktail = cursor.getString(8);
                            coktaildateml.setText(coktail+"ml");
                            coktaildate.setVisibility(View.VISIBLE);
                            coktaildateml.setVisibility(View.VISIBLE);
                        }

                        if(cursor.getString(9).equals("0") ){
                            winedate.setVisibility(View.GONE);
                            winedateml.setVisibility(View.GONE);
                        }
                        else {
                            wine = cursor.getString(9);
                            winedateml.setText(wine+"ml");
                            winedate.setVisibility(View.VISIBLE);
                            winedateml.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    else  {
                        masim.setText("기록 없음");
                        masim.setVisibility(View.VISIBLE);
                        sojudate.setVisibility(View.GONE);
                        sojudateml.setVisibility(View.GONE);
                        beerdate.setVisibility(View.GONE);
                        beerdateml.setVisibility(View.GONE);
                        liquordate.setVisibility(View.GONE);
                        liquordateml.setVisibility(View.GONE);
                        makgullidate.setVisibility(View.GONE);
                        makgullidateml.setVisibility(View.GONE);
                        coktaildate.setVisibility(View.GONE);
                        coktaildateml.setVisibility(View.GONE);
                        winedate.setVisibility(View.GONE);
                        winedateml.setVisibility(View.GONE);
                    }
                }

            }
        });
    }
}