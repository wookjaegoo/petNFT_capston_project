package com.example.capstonrestorego.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstonrestorego.Model.Json;
import com.example.capstonrestorego.Model.Post;
import com.example.capstonrestorego.Model.User;
import com.example.capstonrestorego.R;
import com.klaytn.caver.contract.Contract;
import com.klaytn.caver.contract.SendOptions;

import org.web3j.protocol.core.Request;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.List;

import xyz.groundx.caver_ext_kas.CaverExtKAS;
import xyz.groundx.caver_ext_kas.kas.kip7.KIP7;

public class PostnftAdapter extends RecyclerView.Adapter<PostnftAdapter.ViewHolder> {
    public Context mContext;
    private List<Post> mPost;
    public String Caddress;
    Json abijson= new Json();


    public PostnftAdapter(Context mContext, List<Post> mPost,String Caddress) {
        this.mContext = mContext;
        this.mPost = mPost;
        this.Caddress=Caddress;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Post post = mPost.get(position);
        Glide.with(mContext).asBitmap().load(post.getPhoto()).into(holder.post_image);
        holder.username.setText(post.getUsername());


        if(post.getInformation2().equals(""))
        {
            holder.description.setVisibility(View.GONE);
        }
        else
        {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(post.getInformation2());
        }

        if(post.getInformation1().equals(""))
        {
            holder.location.setVisibility(View.GONE);
        }
        else
        {
            holder.location.setVisibility(View.VISIBLE);
            holder.location.setText(post.getInformation1());
        }

        if(post.getPrice().equals(""))
        {
            holder.title.setVisibility(View.GONE);
        }
        else
        {
            holder.price.setVisibility(View.VISIBLE);
            holder.price.setText(post.getPrice());
        }

        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    try{
                        String accessKey = "KASKD9KL8U3ZZ952PD63RK4V";
                        String secretAccessKey ="Tf4mRN76-gBsqDkUueywDZuQmJPZ-qdjvjMDD2Bj";
                        CaverExtKAS caver = new CaverExtKAS(1001, accessKey, secretAccessKey);

                        String contractAddress = "0xb67a16850c8495033e906c7dfd88d6d363db0905";

                        //Caddress means 어플 접속자


                       String hexBalance =caver.rpc.getKlay().getBalance(Caddress).send().getResult();
                        String klay1=hexBalance.substring(2);

                        BigInteger bigInteger=new BigInteger(klay1,16);
                        BigInteger bigInteger1=new BigInteger("1000000000000000000");
                        bigInteger.divide(bigInteger1).toString();
                        String Sklay=bigInteger.divide(bigInteger1).toString();
                        Double klay=Double.parseDouble(Sklay);


                       Double nftPrice=Double.parseDouble(holder.price.getText().toString());
                       if(klay>nftPrice)
                       {
                           Contract sampleContract = new Contract(caver, abijson.getABIjson(), contractAddress);
                           SendOptions sendOptions1 = new SendOptions(Caddress, BigInteger.valueOf(50000000));
                           sampleContract.send(sendOptions1, "transferOwnership",Caddress);
                       }

//                    잔고조회 이때 로그인 객체에서 받은 주소로 nft살만한 잔고있으면 결제 ㄱ
                    }
                    catch (IOException | TransactionException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e)
                    {

                    }

            }
        });



    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView post_image;
        public TextView description, title,location,username,price;
        Button buy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            post_image = itemView.findViewById(R.id.post_image);
            description = itemView.findViewById(R.id.description);
            title = itemView.findViewById(R.id.title);
            location =itemView.findViewById(R.id.location);
            username = itemView.findViewById(R.id.username);
            buy= itemView.findViewById(R.id.buy);
            price=itemView.findViewById(R.id.price);


        }
    }



    }
