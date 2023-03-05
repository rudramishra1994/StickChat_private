package edu.northeastern.a7team45.stickercounteractivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import edu.northeastern.a7team45.R;

public class StickerCounterAdapter extends RecyclerView.Adapter<StickerCounterAdapter.StickerFrequncyViewHolder> {


    private Map<String, Integer> stickerMapping;
    private Map<String, Integer> stickerFrequncy;
    private List<String> stickers;

    public StickerCounterAdapter(Map<String,Integer> stickerFrequency, Map<String,Integer> stickerMapping,List<String> stickers ) {
        this.stickerFrequncy = stickerFrequency;
        this.stickers = stickers;
        this.stickerMapping = stickerMapping;

    }

    @NonNull
    @Override
    public StickerCounterAdapter.StickerFrequncyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stickerstatisticitems, parent, false);
        return new StickerCounterAdapter.StickerFrequncyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerCounterAdapter.StickerFrequncyViewHolder holder, int position) {
       String stickerName = stickers.get(position);
       holder.sentSticker.setImageResource(stickerMapping.get(stickerName));
       if(stickerFrequncy.get(stickerName)!=null){
           holder.counter.setText(stickerFrequncy.get(stickerName).toString());
       }else{
           holder.counter.setText("0");
       }
    }



    @Override
    public int getItemCount() {
        return stickers.size();
    }

    public static class StickerFrequncyViewHolder extends RecyclerView.ViewHolder {

        private ImageView sentSticker;
        private TextView counter;


        public StickerFrequncyViewHolder(@NonNull View itemView) {
            super(itemView);
            sentSticker = itemView.findViewById(R.id.systemstickers);
            counter = itemView.findViewById(R.id.counter);
        }

    }
}

