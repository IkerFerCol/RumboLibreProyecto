package com.ikerfernandez.rumbolibre.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikerfernandez.rumbolibre.Modelos.Vuelo;
import com.ikerfernandez.rumbolibre.R;
import com.ikerfernandez.rumbolibre.VueloDetailActivity;

import java.util.List;
import java.util.Set;

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


//    ### Infla el diseño de cada fila de vuelo (lv_vuelos_row) y crea el ViewHolder ###
    @NonNull
    @Override
    public VueloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lv_vuelos_row, parent, false);
        return new VueloViewHolder(view);
    }

//    ### Muestra los datos del vuelo (origen, destino, aerolínea, tiempo) y abre los detalles al pulsar el item ###
    @Override
    public void onBindViewHolder(@NonNull VueloViewHolder holder, int position) {
        Vuelo vuelo = vuelos.get(position);
        holder.tvOrigen.setText(vuelo.getCiudadOrigen());
        holder.tvDestino.setText(vuelo.getCiudadDestino());
        holder.tvAerolinea.setText(vuelo.getAerolinea());
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

//    ### Reemplaza la lista de vuelos actual con una nueva y actualiza la vista ###
    public void actualizarLista(List<Vuelo> nuevosVuelos) {
        this.vuelos.clear();
        this.vuelos.addAll(nuevosVuelos);
        notifyDataSetChanged();
    }

//    ### Contiene las referencias a los elementos visuales de cada item (TextViews para origen, destino, aerolínea, tiempo) ###
    public static class VueloViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrigen, tvDestino, tvAerolinea, tvTiempo;
        public VueloViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrigen = itemView.findViewById(R.id.tvVueloOrigen);
            tvDestino = itemView.findViewById(R.id.tvVueloDestino);
            tvAerolinea = itemView.findViewById(R.id.tvAerolinea);
            tvTiempo = itemView.findViewById(R.id.tvTiempo);
        }
    }
}