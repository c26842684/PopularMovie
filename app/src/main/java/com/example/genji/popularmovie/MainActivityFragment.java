package com.example.genji.popularmovie;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public Poster[] list = {
            new Poster(R.drawable.cupcake,"dianying","2015-1-1",3.5,"good movie"),
            new Poster(R.drawable.icecream,"dianying2","2015-1-1",3.5,"good movie")
    };

    public PosterAdapter mPosterAdapter;

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mPosterAdapter= new PosterAdapter(getActivity(), Arrays.asList(list));
        ListView curr = (ListView) rootView.findViewById(R.id.moviePosterList);
        curr.setAdapter(mPosterAdapter);
        return rootView;
    }
}
