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

    private EditText address, pvKey;
    private Button register, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        register.setOnClickListener(v -> {
            try {
                createAccount();
            } catch (ApiException e) {
                e.printStackTrace();
            }
        });

        login.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, com.example.capstonrestorego.LoginActivity.class);
            startActivity(intent);
        });
    }

    private void initViews() {
        register = findViewById(R.id.register);
        address = findViewById(R.id.address);
        pvKey = findViewById(R.id.pvKey);
        login = findViewById(R.id.login);
    }

    private void createAccount() throws ApiException {
        CaverExtKAS caver = new CaverExtKAS();

        String accessKeyId = "KASKD9KL8U3ZZ952PD63RK4V";
        String secretAccessKey = "";
        caver.initKASAPI(1001, accessKeyId, secretAccessKey);

        Account account = caver.kas.wallet.createAccount();
        address.setText("계정주소" + account.getAddress());

        String fpvkey = account.getKeyId().substring(account.getKeyId().lastIndexOf(":"));
        pvKey.setText("개인키:" + fpvkey.substring(1));
    }
}
