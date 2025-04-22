package com.ikerfernandez.rumbolibre;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VueloAdapter extends RecyclerView.Adapter<VueloAdapter.VueloViewHolder> {

    private final List<Vuelo> vuelos;

    public VueloAdapter(List<Vuelo> vuelos) {
        this.vuelos = vuelos;
    }

    @NonNull
    @Override
    public VueloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lv_vuelos_row, parent, false);
        return new VueloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VueloViewHolder holder, int position) {
        Vuelo vuelo = vuelos.get(position);
        holder.tvOrigen.setText(vuelo.getCiudadOrigen());
        holder.tvDestino.setText(vuelo.getCiudadDestino());
        holder.tvAerolinea.setText(vuelo.getAerolinea());
        // holder.tvPrecio.setText(String.format("€%.2f", vuelo.getPrecio()));
        holder.tvTiempo.setText(vuelo.getTiempoIda());
    }

    @Override
    public int getItemCount() {
        return vuelos.size();
    }

    public static class VueloViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrigen, tvDestino, tvAerolinea, tvTiempo; //, tvAerolinea, tvPrecio, tvTiempo;

        public VueloViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrigen = itemView.findViewById(R.id.tvVueloOrigen);
            tvDestino = itemView.findViewById(R.id.tvVueloDestino);
            tvAerolinea = itemView.findViewById(R.id.tvAerolinea);
            // tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvTiempo = itemView.findViewById(R.id.tvTiempo);
        }
    }
}