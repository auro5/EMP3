package json.music.com.emp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;



        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
        import android.widget.ListView;
        import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String JSON_URL = "http://emoplayer.pe.hu/song.php";
    private String KEY="emotion";
    private String KEY1 = "language";
    private Button buttonGet;

    private ListView listView;
    String detected_emotion;
    String lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        detected_emotion = extras.getString("Emotion");
        Log.i("EMO",detected_emotion);
        lang = extras.getString("Lang");

        buttonGet = (Button) findViewById(R.id.buttonGet);
        buttonGet.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Log.i("TAG","qq");
                    Intent music = new Intent(view.getContext(), StreamingMp3Player.class).putExtra("pos", position);

                    startActivityForResult(music,position);

            }
        });
    }

    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        showJSON(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY,detected_emotion);
                params.put(KEY1,lang);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        Log.i("TAG","hIi");
        ParseJSON pj = new ParseJSON(json);

        pj.parseJSON();
        CustomList cl = new CustomList(this, ParseJSON.ids,ParseJSON.names,ParseJSON.path,ParseJSON.mood);
        listView.setAdapter(cl);
       // Log.i("TAG",listView.getMaxScrollAmount()+"");

    }

    @Override
    public void onClick(View v) {

        sendRequest();
    }
}