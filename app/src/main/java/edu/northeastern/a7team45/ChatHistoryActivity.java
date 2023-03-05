package edu.northeastern.a7team45;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.northeastern.a7team45.chathistory.ChatHistoryAdapter;
import edu.northeastern.a7team45.firebase.model.Chat;
import edu.northeastern.a7team45.util.ActivityUtils;

public class ChatHistoryActivity extends AppCompatActivity {

    private DatabaseReference mDatareference;
    private DatabaseReference mChat;

    private List<Chat> listOfChats = new ArrayList<>();

    private RecyclerView chatHistoryRecycleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_history);

        Intent intent = getIntent();
        String currentUser = intent.getStringExtra("sender");
        String receiver = intent.getStringExtra("receiver");

        chatHistoryRecycleView = findViewById(R.id.chathistoryrecycler);
        chatHistoryRecycleView.setLayoutManager(new LinearLayoutManager(this));
        chatHistoryRecycleView.setAdapter(new ChatHistoryAdapter(listOfChats,
                currentUser, receiver, ActivityUtils.createStickerMap()));

        mDatareference = FirebaseDatabase.getInstance().getReference();
        mChat = mDatareference.child("chats");

        mChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listOfChats.clear();
                for(DataSnapshot snap :snapshot.getChildren()) {
                    Chat chat = snap.getValue(Chat.class);
                    if (chat.getSender().equals(receiver) && chat.getReceiver().equals(currentUser)) {
                        listOfChats.add(chat);
                    } else if (chat.getSender().equals(currentUser) && chat.getReceiver().equals(receiver)) {
                        listOfChats.add(chat);
                    }
                }

                chatHistoryRecycleView.getAdapter().notifyDataSetChanged();
                listOfChats.sort(Comparator.comparing(Chat::getTimeStamp));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("UserChatHistory", error.toString());
            }
        });
    }

}