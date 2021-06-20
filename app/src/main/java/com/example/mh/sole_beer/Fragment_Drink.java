package com.example.mh.sole_beer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Fragment_Drink extends Fragment {
    private SharedPreferences appData;

    MainActivity main;
    String mCurrentPhotoPath;
    String dateString;
    Uri imageUri;
    Uri photoURI, albumURI;
    Uri CaptrueUri;


    // 주량 측정 사용 코드
    ImageButton Btn_Camera;
    ImageButton Btn_album;
    ImageButton Btn_kakaoLink;
    ImageView ID_Camera;
    ImageButton PopupButton;
    Button PopupRefreshButton;

    TextView[] Names = new TextView[6];
    TextView SecondName;
    TextView ThirdName;
    TextView FourName;
    TextView FiveName;
    TextView SixName;

    TextView[] Volumes = new TextView[6];
    Button save_Button;

    int NowDrinkNumber = 0;
    int[] GetVolume = new int[6];
    String[] GetDrinkName = new String[6];
    LinearLayout[] Layout = new LinearLayout[6];
    // 임시
    final FrameLayout container = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.estimate_drink_layout, container, false);
        Btn_Camera = (ImageButton) view.findViewById(R.id.ID_btn_Camera);
        Btn_album = (ImageButton) view.findViewById(R.id.btn_album);
        Btn_kakaoLink = (ImageButton) view.findViewById(R.id.ID_kakaorink);
        container = (FrameLayout) view.findViewById(R.id.conent_fragment_layout);
        PopupButton = (ImageButton) view.findViewById(R.id.ID_PopupBtn_drink);
        PopupRefreshButton = (Button) view.findViewById(R.id.ID_Popup_RefreshButton);

        Names[0] = (TextView) view.findViewById(R.id.ID_First_Drink_Name);
        Names[1] = (TextView) view.findViewById(R.id.ID_Second_Drink_Name);
        Names[2] = (TextView) view.findViewById(R.id.ID_Third_Drink_Name);
        Names[3] = (TextView) view.findViewById(R.id.ID_Four_Drink_Name);
        Names[4] = (TextView) view.findViewById(R.id.ID_Five_Drink_Name);
        Names[5] = (TextView) view.findViewById(R.id.ID_Six_Drink_Name);
        Volumes[0] = (TextView) view.findViewById(R.id.ID_First_Drink_Volume);
        Volumes[1] = (TextView) view.findViewById(R.id.ID_Second_Drink_Volume);
        Volumes[2] = (TextView) view.findViewById(R.id.ID_Third_Drink_Volume);
        Volumes[3] = (TextView) view.findViewById(R.id.ID_Four_Drink_Volume);
        Volumes[4] = (TextView) view.findViewById(R.id.ID_Five_Drink_Volume);
        Volumes[5] = (TextView) view.findViewById(R.id.ID_Six_Drink_Volume);
        Layout[0] = (LinearLayout) view.findViewById(R.id.ID_First_Drink_Layout);
        Layout[1] = (LinearLayout) view.findViewById(R.id.ID_Second_Drink_Layout);
        Layout[2] = (LinearLayout) view.findViewById(R.id.ID_Third_Drink_Layout);
        Layout[3] = (LinearLayout) view.findViewById(R.id.ID_Four_Drink_Layout);
        Layout[4] = (LinearLayout) view.findViewById(R.id.ID_Five_Drink_Layout);
        Layout[5] = (LinearLayout) view.findViewById(R.id.ID_Six_Drink_Layout);

        save_Button = (Button) view.findViewById(R.id.Save_Button);
        for (int i = 0; i < 6; i++) {
            if (((MainActivity) getActivity()).GetVolume[i] != 0) {
                NowDrinkNumber++;
                GetVolume[i] = ((MainActivity) getActivity()).GetVolume[i];
                Layout[i].setVisibility(View.VISIBLE);

            }
            if (!((MainActivity) getActivity()).GetDrinkName[i].equals("")) {
                GetDrinkName[i] = ((MainActivity) getActivity()).GetDrinkName[i];
            }
        }

        for (int i = 0; i < NowDrinkNumber; i++)
        {
            Names[i].setText(GetDrinkName[i]);
            if(GetDrinkName[i].equals("소주"))
            {
                Names[i].setTextColor(Color.rgb(0,128,0));
            }
            else if(GetDrinkName[i].equals("맥주"))
            {
                Names[i].setTextColor(Color.rgb(255,153,0));
            }
            else if(GetDrinkName[i].equals("양주"))
            {
                Names[i].setTextColor(Color.rgb(102,51,0));
            }
            else if(GetDrinkName[i].equals("막걸리"))
            {
                Names[i].setTextColor(Color.rgb(255,255,204));
            }
            else if(GetDrinkName[i].equals("칵테일"))
            {
                Names[i].setTextColor(Color.rgb(102,255,102));
            }
            else if(GetDrinkName[i].equals("와인"))
            {
                Names[i].setTextColor(Color.rgb(130,0,40));
            }
            Volumes[i].setText(" " + GetVolume[i]);
        }
        Btn_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).captureCamera();
            }
        });
        Btn_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).getAlbum();
            }
        });
        Btn_kakaoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).mOnCaptureClick(view);
            }
        });
        PopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Popup_Choose_Drink.class);
                startActivityForResult(intent, 1);
            }
        });
        PopupRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).removePrefs();
                for (int i = 0; i < 6; i++) {
                    GetVolume[i] = 0;
                    GetDrinkName[i] = "";
                    Layout[i].setVisibility(View.GONE);
                    Names[i].setText(GetDrinkName[i]);
                    Volumes[i].setText(" " + GetVolume[i]);
                }
            }
        });

        save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Check_Date_Drink.class);

                        intent.putExtra("Drinks_Volumes", GetVolume);
                        intent.putExtra("Drinks_Names", GetDrinkName);
                startActivity(intent);

            }
        });

        getActivity().setTitle("음주 기록");
        return view;
    }
}

