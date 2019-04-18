package net.chenxiy.bilimusic.view;

import android.content.Intent;
import android.os.Bundle;

import net.chenxiy.bilimusic.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = new Intent(getBaseContext(), MainActivity.class);

        intent.putExtra(MainActivity.SHOULD_REFRESH, true);
        startActivity(intent);
        finish();


    }
}
