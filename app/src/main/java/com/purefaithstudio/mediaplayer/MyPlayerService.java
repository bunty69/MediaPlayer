package com.purefaithstudio.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;

import java.io.IOException;

public class MyPlayerService extends Service {
    static MediaPlayer mp = null;

    public MyPlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mp == null) {
            mp = new MediaPlayer();
            try {
                Bundle b = intent.getExtras();
                String path = b.getString("path");
                //Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();
                mp.setDataSource(path);
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mp.release();
            mp = new MediaPlayer();
            Bundle b = intent.getExtras();
            String path = b.getString("path");
            //Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();
            try {
                mp.setDataSource(path);
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
