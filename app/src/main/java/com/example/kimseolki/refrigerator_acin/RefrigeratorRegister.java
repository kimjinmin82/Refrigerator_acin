package com.example.kimseolki.refrigerator_acin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kimseolki.refrigerator_acin.model.PFood;
import com.example.kimseolki.refrigerator_acin.service.PostFoods;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by userpc on 2017-04-21.
 */

public class RefrigeratorRegister extends AppCompatActivity {
    private static final String TAG = "RefrigeratorRegister";

    private int TAKE_CAMERA = 1; // 카메라 리턴 코드값 설정
    private int TAKE_GALLERY = 2; // 앨범선택에 대한 리턴 코드값 설정

    public Spinner type;
    public EditText name;
    public EditText purcharse;
    public EditText exdate;
    public Spinner location;
    private Button btn_qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodregister);
        type = (Spinner) findViewById(R.id.sptype);
        name = (EditText) findViewById(R.id.etname);
        purcharse = (EditText) findViewById(R.id.etpurcharse);
        exdate = (EditText) findViewById(R.id.etexdate);
        location = (Spinner) findViewById(R.id.splocation);
        Log.d(TAG, "register");
        final Activity activity = this;
        ActionBar actionBar = getSupportActionBar();
//        type = (Spinner) findViewById(R.id.sptype);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.FOOD_TYPES, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        location = (Spinner) findViewById(R.id.splocation);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.FOOD_LOCATIONS, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(adapter1);
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Button btncamera = (Button) findViewById(R.id.btn_takePicture);
        Button btnlist = (Button) findViewById(R.id.btn_getAlbum);
        btncamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_CAMERA);
            }
        });
        btnlist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, TAKE_GALLERY);
            }
        });

        btn_qrcode = (Button) findViewById(R.id.btn_qrCode);
        btn_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator Integrator = new IntentIntegrator(activity);
                Integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                Integrator.setPrompt("Scan");
                Integrator.setCameraId(0);
                Integrator.setBeepEnabled(false);
                Integrator.setBarcodeImageEnabled(false);
                Integrator.initiateScan();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "없쩌", Toast.LENGTH_LONG).show();
            } else {
                String qr = result.getContents();
                String[] qrarray;
                qrarray = qr.split(";");
                name.setText(qrarray[1]);
                ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.FOOD_TYPES,android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                type.setAdapter(adapter);
                if(qrarray[0].equals("육류")) {
                    type.setSelection(0);
                }
                else if (qrarray[0].equals("어류")){
                    type.setSelection(1);
                }
                else if (qrarray[0].equals("과일류")){
                    type.setSelection(2);
                }
                else if (qrarray[0].equals("야채류")){
                    type.setSelection(3);
                    Log.d(TAG, "야채류오나");
                    Log.d(TAG, qrarray[0]);
                }
                else if (qrarray[0].equals("유제품류")){
                    type.setSelection(4);
                }
                else if (qrarray[0].equals("음료류")){
                    type.setSelection(5);
                }
                else if (qrarray[0].equals("소스류")){
                    type.setSelection(6);
                }

                purcharse.setText(qrarray[2]);
                exdate.setText(qrarray[3]);

                ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.FOOD_LOCATIONS,android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                location.setAdapter(adapter1);
                if(qrarray[4].equals("냉장")) {
                    location.setSelection(0);
                }
                else if (qrarray[4].equals("냉동")){
                    location.setSelection(1);
                }
                else if (qrarray[4].equals("실온")){
                    location.setSelection(2);
                }

            }
        }
        if (resultCode == RESULT_OK)
            if (requestCode == TAKE_CAMERA) {
                if( data != null )
                {
                    Log.e("Test", "result = " + data);
                    Bitmap thumbnail = (Bitmap)data.getExtras().get("data");
                    if( thumbnail != null )
                    {
                        ImageView Imageview = (ImageView) findViewById(R.id.imageView);
                        Imageview.setImageBitmap(thumbnail);
                    }
                }

            } else if (requestCode == TAKE_GALLERY) {
                if( data != null )
                {
                    Log.e("Test", "result = " + data);

                    Uri thumbnail = data.getData();
                    if( thumbnail != null )
                    {
                        ImageView Imageview = (ImageView) findViewById(R.id.imageView);
                        Imageview.setImageURI(thumbnail);
                    }
                }
            }
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_register, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.atregister:
                PFood pFood = new PFood(
                        type.getSelectedItem().toString(),
                        name.getText().toString(),
                        purcharse.getText().toString(),
                        exdate.getText().toString(),
                        location.getSelectedItem().toString()
                );
                sendNetworkRequest(pFood);
                return true;
            case android.R.id.home:
                Log.d(TAG, "onOptionsItemSelected:android.R.id.home");
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void sendNetworkRequest(PFood pFood) {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.63.110:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build());
        Retrofit retrofit = builder.build();
        PostFoods client = retrofit.create(PostFoods.class);
        Call<PFood> call= client.createAccount(pFood);

        call.enqueue(new Callback<PFood>() {
            @Override
            public void onResponse(Call<PFood> call, Response<PFood> response) {
                Toast.makeText(RefrigeratorRegister.this, "onResponse" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PFood> call, Throwable t) {
                Toast.makeText(RefrigeratorRegister.this, "onFailure:(",Toast.LENGTH_SHORT).show();
            }
        });
    }


}