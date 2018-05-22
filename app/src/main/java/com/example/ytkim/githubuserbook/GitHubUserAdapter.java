package com.example.ytkim.githubuserbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GitHubUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<GitHubUser> gitHubUserArrayList;
    private Context context;
    GitHubUserAdapter(Context context, ArrayList<GitHubUser> gitHubUserArrayList) {
        this.context = context;
        this.gitHubUserArrayList = gitHubUserArrayList;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView userPictureIV;
        TextView userNameTV;
        ImageView favoriteIV;
        UserViewHolder(View view) {
            super(view);
            userPictureIV = view.findViewById(R.id.iv_user_picture);
            userNameTV = view.findViewById(R.id.tv_user_name);
            favoriteIV = view.findViewById(R.id.iv_favorite);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final UserViewHolder userViewHolder = (UserViewHolder) holder;
        GitHubUser gitHubUser = gitHubUserArrayList.get(position);
        GlideApp.with(context).load(gitHubUser.avatarURL).placeholder(R.drawable.ic_image_black_24dp).load(gitHubUser.avatarURL).thumbnail(0.1f).into(userViewHolder.userPictureIV);
        userViewHolder.favoriteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewHolder.favoriteIV.setImageResource(R.drawable.ic_favorite_black_24dp);
            }
        });

        userViewHolder.userNameTV.setText(gitHubUser.userID);
    }

    @Override
    public int getItemCount() {
        return gitHubUserArrayList.size();
    }
}
