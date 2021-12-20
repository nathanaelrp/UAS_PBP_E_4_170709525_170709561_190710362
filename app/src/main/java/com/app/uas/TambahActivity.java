package com.app.uas;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.uas.model.PostUpdate;
import com.app.uas.network.NetworkConfig;
import com.app.uas.network.NetworkInterface;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    EditText nama_obat, harga_obat, jumlah_obat;
    Button btnSimpan, btnGallery;
    TextView backdataproduk;
    ImageView foto_produk;

    private String mediaPath;
    private String postPath;

    private static final int REQUEST_PICK_PHOTO = Setting.REQUEST_PICK_PHOTO;
    private static final int REQUEST_WRITE_PERMISSION = Setting.REQUEST_WRITE_PERMISSION;
    private static final String INSERT_FLAG = Setting.INSERT_FLAG;

    // Akses Izin Ambil Gambar dari Storage
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            createObat();
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah);
        init();
        backdataproduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahActivity.this, TampilActivity.class);
                startActivity(intent);
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(albumIntent, REQUEST_PICK_PHOTO);
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   requestPermission();
            }
        });
    }

    public void init(){
        nama_obat = (EditText) findViewById(R.id.nama_obat);
        harga_obat = (EditText) findViewById(R.id.harga_obat);
        jumlah_obat = (EditText) findViewById(R.id.jumlah_obat);
        foto_produk = (ImageView) findViewById(R.id.foto_produk);
        btnGallery = (Button) findViewById(R.id.btnUploadImage);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        backdataproduk = (TextView) findViewById(R.id.txtKembali);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_PICK_PHOTO){
                if(data!=null){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    foto_produk.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
    }

    private void createObat(){
        if (mediaPath== null)
        {
            Toast.makeText(getApplicationContext(), "Gambar Kosong", Toast.LENGTH_LONG).show();
        }
        else {
            File imagefile = new File(mediaPath);
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto_produk", imagefile.getName(), reqBody);
            final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            NetworkInterface networkInterface = NetworkConfig.getClient().create(NetworkInterface.class);
            Call<PostUpdate> postHerosCall = networkInterface.createObat(partImage,
                    RequestBody.create(MediaType.parse("text/plain"), nama_obat.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), ""),
                    RequestBody.create(MediaType.parse("text/plain"), harga_obat.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), jumlah_obat.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), ""),
                    RequestBody.create(MediaType.parse("text/plain"), date),
                    RequestBody.create(MediaType.parse("text/plain"), INSERT_FLAG));
            postHerosCall.enqueue(new Callback<PostUpdate>() {
                @Override
                public void onResponse(Call<PostUpdate> call, Response<PostUpdate> response) {
                    if(response.isSuccessful()){
                        Log.d("RETRO", "LOG : " + response.message());
                        Toast.makeText(getApplicationContext(), "Obat berhasil ditambah", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TambahActivity.this, TampilActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Log.d("RETRO", "LOG : " + response.message());
                        Toast.makeText(getApplicationContext(), "Obat gagal ditambah "+response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PostUpdate> call, Throwable t) {
                    Log.d("RETRO", "LOG : " + t.getMessage());
                    Toast.makeText(getApplicationContext(), "Obat gagal ditambah", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // Cek Versi Android Tuk Minta Izin
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            createObat();
        }
    }

    // Menu Kembali Ke Home
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
