package com.example.amine.learn2sign;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.amine.learn2sign.LoginActivity.INTENT_TIME_WATCHED;
import static com.example.amine.learn2sign.LoginActivity.INTENT_TIME_WATCHED_VIDEO;
import static com.example.amine.learn2sign.LoginActivity.INTENT_URI;
import static com.example.amine.learn2sign.LoginActivity.INTENT_WORD;
import static com.example.amine.learn2sign.LoginActivity.logfile;

public class PracticeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    long time_started = 0;
    String path;
    String returnedURI;
    long time_started_return = 0;
    @BindView(R.id.btnAction)
    Button btnAction;

    @BindView(R.id.btnRecord)
    Button btnRecord;

    @BindView(R.id.txtAction)
    TextView txtAction;

    @BindView(R.id.vvPracticerecord)
    VideoView vvPracticerecord;

    @BindView(R.id.btnRate)
    Button btnRate;

    @BindView(R.id.btnCancel)
    Button btnCancel;

    @BindView(R.id.llAfterRecord)
    LinearLayout llAfterRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        ButterKnife.bind(this);
        btnRecord.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);
        btnRate.setVisibility(View.GONE);

        MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(mediaPlayer!=null)
                {
                    mediaPlayer.start();

                }

            }
        };

        vvPracticerecord.setOnCompletionListener(onCompletionListener);
        vvPracticerecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vvPracticerecord.start();
            }
        });
        time_started = System.currentTimeMillis();
        sharedPreferences =  this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
    }


    @OnClick(R.id.btnAction)
    public void set_Random_Action(){
        CharSequence[] actions = getResources().getStringArray(R.array.spinner_words);
        Random rand = new Random();
        String act = String.valueOf(actions[rand.nextInt(25)]);
        txtAction.setText(act);
        txtAction.setVisibility(View.VISIBLE);
        btnRecord.setVisibility(View.VISIBLE);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        new WriteToFile().execute(logfile,date+"#"+"generate action"+"#"+act+"\n");
    }

    @OnClick(R.id.btnRecord)
    public void record_video() {

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        new WriteToFile().execute(logfile,date+"#"+"record video"+"#"+txtAction.getText().toString()+"\n");

        if( ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED ) {

            // Permission is not granted
            // Should we show an explanation?

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        101);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        if ( ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ) {

            // Permission is not granted
            // Should we show an explanation?


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        } else {
            // Permission has already been granted
            File f = new File(Environment.getExternalStorageDirectory(), "Learn2Sign");

            if (!f.exists()) {
                f.mkdirs();
            }

            time_started = System.currentTimeMillis() - time_started;

            Intent t = new Intent(this,VideoActivity.class);
            t.putExtra(INTENT_WORD,txtAction.getText().toString());
            t.putExtra(INTENT_TIME_WATCHED, time_started);
            startActivityForResult(t,9999);





 /*           File m = new File(Environment.getExternalStorageDirectory().getPath() + "/Learn2Sign");
            if(!m.exists()) {
                if(m.mkdir()) {
                    Toast.makeText(this,"Directory Created",Toast.LENGTH_SHORT).show();
                }
            }

            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            takeVideoIntent.putExtra(EXTRA_DURATION_LIMIT, 10);

            if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
            }*/
        }
    }


    @OnClick(R.id.btnRate)
    public void sendToServer() {
        //Toast.makeText(this,"Send to Server",Toast.LENGTH_SHORT).show();

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        new WriteToFile().execute(logfile,date+"#"+"start rate"+"#"+txtAction.getText().toString()+"\n");
        Intent t = new Intent(this,RateActivity.class);
        t.putExtra(INTENT_WORD,txtAction.getText().toString());
        t.putExtra(INTENT_TIME_WATCHED, time_started);
        t.putExtra(INTENT_URI,returnedURI);
        startActivityForResult(t,2000);
    }

    @OnClick(R.id.btnCancel)
    public void cancel() {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        new WriteToFile().execute(logfile,date+"#"+"cancel practice"+"#"+txtAction.getText().toString()+"\n");
        vvPracticerecord.setVisibility(View.GONE);

        btnRecord.setVisibility(View.GONE);
        btnRate.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);
//        sp_words.setEnabled(true);
//        rb_learn.setEnabled(true);
        //rb_practice.setEnabled(true);
        time_started = System.currentTimeMillis();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        Log.e("OnActivityresult",requestCode+" "+resultCode);
        if(requestCode==2000 ) {
            //from video activity

            btnCancel.setVisibility(View.GONE);
            btnRate.setVisibility(View.GONE);
            btnRecord.setVisibility(View.GONE);
            txtAction.setVisibility(View.GONE);
            vvPracticerecord.setVisibility(View.GONE);
//            sp_ip_address.setEnabled(true);
        }
        if(requestCode==9999 && resultCode == 8888) {
            if(intent.hasExtra(INTENT_URI) && intent.hasExtra(INTENT_TIME_WATCHED_VIDEO)) {
                returnedURI = intent.getStringExtra(INTENT_URI);
                time_started_return = intent.getLongExtra(INTENT_TIME_WATCHED_VIDEO,0);

                vvPracticerecord.setVisibility(View.VISIBLE);
                btnRecord.setVisibility(View.GONE);
                btnRate.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
                txtAction.setVisibility(View.VISIBLE);
                //rb_practice.setEnabled(false);
                vvPracticerecord.setVideoURI(Uri.parse(returnedURI));
                int try_number = sharedPreferences.getInt("record_"+txtAction.getText().toString(),0);
                try_number++;
                String toAdd  = txtAction.getText().toString()+"_"+try_number+"_"+time_started_return + "";
                HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("RECORDED",new HashSet<String>());
                set.add(toAdd);
                sharedPreferences.edit().putStringSet("RECORDED",set).apply();
                sharedPreferences.edit().putInt("record_"+txtAction.getText().toString(), try_number).apply();

//                vv_video_learn.start();
            }

        }

        if(requestCode==9999 && resultCode==7777)
        {
            if(intent!=null) {
                //create folder
                if(intent.hasExtra(INTENT_URI) && intent.hasExtra(INTENT_TIME_WATCHED_VIDEO)) {
                    returnedURI = intent.getStringExtra(INTENT_URI);
                    time_started_return = intent.getLongExtra(INTENT_TIME_WATCHED_VIDEO,0);
                    File f = new File(returnedURI);
                    f.delete();
                    //  int try_number = sharedPreferences.getInt("record_"+sp_words.getSelectedItem().toString(),0);
                    // try_number++;
                    //String toAdd  = sp_words.getSelectedItem().toString()+"_"+try_number+"_"+time_started_return + "_cancelled";
                    //HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("RECORDED",new HashSet<String>());
                    // set.add(toAdd);
                    //  sharedPreferences.edit().putStringSet("RECORDED",set).apply();
                    //   sharedPreferences.edit().putInt("record_"+sp_words.getSelectedItem().toString(), try_number).apply();




                    time_started = System.currentTimeMillis();
//                    vv_video_learn.start();
                }
            }

        }

        /*if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            final Uri videoUri = intent.getData();


            vv_record.setVisibility(View.VISIBLE);
            vv_record.setVideoURI(videoUri);
            vv_record.start();
            play_video(sp_words.getSelectedItem().toString());
            bt_record.setVisibility(View.GONE);
            int i=0;
            File n = new File(Environment.getExternalStorageDirectory().getPath() + "/Learn2Sign/"
                    + sharedPreferences.getString(INTENT_ID,"0000")+"_"+sp_words.getSelectedItem().toString()+"_0" + ".mp4");
            while(n.exists()) {
                i++;
                n = new File(Environment.getExternalStorageDirectory().getPath() + "/Learn2Sign/"
                        + sharedPreferences.getString(INTENT_ID,"0000")+"_"+sp_words.getSelectedItem().toString()+"_"+i + ".mp4");
            }
            SaveFile saveFile = new SaveFile();
            saveFile.execute(n.getPath(),videoUri.toString());

            bt_send.setVisibility(View.VISIBLE);
            bt_cancel.setVisibility(View.VISIBLE);

            sp_words.setEnabled(false);
            rb_learn.setEnabled(false);
            rb_practice.setEnabled(false);
        }*/
    }
}
