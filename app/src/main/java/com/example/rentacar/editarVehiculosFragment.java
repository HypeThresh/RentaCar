package com.example.rentacar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
        Button btnEditar = vista.findViewById(R.id.btnEditarVehiculo);

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

                            String nombre = "" + documentSnapshot.getData().get("nombre");
                            tv1.setText(nombre);
                            String modelo = "" + documentSnapshot.getData().get("modelo");
                            tv2.setText(modelo);
                            String marca = "" + documentSnapshot.getData().get("marca");
                            tv3.setText(marca);
                            String tip = "" + documentSnapshot.getData().get("tipo");
                            if (tip == "Coche"){
                                sp1.setSelection(1);
                            }else if (tip == "Microbus"){
                                sp1.setSelection(2);
                            }else if (tip == "Furgonetas de carga"){
                                sp1.setSelection(3);
                            }else if (tip == "Camion") {
                                sp1.setSelection(4);
                            }

                            String dis = "" + documentSnapshot.getData().get("estado");
                            if (dis == "Disponible"){
                                sp1.setSelection(1);
                            }else if (dis == "Alquilado"){
                                sp1.setSelection(2);
                            }
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
                
            }
        });

        return vista;
    }
}