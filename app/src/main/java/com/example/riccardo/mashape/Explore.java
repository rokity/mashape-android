package com.example.riccardo.mashape;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by riccardo on 28/06/15.
 */
public class Explore extends ActionBarActivity {

    RecyclerView recList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)

        .build();
        ImageLoader.getInstance().init(config);
        new HttpAsyncTask().execute("https://rokity-mashape-v1.p.mashape.com/?query=explore");
       recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(Explore.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

    }







    private List<ContactInfo> createList(int size) {

        List<ContactInfo> result = new ArrayList<ContactInfo>();
        for (int i=1; i < array.get(0).length(); i++) {
            ContactInfo ci = new ContactInfo();
            try {

            ci.name = array.get(0).getString(i).toString();
            ci.owner =array.get(1).getString(i).toString();
            ci.image_owner = array.get(2).getString(i).toString();
            ci.image_api=array.get(3).getString(i).toString();
            ci.desc=array.get(4).getString(i).toString();
            ci.prices=array.get(5).getString(i).toString();
            ci.links=array.get(6).getString(i).toString();

            result.add(ci);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return result;
    }































    ArrayList<JSONArray> array=new ArrayList<JSONArray>();

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObject=null;

                    jsonObject = new JSONObject(result);




                JSONArray name=jsonObject.getJSONArray("name");
                JSONArray owner=jsonObject.getJSONArray(("owner"));
                JSONArray image_owner=jsonObject.getJSONArray(("image_owner"));
                JSONArray  image_api=jsonObject.getJSONArray("image_api");
                JSONArray desc=jsonObject.getJSONArray("desc");
                JSONArray  prices=jsonObject.getJSONArray("prices");
                JSONArray links=jsonObject.getJSONArray("links");
                array.add(name);array.add(owner);array.add(image_owner);array.add(image_api);array.add(desc);array.add(prices);array.add(links);


            } catch (JSONException e) {
               Log.e("Object", e.getMessage());
            }
            Log.d("STATUS", "FINISH DOWNLOAD");



            ContactAdapter ca = new ContactAdapter(createList(30),Explore.this);
            recList.setAdapter(ca);


        }
    }


    public String GET(String url){




        HttpClient Client = new DefaultHttpClient();

        try
        {

            // Create Request to server and get response
            StringBuilder builder = new StringBuilder();
            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("X-Mashape-Key", "BIbPcODb1Dmsh7mAVx5Tg830cnrop1pWoxujsnlpCWGNRsgH0P");
            httpget.addHeader("Accept", "application/json");

            HttpResponse responseGet = Client.execute(httpget);
            StatusLine statusLine = responseGet.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if(statusCode == 200){
            HttpEntity resEntityGet = responseGet.getEntity();

                //do something with the response

                InputStream content = resEntityGet.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while((line = reader.readLine()) != null){
                    builder.append(line);
                }
                return builder.toString();
            }else {

                return null;
            }

        }
        catch(Exception ex) {
            Log.d("IO", ex.getMessage());
            return null;
        }



        }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
