package edu.northeastern.a7team45;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import edu.northeastern.a7team45.firebase.model.Chat;
import edu.northeastern.a7team45.firebase.model.User;
import edu.northeastern.a7team45.sendsticker.SendStickerAdapter;
import edu.northeastern.a7team45.stickerchathome.UserRecyclerViewAdapter;
import edu.northeastern.a7team45.util.ActivityUtils;

public class SendSticker extends AppCompatActivity {

    private RecyclerView stickerRecycleView;

    private DatabaseReference mDatabase;
    private DatabaseReference mChat;

    private String sender;

    private String receiver;

    private String timestamp;

    private List<Integer> stickerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sticker);
        stickerRecycleView = findViewById(R.id.sendstickerrecyclerview);
        int numColumns = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 4;
        stickerRecycleView.setLayoutManager(new GridLayoutManager(this,numColumns));
        sender = getIntent().getStringExtra("sender");
        receiver = getIntent().getStringExtra("receiver");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mChat = mDatabase.child("chats");

        stickerRecycleView.setAdapter(new SendStickerAdapter(ActivityUtils.createStickerMap(),ActivityUtils.getStickerKeys(), this, new SendStickerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(String sticker) {
                Chat chat = new Chat(sender,receiver,sticker,String.valueOf(System.currentTimeMillis()));
                saveChat(chat);
            }
        }));
    }

    private void saveChat(Chat chat) {

        String uniqueId = sender.concat(chat.getTimeStamp());
        mChat.child(uniqueId).setValue(chat, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference ref) {
                String actionMessage = databaseError != null ? "Couldn't send the sticker" : "Sticker Sent!";
                Snackbar.make(stickerRecycleView, actionMessage,
                                Snackbar.LENGTH_SHORT).show();

            }
        });
    }
}