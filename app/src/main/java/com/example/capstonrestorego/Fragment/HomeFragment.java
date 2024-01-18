package com.example.capstonrestorego.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.capstonrestorego.Adapter.PostnftAdapter;
import com.example.capstonrestorego.Model.Json;
import com.example.capstonrestorego.Model.Post;
import com.example.capstonrestorego.R;
import com.klaytn.caver.Caver;
import com.klaytn.caver.abi.datatypes.DynamicArray;
import com.klaytn.caver.abi.datatypes.DynamicBytes;
import com.klaytn.caver.abi.datatypes.Type;
import com.klaytn.caver.contract.Contract;
import com.klaytn.caver.methods.request.KlayLogFilter;
import com.klaytn.caver.methods.response.KlayLogs;
import org.threeten.bp.Duration;
import org.web3j.protocol.core.DefaultBlockParameterName;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import jnr.constants.platform.Sock;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostnftAdapter postAdapter;
    private List<Post> postLists;
    private ProgressBar progressBar;
    private Json abijson = new Json();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 메인 스레드에서 네트워크 작업을 허용합니다 
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Bundle bundle = getArguments();
        String Caddress = bundle.getString("userinfo");

        postLists = new ArrayList<>();
        postAdapter = new PostnftAdapter(getContext(), postLists, Caddress);

        progressBar = view.findViewById(R.id.progress_circular);
        recyclerView = view.findViewById(R.id.recycler_view);

        setupRecyclerView();

        TransactionLog();

        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(postAdapter);
    }

    public Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public void readPost(Bitmap photo, Post post) {
        post.setPhoto(photo);
        postLists.add(post);
    }

    public void transactionLog() {
        String baourl = "https://api.baobab.klaytn.net:8651";
        Caver caver = new Caver(baourl);

        postLists.clear();

        String realAdd = "0xb67a16850c8495033e906c7dfd88d6d363db0905";
        String secondAdd = "0x865df85ddfc3ebe3647bac58c6ccb61d2c8e7858";

        try {
            Contract contract = caver.contract.create(abijson.getABIjson(), realAdd);

            for (int tkindex = 240; tkindex < 400; tkindex++) {
                Post post = new Post();
                List<Type> output = contract.call("getPhoto", tkindex);

                if (output.isEmpty()) {
                    break;
                }

                // 포토 데이터 정보
                byte[] photobyte = ((DynamicBytes) ((ArrayList) output).get(2)).getValue();
                List OwnerHistory = ((DynamicArray) ((ArrayList) output).get(1)).getValue();

                post.setTokenid(output.get(0).getValue().toString());
                post.setUsername(OwnerHistory.get(OwnerHistory.size() - 1).toString());
                post.setPrice(output.get(3).getValue().toString());
                post.setInformation1(output.get(4).getValue().toString());
                post.setInformation2(output.get(5).getValue().toString());

                readPost(byteArrayToBitmap(photobyte), post);
            }

            postAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);

        } catch (IOException | ClassNotFoundException | NoSuchMethodException |
                InvocationTargetException | InstantiationException | IllegalAccessException e) {
            progressBar.setVisibility(View.GONE);
            postAdapter.notifyDataSetChanged();
        }
    }
}
