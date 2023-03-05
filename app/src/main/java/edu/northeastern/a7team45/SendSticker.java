package edu.northeastern.a7team45;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import java.util.List;

import edu.northeastern.a7team45.firebase.model.User;
import edu.northeastern.a7team45.sendsticker.SendStickerAdapter;
import edu.northeastern.a7team45.stickerchathome.UserRecyclerViewAdapter;
import edu.northeastern.a7team45.util.ActivityUtils;

public class SendSticker extends AppCompatActivity {

    private RecyclerView stickerRecycleView;

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

        stickerRecycleView.setAdapter(new SendStickerAdapter(ActivityUtils.createStickerMap(),ActivityUtils.getStickerKeys(), this, new SendStickerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(String sticker) {

            }
        }));
    }
}