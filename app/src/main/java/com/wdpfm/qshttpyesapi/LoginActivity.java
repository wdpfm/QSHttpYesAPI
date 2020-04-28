package com.wdpfm.qshttpyesapi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.song.http.QSHttp;
import org.song.http.framework.QSHttpCallback;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText editUsername;
    EditText editPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("登录");
        final String TAG="TAG登录";
        //SharePreference的读取
        //1.获取SharePreference对象
        SharedPreferences share=getSharedPreferences("user",MODE_PRIVATE);
        //2.根据key获取内容
        String oldUsername=share.getString("username","");
        if (!oldUsername.equals("")){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            Toast.makeText(this, "登陆成功，欢迎回来:"+oldUsername, Toast.LENGTH_SHORT).show();
            return;
        }
        editUsername = findViewById(R.id.logUsername);
        editPassword = findViewById(R.id.logPassword);
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=editUsername.getText().toString();
                final String password=editPassword.getText().toString();
                if (!username.isEmpty()&&!password.isEmpty()){
                    String url = "https://hb5.api.okayapi.com/?&s=App.SuperTable.FreeFindOne&model_name=user&logic=and&fields=id"+Key.key;
                    QSHttp.postJSON(url)
                            .param("where","[[\"username\",\"=\",\""+username+"\"],[\"password\",\"=\",\""+Md5.encrypt(password)+"\"]]")
                            //.jsonBody(Object) 这个参数可以直接传一个实体类,fastjson会自动转化成json字符串
                            .jsonModel(logResultRoot.class)
                            .buildAndExecute(new QSHttpCallback<logResultRoot>() {
                                @Override
                                public void onComplete(logResultRoot data) {
                                    switch (data.getLoginData().getErr_code()){
                                        case 0:
                                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                            //存储信息到SharePreference
                                            //1.获取SharePreference对象
                                            SharedPreferences share=getSharedPreferences("user",MODE_PRIVATE);
                                            //2.获取Editor对象
                                            SharedPreferences.Editor editor=share.edit();
                                            //3.存储信息
                                            editor.putString("username",username);
                                            //4.提交
                                            editor.commit();
                                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                            finish();
                                            break;
                                        case 1:
                                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 3:
                                            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                                            break;
                                        default:
                                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            });
                }else{
                    Toast.makeText(LoginActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.goRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 0) {
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");
            editUsername.setText(username);
            editPassword.setText(password);
        }
    }
}
class logResultDataData
{
    private int id;

    private String username;

    private String password;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }
}
class logResultData {
    private int err_code;

    private String err_msg;

    private int id;

    //private logResultDataData data;

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
//    public void setData(logResultDataData data){
//        this.data = data;
//    }
//    public logResultDataData getData(){
//        return this.data;
//    }
}

class logResultRoot {
    private int ret;

    private logResultData data;

    private String msg;

    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getRet() {
        return this.ret;
    }

    public void setData(logResultData data) {
        this.data = data;
    }

    public logResultData getLoginData() {
        return this.data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}