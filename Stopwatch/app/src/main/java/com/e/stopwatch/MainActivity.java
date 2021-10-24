package com.e.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
{
    private int milli = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
        {
            milli = savedInstanceState.getInt("milli");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();
    }

    public void onClickBehave(View view)
    {
        running = !running;
    }

    public void onClickReset(View view)
    {
        running = false;
        milli = 0;
    }

    private void runTimer()
    {
        final TextView timetv = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();

        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                // Using delayMillits = 10 in handler.postDelayed() so div with 100 instead of 1000
                // TODO make it accurate - it has delay :'(
                long mils = milli % 100;
                long secs = (milli / 100) % 60;
                long mins = (milli / 100)  / 60;

                String time = String.format("%02d:%02d.%02d", mins, secs, mils);
                timetv.setText(time);
                if (running)
                {
                    milli++;
                }
                handler.postDelayed(this, 10);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("milli", milli);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

//    // Stamataei to timer otan to app paei background (non-visible state)
//    @Override
//    protected void onStop()
//    {
//        super.onStop();
//        wasRunning = running;
//        running = false;
//    }
//    // Sunexizei otan ksanaginei visible
//    // -Den mporei- na ginei me thn onRestart() logw orientation change
//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//        if (wasRunning)
//        {
//            running = true;
//        }
//    }


    // App visible but not in focus
    @Override
    protected void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
    }
    // App in focus again
    @Override
    protected void onResume()
    {
        super.onResume();
        if (wasRunning)
        {
            running = true;
        }
    }
}