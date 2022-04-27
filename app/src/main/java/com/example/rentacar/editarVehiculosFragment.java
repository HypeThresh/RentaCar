package com.example.rentacar;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

        TextView placa = vista.findViewById(R.id.numeroPlacaVehiculoEditar);
        Button btnRegresar = vista.findViewById(R.id.btnRegresarEditarVehiculo);
        Button btnEditar = vista.findViewById(R.id.btnEditarVehiculoEdit);

        TextView tv1, tv2, tv3;
        Spinner sp1, sp2;

        tv1 = vista.findViewById(R.id.nombreVehiculoEditar);
        tv1.setVisibility(View.VISIBLE);
        tv2 = vista.findViewById(R.id.modeloVehiculoEditar);
        tv2.setVisibility(View.VISIBLE);
        tv3 = vista.findViewById(R.id.MarcaVehiculoEditar);
        tv3.setVisibility(View.VISIBLE);
        sp1 = vista.findViewById(R.id.tipoVehiculoEditarSpinner);
        sp1.setVisibility(View.VISIBLE);
        sp2 = vista.findViewById(R.id.estadoVehiculoEditarSpinner);
        sp2.setVisibility(View.VISIBLE);

        placa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                db.collection("vehiculos").document(placa.getText().toString())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                            String nombre = "" + documentSnapshot.getData().get("nombre");
                            tv1.setText(nombre);
                            String modelo = "" + documentSnapshot.getData().get("modelo");
                            tv2.setText(modelo);
                            String marca = "" + documentSnapshot.getData().get("marca");
                            tv3.setText(marca);
                            Toast.makeText(vista.getContext(), "Datos encontrados", Toast.LENGTH_SHORT).show();
                        }
                });
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
                vehiculosClass actualizarVehiculo = new vehiculosClass(tv1.getText().toString(),
                        tv3.getText().toString(),tv2.getText().toString(),
                        sp1.getSelectedItem().toString(),sp2.getSelectedItem().toString(),0);

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
        });

        return vista;
    }
}