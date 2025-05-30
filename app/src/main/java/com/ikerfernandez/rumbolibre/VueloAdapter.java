package com.ikerfernandez.rumbolibre;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikerfernandez.rumbolibre.databinding.VueloDetailsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VueloAdapter extends RecyclerView.Adapter<VueloAdapter.VueloViewHolder> {

    private List<Vuelo> vuelos;
    private Context context;
    private Set<Long> idsFavoritos;
    private Long usuarioId;
    private boolean modoFavoritos;


    public VueloAdapter(List<Vuelo> vuelos, Context context) {
        this.vuelos = vuelos;
        this.context = context;
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

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, VueloDetailActivity.class);
            intent.putExtra("vuelo", vuelo);
            context.startActivity(intent);
        });



    }

    @Override
    public int getItemCount() {
        return vuelos.size();
    }

    public void actualizarLista(List<Vuelo> nuevosVuelos) {
        this.vuelos.clear();
        this.vuelos.addAll(nuevosVuelos);
        notifyDataSetChanged();
    }

    public static class VueloViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrigen, tvDestino, tvAerolinea, tvTiempo; //, tvAerolinea, tvPrecio, tvTiempo;
        CheckBox cbFavorito;
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