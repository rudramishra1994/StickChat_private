package edu.northeastern.a7team45;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.northeastern.a7team45.chathistory.ChatHistoryAdapter;
import edu.northeastern.a7team45.firebase.model.Chat;
import edu.northeastern.a7team45.stickercounteractivity.StickerCounterAdapter;
import edu.northeastern.a7team45.util.ActivityUtils;

public class StickerCounterActivity extends AppCompatActivity {

    private DatabaseReference mDatareference;
    private DatabaseReference mChat;
    private RecyclerView stickerCounterRecyclerView;

    private Map<String,Integer> stickerFrequency;

    private Set<String> stickersent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_counter);
        stickersent = new HashSet<>();
        Intent intent = getIntent();
        String currentUser = intent.getStringExtra("loggedinuser");
        stickerFrequency = new HashMap<>();
        stickerCounterRecyclerView = findViewById(R.id.stickerfrequencyRecyclerView);
        stickerCounterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stickerCounterRecyclerView.setAdapter(new StickerCounterAdapter(stickerFrequency,ActivityUtils.createStickerMap(),ActivityUtils.getStickerKeys()));
        mDatareference = FirebaseDatabase.getInstance().getReference();
        mChat = mDatareference.child("chats");

        mChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                stickerFrequency.clear();
                stickersent.clear();
                for(DataSnapshot snap :snapshot.getChildren()) {
                    Chat chat = snap.getValue(Chat.class);
                    if (chat.getSender().equals(currentUser)) {
                        if(stickerFrequency.get(chat.getStickerId())==null){
                            stickerFrequency.put(chat.getStickerId(),1);
                            stickersent.add(chat.getStickerId());
                        }else{
                            int prevFreq = stickerFrequency.get(chat.getStickerId());
                            stickerFrequency.put(chat.getStickerId(),prevFreq+1);
                        }
                    }
                }

                stickerCounterRecyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("UserChatHistory", error.toString());
            }
        });
    }
}