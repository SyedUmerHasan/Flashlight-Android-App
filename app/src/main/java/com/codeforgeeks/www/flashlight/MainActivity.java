package com.codeforgeeks.www.flashlight;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton imagebutton;
    Camera camera;
    Camera.Parameters parameter;
    boolean isflash = false;
    boolean isOn = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imagebutton = (ImageButton)findViewById(R.id.imagebutton);
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
        {
            camera= Camera.open();
            parameter = camera.getParameters();
            isflash = true;
        }

        imagebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                if(isflash)
                {
                    if(!isOn)
                    {
                        imagebutton.setImageResource(R.drawable.on);
                        parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameter);
                        camera.startPreview();
                        isOn = true;
                    }
                    else {
                        imagebutton.setImageResource(R.drawable.off);
                        parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameter);
                        camera.stopPreview();
                        isOn = false;
                    }
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error...");
                    builder.setMessage("Flashlight is not available");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                      });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(camera!= null){
            camera.release();
            camera = null;
        }
    }
}
