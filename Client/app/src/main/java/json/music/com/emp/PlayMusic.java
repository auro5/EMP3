package json.music.com.emp;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Aurobind on 06-03-2017.
 */

public class PlayMusic extends Activity implements View.OnClickListener{
    private Button play,prv,nxt;
    private int position;
    static MediaPlayer mp = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mp.reset();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_music);
        Log.i("TAG","jhjh");
        Bundle bundle = getIntent().getExtras();
        //mySongs = (ArrayList) b.getParcelableArrayList("songlist");
        position = bundle.getInt("pos",0);
        play_music();
        play = (Button) findViewById(R.id.play);
        prv = (Button) findViewById(R.id.prv);
        nxt = (Button) findViewById(R.id.nxt);

        nxt.setOnClickListener(this);
        prv.setOnClickListener(this);
        play.setOnClickListener(this);


        //  listView = (ListView) findViewById(R.id.listView);
    }
private void play_music(){
    try {

        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Log.i("TAG", ParseJSON.path[position]);
        mp.setDataSource(ParseJSON.path[position]);

    } catch (IllegalArgumentException e) {
        Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
    } catch (SecurityException e) {
        Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
    } catch (IllegalStateException e) {
        Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    try {
        mp.prepare();
    } catch (IllegalStateException e) {
        Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
    } catch (IOException e) {
        Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
    }
    mp.start();
}


    public void onClick(View v) {
        // Perform action on click
        Log.i("TAG", "zzzz");

        int id = v.getId();
        switch (id) {

            case R.id.play:   if(mp.isPlaying())
            {
                play.setText("Play");
                mp.pause();
            }
            else
            {
                play.setText("Pause");
                mp.start();
            }
                break;

            case R.id.nxt:  mp.stop();
                            mp.reset();
                            position=((position+1)== ParseJSON.path.length)? 0 : position+1;
                            play_music();
                            break;


            case R.id.prv:  mp.stop();
                            mp.reset();
                            position=((position-1)<0)? ParseJSON.path.length -1 : position-1;
                            play_music();
                            break;

        }
    }
}
