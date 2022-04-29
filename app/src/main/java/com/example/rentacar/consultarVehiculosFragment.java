package com.example.rentacar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

public class consultarVehiculosFragment extends Fragment {
    public consultarVehiculosFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_consultar_vehiculos, container,false);
        TextView placa = vista.findViewById(R.id.numeroPlacaVehiculoConsutar);
        Button btnRegresar = vista.findViewById(R.id.btnRegresarConsultarVehiculo);
        Button btnConsultar = vista.findViewById(R.id.btnConsultarVehiculoConsult);
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(placa.getText().toString().isEmpty()){
                    Toast.makeText(vista.getContext(), "Ingresa una placa",
                            Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();
                    db.collection("vehiculos").document(placa.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                               @Override
                               public void onComplete(@NonNull Task<DocumentSnapshot>
                                                              task) {
                                   if (task.isSuccessful()) {
                                       DocumentSnapshot document = task.getResult();
                                       TextView tv1, tv2, tv3, tv4, tv5;
                                       tv1 = vista.findViewById(R.id.nombreVehiculoConsultar);
                                       tv2 = vista.findViewById(R.id.modeloVehiculoConsultar);
                                       tv3 = vista.findViewById(R.id.MarcaVehiculoConsultar);
                                       tv4 = vista.findViewById(R.id.tipoVehiculoConsultarSpinner);
                                       tv5 =
                                               vista.findViewById(R.id.estadoVehiculoConsultarSpinner);
                                       if (document.exists()) {
                                           tv1.setVisibility(View.VISIBLE);
                                           tv2.setVisibility(View.VISIBLE);
                                           tv3.setVisibility(View.VISIBLE);
                                           tv4.setVisibility(View.VISIBLE);
                                           tv5.setVisibility(View.VISIBLE);
                                           String nombre = "" +
                                                   task.getResult().get("nombre").toString();
                                           tv1.setText(nombre);
                                           String modelo = "" +
                                                   task.getResult().get("modelo").toString();
                                           tv2.setText(modelo);
                                           String marca = "" +
                                                   task.getResult().get("marca").toString();
                                           tv3.setText(marca);
                                           String tipo = "" + task.getResult().get("tipo").toString();
                                           tv4.setText(tipo);
                                           String estado = "" +
                                                   task.getResult().get("estado").toString();
                                           tv5.setText(estado);
                                           Toast.makeText(vista.getContext(), "Datos encontrados",
                                                   Toast.LENGTH_SHORT).show();
                                       } else {
                                           tv1.setVisibility(View.INVISIBLE);
                                           tv2.setVisibility(View.INVISIBLE);
                                           tv3.setVisibility(View.INVISIBLE);
                                           tv4.setVisibility(View.INVISIBLE);
                                           tv5.setVisibility(View.INVISIBLE);
                                           tv1.setText("");
                                           tv2.setText("");
                                           tv3.setText("");
                                           tv4.setText("");
                                           tv5.setText("");
                                           placa.setText("");
                                           Toast.makeText(vista.getContext(), "Datos no encontrados", Toast.LENGTH_SHORT).show();
                                       }
                                   } else {
                                       Toast.makeText(vista.getContext(), "Error al realizar la consulta", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
                }
            }
        });
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.gestionVehiculos);
            }
        });
        return vista;
    }
}