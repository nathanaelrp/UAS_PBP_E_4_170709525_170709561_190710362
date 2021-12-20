package com.app.uas;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.uas.model.Data;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
    List<Data> mdatalist;

    public Adapter(List<Data> datalist) {
        mdatalist = datalist;
    }


    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tampil, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder (MyViewHolder holder,final int position){
        holder.namaObat.setText(mdatalist.get(position).getNama_produk());
        int number = Integer.parseInt(mdatalist.get(position).getHarga_produk());
        String str = String.format(Locale.US, "%,d", number).replace(',', '.');
        holder.hargaObat.setText("Rp "+str);
        Glide.with(holder.itemView.getContext())
                .load(Setting.IMAGES_URL + mdatalist.get(position).getFoto_produk())
                .apply(new RequestOptions().override(350, 550))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.foto_produk);
    }

    @Override
    public int getItemCount() {
        return mdatalist.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView namaObat, hargaObat;
        public ImageView foto_produk;

        public MyViewHolder(View itemView) {
            super(itemView);
            namaObat = (TextView) itemView.findViewById(R.id.namaObat);
            hargaObat = (TextView) itemView.findViewById(R.id.hargaObat);
            foto_produk = (ImageView) itemView.findViewById(R.id.imgProduk);
        }
    }
}
