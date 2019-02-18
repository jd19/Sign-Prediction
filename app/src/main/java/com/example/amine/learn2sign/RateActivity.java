package com.example.amine.learn2sign;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.amine.learn2sign.LoginActivity.INTENT_EMAIL;
import static com.example.amine.learn2sign.LoginActivity.INTENT_ID;
import static com.example.amine.learn2sign.LoginActivity.INTENT_TIME_WATCHED;
import static com.example.amine.learn2sign.LoginActivity.INTENT_URI;
import static com.example.amine.learn2sign.LoginActivity.INTENT_WORD;
import static com.example.amine.learn2sign.LoginActivity.logfile;

public class RateActivity extends AppCompatActivity {

    @BindView(R.id.vvVideoLearn)
    VideoView vvVideoLearn;

    @BindView(R.id.vvRecorded)
    VideoView vvRecorded;

    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @BindView(R.id.ratingValue)
    TextView ratingValue;

    @BindView(R.id.btnCancel2)
    Button Cancel2;

    @BindView(R.id.btnUpload)
    Button btnUpload;


    String path;
    String returnedURI;
    String old_text = "";
    String word ="";
    long time_watched;
    long time_started = 0;
    long time_started_return = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        ButterKnife.bind(this);
        if(getIntent().hasExtra(INTENT_WORD)) {
            word = getIntent().getStringExtra(INTENT_WORD);
        }
        if(getIntent().hasExtra(INTENT_TIME_WATCHED)) {
            time_watched = getIntent().getLongExtra(INTENT_TIME_WATCHED,0);
        }
        if(getIntent().hasExtra(INTENT_URI)){
            returnedURI =  getIntent().getStringExtra(INTENT_URI);
            vvRecorded.setVideoURI(Uri.parse(returnedURI));
        }
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                ratingValue.setText(String.valueOf(rating));
                ratingValue.setVisibility(View.VISIBLE);

            }
        });
        MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(mediaPlayer!=null)
                {
                    mediaPlayer.start();

                }

            }
        };
        vvVideoLearn.setVisibility(View.VISIBLE);
        vvVideoLearn.start();
        time_started = System.currentTimeMillis();
        vvRecorded.setOnCompletionListener(onCompletionListener);
        vvVideoLearn.setOnCompletionListener(onCompletionListener);
        vvRecorded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vvRecorded.start();
            }
        });
        vvVideoLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!vvVideoLearn.isPlaying()) {
                    vvVideoLearn.start();
                }
            }
        });
        time_started = System.currentTimeMillis();

        play_video(word);

    }


    @OnClick(R.id.btnCancel2)
    public void cancel2(){
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        new WriteToFile().execute(logfile,date+"#"+"cancel rate"+":"+word+"#"+ratingValue.getText().toString()+"\n");
        super.onBackPressed();
    }

    @OnClick(R.id.btnUpload)
    public void upload(){
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        new WriteToFile().execute(logfile,date+"#"+"uploaded"+"#"+word+"#"+ratingValue.getText().toString()+"\n");
        Toast.makeText(this,"Send to Server",Toast.LENGTH_SHORT).show();
        Intent t = new Intent(this,UploadActivity.class);
        startActivity(t);
    }



    public void play_video(String text) {
        old_text = text;
        if(text.equals("About")) {

            path = "android.resource://" + getPackageName() + "/" + R.raw._about;
        } else if(text.equals("And")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._and;
        } else if (text.equals("Can")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._can;
        }else if (text.equals("Cat")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._cat;
        }else if (text.equals("Cop")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._cop;
        }else if (text.equals("Cost")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._cost;
        }else if (text.equals("Day")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._day;
        }else if (text.equals("Deaf")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._deaf;
        }else if (text.equals("Decide")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._decide;
        }else if (text.equals("Father")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._father;
        }else if (text.equals("Find")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._find;
        }else if (text.equals("Go Out")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._go_out;
        }else if (text.equals("Gold")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._gold;
        }else if (text.equals("Goodnight")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._good_night;
        }else if (text.equals("Hearing")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._hearing;
        }else if (text.equals("Here")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._here;
        }else if (text.equals("Hospital")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._hospital;
        }else if (text.equals("Hurt")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._hurt;
        }else if (text.equals("If")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._if;
        }else if (text.equals("Large")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._large;
        }else if (text.equals("Hello")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._hello;
        }else if (text.equals("Help")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._help;
        }else if (text.equals("Sorry")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._sorry;
        }else if (text.equals("After")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._after;
        }else if (text.equals("Tiger")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw._tiger;
        }
        if(!path.isEmpty()) {
            Uri uri = Uri.parse(path);
            vvVideoLearn.setVideoURI(uri);
            vvVideoLearn.start();
        }

    }
}
