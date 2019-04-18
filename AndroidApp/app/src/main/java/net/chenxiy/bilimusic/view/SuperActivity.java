package net.chenxiy.bilimusic.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;


import com.lusfold.spinnerloading.SpinnerLoading;

import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.service.MediaService;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

public class SuperActivity extends AppCompatActivity {
    private Dialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(this);

        View v = getLayoutInflater().inflate(R.layout.dialogloading,null);
        SpinnerLoading view=v.findViewById(R.id.spinnerLoading);
        view.setPaintMode(1);
        view.setCircleRadius(10);
        view.setItemCount(10);
        dialog.setContentView(v);
        dialog.setCancelable(false);
        MediaService.NetStatus.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("Loading")){

                    createloading();
                }else {//finish

                    cancelLoading();
                }
            }
        });
    }


    private void createloading(){


        dialog.show();
    }

    private void cancelLoading(){
        dialog.dismiss();
    }
}
