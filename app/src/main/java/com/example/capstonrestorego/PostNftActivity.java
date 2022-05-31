package com.example.capstonrestorego;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstonrestorego.Model.Json;
import com.example.capstonrestorego.Model.Post;
import com.klaytn.caver.Caver;
import com.klaytn.caver.contract.Contract;
import com.klaytn.caver.contract.SendOptions;
import com.klaytn.caver.wallet.keyring.SingleKeyring;

import org.web3j.protocol.exceptions.TransactionException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.List;

import xyz.groundx.caver_ext_kas.CaverExtKAS;


public class PostNftActivity extends AppCompatActivity {


    TextView post ;
    private static List<Post> mPost;
    public static Post mpost;
    Json abijson= new Json();



    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_nft);
        if (android.os.Build.VERSION.SDK_INT > 9) { StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }

        post = findViewById(R.id.post);


        resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>()
                {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            final int MAX_IMAGE_SIZE=28000;
                            int compressquality=100;
                            try {
                                InputStream is = getContentResolver().openInputStream(data.getData());

                                Bitmap bitmap = BitmapFactory.decodeStream(is);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                Bitmap resize=Bitmap.createScaledBitmap(bitmap,1000,1000,false);
                                resize.compress(Bitmap.CompressFormat.JPEG,10,stream);
                                int compressedboy=bitmap.getAllocationByteCount();

                                byte[] byteArray = stream.toByteArray();
                                Bitmap bitmap2 = BitmapFactory.decodeByteArray( byteArray, 0, byteArray.length );
                                data.putExtra("image", byteArray);
                                String photodata=byteArrayToHex(byteArray);
                                makeNft("0x"+photodata);

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



        String myAddress = "0x819660a4d803ff6235538e312d322fe6dd8af562"; //지갑 주소 전달받아야함 이건 내 클레이튼 개정꺼고 밑에 파라미터는 로그인계정으로 넣어봄
        String myPVkey="0xae77986d342aaa6f887fb0c458e82b48fed5c333b4c677e1ae9960c9e99ad9aa";


        SingleKeyring feePayer = caver.wallet.keyring.create(myAddress, myPVkey);
        caver.wallet.add(feePayer);



        try {
            Contract contract=caver.contract.create(abijson.getABIjson(),"0xb67a16850c8495033e906c7dfd88d6d363db0905");
            //contract adress가 deploy된 klaystagram임 klaytn scope에서 긁어온거 deploy된 이 주소에서 function 가져온거지
            SendOptions sendOptions = new SendOptions();
            sendOptions.setFrom(feePayer.getAddress());
            sendOptions.setGas(BigInteger.valueOf(100000000));
            sendOptions.setFeePayer(feePayer.getAddress());
            sendOptions.setFeeDelegation(true);
            contract.send(sendOptions,"uploadPhoto", photodata, "title", "destination", "location");










        } catch (IOException | TransactionException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {

        }
    }
}