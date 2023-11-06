package com.example.developerchat.adapter;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.developerchat.R;
import com.example.developerchat.models.Messages;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<Messages> messagesArrayList;
    int ITEM_SEND=1;
    int ITEM_RECEIVE=2;

    public MessagesAdapter(Context context, ArrayList<Messages> messagesArrayList) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_SEND){
            View view= LayoutInflater.from(context).inflate(R.layout.sender_layout_item,parent,false);
            return new SenderViewHolder(view);
        }else if(viewType==ITEM_RECEIVE){
            View view= LayoutInflater.from(context).inflate(R.layout.reciver_layout_item,parent,false);
            return new ReciverViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Messages messages=messagesArrayList.get(position);
        if(holder.getClass()==SenderViewHolder.class){

            SenderViewHolder viewHolder = (SenderViewHolder) holder;
            viewHolder.txtMessage.setText(messages.getMessage());
        }else{

            ReciverViewHolder viewHolder = (ReciverViewHolder) holder;
            viewHolder.txtMessage.setText(messages.getMessage());

        }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Messages messages=messagesArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderId())){
            return ITEM_SEND;
        }else{
             return ITEM_RECEIVE;
        }
    }
}

class SenderViewHolder extends RecyclerView.ViewHolder{
    TextView txtMessage;

    public SenderViewHolder(@NonNull View itemView) {
        super(itemView);
        txtMessage=itemView.findViewById(R.id.txtMessage);
    }
}

class ReciverViewHolder extends RecyclerView.ViewHolder{
    TextView txtMessage;
    public ReciverViewHolder(@NonNull View itemView) {
        super(itemView);
        txtMessage=itemView.findViewById(R.id.txtMessage);
    }
}
