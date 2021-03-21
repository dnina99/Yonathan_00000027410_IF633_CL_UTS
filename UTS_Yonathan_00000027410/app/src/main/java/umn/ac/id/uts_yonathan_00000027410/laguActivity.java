package umn.ac.id.uts_yonathan_00000027410;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class laguActivity extends AppCompatActivity {

    ListView lvForSongs;
    String[] items;

    RecyclerView audioListView;
    DaftarLaguAdapter audioListAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lagu);
        lvForSongs = (ListView) findViewById(R.id.lvForSongs);


        getSupportActionBar().setTitle("List Lagu");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        runTimePermissions();

        AlertDialog.Builder builder = new AlertDialog.Builder(laguActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Selamat Datang");
        builder.setMessage("Yonathan Christian - 00000027410");

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void runTimePermissions(){
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        //display(); //untuk listview
                        getSongs();  //untukrecyclerview
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

    public ArrayList<File> findSong(File file){
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();
        for(File singleFile: files){
            /*if(files != null){

            }*/
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.addAll(findSong(singleFile));
            }else{
                if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")){
                    arrayList.add(singleFile);
                }
            }
            /*if(singleFile == null){
                arrayList.add(new File(new String("kosong")));
            }*/
        }
        return arrayList;
    }

    void display(){
        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());
        items = new String[mySongs.size()];
        for(int i=0; i<mySongs.size(); i++) {
            items[i] = mySongs.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");
        }
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        lvForSongs.setAdapter(myAdapter);
        lvForSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songName = lvForSongs.getItemAtPosition(position).toString();
                startActivity(new Intent(getApplicationContext(),playerActivity.class)
                        .putExtra("Songs", mySongs)
                        .putExtra("songName", songName)
                        .putExtra("position", position));

            }
        });
    }

    public void getSongs(){
        context = laguActivity.this;
        audioListView = findViewById(R.id.recyclerView);
        ArrayList<File> allAudioFiles = getAllAudioFromDevice(context);
        audioListAdapter = new DaftarLaguAdapter(context,allAudioFiles);
        audioListView.setLayoutManager(new LinearLayoutManager(context));
        audioListView.setAdapter(audioListAdapter);
    }

    public ArrayList<File> getAllAudioFromDevice(final Context context) {
        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());
        //mySongs.get(position).getName().toString().replace(".mp3", "").replace(".wav", "");
        return mySongs;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.profile:
                profile();
                return true;
            case R.id.logOut:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void profile() {
        Intent intent = new Intent(laguActivity.this, profilActivity.class);
        startActivity(intent);
    }

    private void logOut(){
        if(playerActivity.mPlayer != null){
            playerActivity.mPlayer.pause();
        }
        //playerActivity.mPlayer.release();
        Intent intent = new Intent(laguActivity.this, loginActivity.class);
        startActivity(intent);
    }


}