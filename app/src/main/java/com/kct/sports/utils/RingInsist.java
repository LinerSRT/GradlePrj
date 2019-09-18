package com.kct.sports.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import java.io.IOException;

public class RingInsist {
    static MediaPlayer MediaPlayer = null;

    public static void startRing(Context context) throws IllegalArgumentException, SecurityException, IllegalStateException, IOException {
        Uri alert = RingtoneManager.getDefaultUri(4);
        MediaPlayer = new MediaPlayer();
        MediaPlayer.setDataSource(context, alert);
        if (((AudioManager) context.getSystemService(Context.AUDIO_SERVICE)).getStreamVolume(4) != 0) {
            MediaPlayer.setAudioStreamType(4);
            MediaPlayer.setLooping(true);
            MediaPlayer.prepare();
            MediaPlayer.start();
        }
    }

    public static void stopRing() {
        if (MediaPlayer != null) {
            MediaPlayer.stop();
            MediaPlayer = null;
        }
    }
}
