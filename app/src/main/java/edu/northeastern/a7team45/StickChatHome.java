package edu.northeastern.a7team45;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.a7team45.firebase.model.User;
import edu.northeastern.a7team45.stickerchathome.UserRecyclerViewAdapter;

public class StickChatHome extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mUsers;

    private RecyclerView appUsersView;

    private List<User> allUsers;

    private User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stick_chat_home);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUsers = mDatabase.child("users");
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

    }

    private void addUserToTheDatabase(User user){
        Task<Void> t1 = mUsers.child(user.getUsername()).setValue(user);
        if(!t1.isSuccessful()){
            Toast.makeText(getApplicationContext(),"Unable to add user to the database!",Toast.LENGTH_SHORT).show();
        }
    }


}