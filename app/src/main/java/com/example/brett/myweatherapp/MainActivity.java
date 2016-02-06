package com.example.brett.myweatherapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.particle.android.sdk.cloud.*;
import io.particle.android.sdk.utils.Async;
import io.particle.android.sdk.utils.Parcelables;
import io.particle.android.sdk.utils.Toaster;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {


                        Async.executeAsync(ParticleCloud.get(v.getContext()), new Async.ApiWork<ParticleCloud, Integer>() {

                            private ParticleDevice mDevice;

                            @Override
                            public Integer callApi(ParticleCloud cloud) throws ParticleCloudException, IOException {
                                cloud.logIn("Backooff", "yourownpasswd");
                                cloud.getDevices();
                                mDevice = cloud.getDevice("Your own App ID");
                                Integer obj;
                                try {
                                    obj = mDevice.getIntVariable("temp");
                                } catch (ParticleDevice.VariableDoesNotExistException e) {
                                    Toaster.l(MainActivity.this,"Erorr");
                                    obj = -1;
                                }
                                return obj;
                            }

                            @Override
                            public void onSuccess(Integer value) {
                                Toaster.l(MainActivity.this, "Logged in");
                                Intent intent = Weather.buildIntent(MainActivity.this, "Displaying Temp:"+value.toString()+" F", mDevice.getID());
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(ParticleCloudException e) {
                                Toaster.l(MainActivity.this, e.getBestMessage());
                                e.printStackTrace();
                                Log.d("info", e.getBestMessage());
                            }
                        });


                    }


                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
