package com.purefaithstudio.mediaplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MY System on 9/18/2015.
 */
public class MyArrayAdapter extends ArrayAdapter<HashMap<String, Song>> {


    private final ArrayList<HashMap<String, Song>> objects;
    private int id;
    private Context mcontext;
    private Bitmap art;
    private Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Aaj.mp3"));
    private int i = 0;

    public MyArrayAdapter(Context context, int resource, ArrayList<HashMap<String,Song>> objects) {
        super(context, resource, objects);
        this.mcontext = context;
        this.id = resource;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mview = convertView;
        mview = LayoutInflater.from(mcontext).inflate(R.layout.custom_listview, null,true);
        ImageView imageView = (ImageView) mview.findViewById(R.id.image);
        TextView textView = (TextView) mview.findViewById(R.id.text1);
        //art=MainActivity.art;

        HashMap<String,Song> hashMap = objects.get(position);
        Song song = hashMap.get("song");
        String songTitle=song.getSongName();

        //Log.e("harjas",songPath);
        textView.setText(songTitle);
        Bitmap bitmap=song.getBitmap();
        if(bitmap!=null)
        imageView.setImageBitmap(bitmap);
        else imageView.setImageResource(R.drawable.musicicon);

        return mview;
    }


}
