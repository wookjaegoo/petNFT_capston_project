package com.example.capstonrestorego;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.capstonrestorego.Model.Json;
import com.example.capstonrestorego.Model.Post;
import com.example.capstonrestorego.Model.User;
import com.example.capstonrestorego.Model.UserInfo;
import com.klaytn.caver.Caver;
import com.klaytn.caver.contract.Contract;
import com.klaytn.caver.contract.SendOptions;
import com.klaytn.caver.wallet.keyring.SingleKeyring;
import com.squareup.okhttp.OkHttpClient;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import xyz.groundx.caver_ext_kas.CaverExtKAS;


public class PostNftActivity extends AppCompatActivity {

    private ImageView image_added;
    private TextView post, price, information1, information2;
    private Json abijson = new Json();
    private static String photodata = null;
    private UserInfo userInfo;

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    handleImageResult(result.getData());
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_nft);

        initViews();

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        resultLauncher.launch(intent);

        post.setOnClickListener(view -> {
            makeNft("0x" + photodata);
            Toast.makeText(PostNftActivity.this, "민팅을 진행중입니다..", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostNftActivity.this, MainActivity.class));
        });
    }

    private void initViews() {
        post = findViewById(R.id.post);
        price = findViewById(R.id.price);
        information1 = findViewById(R.id.information1);
        information2 = findViewById(R.id.information2);
        image_added = findViewById(R.id.image_added);
    }

    private void handleImageResult(Intent data) {
        int compressquality = 90;
        try {
            InputStream is = getContentResolver().openInputStream(data.getData());
            Bitmap bitmap = BitmapFactory.decodeStream(is);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap resize = Bitmap.createScaledBitmap(bitmap, 1000, 1000, false);
            resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            while (stream.toByteArray().length / 1024 > 32) {
                stream.reset();
                resize.compress(Bitmap.CompressFormat.JPEG, compressquality, stream);
                compressquality = compressquality - 10;
                if (compressquality == 0) {
                    compressquality = 90;
                }
            }

            byte[] byteArray = stream.toByteArray();
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            data.putExtra("image", byteArray);
            photodata = byteArrayToHex(byteArray);
            image_added.setImageBitmap(bitmap);

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private String byteArrayToHex(byte[] byteArray) {
        if (byteArray == null || byteArray.length == 0) {
            return null;
        }
        StringBuilder stringBuffer = new StringBuilder(byteArray.length * 2);
        String hexNumber;
        for (byte aBa : byteArray) {
            hexNumber = "0" + Integer.toHexString(0xff & aBa);
            stringBuffer.append(hexNumber.substring(hexNumber.length() - 2));
        }
        return stringBuffer.toString();
    }

    private void makeNft(String photodata) {
        String baourl = "https://api.baobab.klaytn.net:8651";
        Caver caver = new Caver(baourl);

        String secondAdd = "0x865df85ddfc3ebe3647bac58c6ccb61d2c8e7858";
        String myAddress = "0x819660a4d803ff6235538e312d322fe6dd8af562";
        String myPVkey = "";

        SingleKeyring feePayer = caver.wallet.keyring.create(myAddress, myPVkey);
        caver.wallet.add(feePayer);

        String value = price.getText().toString();
        String informationn1 = information1.getText().toString();
        String informationn2 = information2.getText().toString();

        if (value.equals(null)) value = "";
        if (informationn1.equals(null)) informationn1 = "";
        if (informationn2.equals(null)) informationn2 = "";

        try {
            userInfo = UserInfo.getInstance();

            String accessKey = "KASKD9KL8U3ZZ952PD63RK4V";
            String secretAccessKey = "";
            CaverExtKAS caver1 = new CaverExtKAS(1001, accessKey, secretAccessKey);

            String contractAddress1 = "0xb67a16850c8495033e906c7dfd88d6d363db0905";
            String executor = userInfo.getUseradd();
            Contract sampleContract = new Contract(caver1, abijson.getABIjson(), contractAddress1);

            SendOptions sendOptions1 = new SendOptions(executor, BigInteger.valueOf(50000000));
            sampleContract.send(sendOptions1, "uploadPhoto", photodata, value, informationn1, informationn2);

        } catch (IOException | TransactionException | ClassNotFoundException |
                 NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
