package com.example.serviceintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button start;
    private Button stop;
    private TextView percent;
    private ProgressBar progressBar;
    private Intent serviceIntent;
    private ResponseReceiver receiver = new ResponseReceiver();

    public class ResponseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(SimpleIntentService.ACTION_1))
            {
                int value=intent.getIntExtra(SimpleIntentService.PARAM_PERCENT,0);
                new ShowProgressBarTask().execute(value);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start=findViewById(R.id.button);
        stop=findViewById(R.id.button2);
        progressBar=findViewById(R.id.progressBar);
        percent=findViewById(R.id.textView);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.setEnabled(false);
                serviceIntent=new Intent(MainActivity.this,SimpleIntentService.class);
                startService(serviceIntent);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(serviceIntent!=null)
                {
                    SimpleIntentService.shouldStop=true;
                }
            }
        });
    }

    class ShowProgressBarTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            return integers[0];
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressBar.setProgress(integer);
            percent.setText(integer+"% Loaded");
            if(integer==100)
            {
                percent.setText("Complited");
                start.setEnabled(true);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,new IntentFilter(SimpleIntentService.ACTION_1));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }
}