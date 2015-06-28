package com.example.riccardo.mashape;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by riccardo on 28/06/15.
 */
public class Explore extends ActionBarActivity {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CardAdapter();
        mRecyclerView.setAdapter(mAdapter);

      /*  AsyncTask<String, Void, String> app =  new HttpAsyncTask().execute("https://rokity-mashape-v1.p.mashape.com/?query=explore");
        try {

            JSONObject obj = new JSONObject(app.toString());



        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: "+app.toString()+"");
        }
*/


    }






    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();

        }
    }


    public String GET(String url){




        HttpClient Client = new DefaultHttpClient();

        try
        {
            String SetServerString = "";

            // Create Request to server and get response

            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("X-Mashape-Key", "BIbPcODb1Dmsh7mAVx5Tg830cnrop1pWoxujsnlpCWGNRsgH0P");
            httpget.addHeader("Accept", "application/json");

            HttpResponse responseGet = Client.execute(httpget);

            HttpEntity resEntityGet = responseGet.getEntity();
            if (resEntityGet != null) {
                //do something with the response
                Log.i("GET RESPONSE", EntityUtils.toString(resEntityGet));
            }

            return EntityUtils.toString(resEntityGet);

        }
        catch(Exception ex)
        {
            Log.d("IO", ex.toString());
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
