package com.example.mh.sole_beer;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Popup_Choose_Drink extends Activity {

    MainActivity main;
    LinearLayout Choose_Drink; // 주류 선택할때 visible
    LinearLayout Choose_Volume; // 용량 입력할때 visible

    Boolean isVolume = false;

    TextView TitleName;
    EditText DrinkVolume;

    RadioButton Wine;
    RadioButton Makgulli;
    RadioButton Liquor;
    RadioButton Soju;
    RadioButton Beer;
    RadioButton Caktail;
    String Choosed_Drink = "None";
    int Drinktoint;
    float DrinktoFloat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    //타이틀바 없애기
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.popup_choose_drink);

    //데이터 가져오기
    Intent intent = getIntent();
    TitleName = (TextView) findViewById(R.id.ID_PopUp_TitleName);
    Wine = (RadioButton) findViewById(R.id.ID_Popup_Wine);
    Makgulli = (RadioButton) findViewById(R.id.ID_Popup_Makgulli);
    Liquor = (RadioButton) findViewById(R.id.ID_Popup_Liquor);
    Soju = (RadioButton) findViewById(R.id.ID_Popup_Soju);
    Beer = (RadioButton) findViewById(R.id.ID_Popup_Beer);
    Caktail = (RadioButton) findViewById(R.id.ID_Popup_Caktail);
    Choose_Drink = (LinearLayout) findViewById(R.id.ID_Popup_Choose_DrinkLayout);
    Choose_Volume = (LinearLayout) findViewById(R.id.ID_Popup_Choose_VolumeLayout);
    DrinkVolume = (EditText) findViewById(R.id.ID_Popup_DrinkVolume);
    DrinkVolume.setFocusableInTouchMode(true);
        Soju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choosed_Drink = "소주";
                Wine.setChecked(false);
                Makgulli.setChecked(false);
                Caktail.setChecked(false);
            }
        });
        Beer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choosed_Drink = "맥주";
                Wine.setChecked(false);
                Makgulli.setChecked(false);
                Caktail.setChecked(false);
            }
        });
        Liquor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choosed_Drink = "양주";
                Wine.setChecked(false);
                Makgulli.setChecked(false);
                Caktail.setChecked(false);
            }
        });

        Wine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choosed_Drink = "와인";
                Soju.setChecked(false);
                Liquor.setChecked(false);
                Beer.setChecked(false);
            }
        });
        Makgulli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choosed_Drink = "막걸리";
                Soju.setChecked(false);
                Liquor.setChecked(false);
                Beer.setChecked(false);
            }
        });
        Caktail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choosed_Drink = "칵테일";
                Soju.setChecked(false);
                Liquor.setChecked(false);
                Beer.setChecked(false);
            }
        });
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        if(isVolume == true && !(DrinkVolume.getText().toString().equals("0") || DrinkVolume.getText().toString().equals("")))
        // 입력된 [마신 량]을 int형으로 변경
        {
            String str = DrinkVolume.getText().toString();
            DrinktoFloat = Float.parseFloat(str); // 입력된 EditText를 Float형으로 변경
            Drinktoint = Math.round(DrinktoFloat); // float형을 정수형으로 변경
        }
        if(Choosed_Drink == "None" && isVolume == false)
            // 선택된 술이 없을때 Toast로 술을 선택해달라고 메세지
        {
            Toast.makeText(this, "술을 선택해 주세요", Toast.LENGTH_SHORT).show();
        }
        else if(isVolume == false)
            // 술을 선택하고 [확인] 버튼 클릭시 음주량 선택 화면으로 변경
        {
            isVolume = true;
            TitleName.setText("음주량 입력");
            Choose_Drink.setVisibility(View.GONE);
            Choose_Volume.setVisibility(View.VISIBLE);
            DrinkVolume.requestFocus();
        }
        else if(isVolume == true && (DrinkVolume.getText().toString().equals("0") || DrinkVolume.getText().toString().equals("")))
        {
            Toast.makeText(this, "음주량을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }



        else
        // 팝업을 닫고 액티비티에 정보 전달
        {

            //데이터 전달하기
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("Volume", Drinktoint);
            intent.putExtra("DrinkName", Choosed_Drink);
            setResult(RESULT_OK, intent);
//
//            Fragment_Drink fragment = new Fragment_Drink(); // Fragment 생성
//            Bundle bundle = new Bundle();
//            bundle.putString("DrinkName", Choosed_Drink); // Key, Value
//            fragment.setArguments(bundle);
            //액티비티(팝업) 닫기
            startActivity(intent);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
