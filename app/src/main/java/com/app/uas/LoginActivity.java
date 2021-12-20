package com.app.uas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.uas.model.DetailLogin;
import com.app.uas.model.Login;
import com.app.uas.network.NetworkConfig;
import com.app.uas.network.NetworkInterface;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{
    EditText username_login, password_login;
    Button btnLogin;
    NetworkInterface networkInterface = NetworkConfig.getClient().create(NetworkInterface.class);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username_login = findViewById(R.id.username_login);
        password_login = findViewById(R.id.password_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username_login.getText().toString().isEmpty() || password_login.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Data Kurang Lengkap", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Login();
                }
            }
        });

    }

    private void Login(){
            Call<Login> call = networkInterface.loginUsers(username_login.getText().toString(), password_login.getText().toString());
            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    if(response.isSuccessful()) {
                        List<DetailLogin> usersList = response.body().getLoginUsers();
                        Toast.makeText(getApplicationContext(), "Selamat datang kembali "+username_login.getText().toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TampilActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Log.d("RETRO", "ON FAIL : " + response.message());
                        Toast.makeText(getApplicationContext(), "Login Gagal ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Login Gagal ", Toast.LENGTH_SHORT).show();
                }
            });
    }
}



