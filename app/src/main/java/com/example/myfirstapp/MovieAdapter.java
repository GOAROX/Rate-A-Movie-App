package com.example.myfirstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Movie> movieList;

    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false);
            holder = new ViewHolder();
            holder.movieNameTextView = convertView.findViewById(R.id.movieNameTextView);
            holder.dateWatchedTextView = convertView.findViewById(R.id.dateWatchedTextView);
            holder.ratingTextView = convertView.findViewById(R.id.ratingTextView);
            holder.directionTextView = convertView.findViewById(R.id.directionTextView);
            holder.storyTextView = convertView.findViewById(R.id.storyTextView);
            holder.animationTextView = convertView.findViewById(R.id.animationTextView);
            holder.actingTextView = convertView.findViewById(R.id.actingTextView);
            holder.storybuildingTextView = convertView.findViewById(R.id.storybuildingTextView);
            holder.yearnTextView = convertView.findViewById(R.id.yearnTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Movie movie = movieList.get(position);
        holder.movieNameTextView.setText(movie.getMovieName());
        holder.dateWatchedTextView.setText(movie.getDateWatched());
        holder.ratingTextView.setText(String.valueOf(movie.getRating()));
        // Set boolean values text based on true/false
        holder.directionTextView.setText(movie.isDirection() ? "Direction" : "");
        holder.storyTextView.setText(movie.isStory() ? "Story" : "");
        holder.animationTextView.setText(movie.isAnimation() ? "Animation" : "");
        holder.actingTextView.setText(movie.isActing() ? "Acting" : "");
        holder.storybuildingTextView.setText(movie.isStorybuilding() ? "Storybuilding" : "");
        holder.yearnTextView.setText(movie.isYearn() ? "Yearn" : "");

        return convertView;
    }

    static class ViewHolder {
        TextView movieNameTextView;
        TextView dateWatchedTextView;
        TextView ratingTextView;
        TextView directionTextView;
        TextView storyTextView;
        TextView animationTextView;
        TextView actingTextView;
        TextView storybuildingTextView;
        TextView yearnTextView;
    }
}
