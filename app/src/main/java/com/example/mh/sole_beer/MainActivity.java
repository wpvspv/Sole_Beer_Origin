package com.example.mh.sole_beer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.exception.KakaoException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DB_Date extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "prac.db";
    private static final int DATABASE_VERSION = 2;
    public DB_Date (Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contacts ( _id INTEGER PRIMARY KEY AUTOINCREMENT, year TEXT, month TEXT, day TEXT, soju TEXT, beer TEXT, liquor TEXT, makgulli TEXT, coktail TEXT, wine TEXT);");
        //db.execSQL("CREATE TABLE contacts ( _id INTEGER PRIMARY KEY AUTOINCREMENT, year TEXT, month TEXT, day TEXT);");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }
}
class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "alcohol.db";
    private static final int DATABASE_VERSION = 1;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contacts ( _id INTEGER PRIMARY KEY AUTOINCREMENT, kind TEXT, name TEXT, ABV NUMERIC, vintage INTEGER, country TEXT);");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }
}

@SuppressWarnings("deprecation")
    public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DBHelper helper;
    SQLiteDatabase db;

    private static final int MY_PERMISSION_STORAGE = 1111;
    private static final int REQUEST_TAKE_PHOTO = 2222;
    private static final int REQUEST_TAKE_ALBUM = 3333;
    private static final int REQUEST_IMAGE_CROP = 4444;

    String mCurrentPhotoPath;
    String dateString;
    Uri imageUri;
    Uri photoURI, albumURI;
    Uri CaptrueUri;


    // ?????? ?????? ?????? ??????
    ImageView Now_liquor_Image;
    TextView Now_Volume_text;
    TextView Now_Volumezan_text;
    ImageButton Btn_SojuCup,Btn_Initialization,Btn_OneShot;
    int Now_liquor_volume_beer;
    int Now_liquor_volume_soju;
    int Now_liquor_volume;
    String Now_liquor = "Soju";
    ImageButton Btn_Camera;
    ImageButton Btn_album;
    ImageButton Btn_kakaoLink;
    // ??????
    FrameLayout container;
    int NowGetNumber = 0;
    int[] GetVolume = new int[6];
    String[] GetDrinkName = new String[6];


    // ??????????????? ?????? ??????
    RadioGroup Blood_RadioGroup;
    RadioButton Blood_Man;
    RadioButton Blood_Woman;
    Boolean Blood_Sex = true;
    EditText Blood_Weight;
    EditText Blood_Volume;
    EditText Blood_Con;
    EditText Blood_Time;
    String string;
    Button btnBlood;

    float blood_volume, blood_concentration, blood_weight, blood_time;

    // ??? ????????? ?????? ??????
    ImageButton Btn_KoreaBeer;
    ImageButton Btn_OverseasBeer;
    ImageButton Btn_Soju;
    ImageButton Btn_Cacktail;
    ImageButton Btn_Makgulri;
    ImageButton Btn_Wine;

    Fragment fragment;
    String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        //????????? ?????? ???????????? ?????? ?????? ??????????????? ??????.
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);


        for(int i = 0; i< 6; i++)
        {
            GetDrinkName[i] = sf.getString("Save_DrinkName"  + i, "");
            GetVolume[i] = sf.getInt("Save_Volumes" + i, 0);
        }


        for(int i = 0; i < 6; i++)
        {
            if(intent.getExtras() != null && GetDrinkName[i].equals(""))
            {
                GetDrinkName[i] = intent.getExtras().getString("DrinkName");
                GetVolume[i]  = intent.getExtras().getInt("Volume");
                Log.e("???????????????.", "?????? i " + i);
                break;
            }
        }

        // Activity??? ???????????? ?????? ????????????.
        //SharedPreferences??? sFile??????, ??????????????? ??????
        SharedPreferences sharedPreferences = getSharedPreferences("sFile",MODE_PRIVATE);

        //????????? ???????????? editor??? ???????????? ?????? ??????????????????.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String[] Save_DrinkName = new String[6];   // ???????????? ????????? ????????? ?????????
        int[] Save_Volumes = new int[6];
        for(int i =0 ; i< 6 ; i++)
        {
            Save_DrinkName[i] = GetDrinkName[i];
            editor.putString("Save_DrinkName" + i,Save_DrinkName[i]);
            Save_Volumes[i] = GetVolume[i];
            editor.putInt("Save_Volumes" + i,Save_Volumes[i]);
        }

        //?????? ??????
        editor.commit();

        fragment = new Fragment_Drink();
        setContentView(R.layout.activity_main);
        title = getString(R.string.app_name);
        title = "????????????";
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.conent_fragment_layout, fragment);
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        helper = new DBHelper(this);
        try {
            db = helper.getWritableDatabase(); //?????????????????? ????????? ??????
        } catch (SQLiteException ex) {
            db = helper.getReadableDatabase();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Btn_Camera = (ImageButton) findViewById(R.id.ID_btn_Camera);
        Btn_album = (ImageButton) findViewById(R.id.btn_album);
        Btn_kakaoLink = (ImageButton) findViewById(R.id.ID_kakaorink);
        // ?????? ????????? ?????????
        // ??????????????? ?????? ??????
        Blood_RadioGroup = (RadioGroup) findViewById(R.id.ID_blood_radiogroup);
        Blood_Man = (RadioButton) findViewById(R.id.ID_blood_man);
        Blood_Woman = (RadioButton) findViewById(R.id.ID_blood_woman);

        Blood_Weight = (EditText) findViewById(R.id.ID_blood_weight);
        Blood_Volume = (EditText) findViewById(R.id.ID_blood_volume);
        Blood_Con = (EditText) findViewById(R.id.ID_blood_Concentration);
        Blood_Time = (EditText) findViewById(R.id.ID_blood_time);

        btnBlood = (Button) findViewById(R.id.ID_blood_check);
        // ??? ????????? ?????? ??????
        Btn_KoreaBeer = (ImageButton) findViewById(R.id.ID_KoreaBeer);
        Btn_OverseasBeer = (ImageButton) findViewById(R.id.ID_OverSeasBeer);
        Btn_Soju = (ImageButton) findViewById(R.id.ID_Soju);
        Btn_Cacktail = (ImageButton) findViewById(R.id.ID_Cacktail);
        Btn_Makgulri = (ImageButton) findViewById(R.id.ID_Makgulri);
        Btn_Wine = (ImageButton) findViewById(R.id.ID_Wine);
        checkPermission();


    }
    public void removePrefs()
    {
        for(int i = 0; i< 6; i++)
        {
            GetDrinkName[i] = "";
            GetVolume[i] = 0;
        }
        SharedPreferences pref = getSharedPreferences("sFile", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public void onStop() {
        super.onStop();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        int id = item.getItemId();

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        if (id == R.id.Estimate_Drink_Menu_Button) {
            fragment = new Fragment_Drink();
            title = "?????? ??????";
        } else if (id == R.id.Estimate_Blood_Menu_Button) {
            fragment = new Fragment_Blood();
            title = "??????????????? ??????";
        } else if (id == R.id.Liquor_Store_Menu_Button) {
            fragment = new Fragment_Liquor_Store();
            title = "??? ?????????";
        } else if (id == R.id.Drink_Average_Menu_Button) {
            Intent intent = new Intent(getApplicationContext(), Check_Date_Record.class);
            startActivity(intent);
            title = "?????? ??????";
        } else if(id == R.id.kakao_Link_Nav) {
            View view = getWindow().getDecorView() ;
            mOnCaptureClick(view);
        } else if(id == R.id.nav_send) {
            captureCamera();
        }
        if(fragment != null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.conent_fragment_layout, fragment);
            ft.commit();
        }
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    private  void checkPermission()
    // ?????? ??????
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                new AlertDialog.Builder(this)
                        .setTitle("??????")
                        .setMessage("????????? ????????? ?????????????????????. ????????? ???????????? ???????????? ?????? ????????? ?????? ??????????????? ?????????.")
                        .setNeutralButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("Package : " + getPackageName() ));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_STORAGE);
            }
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    // ?????? ?????? ??????
    {
        switch (requestCode)
        {
            case MY_PERMISSION_STORAGE:
                for(int i =0; i < grantResults.length;i++)
                {
                    if(grantResults[i] < 0)
                    {
                        Toast.makeText(this, "?????? ????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
        }
    }

    public void captureCamera()
    {
        String state = Environment.getExternalStorageState();
        // ?????? ????????? ??????
        if(Environment.MEDIA_MOUNTED.equals(state))
        {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePictureIntent.resolveActivity(getPackageManager()) != null)
            {
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                } catch (IOException ex){
                    Log.e("captureCamera Error", ex.toString());
                }
                if(photoFile != null)
                {
                    // getUriForFile??? ????????? ????????? Manifest provier??? authorites??? ???????????????
                    Uri providerURI = FileProvider.getUriForFile(this, "com.shuvic.alumni.camera_album", photoFile);
                    imageUri = providerURI;
                    // ???????????? ????????? ?????? FileProvier??? Return ?????? content://??????!!, provierURI ?????? ????????? ???????????? ?????? ??????
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);

                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }

        }
        else
        {
            Toast.makeText(this, "??????????????? ?????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show();
            return;
        };
    }
    public File createImageFile() throws  IOException{
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "Sole_Drink");
        if(!storageDir.exists())
        {
            Log.i("mCurrentPhotoPath1", storageDir.toString());
            storageDir.mkdir();
        }
        imageFile = new File(storageDir, imageFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
    }
    public void getAlbum()
    {
        Log.i("getAlbum", "Call");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }
    public void galleryAddPic()
    {
        Log.i("galleryAddPic","Call");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // ?????? ????????? ?????? ????????? ????????? (?????? ????????? ???????????? ????????? ???????????? ??? ???)
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "????????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
    }
    public void cropImage()
    {
        Log.i("cropImage", "Call");
        Log.i("cropImage", "photoURI : " + photoURI + " / albumURI : "  + albumURI);
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        // 50x50 ?????? ????????? ????????? ??? ????????? ?????? ?????? + ?????????, ?????? ?????? ???????????? ??????
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(photoURI, "image/*");
        // cropIntent.putExtra("outputX",200);   =>  crop??? ???????????? x??? ??????.
        // cropIntent.putExtra("outputY", 200);  =>  crop??? ???????????? y??? ??????.
        cropIntent.putExtra("aspectX",1);
        cropIntent.putExtra("aspectY",1);
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("output", albumURI);
        startActivityForResult(cropIntent,REQUEST_IMAGE_CROP);
    }
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Log.i("REQUEST_TAKE_PHOTO", "OK");
                        galleryAddPic();


                    } catch (Exception e) {
                        Log.e("REQUEST_TAKE_PHOTO", e.toString());
                    }
                }
                else {
                    Toast.makeText(this, "??????????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_TAKE_ALBUM:
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getData() != null) {
                        try {
                            File albumFile = null;
                            albumFile = createImageFile();
                            photoURI = data.getData();
                            albumURI = Uri.fromFile(albumFile);
                            cropImage();
                        } catch (Exception e) {
                            Log.e("TAKE_ALBUM_SINGLE ERROR", e.toString());
                        }
                    }
                }
                break;
            case REQUEST_IMAGE_CROP:
                if (resultCode == Activity.RESULT_OK) {
                    galleryAddPic();
                }
                break;
        }
    }

    public void sendKaKao()
    // ???????????? ??????????????? ?????????
    {

        String message = "[?????? ??????]\n ?????? ?????? ?????? ?????????";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message );
        intent.setPackage("com.kakao.talk");
        startActivity(intent);
        sendImageKakao(CaptrueUri);
    }
    public void sendImageKakao(Uri uri)
    // ???????????? ????????? ?????? ????????? ?????????
    {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setPackage("com.kakao.talk");
        startActivity(Intent.createChooser(intent, "Choose"));
    }

    public void mOnCaptureClick(View v)
    // ??????????????????
    {

        View rootView = getWindow().getDecorView();
        File screenShot = ScreenShot(rootView);
        if(screenShot!=null){
            //???????????? ??????
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(screenShot)));
        }


        sendKaKao();
    }

    public File ScreenShot(View view)
    //?????? ????????????
    {
        view.setDrawingCacheEnabled(true);  //????????? ????????? ????????? ???????????? ??????
        Bitmap screenBitmap = view.getDrawingCache();   //????????? ??????????????? ??????
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");// ??????????????????
        Date currentTime_1 = new Date();
        dateString = formatter.format(currentTime_1) + ".jpg"; // ?????? ?????? ????????? ???????????? ??????

        File file = new File(Environment.getExternalStorageDirectory()+"/Pictures/Sole_Drink", dateString);  //Pictures?????? screenshot.png ??????

        Toast.makeText(this, Environment.getExternalStorageDirectory() + "/Pictures/Sole_Drink" + dateString, Toast.LENGTH_SHORT).show();

        File dirName = new File(Environment.getExternalStorageDirectory()+"/Pictures/Sole_Drink");  //??????????????? ???????????????.
        String name = dateString; //????????? ????????? ?????? ???
        File Final = new File(dirName, name); //image ????????? ????????? ???????????????
        CaptrueUri = Uri.fromFile(Final);
        FileOutputStream os = null;
        try{
            os = new FileOutputStream(file);
            screenBitmap.compress(Bitmap.CompressFormat.PNG, 90, os);   //???????????? PNG????????? ??????
            os.close();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        view.setDrawingCacheEnabled(false);

        // CaptrueUri = Uri.fromFile(file); // savefile??? MMS??? ?????? ????????? ????????? File ????????? ???????????????.
        Toast.makeText(this, "" + CaptrueUri, Toast.LENGTH_SHORT).show();
        return file;
    }

}