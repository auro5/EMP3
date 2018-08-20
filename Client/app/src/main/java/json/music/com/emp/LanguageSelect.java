package json.music.com.emp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Aurobind on 18-05-2017.
 */

public class LanguageSelect extends AppCompatActivity implements View.OnClickListener {
    TextView lang1;
    TextView lang2;
    TextView langmix;
    String detected_emotion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language);
        detected_emotion= getIntent().getStringExtra("detected_emotion");

        lang1 = (TextView) findViewById(R.id.hindi);
        lang2 = (TextView) findViewById(R.id.english);
        langmix = (TextView) findViewById(R.id.mix);

        lang1.setOnClickListener(this);
        lang2.setOnClickListener(this);
        langmix.setOnClickListener(this);
    }

    private void langHindi(){
        Intent i1 = new Intent(this,MainActivity.class);
        Bundle extras = new Bundle();
        extras.putString("Emotion",detected_emotion);
        extras.putString("Lang","Hindi");
        i1.putExtras(extras);
        startActivity(i1);
    }

    private void langEnglish(){
        Intent i2 = new Intent(this,MainActivity.class);
        Bundle extras = new Bundle();
        extras.putString("Emotion",detected_emotion);
        extras.putString("Lang","English");
        i2.putExtras(extras);
        startActivity(i2);
    }
    private void langMix(){
        Intent i3 = new Intent(this,MainActivity.class);
        Bundle extras = new Bundle();
        extras.putString("Emotion",detected_emotion);
        extras.putString("Lang","Mix");
        i3.putExtras(extras);
        startActivity(i3);
    }
    @Override
    public void onClick(View v) {

        if(v == lang1){
            langHindi();
        }

        if(v == lang2){
            langEnglish();
        }

        if(v == langmix){
            langMix();
        }
    }
}
