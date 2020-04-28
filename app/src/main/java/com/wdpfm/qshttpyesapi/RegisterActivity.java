package com.wdpfm.qshttpyesapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.song.http.QSHttp;
import org.song.http.framework.QSHttpCallback;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("注册");
        final String TAG = "TAG注册";
        final EditText editUsername = findViewById(R.id.regUsername);
        final EditText editPassword = findViewById(R.id.regPassword);
        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editUsername.getText().toString();
                final String password = editPassword.getText().toString();
                if (!username.isEmpty() && !password.isEmpty()) {
                    regRoot user = new regRoot(new regData(username, Md5.encrypt(password)));
                    String url = "https://hb5.api.okayapi.com/?&s=App.SuperTable.CheckCreate&model_name=user&database=super&check_field=username"+Key.key;
                    QSHttp.postJSON(url)
                            .jsonBody(user)
                            .jsonModel(regResultRoot.class)
                            .buildAndExecute(new QSHttpCallback<regResultRoot>() {
                                @Override
                                public void onComplete(regResultRoot data) {
                                    switch (data.getRegisterData().getErr_code()) {
                                        case 0:
                                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent();
                                            intent.putExtra("username", username);
                                            intent.putExtra("password", password);
                                            setResult(0, intent);
                                            finish();
                                            break;
                                        case 1:
                                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 3:
                                            Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                                            break;
                                        default:
                                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            });
                } else {
                    Toast.makeText(RegisterActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

class regData {
    private String username;

    private String password;

    public regData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class regRoot {
    private regData data;

    public regRoot(regData data) {
        this.data = data;
    }

    public regData getData() {
        return data;
    }
}

class regResultData {
    private int err_code;

    private String err_msg;

    private int id;

    private List<String> data;

    public void setErr_code(int err_code){
        this.err_code = err_code;
    }
    public int getErr_code(){
        return this.err_code;
    }
    public void setErr_msg(String err_msg){
        this.err_msg = err_msg;
    }
    public String getErr_msg(){
        return this.err_msg;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setData(List<String> data){
        this.data = data;
    }
    public List<String> getData(){
        return this.data;
    }
}

class regResultRoot {
    private int ret;

    private regResultData data;

    private String msg;

    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getRet() {
        return this.ret;
    }

    public void setData(regResultData data) {
        this.data = data;
    }

    public regResultData getRegisterData() {
        return this.data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}