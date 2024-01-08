package com.example.capstonrestorego;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import xyz.groundx.caver_ext_kas.CaverExtKAS;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.Account;

public class RegisterActivity extends AppCompatActivity {

    EditText address, pvKey;
    Button register, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        register = findViewById(R.id.register);
        address = findViewById(R.id.address);
        pvKey = findViewById(R.id.pvKey);
        login = findViewById(R.id.login);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createAccount();

                } catch (ApiException e) {


                }

            }

        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, com.example.capstonrestorego.LoginActivity.class);
                startActivity(intent);

            }
        });

    }

    public void createAccount() throws ApiException {
        CaverExtKAS caver = new CaverExtKAS();

        String accessKeyId = "KASKD9KL8U3ZZ952PD63RK4V";
        String secretAccessKey = "Tf4mRN76-gBsqDkUueywDZuQmJPZ-qdjvjMDD2Bj";
        caver.initKASAPI(1001, accessKeyId, secretAccessKey);
        Account account = caver.kas.wallet.createAccount();
        address.setText("계정주소" + account.getAddress());

        String fpvkey = account.getKeyId().substring(account.getKeyId().lastIndexOf(":"));
        //:포함 개인키 밑에건 제끼고 나열열
        pvKey.setText("개인키:" + fpvkey.substring(1));

    }

}