package com.example.pedometer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.ProtocolException;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {

    private SensorManager mSensorManager=null;
    private Sensor stepSensor;
    private int totalSteps=0;
    private int previewsTotalSteps=0;
    private ProgressBar progressBar;
    private TextView steps;
    FirebaseAuth auth;
    Button button;
    TextView textView;

    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        progressBar=findViewById(R.id.progress_Bar);
        steps=findViewById(R.id.steps);
        auth=FirebaseAuth.getInstance();
        button=findViewById(R.id.logout);
        user=auth.getCurrentUser();


        if(user!=null){

        }
        else{
            Intent intent=new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            finish();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();


            }
        });


        resetSteps();
        loadData();
        mSensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        stepSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);





    }
    protected void onResume()
    {
        super.onResume();


        if (stepSensor==null)
        {
            Toast.makeText(this,"This device has no sensors",Toast.LENGTH_SHORT).show();

        }
        else{
            mSensorManager.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    protected  void onPause()
    {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_STEP_COUNTER)
        {
            totalSteps=(int)event.values[0];
            int currentSteps=totalSteps-previewsTotalSteps;
            steps.setText(String.valueOf(currentSteps));

            progressBar.setProgress(currentSteps);

        }

    }
    private void resetSteps()
    {
        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Long press to reset steps", Toast.LENGTH_SHORT).show();
            }
        });
        steps.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                previewsTotalSteps=totalSteps;
                steps.setText("0");
                progressBar.setProgress(0);
                saveData();
                return true;
            }
        });
    }

    private void saveData()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putFloat("key1",previewsTotalSteps);
        editor.apply();
    }
private void loadData()
{
    SharedPreferences sharedPreferences=getSharedPreferences("my_pref", Context.MODE_PRIVATE);
    int savedNumber= (int) sharedPreferences.getFloat("key1",0f);
    previewsTotalSteps=savedNumber;
//    steps.setText(String.valueOf(previewsTotalSteps));
}
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}