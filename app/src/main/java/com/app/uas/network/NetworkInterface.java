package com.app.uas.network;

import com.app.uas.model.GetData;
import com.app.uas.model.Login;
import com.app.uas.model.PostUpdate;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface NetworkInterface {
    @POST("accounts/login")
    @FormUrlEncoded
    Call<Login> loginUsers(@Field("nama_user") String nama_user,
                           @Field("password_user") String password_user);

    @GET("obats")
    Call<GetData> getData();

    @Multipart
    @POST("obats")
    Call<PostUpdate> createObat(@Part MultipartBody.Part foto_produk,
                                  @Part("nama_produk") RequestBody nama_produk,
                                  @Part("biaya_produk") RequestBody biaya_produk,
                                  @Part("harga_produk") RequestBody harga_produk,
                                  @Part("jumlah_produk") RequestBody jumlah_produk,
                                  @Part("kategori_produk") RequestBody kategori_produk,
                                  @Part("tanggal_produk") RequestBody tanggal_produk,
                                  @Part("flag") RequestBody flag);


}
