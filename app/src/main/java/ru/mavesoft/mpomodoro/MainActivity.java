package ru.mavesoft.mpomodoro;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    /*
    int workTime = 1500020;
    int restTime = 300020;
    int longRestTime = 900020;
     */

    TextView tvTimer;
    TextView tvStatus;
    TextView tvStart;
    ImageButton btnStart;
    LinearLayout layoutTimer;

    CountDownTimer timer;

    int mode = 0;
    int workTime = 1500020;
    int restTime = 300020;
    int longRestTime = 900020;

    int cycles = 0;

    NotificationManager notificationManager;
    NotificationCompat.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tvTimer);
        tvStatus = findViewById(R.id.tvStatus);
        tvStart = findViewById(R.id.tvStart);
        btnStart = findViewById(R.id.btnStart);
        layoutTimer = findViewById(R.id.layoutTimer);

        mode = 0;

        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.button)
                .setContentTitle("mPomodoro")
                .setContentText("Rest!")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }



    public void startClick(View view)
    {
        tvStart.animate().alpha(0f).setDuration(500);
        btnStart.animate().alpha(0f).setDuration(500);
        btnStart.setVisibility(View.GONE);
        layoutTimer.setVisibility(View.VISIBLE);
        layoutTimer.animate().alpha(1f).setDuration(500);
        start(mode);
    }

    public void start(int mode)
    {

        if (mode == 0)
        {
            startWork();
        } else if (mode == 1)
        {
            startRest();
        } else
        {
            startLongRest();
        }
    }

    public void setMode(int newMode)
    {
        mode = newMode;

        if (mode == 0)
        {
            tvStatus.setText("Work!");
        } else
        {
            tvStatus.setText("Rest!");
        }
    }

    public void stopClick(View view)
    {
        timer.cancel();
        tvStart.animate().alpha(1f).setDuration(500);
        btnStart.setVisibility(View.VISIBLE);
        btnStart.animate().alpha(1f).setDuration(500);
        layoutTimer.animate().alpha(0f).setDuration(500);
        layoutTimer.setVisibility(View.GONE);
        setMode(0);
        cycles = 0;
    }

    public int getMode()
    {
        return mode;
    }

    public void restartTimer()
    {
        start(getMode());
    }

    public void startWork()
    {
        cycles++;

        timer = new CountDownTimer(workTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished)
            {
                int minutes = (int) (millisUntilFinished / 1000 / 60);
                int seconds = (int) (millisUntilFinished / 1000 % 60);

                tvTimer.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));

                mBuilder.setOnlyAlertOnce(true);
                mBuilder.setContentText(Integer.toString(minutes) + ":" + Integer.toString(seconds) + " left!");
                notificationManager.notify(1, mBuilder.build());
            }

            @Override
            public void onFinish() {
                //Notification
                mBuilder.setOnlyAlertOnce(false);
                mBuilder.setContentText("Rest!");
                notificationManager.notify(1, mBuilder.build());

                if (cycles == 4)
                {
                    setMode(2);
                    cycles = 0;
                } else {
                    setMode(1);
                }
                restartTimer();
            }
        }.start();
    }

    public void startRest()
    {
        timer = new CountDownTimer(restTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished)
            {
                int minutes = (int) (millisUntilFinished / 1000 / 60);
                int seconds = (int) (millisUntilFinished / 1000 % 60);

                tvTimer.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));

                mBuilder.setOnlyAlertOnce(true);
                mBuilder.setContentText(Integer.toString(minutes) + ":" + Integer.toString(seconds) + " left!");
                notificationManager.notify(1, mBuilder.build());
            }

            @Override
            public void onFinish() {
                //Notification
                mBuilder.setOnlyAlertOnce(false);
                mBuilder.setContentText("Work!");
                notificationManager.notify(1, mBuilder.build());

                setMode(0);

                restartTimer();
            }
        }.start();
    }

    public void startLongRest()
    {
        timer = new CountDownTimer(longRestTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished)
            {
                int minutes = (int) (millisUntilFinished / 1000 / 60);
                int seconds = (int) (millisUntilFinished / 1000 % 60);

                tvTimer.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));

                mBuilder.setOnlyAlertOnce(true);
                mBuilder.setContentText(Integer.toString(minutes) + ":" + Integer.toString(seconds) + " left!");
                notificationManager.notify(1, mBuilder.build());
            }

            @Override
            public void onFinish() {
                //Notification
                mBuilder.setOnlyAlertOnce(false);
                mBuilder.setContentText("Work!");
                notificationManager.notify(1, mBuilder.build());

                setMode(0);

                restartTimer();
            }
        }.start();
    }
}
