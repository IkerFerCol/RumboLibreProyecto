package com.ikerfernandez.rumbolibre;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder> {
    private List<Mensaje> mensajesList;

    public MensajeAdapter(List<Mensaje> mensajesList){
        this.mensajesList = mensajesList;
    }

    @Override
    public MensajeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensaje, parent, false);
        return new MensajeViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(MensajeViewHolder holder, int position) {
        Mensaje mensaje = mensajesList.get(position);
        holder.autor.setText(mensaje.getAutor());
        holder.contenido.setText(mensaje.getContenido());
    }

    @Override
    public int getItemCount() {
        return mensajesList.size();
    }

    public static class MensajeViewHolder extends RecyclerView.ViewHolder {
        TextView autor, contenido;

        public MensajeViewHolder(View itemView) {
            super(itemView);
            autor = itemView.findViewById(R.id.tvAutor);
            contenido = itemView.findViewById(R.id.tvContenido);
        }
    }
}
