package com.example.ytkim.githubuserbook;

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
    GitHubUserAdapter(ArrayList<GitHubUser> gitHubUserArrayList) {
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
//        userViewHolder.userPictureIV.setImageResource(gitHubUserArrayList.get(position).avatarURL);

        userViewHolder.userNameTV.setText(gitHubUserArrayList.get(position).userID);
    }

    @Override
    public int getItemCount() {
        return gitHubUserArrayList.size();
    }
}
