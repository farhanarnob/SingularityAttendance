package com.farhanarnob.singularityattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.farhanarnob.singularityattendance.network.room.DataRoomDatabase;
import com.farhanarnob.singularityattendance.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
}