package com.example.riccardo.mashape;

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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by riccardo on 28/06/15.
 */
public class Explore extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore);
        AsyncTask<String, Void, String> app =  new HttpAsyncTask().execute("https://rokity-mashape-v1.p.mashape.com/?query=explore");



        /*
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        initializeData();
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);


*/


    }




    class Person {
        String name;
        String owner;
        String image_owner;
        String  image_api;
        String desc;
        String  prices;
        String links;

        Person(String name, String owner, String image_owner,String  image_api, String desc,String  prices,String links) {
            this.name = name;
            this.owner = owner;
            this.image_owner = image_owner;
            this. image_api =  image_api;
            this.desc = desc;
            this.prices =prices;
            this.links = links;

        }
    }


    private List<Person> persons;

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
   /* private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.aurora_borealis));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.baltoro_glacier));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.grand_canyon));
    }
*/









/*


    public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

        @Override
        public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);
            PersonViewHolder pvh = new PersonViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
            personViewHolder.personName.setText(persons.get(i).name);
            personViewHolder.personAge.setText(persons.get(i).age);
            personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
        }

        @Override
        public int getItemCount() {
            return persons.size();
        }
        List<Person> persons;

        public RVAdapter(List<Person> persons){
            this.persons = persons;

        }
        public class PersonViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView personName;
            TextView personAge;
            ImageView personPhoto;

            PersonViewHolder(View itemView) {
                super(itemView);
                cv = (CardView)itemView.findViewById(R.id.cv);
                personName = (TextView)itemView.findViewById(R.id.person_name);
                personAge = (TextView)itemView.findViewById(R.id.person_age);
                personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            }
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

    }

*/

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject obj = new JSONObject(result.toString());
                JSONArray name=obj.getJSONArray("name");
                ArrayList<String> list = new ArrayList<String>();
                int len = name.length();

                for (int i=0;i<len;i++){
                    list.add(name.get(i).toString());
                   Log.d("VALUE", String.valueOf(name.get(i).toString()));
                }


            } catch (JSONException e) {
               Log.e("Object",e.getMessage());
            }



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

                return EntityUtils.toString(resEntityGet, HTTP.UTF_8);
            }else {

                return null;
            }

        }
        catch(Exception ex)
        {
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
