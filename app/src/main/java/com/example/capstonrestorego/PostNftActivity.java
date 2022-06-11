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


    ImageView image_added;
    TextView post,price,information1,information2 ;
    Json abijson= new Json();
    static String photodata=null;




    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_nft);
        if (android.os.Build.VERSION.SDK_INT > 9) { StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }




        post = findViewById(R.id.post);
        price= findViewById(R.id.price);
        information1=findViewById(R.id.information1);
        information2=findViewById(R.id.information2);
        image_added=findViewById(R.id.image_added);




        resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>()
                {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            int compressquality=90;
                            try {
                                InputStream is = getContentResolver().openInputStream(data.getData());

                                Bitmap bitmap = BitmapFactory.decodeStream(is);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                Bitmap resize=Bitmap.createScaledBitmap(bitmap,1000,1000,false);
                                resize.compress(Bitmap.CompressFormat.JPEG,100,stream);
                                while(stream.toByteArray().length/1024>32)
                                {
                                    stream.reset();
                                    resize.compress(Bitmap.CompressFormat.JPEG,compressquality,stream);
                                    compressquality=compressquality-10;
                                    if (compressquality==0)
                                    {
                                        compressquality=90;
                                    }

                                }
                                byte[] byteArray = stream.toByteArray();
                                Bitmap bitmap2 = BitmapFactory.decodeByteArray( byteArray, 0, byteArray.length );
                                data.putExtra("image", byteArray);
                                photodata=byteArrayToHex(byteArray);
                                image_added.setImageBitmap(bitmap);


                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                        }
                    }
                });

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        resultLauncher.launch(intent);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeNft("0x"+photodata);
                Toast.makeText(PostNftActivity.this, "민팅을 진행중입니다..", Toast.LENGTH_SHORT).show();
                startActivity (new Intent( PostNftActivity.this, MainActivity.class));
            }
        });

    }

    public  String byteArrayToHex(byte[] byteArray) {
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

    public  void makeNft(String photodata) {
        String baourl = "https://api.baobab.klaytn.net:8651";
        Caver caver = new Caver(baourl);



        String secondAdd="0x865df85ddfc3ebe3647bac58c6ccb61d2c8e7858";
        String myAddress = "0x819660a4d803ff6235538e312d322fe6dd8af562";
        String myPVkey="0xae77986d342aaa6f887fb0c458e82b48fed5c333b4c677e1ae9960c9e99ad9aa";


        SingleKeyring feePayer = caver.wallet.keyring.create(myAddress, myPVkey);
        caver.wallet.add(feePayer);

        String valued=price.getText().toString();
        String informationn1=information1.getText().toString();
        String informationn2=information2.getText().toString();
        if(valued.equals(null))
            valued="";
        if(informationn1.equals(null))
            informationn1="";
        if(informationn2.equals(null))
            informationn2="";




        try {
//            Contract contract=caver.contract.create(abijson.getABIjson(),"0xb67a16850c8495033e906c7dfd88d6d363db0905");
//            //contract adress가 deploy된 klaystagram임 klaytn scope에서 긁어온거 deploy된 이 주소에서 function 가져온거지
//            SendOptions sendOptions = new SendOptions();
//            sendOptions.setFrom(feePayer.getAddress());
//            sendOptions.setGas(BigInteger.valueOf(100000000));
//            sendOptions.setFeePayer(feePayer.getAddress());
//            sendOptions.setFeeDelegation(true);
//            contract.send(sendOptions,"uploadPhoto", photodata, titled, locationed,descriptiond);


            //caver extensionver..
            Intent intent1=getIntent();
            User userinfo= (User)intent1.getSerializableExtra("userinfo");

            String accessKey = "KASKD9KL8U3ZZ952PD63RK4V";
            String secretAccessKey ="Tf4mRN76-gBsqDkUueywDZuQmJPZ-qdjvjMDD2Bj";
            CaverExtKAS caver1 = new CaverExtKAS(1001, accessKey, secretAccessKey);


            String contractAddress1 = "0xb67a16850c8495033e906c7dfd88d6d363db0905";
            String executor = userinfo.getAddress();
            Contract sampleContract = new Contract(caver1, abijson.getABIjson(),contractAddress1);

            SendOptions sendOptions1 = new SendOptions(executor, BigInteger.valueOf(50000000));
            sampleContract.send(sendOptions1, "uploadPhoto", photodata, valued,informationn1,informationn2);




        } catch (IOException | TransactionException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {

        }
    }
}