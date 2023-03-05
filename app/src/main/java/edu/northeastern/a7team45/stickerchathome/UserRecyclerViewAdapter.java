package edu.northeastern.a7team45.stickerchathome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.a7team45.R;
import edu.northeastern.a7team45.firebase.model.User;

public class UserRecyclerViewAdapter  extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder>  {
    private static final String TAG = "RecycleView";
    private List<User> userList;
    private final Context serviceActivity;
    private ItemClickListener itemClickListener;

    public UserRecyclerViewAdapter(List<User> users, Context serviceActivity,ItemClickListener itemClickListener) {
        userList = users;
        this.serviceActivity = serviceActivity;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public UserRecyclerViewAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appuserecyclerviewitems, parent, false);
        return new UserRecyclerViewAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {

        final User user = userList.get(position);
        holder.username.setText(user.getUsername());
        holder.sendSticker.setOnClickListener(view -> {
            itemClickListener.onSendStickerButtonClick(userList.get(position));
        });
        holder.chatHistory.setOnClickListener(view -> {
            itemClickListener.onChatHistoryButtonClick(user);
        });
    }

    @Override
    public int getItemCount() {
        if (userList == null) {
            return 0;
        }
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView persona;

        public Button chatHistory;

        public Button sendSticker;



        public UserViewHolder(View view) {
            super(view);
            persona = view.findViewById(R.id.userpersona);
            username = view.findViewById(R.id.usernameinappuseritem);
            sendSticker = view.findViewById(R.id.sendstickerbutton);
            chatHistory = view.findViewById(R.id.chathistorybutton);
        }
    }


    @Override
    public void onViewRecycled(UserViewHolder holder) {
        super.onViewRecycled(holder);
    }


    public interface ItemClickListener{
        void onSendStickerButtonClick(User user);
        void onChatHistoryButtonClick(User user);
    }

}
