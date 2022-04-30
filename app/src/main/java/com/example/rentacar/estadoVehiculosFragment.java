package com.example.rentacar;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class estadoVehiculosFragment extends Fragment {

    public estadoVehiculosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_estado_vehiculos, container, false);

        TextView placa = vista.findViewById(R.id.numeroPlacaVehiculoEstado);
        Button btnRegresar = vista.findViewById(R.id.btnRegresarEstadoVehiculo);
        Button btnActualizar = vista.findViewById(R.id.btnActualizarEstadoVehiculo);

        placa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle("CONSULTAR");
                String mensaje = "Deseas consultar el vehiculo con la placa: " + placa.getText().toString();
                builder.setMessage(mensaje);
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // code

                        if(placa.getText().toString().isEmpty()){
                            Toast.makeText(vista.getContext(), "Ingresa una placa", Toast.LENGTH_SHORT).show();
                        }else {

                            FirebaseFirestore db;
                            db = FirebaseFirestore.getInstance();
                            db.collection("vehiculos").document(placa.getText().toString())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                       public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                           if (task.isSuccessful()) {
                                               DocumentSnapshot document = task.getResult();
                                               Spinner sp1;
                                               sp1 = vista.findViewById(R.id.estadoVehiculoSpinnerEstado);
                                               if (document.exists()){

                                                   sp1.setVisibility(View.VISIBLE);

                                                   String estado = "" + task.getResult().getData().get("estado");
                                                   sp1.setSelection(estados(estado));
                                                   Toast.makeText(vista.getContext(), "Vehiculo encontrado", Toast.LENGTH_SHORT).show();

                                               }else{
                                                   sp1.setSelection(0);

                                                   Toast.makeText(vista.getContext(), "No encontrados", Toast.LENGTH_SHORT).show();
                                               }
                                           }else{
                                               Toast.makeText(vista.getContext(), "Error al realizar la consulta", Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                });
                        }

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Navigation.findNavController(vista).navigate(R.id.gestionVehiculos);
                    }
                });
                builder.create();
                builder.show();
            }
        });
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.gestionVehiculos);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = placa.getText().toString();
                if(placa.getText().toString().isEmpty()){
                    Toast.makeText(vista.getContext(), "Ingresa una placa", Toast.LENGTH_SHORT).show();
                }else {
                    // code
                    FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("vehiculos").document(placa.getText().toString());
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                Spinner sp1;
                                sp1 = vista.findViewById(R.id.estadoVehiculoSpinnerEstado);
                                if (document.exists()) {
                                    Map<String, Object> update = new HashMap<>();
                                    update.put("tipo", sp1.getSelectedItem().toString());

                                    db.collection("vehiculos")
                                            .document(placa.getText().toString())
                                            .set(update, SetOptions.merge());
                                    Toast.makeText(vista.getContext(), "Actualizado con exito", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(vista.getContext(), "Id no encontrado", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(vista.getContext(), "Error al asignar estado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        return vista;
    }

    public int estados(String aux){

        int posicion = 0;
        if(aux.equals("Disponible")){
            posicion = 0;
        }else if (aux.equals("Alquilado")){
            posicion = 1;
        }
        return posicion;
    }
}