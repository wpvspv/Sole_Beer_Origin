package com.example.mh.sole_beer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by bicus on 2019-05-13.
 */

public class Calc_Blood_Alcohol extends Activity {
    Button btnReturn;
    boolean Sex;
    float blood_sex, blood_volume, blood_concentration, blood_weight, blood_time;
    float Now_Blood_Concentration;
    TextView FinalConcentration;
    TextView DangerText;
    ImageView DangerLevel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blood_alcohol);
        btnReturn = (Button) findViewById(R.id.ID_bloodreturn);
        FinalConcentration = (TextView) findViewById(R.id.ID_blood_FinalConcentration);
        DangerText = (TextView) findViewById(R.id.ID_Danger);
        DangerLevel = (ImageView) findViewById(R.id.ID_Danger_Level);
        Intent intent = getIntent();
        Sex = intent.getBooleanExtra("Sex", true);
        if(Sex == true)
        {
            blood_sex = 0.86f;
        }
        else{
            blood_sex = 0.64f;
        }
        blood_weight = intent.getFloatExtra("Weight",0);
        blood_volume = intent.getFloatExtra("Volume",0);
        blood_concentration = intent.getFloatExtra("Concentration", 0);
        blood_time = intent.getFloatExtra("Time", 0);

        Now_Blood_Concentration = (((blood_volume * (blood_concentration / 100) * 0.7894f) * 0.7f) /
                (blood_weight * blood_sex * 10f))                   - ((blood_time - 1.5f) * 0.015f) ;

        if(Now_Blood_Concentration <= 0)
        {
            Now_Blood_Concentration = 0;
        }
        FinalConcentration.setText(("현재 혈중 알콜 농도 : ") + Now_Blood_Concentration + "%");
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(Now_Blood_Concentration < 0.03f)
        {
            DangerText.setText("현재 혈중알코올 0.03% 미만 음주운전에 해당되지않습니다.");
            DangerLevel.setImageDrawable(getResources().getDrawable(R.drawable.blood_level_0));
        }
        else if(Now_Blood_Concentration < 0.08f && Now_Blood_Concentration >= 0.03f)
        {
            DangerText.setText("현재 혈중알코올 0.03% 이상 0.08% 이하이므로 음주운전에 해당됩니다.\n벌점 100점\n벌금 500만원 이하\n징역 1년 이하");
            DangerLevel.setImageDrawable(getResources().getDrawable(R.drawable.blood_level_1));
            Toast.makeText(this, "지나친 음주는 간암이나 간경화를 일으키며, 운전이나 작업중 사고 발생률을 높입니다.", Toast.LENGTH_LONG).show();

        }
        else if(Now_Blood_Concentration < 0.20f && Now_Blood_Concentration >= 0.08f)
        {
            DangerText.setText("현재 혈중알코올 0.08% 이상 0.2% 이하이므로 음주운전에 해당됩니다.\n면허취소 1년\n벌금 500~1000만원\n징역 1~2년");
            DangerLevel.setImageDrawable(getResources().getDrawable(R.drawable.blood_level_2));
            Toast.makeText(this, "지나친 음주는 간암이나 간경화를 일으키며, 운전이나 작업중 사고 발생률을 높입니다.", Toast.LENGTH_LONG).show();
        }
        else if(Now_Blood_Concentration > 0.2f)
        {
            DangerText.setText("현재 혈중알코올 0.2% 이상이므로 음주운전에 해당됩니다.\n면허취소 1년\n벌금 500~1000만원\n징역 2~5년");
            DangerLevel.setImageDrawable(getResources().getDrawable(R.drawable.blood_level_3));
            Toast.makeText(this, "지나친 음주는 간암이나 간경화를 일으키며, 운전이나 작업중 사고 발생률을 높입니다.", Toast.LENGTH_LONG).show();
        }
    }


}
