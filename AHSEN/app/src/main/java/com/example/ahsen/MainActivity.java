package com.example.ahsen;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Handler;
import android.view.Gravity;

public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private Runnable internetKontrol;
    private boolean internetMesajiGosterildi = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_main);

        ImageView tekerlek = findViewById(R.id.tekerlek);
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        tekerlek.startAnimation(rotateAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                internetKontrol = new Runnable() {
                    @Override
                    public void run() {
                        if (internetVarMi() && !internetMesajiGosterildi) {
                            Toast toast = Toast.makeText(MainActivity.this, 
                                    "İnternet bağlantısı sağlandı", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 50);
                            toast.show();
                            internetMesajiGosterildi = true;
                            
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(MainActivity.this, ListActivity.class));
                                    finish();
                                }
                            }, 2000);
                        } else if (!internetVarMi()) {
                            Toast toast = Toast.makeText(MainActivity.this, 
                                    "İnternet bağlantısı bulunamadı!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 50);
                            toast.show();
                        }
                        if (!internetMesajiGosterildi) {
                            handler.postDelayed(this, 3000);
                        }
                    }
                };
                handler.post(internetKontrol);
            }
        }, 3000);
    }

    private boolean internetVarMi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(internetKontrol);
    }
} 