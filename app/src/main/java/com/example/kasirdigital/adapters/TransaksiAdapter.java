package com.example.kasirdigital.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirdigital.R;
import com.example.kasirdigital.models.Transaksi;

import java.util.List;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder> {
    private List<Transaksi> transaksiList;
    private OnTransaksiClickListener listener;
    private Context context;

    public interface OnTransaksiClickListener {
        void onTransaksiClick(Transaksi transaksi);
    }

    public TransaksiAdapter(Context context, List<Transaksi> transaksiList, OnTransaksiClickListener listener) {
        this.context = context;
        this.transaksiList = transaksiList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transaksi transaksi = transaksiList.get(position);
        DecimalFormat df = new DecimalFormat("#,###");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        holder.tvNomorTransaksi.setText(transaksi.getNomorTransaksi());
        holder.tvTanggal.setText(sdf.format(new Date(transaksi.getTanggal())));
        holder.tvTotal.setText("Rp " + df.format(transaksi.getTotalHarga()));
        holder.tvPenjual.setText(transaksi.getNamaPenjual());
        holder.tvJumlahItem.setText(transaksi.getJumlahItem() + " item");

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTransaksiClick(transaksi);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transaksiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomorTransaksi, tvTanggal, tvTotal, tvPenjual, tvJumlahItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNomorTransaksi = itemView.findViewById(R.id.tv_nomor_transaksi);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal_transaksi);
            tvTotal = itemView.findViewById(R.id.tv_total_transaksi);
            tvPenjual = itemView.findViewById(R.id.tv_penjual_transaksi);
            tvJumlahItem = itemView.findViewById(R.id.tv_jumlah_item_transaksi);
        }
    }
}
