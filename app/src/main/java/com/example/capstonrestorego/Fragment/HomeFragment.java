package com.example.capstonrestorego.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonrestorego.Adapter.PostnftAdapter;
import com.example.capstonrestorego.Model.Json;
import com.example.capstonrestorego.Model.Post;
import com.example.capstonrestorego.R;
import com.klaytn.caver.Caver;
import com.klaytn.caver.abi.datatypes.DynamicBytes;
import com.klaytn.caver.abi.datatypes.Type;
import com.klaytn.caver.contract.Contract;
import com.klaytn.caver.methods.request.KlayLogFilter;
import com.klaytn.caver.methods.response.KlayLogs;

import org.web3j.protocol.core.DefaultBlockParameterName;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {




    private RecyclerView recyclerView;
    private PostnftAdapter postAdapter;
    private List<Post> postLists;
//    public static Post post =new Post();
    ProgressBar progressBar;

    Json abijson= new Json();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) { StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }


        postLists = new ArrayList<>();
        postAdapter = new PostnftAdapter(getContext(),postLists);
        progressBar = view.findViewById(R.id.progress_circular);


        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(postAdapter);
        TransactionLog();

        return view;
    }

    public Bitmap byteArrayToBitmap( byte[] byteArray )
    {
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteArray, 0, byteArray.length );
        return bitmap ;
    }


    public void readpost(Bitmap photo,Post post)
    {
        post.setPhoto(photo);
        postLists.add(post);
    }


    public void TransactionLog()
    {
        String baourl = "https://api.baobab.klaytn.net:8651";
        Caver caver = new Caver(baourl);


        postLists.clear();

        try {
            //블록 로그 참조
            Contract contract=caver.contract.create(abijson.getABIjson(),"0xb67a16850c8495033e906c7dfd88d6d363db0905");
            KlayLogFilter filter = new KlayLogFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, "0xb67a16850c8495033e906c7dfd88d6d363db0905", null);
            KlayLogs logs = contract.getPastEvent("Transfer", filter);

            for(int index=10; index>0; index--)
            {
                Post post= new Post();
                List<KlayLogs.LogResult> logResults = logs.getResult();
                KlayLogs.LogObject logObject = (KlayLogs.LogObject)logResults.get(logResults.size()-index);
                KlayLogs.Log log = logObject.get();

                List<Type> output=contract.call("getPhoto",log.getTopics().get(3));

                //photodata 정보
                byte[] photobyte=((DynamicBytes)((ArrayList)output).get(2)).getValue();

                post.setUsername(output.get(1).getValue().toString());
                post.setTitle(output.get(3).getValue().toString());
                post.setLocation(output.get(4).getValue().toString());
                post.setDescription(output.get(5).getValue().toString());
                readpost(byteArrayToBitmap(photobyte),post);

            }
            postAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);




        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | java.lang.InstantiationException | IllegalAccessException e) {

        }


    }
}