package com.example.rentacar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class alqAdapter extends RecyclerView.Adapter<alqAdapter.ViewHolder> {

    ArrayList<alquileresClass> localDataSet;

    public alqAdapter(ArrayList<alquileresClass> dataSet) {
        localDataSet = dataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dui,placa,estado,fechaIni,fechaFin;
        ImageView editar, eliminar;

        public ViewHolder(View view) {
            super(view);

            dui = view.findViewById(R.id.viewDui);
            placa = view.findViewById(R.id.viewPlaca);
            estado = view.findViewById(R.id.viewEstado);
            fechaIni = view.findViewById(R.id.viewFecIni);
            fechaFin = view.findViewById(R.id.viewFecFin);

            editar = view.findViewById(R.id.imageViewEditar);
            eliminar = view.findViewById(R.id.imageViewEliminar);
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
        viewHolder.fechaIni.setText("Fecha de inicio:\n"+localDataSet.get(position).getFechaIni());
        viewHolder.fechaFin.setText("Fecha de entrega:\n"+localDataSet.get(position).getFechaFin());

        viewHolder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle("Eliminar");
                String mensaje = "Deseas eliminar el registro? ";
                builder.setMessage(mensaje);
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseFirestore db;
                        db = FirebaseFirestore.getInstance();
                        db.collection("alquileres").document(localDataSet.get(position).getDocumento())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(view.getContext(),"Eliminado con exito",Toast.LENGTH_LONG).show();
                                        Navigation.findNavController(view).navigate(R.id.inicioFragment);
                                    }
                                });
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(),"Operacion cancelada con exito",Toast.LENGTH_LONG).show();
                    }
                });
                builder.create();
                builder.show();
            }
        });

        viewHolder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("documento", localDataSet.get(position).getDocumento());
                bundle.putString("cliente", localDataSet.get(position).getClienteDui());
                bundle.putString("vehiculo", localDataSet.get(position).getVehiculoPlaca());
                bundle.putString("fecha", localDataSet.get(position).getFechaIni());
                bundle.putString("fechaFin", localDataSet.get(position).getFechaFin());
                bundle.putString("estado", localDataSet.get(position).getAlquilado());
                Navigation.findNavController(view).navigate(R.id.edit_alq_Fragment, bundle);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
