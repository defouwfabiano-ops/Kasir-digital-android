package com.example.kasirdigital.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirdigital.R;
import com.example.kasirdigital.models.KeranjangItem;

import java.util.List;
import java.text.DecimalFormat;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.ViewHolder> {
    private List<KeranjangItem> items;
    private OnItemActionListener listener;
    private Context context;

    public interface OnItemActionListener {
        void onItemRemoved(int position);
        void onQuantityChanged(int position, int quantity);
    }

    public KeranjangAdapter(Context context, List<KeranjangItem> items, OnItemActionListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_keranjang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        KeranjangItem item = items.get(position);
        DecimalFormat df = new DecimalFormat("#,###");

        holder.tvNamaProduk.setText(item.getNamaProduk());
        holder.tvHarga.setText("Rp " + df.format(item.getHarga()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
        holder.tvSubtotal.setText("Rp " + df.format(item.getSubtotal()));

        holder.btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyItemChanged(position);
                if (listener != null) {
                    listener.onQuantityChanged(position, item.getQuantity());
                }
            }
        });

        holder.btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyItemChanged(position);
            if (listener != null) {
                listener.onQuantityChanged(position, item.getQuantity());
            }
        });

        holder.btnRemove.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaProduk, tvHarga, tvQuantity, tvSubtotal;
        ImageButton btnMinus, btnPlus, btnRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNamaProduk = itemView.findViewById(R.id.tv_nama_produk_keranjang);
            tvHarga = itemView.findViewById(R.id.tv_harga_keranjang);
            tvQuantity = itemView.findViewById(R.id.tv_quantity_keranjang);
            tvSubtotal = itemView.findViewById(R.id.tv_subtotal_keranjang);
            btnMinus = itemView.findViewById(R.id.btn_minus_keranjang);
            btnPlus = itemView.findViewById(R.id.btn_plus_keranjang);
            btnRemove = itemView.findViewById(R.id.btn_remove_keranjang);
        }
    }
}
