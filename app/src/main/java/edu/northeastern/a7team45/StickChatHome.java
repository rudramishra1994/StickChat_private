package edu.northeastern.a7team45;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

public class StickChatHome extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mUsers;

    private RecyclerView appUsersView;

    private List<User> allUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stick_chat_home);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUsers = mDatabase.child("users");
        User currentUser = new User(getIntent().getStringExtra("username"));
        addUserToTheDatabase(currentUser);
        allUsers = new ArrayList<>();

        appUsersView = findViewById(R.id.appusersview);

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
                //adapterRecyclerView.notifyDataSetChanged();
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