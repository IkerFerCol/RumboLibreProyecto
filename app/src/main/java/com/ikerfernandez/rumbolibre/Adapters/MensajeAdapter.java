package com.ikerfernandez.rumbolibre.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ikerfernandez.rumbolibre.Modelos.Mensaje;
import com.ikerfernandez.rumbolibre.R;

import java.util.List;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder> {
    private List<Mensaje> mensajesList;

//    ########### Constructor que recibe la lista de mensajes que se mostrarán en el RecyclerView #######
    public MensajeAdapter(List<Mensaje> mensajesList){
        this.mensajesList = mensajesList;
    }

    // ########## Infla el diseño item_mensaje.xml y crea un nuevo MensajeViewHolder #######
    @Override
    public MensajeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensaje, parent, false);
        return new MensajeViewHolder(vista);
    }

// ####### Asigna los datos del mensaje (autor y contenido) al ViewHolder en la posición dada #######
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

//     ### Contiene las vistas TextView para mostrar el autor y el contenido del mensaje. Se inicializan en su constructor. ###
    public static class MensajeViewHolder extends RecyclerView.ViewHolder {
        TextView autor, contenido;

        public MensajeViewHolder(View itemView) {
            super(itemView);
            autor = itemView.findViewById(R.id.tvAutor);
            contenido = itemView.findViewById(R.id.tvContenido);
        }
    }
}
