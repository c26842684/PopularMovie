package com.example.genji.popularmovie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    public Poster[] list = {
            new Poster("https://image.tmdb.org/t/p/w185/bwVhmPpydv8P7mWfrmL3XVw0MV5.jpg", " my dianying" , "2014-5-26",7.8,"good movie")
    };

    public PosterAdapter mPosterAdapter;

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        List<Poster> items = new ArrayList<Poster>();
        for(Poster c : list)
            items.add(c);
        mPosterAdapter= new PosterAdapter(getActivity(), items);
        GridView curr = (GridView) rootView.findViewById(R.id.moviePosterList);
        curr.setAdapter(mPosterAdapter);
        curr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Poster temp = mPosterAdapter.getItem(position);
                Intent intent = new Intent(getActivity(),DetailActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT,temp);
                startActivity(intent);
            }
        });
        return rootView;
    }

    public void fetchMovie(){
        FetchMovieTask mytask = new FetchMovieTask();
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort_type = sharedPrefs.getString(
                getString(R.string.sort_key),
                getString(R.string.byrate));
        mytask.execute(sort_type);
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchMovie();
    }


    public class FetchMovieTask extends AsyncTask<String, Void, Poster[]> {

        String LOG_TAG = FetchMovieTask.class.getSimpleName();



        @Override
        protected Poster[] doInBackground(String... params) {
            if(params.length==0)
                return null;

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // received JSON format String
            String movieJSON = null;
            try{

                Uri builtUri = Uri.parse(Config.BASE_URL).buildUpon()
                        .appendQueryParameter("api_key", Config.API_KEY)
                        .appendQueryParameter("vote_count.gte", "100")
                        .appendQueryParameter("sort_by", params[0])
                        .build();

                URL url = new URL(builtUri.toString());


                // get url
                Log.v(LOG_TAG,"URL : " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJSON = buffer.toString();


            }catch(IOException e){
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            }finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return parseJSON(movieJSON);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(Poster[] posters) {
            if(posters!=null){
                for(Poster c : posters){
                    Log.v(LOG_TAG, "image   " + c.image + " " + c.name + " " + c.rate + " " + c.releaseDate + " " + c.desc);
                }
                mPosterAdapter.clear();
                mPosterAdapter.addAll(posters);
                //mPosterAdapter.notifyDataSetChanged();
            }
        }

        public Poster[] parseJSON(String movieJSON)throws JSONException{
            JSONObject JSON = new JSONObject(movieJSON);
            JSONArray movieArray = JSON.getJSONArray("results");
            int len = movieArray.length();
            Poster[] res = new Poster[len];
            for(int i =0;i<len;i++){
                JSONObject curr = movieArray.getJSONObject(i);
                String img = Config.BASE_IMG + curr.getString("poster_path");
                String name = curr.getString("original_title");
                String desc = curr.getString("overview");
                String date = curr.getString("release_date");
                double rate = curr.getDouble("vote_average");
                res[i] = new Poster(img,name,date,rate,desc);
            }
            return res;
        }
    }
}
