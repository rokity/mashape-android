package com.example.riccardo.mashape;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void explore(View v){

      AsyncTask<String, Void, String> app =  new HttpAsyncTask().execute("https://rokity-mashape-v1.p.mashape.com/?query=explore");
        try {

            JSONObject obj = new JSONObject(app.toString());



        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: "+app.toString()+"");
        }



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


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

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
