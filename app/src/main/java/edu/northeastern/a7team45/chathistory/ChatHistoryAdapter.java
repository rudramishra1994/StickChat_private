package edu.northeastern.a7team45.chathistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import edu.northeastern.a7team45.R;
import edu.northeastern.a7team45.firebase.model.Chat;

public class ChatHistoryAdapter extends RecyclerView.Adapter<ChatHistoryAdapter.ChatViewHolder>{
    private List<Chat> listOfChats;
    private String currentUsername;
    private  String sender;
    private Map<String, Integer> stickerMapping;

    public ChatHistoryAdapter(List<Chat> listOfChats,
                              String currentUsername, String receiver, Map<String,Integer> stickerMapping ) {
        this.listOfChats = listOfChats;
        this.currentUsername = currentUsername;
        this.sender = sender;
        this.stickerMapping = stickerMapping;

    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_history_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHistoryAdapter.ChatViewHolder holder, int position) {
        Chat chat = listOfChats.get(position);

        String stickerid = chat.getStickerId();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formatted = format.format(new Date(Long.parseLong(chat.getTimeStamp())));

        String sender = chat.getSender();
        String receiver = chat.getReceiver();

        //if the sticker is null the app doesn't set stuff.
        if(stickerid == null)
            return;

        if (sender.equals(currentUsername)) {
            holder.sentSticker.setImageResource(stickerMapping.get(stickerid));
            holder.receivedSticker.setImageResource(0);
            holder.sendTime.setText(formatted);
            holder.receiveTime.setText("");
            holder.sender.setText("");
            holder.currentUser.setText(chat.getSender());
        } else if(receiver.equals(currentUsername)) {
            holder.receivedSticker.setImageResource(stickerMapping.get(stickerid));
            holder.sentSticker.setImageResource(0);
            holder.receiveTime.setText(formatted);
            holder.sendTime.setText("");
            holder.sender.setText(chat.getSender());
            holder.currentUser.setText("");
        }
    }



    @Override
    public int getItemCount() {
        return listOfChats.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        private ImageView sentSticker;

        private ImageView receivedSticker;
        private TextView receiveTime;
        private TextView sendTime;

        private TextView sender;
        private  TextView currentUser;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            sentSticker = itemView.findViewById(R.id.chatsentsticker);
            receivedSticker = itemView.findViewById(R.id.receivedsticker);
            receiveTime = itemView.findViewById(R.id.receivetimestamp);
            sendTime = itemView.findViewById(R.id.senttimestamp);
            sender = itemView.findViewById(R.id.senderusername);
            currentUser = itemView.findViewById(R.id.currentusername);
        }

    }
}
