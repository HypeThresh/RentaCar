package com.example.rentacar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class clienteAdapter extends RecyclerView.Adapter<clienteAdapter.ViewHolder> {

    ArrayList<alquileresClass> localDataSet;

    public clienteAdapter(ArrayList<alquileresClass> dataSet) { localDataSet = dataSet;    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dui,placa,estado,fechaIni,fechaFin;

        public ViewHolder(View view) {
            super(view);

            dui = view.findViewById(R.id.viewDuiCliente);
            placa = view.findViewById(R.id.viewPlacaCliente);
            estado = view.findViewById(R.id.viewEstadoCliente);
            fechaIni = view.findViewById(R.id.viewFecIniCliente);
            fechaFin = view.findViewById(R.id.viewFecFinCliente);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cliente_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.dui.setText("Dui: "+localDataSet.get(position).getClienteDui());
        viewHolder.placa.setText("Placa del vehiculo: "+localDataSet.get(position).getVehiculoPlaca());
        viewHolder.estado.setText("Estado: "+localDataSet.get(position).getAlquilado());
        viewHolder.fechaIni.setText("Fecha de inicio:\n"+localDataSet.get(position).getFechaIni());
        viewHolder.fechaFin.setText("Fecha de entrega:\n"+localDataSet.get(position).getFechaFin());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

}