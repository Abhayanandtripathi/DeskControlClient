package com.example.remotecontol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JoyStick joyStick = new JoyStick(this);
        setContentView(joyStick);
    }
}