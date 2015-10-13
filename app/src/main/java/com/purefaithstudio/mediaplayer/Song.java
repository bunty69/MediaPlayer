package com.purefaithstudio.mediaplayer;

import android.graphics.Bitmap;

/**
 * Created by MY System on 9/18/2015.
 */
public class Song {
    Bitmap bitmap;
    String path, songName;

    public Song(String songName, String path, Bitmap bitmap) {
        this.songName = songName;
        this.path = path;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getPath() {

        return path;
    }

    public String getSongName() {
        return songName;
    }


}
