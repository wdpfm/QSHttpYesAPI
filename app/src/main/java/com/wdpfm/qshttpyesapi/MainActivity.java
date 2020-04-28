package com.wdpfm.qshttpyesapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("主界面");
        findViewById(R.id.btnLogout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences share=getSharedPreferences("user",MODE_PRIVATE);
                        SharedPreferences.Editor editor = share.edit();
                        editor.clear();//清空SharePreference
                        editor.commit();
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    }
                }
        );
    }
}
