package com.example.genji.popularmovie;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by genji on 10/17/2016.
 */
public class DetailFragment extends Fragment {

    public DetailFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail,container,false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            Poster curr = intent.getBundleExtra(Intent.EXTRA_TEXT).getParcelable("key");
            ImageView img =(ImageView)rootView.findViewById(R.id.detail_image);
            Picasso.with(getActivity()).load(curr.image).into(img);

            TextView title = (TextView)rootView.findViewById(R.id.bigtitle);
            title.setText(curr.name);

            TextView date = (TextView)rootView.findViewById(R.id.date);
            date.setText(curr.releaseDate);

            TextView rate = (TextView)rootView.findViewById(R.id.rate);
            rate.setText(String.valueOf(curr.rate));

            TextView desc = (TextView)rootView.findViewById(R.id.desc);
            desc.setText(curr.desc);
        }
        return rootView;
    }
}
