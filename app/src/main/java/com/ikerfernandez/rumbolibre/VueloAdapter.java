package com.ikerfernandez.rumbolibre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class VueloAdapter extends ArrayAdapter<Vuelo> {
    public VueloAdapter(Context context, List<Vuelo> vuelos) {
        super(context, R.layout.lv_vuelos_row, vuelos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Vuelo vuelo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.lv_vuelos_row, parent, false);
        }

        TextView tvOrigen = convertView.findViewById(R.id.tvVueloOrigen);
        TextView tvDestino = convertView.findViewById(R.id.tvVueloDestino);
//        TextView tvAerolinea = convertView.findViewById(R.id.tvAerolinea);
//        TextView tvPrecio = convertView.findViewById(R.id.tvPrecio);
//        TextView tvTiempo = convertView.findViewById(R.id.tvTiempo);

        tvOrigen.setText(vuelo.getOrigen());
        tvDestino.setText(vuelo.getDestino());
//        tvAerolinea.setText(vuelo.getAerolinea());
//        tvPrecio.setText(String.format("€%.2f", vuelo.getPrecio()));
//        tvTiempo.setText(vuelo.getTiempo());

        return convertView;
    }
}
