package com.example.amine.learn2sign;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import static android.content.Context.MODE_APPEND;
import static com.example.amine.learn2sign.LoginActivity.INTENT_ID;

public class WriteToFile extends AsyncTask<String,Integer,String> {
    public WriteToFile() {
        super();
    }

    @Override
    protected String doInBackground(String... params) {

        // get the state of your external storage
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // if storage is mounted return true
            Log.v("Jigar", "Yes, can write to external storage.");
            String writeString =params[1];
            String filename = params[0];
            BufferedWriter bw = null;
            FileWriter fw = null;
            try {
                File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/Learn2Sign_logs1/");
                Log.d("Jigar","path is"+Environment.getExternalStorageDirectory().getPath());
                if (!dir.exists()) {
                    Log.d("Jigar","creating new dir");
                    dir.mkdir();
                }

                File file = new File(dir, filename + ".txt");
                if (!file.exists()) {
                    Log.d("Jigar","creating new file");
                    file.createNewFile();
                }
                fw = new FileWriter(file.getAbsoluteFile(), true);
                bw = new BufferedWriter(fw);
                Log.d("Jigar","appending"+writeString);
                bw.write(writeString);

            }catch (Exception e){
                Log.d("Jigar",e+"");
                e.printStackTrace();
            }
            finally {
                try {

                    if (bw != null)
                        bw.close();

                    if (fw != null)
                        fw.close();

                } catch (IOException ex) {

                    ex.printStackTrace();

                }
            }
//            FileOutputStream os = null;
//            try {
//                os = new FileOutputStream();
//                String data = writeString;
//                os.write(data.getBytes());
//                os.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }





        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {

//        super.onPostExecute(o);
    }
}
