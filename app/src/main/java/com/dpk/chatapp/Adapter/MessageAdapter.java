package com.dpk.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dpk.chatapp.MessageActivity;
import com.dpk.chatapp.Model.Chat;
import com.dpk.chatapp.Model.User;
import com.dpk.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<Chat> mChat;
    private String mImageUrl;

    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<Chat> mChat, String mImageUrl) {
        this.mContext = mContext;
        this.mChat = mChat;
        this.mImageUrl = mImageUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = mChat.get(position);
        holder.mShowMessage.setText(chat.getMessage());

        if (mImageUrl.equals("default")) {
            holder.mProfileImg.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(mImageUrl).into(holder.mProfileImg);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mShowMessage;
        public ImageView mProfileImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mShowMessage = itemView.findViewById(R.id.show_message);
            mProfileImg = itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        if (mChat.get(position).getSender().equals(fuser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return  MSG_TYPE_LEFT;
        }
    }
}
