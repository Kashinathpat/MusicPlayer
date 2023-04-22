package com.example.easytutomusicapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    ArrayList<AudioModel> songsList;
    Context context;

    public MusicListAdapter(ArrayList<AudioModel> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new MusicListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AudioModel songData = songsList.get(position);
        holder.titleTextView.setText(songData.getTitle());
        holder.lengthTextView.setText(convertToMMSS(songData.getDuration()));

        if (MyMediaPlayer.currentIndex == position) {
            holder.titleTextView.setTextColor(Color.parseColor("#00ff00"));
        } else {
            holder.titleTextView.setTextColor(Color.parseColor("#ffffff"));
        }

        holder.itemView.setOnClickListener(v -> {
            MyMediaPlayer.getInstance().reset();
            MyMediaPlayer.currentIndex = position;
            Intent intent = new Intent(context, MusicPlayerActivity.class);
            intent.putExtra("LIST", songsList);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView,lengthTextView;
        ImageView iconImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.music_title_text);
            lengthTextView = itemView.findViewById(R.id.music_length_text);
            iconImageView = itemView.findViewById(R.id.icon_view);
        }
    }
    public static String convertToMMSS(String duration) {
        long millis = 0L;
        try {
            millis = Long.parseLong(duration);
        } catch (NumberFormatException ignored) {}
        return String.format(Locale.US, "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }
}
