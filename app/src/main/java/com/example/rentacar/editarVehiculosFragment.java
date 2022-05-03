package com.example.rentacar;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firestore.v1.WriteResult;

import java.util.HashMap;
import java.util.Map;

public class editarVehiculosFragment extends Fragment {


    public editarVehiculosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_editar_vehiculos, container, false);

        EditText placa = vista.findViewById(R.id.numeroPlacaVehiculoEditar);
        Button btnRegresar = vista.findViewById(R.id.btnRegresarEditarVehiculo);
        Button btnEditar = vista.findViewById(R.id.btnEditarVehiculoEdit);

        TextView tv1, tv2, tv3;
        Spinner sp1, sp2;
        tv1 = vista.findViewById(R.id.nombreVehiculoEditar);
        tv2 = vista.findViewById(R.id.modeloVehiculoEditar);
        tv3 = vista.findViewById(R.id.MarcaVehiculoEditar);
        sp1 = vista.findViewById(R.id.tipoVehiculoEditarSpinner);
        sp2 = vista.findViewById(R.id.estadoVehiculoEditarSpinner);

        placa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle("CONSULTAR");
                String mensaje = "Deseas consultar el vehiculo con la placa: " + placa.getText().toString();
                builder.setMessage(mensaje);
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(placa.getText().toString().isEmpty()){
                            Toast.makeText(vista.getContext(), "Ingresa una placa", Toast.LENGTH_SHORT).show();
                        }else {

                            FirebaseFirestore db;
                            db = FirebaseFirestore.getInstance();
                            db.collection("vehiculos").document(placa.getText().toString())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();

                                            if (document.exists()){
                                                tv1.setVisibility(View.VISIBLE);
                                                tv2.setVisibility(View.VISIBLE);
                                                tv3.setVisibility(View.VISIBLE);
                                                sp1.setVisibility(View.VISIBLE);
                                                sp2.setVisibility(View.VISIBLE);

                                                String nombre = "" + task.getResult().getData().get("nombre");
                                                tv1.setText(nombre);
                                                String modelo = "" + task.getResult().getData().get("modelo");
                                                tv2.setText(modelo);
                                                String marca = "" + task.getResult().getData().get("marca");
                                                tv3.setText(marca);
                                                String tipo = "" + task.getResult().getData().get("tipo");
                                                sp1.setSelection(tipos(tipo));

                                                String estado = "" + task.getResult().getData().get("estado");
                                                sp2.setSelection(estados(estado));

                                                Toast.makeText(vista.getContext(), "Datos encontrados", Toast.LENGTH_SHORT).show();

                                            }else{
                                                tv1.setVisibility(View.INVISIBLE);
                                                tv2.setVisibility(View.INVISIBLE);
                                                tv3.setVisibility(View.INVISIBLE);
                                                sp1.setVisibility(View.INVISIBLE);
                                                sp2.setVisibility(View.INVISIBLE);
                                                tv1.setText("");
                                                tv2.setText("");
                                                tv3.setText("");
                                                sp1.setSelection(0);
                                                sp2.setSelection(0);

                                                Toast.makeText(vista.getContext(), "Datos no encontrados", Toast.LENGTH_SHORT).show();
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

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv1.getText().toString().isEmpty() || tv2.getText().toString().isEmpty() ||
                        tv3.getText().toString().isEmpty()) {
                    Toast.makeText(vista.getContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    vehiculosClass actualizarVehiculo = new vehiculosClass(tv1.getText().toString(),
                            tv3.getText().toString(), tv2.getText().toString(),
                            sp1.getSelectedItem().toString(), sp2.getSelectedItem().toString(), 0);

                    FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("vehiculos").document(placa.getText().toString());
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Map<String, Object> update = new HashMap<>();
                                    update.put("nombre", tv1.getText().toString());
                                    update.put("modelo", tv2.getText().toString());
                                    update.put("marca", tv3.getText().toString());
                                    update.put("tipo", sp1.getSelectedItem().toString());
                                    update.put("estado", sp2.getSelectedItem().toString());

                                    db.collection("vehiculos")
                                            .document(placa.getText().toString())
                                            .set(update, SetOptions.merge());
                                    Toast.makeText(vista.getContext(), "Actualizado con exito", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(vista.getContext(), "Id no encontrado", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(vista.getContext(), "Error al editar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        return vista;
    }

    public int tipos(String aux){

        int posicion = 0;
        if(aux.equals("Coche")){
            posicion = 0;
        }else if (aux.equals("Microbus")){
            posicion = 1;
        }else if (aux.equals("Furgonetas de carga")){
            posicion = 2;
        }else if (aux.equals("Camion")){
            posicion = 3;
        }
        return posicion;
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