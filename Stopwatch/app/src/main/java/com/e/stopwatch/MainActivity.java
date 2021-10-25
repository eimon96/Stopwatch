package com.e.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
//    Have this in mind for future use
//    Chronometer chronometer;
    private long milli, start, buff, update = 0;
    private int mils, secs, mins;
    private boolean running = false;
    Handler handler;
    TextView timetv;

    // Constructor
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        timetv = (TextView) findViewById(R.id.time_view);
        timetv.setText("00:00:00");
    }

    // Start/Pause
    public void onClickBehave(View view)
    {
        if (!running)
        {
            start = SystemClock.uptimeMillis();
            handler.post(runnable);
            running = true;
        }
        else
        {
            buff += milli;
            handler.removeCallbacks(runnable);
            running = false;
        }
    }

    // Reset button
    public void onClickReset(View view)
    {

        milli = start = buff = update = 0;
        mils = secs = mins = 0;
        buff += milli;
        handler.removeCallbacks(runnable);
        running = false;
        timetv.setText("00:00:00");
    }

    // Create a new thread
    public Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            milli = SystemClock.uptimeMillis() - start;
            update = buff + milli;
            secs = (int) (update / 1000);
            mins = secs / 60;
            secs = secs % 60;
            mils = (int) (update %  100);

            String time = String.format("%02d:%02d.%02d", mins, secs, mils);
            timetv.setText(time);

            handler.post(this);
        }
    };
}