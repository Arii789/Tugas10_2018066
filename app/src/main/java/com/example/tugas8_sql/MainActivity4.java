package com.example.tugas8_sql;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import com.example.tugas8_sql.databinding.ActivityMain4Binding;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
public class MainActivity4 extends AppCompatActivity implements View.OnClickListener{
    //declaration variable
    private ActivityMain4Binding binding;
    String index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setup view binding
        binding = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fetchButton.setOnClickListener(this);
    }
    //onclik button fetch
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fetch_button){
            index = binding.inputId.getText().toString();
            try {
                getData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
    //get data using api link
    public void getData() throws MalformedURLException {
        Uri uri = Uri.parse("https://run.mocky.io/v3/33684692-960e-4f5c-91b2-9df81f12a17b")
                .buildUpon().build();
        URL url = new URL(uri.toString());
        new DOTask().execute(url);
    }
    class DOTask extends AsyncTask<URL, Void, String>{
        //connection request
        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls [0];
            String data = null;
            try {
                data = NetworkUtils.makeHTTPRequest(url);
            }catch (IOException e){
                e.printStackTrace();
            }
            return data;
        }
        @Override
        protected void onPostExecute(String s){
            try {
                parseJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //get data json
        public void parseJson(String data) throws JSONException{
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(data);
            }catch (JSONException e){
                e.printStackTrace();}
            JSONObject innerObj =
                    jsonObject.getJSONObject("data");
            JSONArray cityArray = innerObj.getJSONArray("data");
            for (int i =0; i <cityArray.length(); i++){
                JSONObject obj = cityArray.getJSONObject(i);
                String Sobj = obj.get("id").toString();
                if (Sobj.equals(index)){
                    String id = obj.get("id").toString();
                    String title = obj.get("title").toString();
                    String genre = obj.get("genre").toString();
                    String publishedOn = obj.get("publishedOn").toString();
                    String authors = obj.get("authors").toString();
                    String pages= obj.get("pages").toString();
                    String isbn = obj.get("isbn" ).toString();
                    String bestFor = obj.get("bestFor" ).toString();
                    String publisher = obj.get("publisher" ).toString();
                    String description = obj.get("description" ).toString();

                    binding.resultId.setText(id);
                    binding.resultTitle.setText(title);
                    binding.resultGenre.setText(genre);
                    binding.resultPublishedOn.setText(publishedOn);
                    binding.resultAuthors.setText(authors);
                    binding.resultPages.setText(pages);
                    binding.resultIsbn.setText(isbn);
                    binding.resultBestFor.setText(bestFor);
                    binding.resultPublisher.setText(publisher);
                    binding.resultDescription.setText(description);
                }
                else{
                    binding.resultTitle.setText("Not Found");
                }
            }
        }
    }
}