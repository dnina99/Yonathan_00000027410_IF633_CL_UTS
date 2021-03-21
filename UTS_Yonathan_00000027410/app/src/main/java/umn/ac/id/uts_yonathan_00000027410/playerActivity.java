package umn.ac.id.uts_yonathan_00000027410;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class playerActivity extends AppCompatActivity {
    Button btnNext, btnPrev, btnPause;
    TextView tvSongName;
    SeekBar sbSong;

    static MediaPlayer mPlayer;
    int position;
    //int totDuration, curPos;
    String sName;

    ArrayList<File> mySongs;
    Thread updateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        btnNext = findViewById(R.id.next);
        btnPrev = findViewById(R.id.prev);
        btnPause = findViewById(R.id.pause);
        tvSongName = findViewById(R.id.songName);
        sbSong = findViewById(R.id.seekBar);

        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        updateSeekBar = new Thread(){
            @Override
            public void run() {
                //loadNextSong();
                synchronized (this){
                    int totDuration = mPlayer.getDuration();
                    int curPos = 0;


                    while(curPos < totDuration){
                        try {
                            sleep(1000);
                            curPos = mPlayer.getCurrentPosition();
                            sbSong.setProgress(curPos);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
                //Log.v(curPos, totDuration);
            }
        };

        if(mPlayer != null){
            mPlayer.stop();
            mPlayer.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        mySongs = (ArrayList) bundle.getParcelableArrayList("Songs");
        sName = mySongs.get(position).getName().toString();
        String songName = i.getStringExtra("songName");
        tvSongName.setText(songName);
        tvSongName.setSelected(true);

        position = bundle.getInt("position",0);
        Uri uri = Uri.parse(mySongs.get(position).toString());
        mPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mPlayer.start();

        sbSong.setMax(mPlayer.getDuration());
        updateSeekBar.start();

        sbSong.getProgressDrawable().setColorFilter(getResources().getColor(R.color.teal_200), PorterDuff.Mode.MULTIPLY);
        sbSong.getThumb().setColorFilter(getResources().getColor(R.color.teal_200), PorterDuff.Mode.MULTIPLY);

        sbSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPlayer.seekTo(seekBar.getProgress());
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbSong.setMax(mPlayer.getDuration());
                if(mPlayer.isPlaying()){
                    btnPause.setBackgroundResource(R.drawable.play_arrow_icon);
                    mPlayer.pause();
                }else{
                    btnPause.setBackgroundResource(R.drawable.pause_icon);
                    mPlayer.start();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(updateSeekBar.isAlive()){
                    //Thread.currentThread().interrupted();;
                //}

                //loadNextSong();
                //Thread.currentThread().interrupted();
                //updateSeekBar.destroy();
                mPlayer.stop();
                mPlayer.release();
                position = ((position + 1)%mySongs.size());

                Uri uri = Uri.parse(mySongs.get(position).toString());
                mPlayer = MediaPlayer.create(getApplicationContext(),uri);
                loadNextSong();

                sName = mySongs.get(position).getName().toString().replace(".mp3", "").replace(".wav", "");
                tvSongName.setText(sName);

                sbSong.setMax(mPlayer.getDuration());
                new Thread(updateSeekBar).start();

                mPlayer.start();
                //updateSeekBar = new Thread(){};
                //updateSeekBar.start();
                //loadNextSong();
                /*mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        loadNextSong();
                    }
                });*/
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(updateSeekBar.isAlive()){
                    //Thread.currentThread().interrupted();;
                //}
                //updateSeekBar = new Thread();
                mPlayer.stop();
                mPlayer.release();
                position = ((position - 1) < 0) ? (mySongs.size() - 1) : (position - 1);

                Uri uri = Uri.parse(mySongs.get(position).toString());
                mPlayer = MediaPlayer.create(getApplicationContext(),uri);
                loadNextSong();

                sName = mySongs.get(position).getName().toString().replace(".mp3", "").replace(".wav", "");
                tvSongName.setText(sName);

                sbSong.setMax(mPlayer.getDuration());
                new Thread(updateSeekBar).start();
                mPlayer.start();
                //updateSeekBar = new Thread();
                //updateSeekBar.start();
                //loadNextSong();
                /*mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        loadNextSong();
                    }
                });*/
            }
        });

        loadNextSong();
        /*MediaPlayer.OnCompletionListener m_CompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mPlayer.stop();
                mPlayer.release();
                position = ((position + 1) % mySongs.size());

                Uri uri = Uri.parse(mySongs.get(position).toString());
                mPlayer = MediaPlayer.create(getApplicationContext(),uri);

                sName = mySongs.get(position).getName().toString().replace(".mp3", "").replace(".wav", "");
                tvSongName.setText(sName);

                sbSong.setMax(mPlayer.getDuration());
                new Thread(updateSeekBar).start();
                mPlayer.start();
            }
        };
        mPlayer.setOnCompletionListener(m_CompletionListener);*/

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    void loadNextSong(){
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //Thread.currentThread().interrupted();
                //updateSeekBar = null;
                mPlayer.stop();
                mPlayer.release();
                position = ((position + 1)%mySongs.size());

                Uri uri = Uri.parse(mySongs.get(position).toString());
                mPlayer = MediaPlayer.create(getApplicationContext(),uri);

                sName = mySongs.get(position).getName().toString().replace(".mp3", "").replace(".wav", "");
                tvSongName.setText(sName);

                sbSong.setMax(mPlayer.getDuration());
                new Thread(updateSeekBar).start();
                mPlayer.start();
                loadNextSong();
            }
        });

    }


}