package com.example.brett.myweatherapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudException;
import io.particle.android.sdk.cloud.ParticleDevice;
import io.particle.android.sdk.utils.Async;
import io.particle.android.sdk.utils.Toaster;

public class Weather extends ActionBarActivity {
    private static final String ARG_VALUE = "ARG_VALUE";
    private static final String ARG_DEVICEID = "ARG_DEVICEID";
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        text = (TextView) findViewById(R.id.textView2);
        text.setText(getIntent().getStringExtra(ARG_VALUE));
        findViewById(R.id.button3).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {
                        Async.executeAsync(ParticleCloud.get(v.getContext()), new Async.ApiWork<ParticleCloud, Integer>() {

                            public Integer callApi(ParticleCloud ParticleCloud) throws ParticleCloudException, IOException {
                                ParticleDevice device = ParticleCloud.getDevice(getIntent().getStringExtra(ARG_DEVICEID));
                                int variable;
                                try {
                                    variable = device.getIntVariable("temp");
                                } catch (ParticleDevice.VariableDoesNotExistException e) {
                                    Toaster.l(Weather.this, e.getMessage());
                                    variable = -1;
                                }
                                return variable;
                            }


                            public void onSuccess(Integer dub) { // this goes on the main thread
                                text.setText("Tempature is "+dub.toString());
                            }

                            @Override
                            public void onFailure(ParticleCloudException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
        findViewById(R.id.button4).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {
                        Async.executeAsync(ParticleCloud.get(v.getContext()), new Async.ApiWork<ParticleCloud, Integer>() {

                            public Integer callApi(ParticleCloud ParticleCloud) throws ParticleCloudException, IOException {
                                ParticleDevice device = ParticleCloud.getDevice(getIntent().getStringExtra(ARG_DEVICEID));
                                int variable;
                                try {
                                    variable = device.getIntVariable("humd");
                                } catch (ParticleDevice.VariableDoesNotExistException e) {
                                    Toaster.l(Weather.this, e.getMessage());
                                    variable = -1;
                                }
                                return variable;
                            }


                            public void onSuccess(Integer dub) { // this goes on the main thread
                                text.setText("Humidty is "+dub.toString());
                            }

                            @Override
                            public void onFailure(ParticleCloudException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
        findViewById(R.id.button5).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {
                        Async.executeAsync(ParticleCloud.get(v.getContext()), new Async.ApiWork<ParticleCloud, Integer>() {

                            public Integer callApi(ParticleCloud ParticleCloud) throws ParticleCloudException, IOException {
                                ParticleDevice device = ParticleCloud.getDevice(getIntent().getStringExtra(ARG_DEVICEID));
                                int variable;
                                try {
                                    variable = device.getIntVariable("pressure");
                                } catch (ParticleDevice.VariableDoesNotExistException e) {
                                    Toaster.l(Weather.this, e.getMessage());
                                    variable = -1;
                                }
                                return variable;
                            }


                            public void onSuccess(Integer dub) { // this goes on the main thread
                                text.setText("Pressur(in Kpa) is "+dub.toString());
                            }

                            @Override
                            public void onFailure(ParticleCloudException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
    }
    public static Intent buildIntent(Context ctx, String value, String deviceid) {
        Intent intent = new Intent(ctx, Weather.class);
        intent.putExtra(ARG_VALUE, value);
        intent.putExtra(ARG_DEVICEID, deviceid);

        return intent;
    }
}







