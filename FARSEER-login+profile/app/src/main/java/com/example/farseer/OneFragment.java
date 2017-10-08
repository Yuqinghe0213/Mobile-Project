package com.example.farseer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farseer.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OneFragment extends Fragment{

    private TextView creatureName;
    private TextView creatureDescription;
    private TextView speciess;
    private ImageView img;
    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_one, container, false);
        final Bundle args = getArguments();
        final String creatureid = args.getString("creature");
        creatureName = (TextView)v.findViewById(R.id.creaturename);
        speciess = (TextView)v.findViewById(R.id.species);
        creatureDescription = (TextView)v.findViewById(R.id.creaturedescrption);
        img = (ImageView)v.findViewById(R.id.creatureimg);

        new OneFragment.getCreature().execute("1");
//        new UserProfileActivity.getCreature().execute(creatureid);
        return v;

    }

    private class getCreature extends AsyncTask<String, String, String> {
        String name;
        String description;
        String species;

        protected String doInBackground(String... params) {
            try {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://13.73.108.122/getCreature?Creature_ID=1")
                        .addHeader("cache-control","no-cache")
                        .addHeader("postman-token","55d1aa01-810e-d677-340d-4c17c0f261cd")
                        .get()
                        .build();


                Response response = client.newCall(request).execute();
                final String sResponse=response.body().string();

                String origin = sResponse.substring(sResponse.indexOf('{'),sResponse.indexOf('}')+1);
                final String sResult = origin.replace("\\", "");
                JSONObject result = new JSONObject(sResult);
                name = result.getString("creaturename");
                description = result.getString("creaturedescription");
                species = result.getString("species");

            } catch (final IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
//                        Toast.makeText(OneFragment.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }

            catch (final JSONException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
//                        Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
            return name;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            creatureName.setText(name);
            creatureDescription.setText(description);
            speciess.setText(species);
            img.setImageResource(R.drawable.user1);
        }
    }

}
