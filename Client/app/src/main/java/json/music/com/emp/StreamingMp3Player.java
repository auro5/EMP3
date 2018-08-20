package json.music.com.emp;

/**
 * Created by Aurobind on 30-03-2017.
 */

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class StreamingMp3Player extends Activity implements OnClickListener, OnTouchListener, OnCompletionListener, OnBufferingUpdateListener {

    private ImageButton buttonPlayPause;
    private Button prv,nxt;
    private SeekBar seekBarProgress;
    public EditText editTextSongURL;
    private int position;
    TextView start_time;
    TextView end_time;
    Thread updateTime;

    static MediaPlayer mediaPlayer = new MediaPlayer();;
    private int mediaFileLengthInMilliseconds; // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class

    private final Handler handler = new Handler();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mediaPlayer.reset();
        initView();
    }

    /**
     * This method initialise all the views in project
     */
    private void initView() {
        Bundle bundle = getIntent().getExtras();
        //mySongs = (ArrayList) b.getParcelableArrayList("songlist");
        position = bundle.getInt("pos",0);
        buttonPlayPause = (ImageButton) findViewById(R.id.ButtonTestPlayPause);
        start_time = (TextView) findViewById(R.id.starttime);
        end_time = (TextView) findViewById(R.id.endtime);
        prv = (Button) findViewById(R.id.prv);
        nxt = (Button) findViewById(R.id.nxt);


        buttonPlayPause.setOnClickListener(this);
        nxt.setOnClickListener(this);
        prv.setOnClickListener(this);

        seekBarProgress = (SeekBar) findViewById(R.id.SeekBarTestPlay);
        seekBarProgress.setMax(99); // It means 100% .0-99
        seekBarProgress.setOnTouchListener(this);
        editTextSongURL = (EditText) findViewById(R.id.EditTextSongURL);
        editTextSongURL.setText(ParseJSON.names[position]);


       // mediaPlayer.reset();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    /**
     * Method which updates the SeekBar primary progress by current song playing position
     */
    /*int currentPosition=0;
    double time;
    String beg_time;
    double y;*/
    private void primarySeekBarProgressUpdater() {
        seekBarProgress.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100)); // This math construction give a percentage of "was playing"/"song length"
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
           /* time=mediaPlayer.getCurrentPosition();
            time=time*(1.6666667/100000);
            Log.i("ABC",time+"");
            //time=Math.round(time*100)/100;
            beg_time=String.format("%.3f",time);

            int x=(int) beg_time.charAt(4);
            if(x>=5)
                y=Double.parseDouble(beg_time) +0.01;*/
//start_time.setText(String.format("%d:0%d", TimeUnit.MILLISECONDS.toMinutes((long) mediaPlayer.getCurrentPosition()), TimeUnit.MILLISECONDS.toSeconds((long) mediaPlayer.getCurrentPosition()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) mediaPlayer.getCurrentPosition()))));
        //start_time.setText(String.format("%.2f",time)+"");
            if((TimeUnit.MILLISECONDS.toSeconds((long) mediaPlayer.getCurrentPosition()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) mediaPlayer.getCurrentPosition()))) < 10)
                start_time.setText(String.format("%d:0%d", TimeUnit.MILLISECONDS.toMinutes((long) mediaPlayer.getCurrentPosition()), TimeUnit.MILLISECONDS.toSeconds((long) mediaPlayer.getCurrentPosition()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) mediaPlayer.getCurrentPosition()))));
            else
                start_time.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) mediaPlayer.getCurrentPosition()), TimeUnit.MILLISECONDS.toSeconds((long) mediaPlayer.getCurrentPosition()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) mediaPlayer.getCurrentPosition()))));
            if((TimeUnit.MILLISECONDS.toSeconds((long) mediaPlayer.getDuration()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) mediaPlayer.getDuration()))) < 10)
                end_time.setText(String.format("%d:0%d", TimeUnit.MILLISECONDS.toMinutes((long) mediaPlayer.getDuration()), TimeUnit.MILLISECONDS.toSeconds((long) mediaPlayer.getDuration()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) mediaPlayer.getDuration()))));
            else
                end_time.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) mediaPlayer.getDuration()), TimeUnit.MILLISECONDS.toSeconds((long) mediaPlayer.getDuration()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) mediaPlayer.getDuration()))));


        }
    }
    /***time trial**/
    /****time end***/


    @Override
    public void onClick(View v) {


        int id = v.getId();
        switch (id) {
/*
            case R.id.play:
                if (mediaPlayer.isPlaying()) {
                    play.setText("Play");
                    mediaPlayer.pause();
                } else {
                    play.setText("Pause");
                    mediaPlayer.start();
                }
                break;
*/
            case R.id.nxt:
                mediaPlayer.stop();
                mediaPlayer.reset();
                position = ((position + 1) == ParseJSON.path.length) ? 0 : position + 1;
                editTextSongURL.setText(ParseJSON.names[position]);
                //play_music();
                try {
                    mediaPlayer.setDataSource(ParseJSON.path[position]); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                    //mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer
                    mediaPlayer.prepareAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL
                Log.i("TAG", mediaFileLengthInMilliseconds + "");

                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    buttonPlayPause.setImageResource(R.drawable.button_pause);
                } else {
                    mediaPlayer.pause();
                    buttonPlayPause.setImageResource(R.drawable.button_play);
                }

                primarySeekBarProgressUpdater();


                break;


            case R.id.prv:
                mediaPlayer.stop();
                mediaPlayer.reset();
                position = ((position - 1) < 0) ? ParseJSON.path.length - 1 : position - 1;
                editTextSongURL.setText(ParseJSON.names[position]);
                //play_music();
                try {
                    mediaPlayer.setDataSource(ParseJSON.path[position]); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                    //mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer
                    mediaPlayer.prepareAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL
                Log.i("TAG", mediaFileLengthInMilliseconds + "");
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    buttonPlayPause.setImageResource(R.drawable.button_pause);
                } else {
                    mediaPlayer.pause();
                    buttonPlayPause.setImageResource(R.drawable.button_play);
                }

                primarySeekBarProgressUpdater();
                break;


            case  R.id.ButtonTestPlayPause:
                /** ImageButton onClick event handler. Method which start/pause mediaplayer playing */
                try {
                    mediaPlayer.setDataSource(ParseJSON.path[position]); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                    //mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer
                    mediaPlayer.prepareAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL
                Log.i("TAG123", mediaFileLengthInMilliseconds + "");
                int Milliseconds= mediaPlayer.getCurrentPosition();
                Log.i("TAGGG", Milliseconds + "");

                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    buttonPlayPause.setImageResource(R.drawable.button_pause);
                } else {
                    mediaPlayer.pause();
                    buttonPlayPause.setImageResource(R.drawable.button_play);
                }

                primarySeekBarProgressUpdater();
            }
        }





    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.SeekBarTestPlay) {
            /** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position*/
            if (mediaPlayer.isPlaying()) {
                SeekBar sb = (SeekBar) v;
                int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
                mediaPlayer.seekTo(playPositionInMillisecconds);
            }
        }
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        /** MediaPlayer onCompletion event handler. Method which calls then song playing is complete*/
        buttonPlayPause.setImageResource(R.drawable.button_play);
      /*  mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                position = ((position + 1) == ParseJSON.path.length) ? 0 : position + 1;
                // u = Uri.parse(mySongs.get(position).toString());
                editTextSongURL.setText(ParseJSON.names[position]);
                //play_music();
                try {
                    mediaPlayer.setDataSource(ParseJSON.path[position]); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                    //mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer
                    mediaPlayer.prepareAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL
                Log.i("TAG", mediaFileLengthInMilliseconds + "");
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    buttonPlayPause.setImageResource(R.drawable.button_pause);
                } else {
                    mediaPlayer.pause();
                    buttonPlayPause.setImageResource(R.drawable.button_play);
                }

                primarySeekBarProgressUpdater();
            }
        });

*/

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        /** Method which updates the SeekBar secondary progress by current song loading from URL position*/
        seekBarProgress.setSecondaryProgress(percent);
    }
}