package com.example.tomofinalv4;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tomofinalv4.R;

import java.io.IOException;
import java.util.UUID;

public class Colorcase extends AppCompatActivity  {

    ImageView box1, box2, box3, box4, box5, box6, box7, box8, box9;
    ImageButton play, record, stop, pause;
    TextView tempo;
    SeekBar tempobar;
    String fileName = null;
    String LOG_TAG = "Record_Log";
    String pathSave = "";
    Button instrument;
    boolean recording = false;
    int a;
    MediaPlayer son, mediaPlayer;
    MediaRecorder mediaRecorder;
    final int REQUEST_PERMISSION_CODE = 1000;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(!checkPermissionFromDevice())
            requestPermission();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondepage);
        box1 = findViewById(R.id.box1);
        box2 = findViewById(R.id.box2);
        box3 = findViewById(R.id.box3);
        box4 = findViewById(R.id.box4);
        box5 = findViewById(R.id.box5);
        box6 = findViewById(R.id.box6);
        box7 = findViewById(R.id.box7);
        box8 = findViewById(R.id.box8);
        box9 = findViewById(R.id.box9);

        play = (ImageButton) findViewById(R.id.play);
        record = (ImageButton) findViewById(R.id.record);
        pause = (ImageButton) findViewById(R.id.pause);
        stop = (ImageButton) findViewById(R.id.stop);

        tempo = (TextView) findViewById(R.id.tempo);
        tempobar = (SeekBar) findViewById(R.id.tempobar);
        tempobar.setMax(120);
        tempobar.setMin(40);
        instrument = findViewById(R.id.button2);

        fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName += "/TomoAudio.3gp";




            record.setOnClickListener(v -> {
                if(checkPermissionFromDevice()){
                pathSave = Environment.getExternalStorageDirectory()
                        .getAbsolutePath()+"/"
                        +UUID.randomUUID().toString()+"_Tomo_audio.3gp";
                setupMediaRecorder();
                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                play.setEnabled(false);
                pause.setEnabled(false);
                Toast.makeText(Colorcase.this,"Recording...",Toast.LENGTH_SHORT).show();


            }
                else{
                    requestPermission();
                }
            });

            stop.setOnClickListener(v -> {
                mediaRecorder.stop();
                stop.setEnabled(false);
                play.setEnabled(true);
                record.setEnabled(true);
                pause.setEnabled(false);


            });
        play.setOnClickListener(v -> {
            pause.setEnabled(true);
            stop.setEnabled(false);
            record.setEnabled(false);

            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(pathSave);
                mediaPlayer.prepare();

            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.start();
            Toast.makeText(Colorcase.this,"Playing...",Toast.LENGTH_SHORT).show();

        });

        pause.setOnClickListener(v -> {
            stop.setEnabled(false);
            record.setEnabled(true);
            pause.setEnabled(false);
            play.setEnabled(true);

            if (mediaPlayer != null){
                mediaPlayer.stop();
                mediaPlayer.release();
                setupMediaRecorder();
            }
        });













       box1.setOnClickListener(v -> {

           son = MediaPlayer.create(Colorcase.this, R.raw.snare);
           son.start();
       });
        box2.setOnClickListener(v -> {

            son = MediaPlayer.create(Colorcase.this, R.raw.hihat);
            son.start();
        });
        box3.setOnClickListener(v -> {
            son = MediaPlayer.create(Colorcase.this, R.raw.clap);
            son.start();
        });
        box4.setOnClickListener(v -> {
            instrument.setOnClickListener(v1 -> son = MediaPlayer.create(Colorcase.this, R.raw.guitar3));

            son = MediaPlayer.create(Colorcase.this, R.raw.piano3);


            son.start();
        });
        box5.setOnClickListener(v -> {

            son = MediaPlayer.create(Colorcase.this, R.raw.piano);


            son.start();
        });
        box6.setOnClickListener(v -> {

            son = MediaPlayer.create(Colorcase.this, R.raw.piano2);

            son.start();
        });
        box7.setOnClickListener(v -> {


            son = MediaPlayer.create(Colorcase.this, R.raw.piano6);

            son.start();
        });
        box8.setOnClickListener(v -> {


            son = MediaPlayer.create(Colorcase.this, R.raw.piano4);

            son.start();
        });
        box9.setOnClickListener(v -> {


            son = MediaPlayer.create(Colorcase.this, R.raw.piano5);

            son.start();
        });


        tempobar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tempo.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });






    }

    private void setupMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);
    }


    private void buttontest() {

        Drawable recorddraw = record.getDrawable();
        if (recorddraw.getConstantState().equals(getResources().getDrawable(R.drawable.ic_recordbutton).getConstantState())) {
            record.setImageResource(R.drawable.ic_stopbutton);
            Log.d("test", "stop ");


        } else if (recorddraw.getConstantState().equals(getResources().getDrawable(R.drawable.ic_stopbutton).getConstantState())) {
            record.setImageResource(R.drawable.ic_recordbutton);
            Log.d("test", "record ");
        }
    }





    private void buttontest2(){
        Drawable playdraw = play.getDrawable();
        if (playdraw.getConstantState().equals(getResources().getDrawable(R.drawable.ic_playbutton).getConstantState()))
            play.setImageResource(R.drawable.ic_pausebutton);
        else if (playdraw.getConstantState().equals(getResources().getDrawable(R.drawable.ic_pausebutton).getConstantState()))
            play.setImageResource(R.drawable.ic_playbutton);
    }
    private boolean checkPermissionFromDevice(){
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED &&
                record_audio_result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        },REQUEST_PERMISSION_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_CODE:
                if (grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this,"Permission granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
