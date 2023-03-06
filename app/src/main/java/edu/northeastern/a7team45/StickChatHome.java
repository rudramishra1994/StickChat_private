package edu.northeastern.a7team45;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.a7team45.firebase.model.Chat;
import edu.northeastern.a7team45.firebase.model.User;
import edu.northeastern.a7team45.stickerchathome.UserRecyclerViewAdapter;
import edu.northeastern.a7team45.util.ActivityUtils;

public class StickChatHome extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mUsers;

    private DatabaseReference mChat;

    private RecyclerView appUsersView;

    private List<User> allUsers;

    private User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stick_chat_home);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUsers = mDatabase.child("users");
        mChat = mDatabase.child("chats");
        currentUser = new User(getIntent().getStringExtra("username"));
        addUserToTheDatabase(currentUser);
        allUsers = new ArrayList<>();
        appUsersView = findViewById(R.id.appuserrecyclerview);
        appUsersView.setLayoutManager(new LinearLayoutManager(this));
        appUsersView.setAdapter(new UserRecyclerViewAdapter(allUsers, this, new UserRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onSendStickerButtonClick(User user) {
                Intent intent = new Intent(StickChatHome.this, SendSticker.class);
                // Pass data object in the bundle and populate details activity.
                intent.putExtra("receiver", user.getUsername());
                intent.putExtra("sender", currentUser.getUsername());
                startActivity(intent);
            }

            @Override
            public void onChatHistoryButtonClick(User user) {
                Intent intent = new Intent(StickChatHome.this, ChatHistoryActivity.class);
                // Pass data object in the bundle and populate details activity.
                intent.putExtra("receiver", user.getUsername());
                intent.putExtra("sender", currentUser.getUsername());
                startActivity(intent);
            }

        }));

        mUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allUsers.clear();
                for(DataSnapshot snapshot1 :snapshot.getChildren()){
                    User user = snapshot1.getValue(User.class);
                    if(user!=null && !user.getUsername().equals(currentUser.getUsername())) {
                        allUsers.add(user);
                    }
                }
                appUsersView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Chat chat = snapshot.getValue(Chat.class);
                if(chat.getReceiver().equals(currentUser.getUsername())){
                    createnotificationChannel(chat);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addUserToTheDatabase(User user){
        Task<Void> t1 = mUsers.child(user.getUsername()).setValue(user);
        if(!t1.isSuccessful()){
            Toast.makeText(getApplicationContext(),"Unable to add user to the database!",Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToStickerCounterActivity(){
        Intent intent = new Intent(StickChatHome.this, StickerCounterActivity.class);
        // Pass data object in the bundle and populate details activity.
        intent.putExtra("loggedinuser", currentUser.getUsername());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menusentitem: navigateToStickerCounterActivity();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.stickchatmenu,menu);
        return true;
    }

    @SuppressLint("MissingPermission")
    private void createnotificationChannel(Chat chat) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "n";
            String description = "demochannel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("n", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            NotificationCompat.Builder notifyBuild = new NotificationCompat.Builder(this, "n")
                    //"Notification icons must be entirely white."
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), ActivityUtils.createStickerMap().get(chat.getStickerId())))
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(BitmapFactory.decodeResource(getApplicationContext().getResources(), ActivityUtils.createStickerMap().get(chat.getStickerId())))
                            .bigLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), ActivityUtils.createStickerMap().get(chat.getStickerId()))))
                    .setContentTitle("New Sticker received from " + chat.getSender())
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    // hide the notification after its selected
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(0, notifyBuild.build());
        }
    }
}