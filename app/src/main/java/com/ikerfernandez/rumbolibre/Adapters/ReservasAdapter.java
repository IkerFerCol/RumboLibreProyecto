package com.ikerfernandez.rumbolibre.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikerfernandez.rumbolibre.Modelos.Reserva;
import com.ikerfernandez.rumbolibre.Modelos.Vuelo;
import com.ikerfernandez.rumbolibre.R;
import com.ikerfernandez.rumbolibre.Servicios.ReservaApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservasAdapter extends RecyclerView.Adapter<ReservasAdapter.ReservaViewHolder> {

    private List<Reserva> listaReservas;
    private ReservaApiService apiService;
    private Context context;
    private List<Vuelo> listaVuelos;


    public ReservasAdapter(Context context, List<Reserva> reservas, List<Vuelo> vuelos, ReservaApiService apiService) {
        this.context = context;
        this.listaReservas = reservas;
        this.listaVuelos = vuelos;
        this.apiService = apiService;
    }

//    ### Infla el layout reserva_item.xml y crea el ViewHolder ###
    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reserva_item, parent, false);
        return new ReservaViewHolder(vista);
    }

//    ### Asigna los datos del vuelo asociado a la reserva y gestiona el botón de cancelar ###
    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        Reserva reserva = listaReservas.get(position);
        Long vueloId = reserva.getVueloId();

        Vuelo vuelo = buscarVueloPorId(vueloId);
        if (vuelo != null) {
            holder.tvOrigen.setText(vuelo.getCiudadOrigen());
            holder.tvDestino.setText(vuelo.getCiudadDestino());
            holder.tvFechaIda.setText(vuelo.getFechaInicioIda());
            holder.tvTiempo.setText(vuelo.getTiempoIda());
        }

        holder.btnCancelar.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition == RecyclerView.NO_POSITION) return;

            new android.app.AlertDialog.Builder(context).setTitle("Cancelar reserva").setMessage("¿Estás seguro de que quieres cancelar esta reserva?").setPositiveButton("Sí", (dialog, which) -> {
                        Reserva reservaActual = listaReservas.get(currentPosition);
                        Call<Void> call = apiService.eliminarReserva(reservaActual.getId());
                        call.enqueue(new Callback<>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    listaReservas.remove(currentPosition);
                                    notifyItemRemoved(currentPosition);
                                    Toast.makeText(context, "Reserva cancelada", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Error al cancelar", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(context, "Error de red", Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

    }



    @Override
    public int getItemCount() {
        return listaReservas.size();
    }

//    ### Guarda referencias a los elementos visuales del item: origen, destino, fecha, duración y botón ###
    public static class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrigen, tvDestino, tvFechaIda, tvTiempo;
        Button btnCancelar;

        public ReservaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrigen = itemView.findViewById(R.id.tvVueloOrigen);
            tvDestino = itemView.findViewById(R.id.tvVueloDestino);
            tvFechaIda = itemView.findViewById(R.id.fechaInicioIda);
            tvTiempo = itemView.findViewById(R.id.tiempoIda);
            btnCancelar = itemView.findViewById(R.id.btnCancelarReserva);
        }
    }

    private Vuelo buscarVueloPorId(Long id) {
        for (Vuelo vuelo : listaVuelos) {
            if (vuelo.getId().equals(id)) {
                return vuelo;
            }
        }
        return null;
    }

}
