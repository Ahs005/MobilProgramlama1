package com.example.ahsen;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class ListActivity extends AppCompatActivity {
    
    private String[] markalar = {"ARORA\nCAPPUCINO", "MONDİAL\nTORİSMO", "KUBA\nBLUEBRİD"};
    private int[] resimler = {
        R.drawable.arora,
        R.drawable.mondial1,
        R.drawable.kuba
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listView = findViewById(R.id.listView);
        OzelAdapter adapter = new OzelAdapter(this, markalar, resimler);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String secilenMarka = markalar[position];
            Intent intent = new Intent(ListActivity.this, VideoActivity.class);
            intent.putExtra("marka", secilenMarka);
            startActivity(intent);
        });
    }

    private class OzelAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] markalar;
        private final int[] resimler;

        public OzelAdapter(Context context, String[] markalar, int[] resimler) {
            super(context, R.layout.list_item, markalar);
            this.context = context;
            this.markalar = markalar;
            this.resimler = resimler;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, parent, false);
            }

            ImageView imageView = convertView.findViewById(R.id.marka_resim);
            TextView textView = convertView.findViewById(R.id.marka_text);
            
            imageView.setImageResource(resimler[position]);
            textView.setText(markalar[position]);

            return convertView;
        }
    }
} 