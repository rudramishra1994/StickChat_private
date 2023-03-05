package edu.northeastern.a7team45.sendsticker;

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
import java.util.Map;

import edu.northeastern.a7team45.R;
import edu.northeastern.a7team45.firebase.model.User;


public class SendStickerAdapter extends  RecyclerView.Adapter<SendStickerAdapter.SendStickerHolder>  {
    private static final String TAG = "RecycleView";
    private Map<String,Integer> stickerList;

    private List<String> stickerKeys;

    private final Context serviceActivity;
    private SendStickerAdapter.ItemClickListener itemClickListener;

    public SendStickerAdapter(Map<String,Integer> stickerIds,List<String> stickerKeys, Context serviceActivity, SendStickerAdapter.ItemClickListener itemClickListener) {
        stickerList = stickerIds;
        this.stickerKeys = stickerKeys;
        this.serviceActivity = serviceActivity;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public SendStickerAdapter.SendStickerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sendstickertouseritem, parent, false);
        return new SendStickerAdapter.SendStickerHolder(view);
    }

    @Override
    public void onBindViewHolder(final SendStickerAdapter.SendStickerHolder holder, final int position) {

        holder.sticker.setImageResource(stickerList.get(stickerKeys.get(position)));
        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onItemClick(stickerKeys.get(position));
        });
    }

    @Override
    public int getItemCount() {
        if (stickerList == null) {
            return 0;
        }
        return stickerList.size();
    }

    public static class SendStickerHolder extends RecyclerView.ViewHolder{

        public TextView send;
        public ImageView sticker;



        public SendStickerHolder(View view) {
            super(view);
            sticker = view.findViewById(R.id.sticker);
            send = view.findViewById(R.id.sendsticker);
        }
    }


    @Override
    public void onViewRecycled(SendStickerAdapter.SendStickerHolder holder) {
        super.onViewRecycled(holder);
    }


    public interface ItemClickListener{
        void onItemClick(String stickerid);
    }

}
