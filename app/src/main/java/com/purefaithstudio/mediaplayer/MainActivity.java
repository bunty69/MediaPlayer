package com.purefaithstudio.mediaplayer;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    Intent playerService;
    static Bitmap art = null;
    boolean flag = false;
    Uri uri;
    private int i = 0;
    private String[] songsArray;
    Bitmap bitmap;
    private ImageView imageView;
    private TextView textView;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerService = new Intent(this, MyPlayerService.class);
        ListView listView = (ListView) findViewById(R.id.list1);
        imageView = (ImageView) findViewById(R.id.controls);
        textView = (TextView) findViewById(R.id.songplaying);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavDrawer navDrawer=(NavDrawer)getSupportFragmentManager().findFragmentById(R.id.nav);
        navDrawer.set
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == true) {
                    flag = false;
                    imageView.setImageResource(R.drawable.playblue);
                    if (MyPlayerService.mp != null)
                        MyPlayerService.mp.pause();
                } else {
                    flag = true;
                    imageView.setImageResource(R.drawable.pause);
                    if (MyPlayerService.mp != null)
                        MyPlayerService.mp.start();
                }
            }
        });
        final ArrayList<HashMap<String, Song>> songsList = scanSongs();

        MyArrayAdapter arrayAdapter = new MyArrayAdapter(this, R.layout.custom_listview, songsList);


        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                flag = true;
                imageView.setImageResource(R.drawable.pause);
                String name = songsList.get(position).get("song").getSongName();
                name = name.substring(0, name.length() - 4);
                if (name.length() > 20)
                    name = name.substring(0, 20) + "..";
                textView.setText(name);
                textView.setSelected(true);
                String pathtoMusic = songsList.get(position).get("song").getPath();
                Log.e("path", pathtoMusic);
                Bundle b = new Bundle();
                b.putString("path", pathtoMusic);
                playerService.putExtras(b);
                startService(playerService);
            }
        });
        //startService(playerService);

    }

    public ArrayList scanSongs() {
        ArrayList<HashMap<String, Song>> songsList = new ArrayList<>();
        String[] STAR = {"*"};
        Cursor cursor;
        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";


        cursor = managedQuery(uri, STAR, selection, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String songName = cursor
                            .getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));


                    String path = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DATA));


                    String albumName = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    int albumId = cursor
                            .getInt(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                    HashMap<String, Song> song = new HashMap<>();
                    Log.e("har", path);
                    bitmap = getAlbumart((long) albumId);
                    Song songObject = new Song(songName, path, bitmap);
                    song.put("song", songObject);
                    //song.put("songPath", path);
                    songsList.add(song);

                } while (cursor.moveToNext());


            }

        }
        return songsList;
    }


    public Bitmap getAlbumart(Long album_id) {
        Bitmap bm = null;
        try {
            final Uri sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart");

            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);

            ParcelFileDescriptor pfd = getApplicationContext().getContentResolver()
                    .openFileDescriptor(uri, "r");

            if (pfd != null) {
                FileDescriptor fd = pfd.getFileDescriptor();
                bm = BitmapFactory.decodeFileDescriptor(fd);
            }
        } catch (Exception e) {
        }
        return bm;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(playerService);
    }


}
