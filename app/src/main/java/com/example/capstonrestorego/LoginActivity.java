package com.example.capstonrestorego;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.List;

import xyz.groundx.caver_ext_kas.CaverExtKAS;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.Account;

public class LoginActivity extends AppCompatActivity {


    EditText pvKey;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) { StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }

        pvKey=findViewById(R.id.pvKey);
        login= findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("please wait....");
                pd.show();

                String KeyArea=pvKey.getText().toString();

                if (TextUtils.isEmpty(KeyArea))
                {
                    Toast.makeText(LoginActivity.this, "ALL Fields are required", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    if(pvKey.getText().toString().length()>63)
                    {
                        try
                        {
                            Accountmatch();

                            pd.dismiss();
                        }
                        catch(ApiException e)
                        {

                        }
                    }

                }


            }
        });

    }

    public void Accountmatch() throws ApiException
    {
        CaverExtKAS caver = new CaverExtKAS();

        String accessKeyId="KASKD9KL8U3ZZ952PD63RK4V";
        String secretAccessKey="Tf4mRN76-gBsqDkUueywDZuQmJPZ-qdjvjMDD2Bj";
        caver.initKASAPI(1001, accessKeyId, secretAccessKey);
        List<Account> account = caver.kas.wallet.getAccountList().getItems();
        //저걸 두번 가공해야함 get(0)부터 마지막 인덱스 까지 반복문 돌리면된다.
        for(int i=0; i<caver.kas.wallet.getAccountList().getItems().size()-1; i++)
        {
            String Tpvkey=account.get(i).getKeyId().substring(account.get(3).getKeyId().lastIndexOf(":")).substring(1);
            if(pvKey.getText().toString().equals(Tpvkey))
            {

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                Toast.makeText(LoginActivity.this, "잠시만 기다려주세요!", Toast.LENGTH_SHORT).show();
                finish();
                break;

            }
        }

        Toast.makeText(LoginActivity.this, "없는계정입니다!", Toast.LENGTH_SHORT).show();





    }
}