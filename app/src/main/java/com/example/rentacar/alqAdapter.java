package com.example.rentacar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class alqAdapter extends RecyclerView.Adapter<alqAdapter.ViewHolder> {

    ArrayList<alquileresClass> localDataSet;

    public alqAdapter(ArrayList<alquileresClass> dataSet) {
        localDataSet = dataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dui,placa,estado,fechaIni,fechaFin;

        public ViewHolder(View view) {
            super(view);

            dui = view.findViewById(R.id.viewDui);
            placa = view.findViewById(R.id.viewPlaca);
            estado = view.findViewById(R.id.viewEstado);
            fechaIni = view.findViewById(R.id.viewFecIni);
            fechaFin = view.findViewById(R.id.viewFecFin);
        }
        public TextView getTextViewDui() { return dui; }
        public TextView getTextViewPlaca() { return placa; }
        public TextView getTextViewEstado() { return estado; }
        public TextView getTextViewfechaIni() { return fechaIni; }
        public TextView getTextViewfechaFin() { return fechaFin; }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_item, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.dui.setText("Dui: "+localDataSet.get(position).getClienteDui());
        viewHolder.placa.setText("Placa del vehiculo: "+localDataSet.get(position).getVehiculoPlaca());
        viewHolder.estado.setText("Estado: "+localDataSet.get(position).getAlquilado());
        viewHolder.fechaIni.setText("Fecha de inicio: "+localDataSet.get(position).getFechaIni());
        viewHolder.fechaFin.setText("Fecha de entrega: "+localDataSet.get(position).getFechaFin());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
