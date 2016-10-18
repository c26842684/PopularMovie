package com.example.genji.popularmovie;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by genji on 10/16/2016.
 */
public class PosterAdapter extends ArrayAdapter<Poster> {


    public PosterAdapter(Activity context, List<Poster> PosterList){
        super(context,0,PosterList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position
        Poster poster1 = getItem(position);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.poster_item, parent, false);
        }

        ImageView thumbnail_poster = (ImageView) convertView.findViewById(R.id.imageView);
        Picasso.with(getContext()).load(poster1.image).into(thumbnail_poster);

        return convertView;
    }
}
