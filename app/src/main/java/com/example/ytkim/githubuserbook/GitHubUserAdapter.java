package com.example.ytkim.githubuserbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
        UserViewHolder(View view) {
            super(view);
            userPictureIV = view.findViewById(R.id.iv_user_picture);
            userNameTV = view.findViewById(R.id.tv_user_name);
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
        UserViewHolder userViewHolder = (UserViewHolder) holder;
        GitHubUser gitHubUser = gitHubUserArrayList.get(position);
        Glide.with(context).load(gitHubUser.avatarURL).into(userViewHolder.userPictureIV);

        userViewHolder.userNameTV.setText(gitHubUser.userID);
    }

    @Override
    public int getItemCount() {
        return gitHubUserArrayList.size();
    }
}
