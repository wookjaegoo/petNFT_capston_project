package com.example.capstonrestorego.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstonrestorego.Model.Post;
import com.example.capstonrestorego.R;

import java.util.List;

public class PostnftAdapter extends RecyclerView.Adapter<PostnftAdapter.ViewHolder> {
    public Context mContext;
    private List<Post> mPost;

    public PostnftAdapter(Context mContext, List<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;

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

        if(post.getDescription().equals(""))
        {
            holder.description.setVisibility(View.GONE);
        }
        else
        {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(post.getDescription());
        }

        if(post.getLocation().equals(""))
        {
            holder.location.setVisibility(View.GONE);
        }
        else
        {
            holder.location.setVisibility(View.VISIBLE);
            holder.location.setText(post.getLocation());
        }

        if(post.getTitle().equals(""))
        {
            holder.title.setVisibility(View.GONE);
        }
        else
        {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(post.getTitle());
        }


    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView post_image;
        public TextView description, title,location,username;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            post_image = itemView.findViewById(R.id.post_image);
            description = itemView.findViewById(R.id.description);
            title = itemView.findViewById(R.id.title);
            location =itemView.findViewById(R.id.location);
            username = itemView.findViewById(R.id.username);


        }
    }



    }
