package com.example.ahsen;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class VideoActivity extends AppCompatActivity {
    private YouTubePlayerView youTubePlayerView;
    private TextView aciklamaText;
    private MotorDatabase dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        youTubePlayerView = findViewById(R.id.youtube_player);
        getLifecycle().addObserver(youTubePlayerView);
        
        aciklamaText = findViewById(R.id.aciklama_text);
        dbHelper = new MotorDatabase(this);

        String marka = getIntent().getStringExtra("marka");
        
        Cursor cursor = dbHelper.getMotorBilgileri(marka);
        if (cursor.moveToFirst()) {
            String videoId = cursor.getString(cursor.getColumnIndex(MotorDatabase.COL_VIDEO_ID));
            String aciklama = cursor.getString(cursor.getColumnIndex(MotorDatabase.COL_ACIKLAMA));

            aciklamaText.setText(aciklama);
            
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(YouTubePlayer youTubePlayer) {
                    youTubePlayer.loadVideo(videoId, 0);
                }
            });
        }
        cursor.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        youTubePlayerView.release();
    }
} 