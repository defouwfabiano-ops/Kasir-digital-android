package com.example.kasirdigital.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirdigital.R;
import com.example.kasirdigital.models.Produk;

import java.util.List;
import java.text.DecimalFormat;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ViewHolder> {
    private List<Produk> produkList;
    private OnProdukClickListener listener;
    private Context context;

    public interface OnProdukClickListener {
        void onProdukClick(Produk produk);
    }

    public ProdukAdapter(Context context, List<Produk> produkList, OnProdukClickListener listener) {
        this.context = context;
        this.produkList = produkList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produk, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Produk produk = produkList.get(position);
        DecimalFormat df = new DecimalFormat("#,###");

        holder.tvNama.setText(produk.getNama());
        holder.tvHarga.setText("Rp " + df.format(produk.getHarga()));
        holder.tvStok.setText("Stok: " + produk.getStok());
        holder.tvKategori.setText(produk.getKategori());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProdukClick(produk);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvHarga, tvStok, tvKategori;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama_produk);
            tvHarga = itemView.findViewById(R.id.tv_harga_produk);
            tvStok = itemView.findViewById(R.id.tv_stok_produk);
            tvKategori = itemView.findViewById(R.id.tv_kategori_produk);
        }
    }
}
