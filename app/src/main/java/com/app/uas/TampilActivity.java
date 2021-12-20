package com.app.uas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.uas.model.Data;
import com.app.uas.model.GetData;
import com.app.uas.network.NetworkConfig;
import com.app.uas.network.NetworkInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilActivity extends AppCompatActivity {
    NetworkInterface networkInterface;
    Button btnTambahObat;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static TampilActivity ua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampil);
        btnTambahObat = findViewById(R.id.btnTambahProduk);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_produk);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        networkInterface = NetworkConfig.getClient().create(NetworkInterface.class);
        ua=this;

        tampildataobat();
        btnTambahObat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TampilActivity.this, TambahActivity.class);
                startActivity(intent);
            }
        });
    }

    public void tampildataobat() {
        NetworkInterface networkInterface = NetworkConfig.getClient().create(NetworkInterface.class);
        Call<GetData> call = networkInterface.getData();
        call.enqueue(new Callback<GetData>() {
            @Override
            public void onResponse(Call<GetData> call, Response<GetData> response) {
                List<Data> dataList = response.body().getDataList();
                mAdapter = new Adapter(dataList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetData> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toast.makeText(getApplicationContext(), "Gagal Menampilkan Obat  " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
