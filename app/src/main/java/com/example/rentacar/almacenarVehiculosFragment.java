package com.example.rentacar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class almacenarVehiculosFragment extends Fragment {

    public almacenarVehiculosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_almacenar_vehiculos, container, false);

        Button btn1 = vista.findViewById(R.id.btnregresarAlmacenVehiculo);
        Button btn2 = vista.findViewById(R.id.btnAlmacenarAlmacenVehiculo);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.gestionVehiculos);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txtNombre, txtModelo, txtPlaca, txtMarca;
                Spinner spnTipo, spnEstado;

                txtNombre = vista.findViewById(R.id.nombreVehiculo);
                txtModelo = vista.findViewById(R.id.modeloVehiculo);
                txtPlaca = vista.findViewById(R.id.numeroPlacaVehiculo);
                txtMarca = vista.findViewById(R.id.marcaVehiculo);
                spnTipo = vista.findViewById(R.id.tipoVehiculoAlmacenarSpinner);
                spnEstado = vista.findViewById(R.id.estadoVehiculoAlmacenarSpinner);

                if (txtNombre.getText().toString().isEmpty() || txtModelo.getText().toString().isEmpty() ||
                        txtPlaca.getText().toString().isEmpty() || txtMarca.getText().toString().isEmpty()) {
                    Toast.makeText(vista.getContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    //Conexion a bd
                    FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();

                    vehiculosClass nuevoVehiculo = new vehiculosClass(txtNombre.getText().toString(),
                            txtMarca.getText().toString(), txtModelo.getText().toString(),
                            spnTipo.getSelectedItem().toString(), spnEstado.getSelectedItem().toString(), 0
                    );

                    db.collection("vehiculos")
                            .document(txtPlaca.getText().toString())
                            .set(nuevoVehiculo)// objeto nuevo
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(vista.getContext(),
                                            "REGISTRO INSERTADO CON EXITO", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(view).navigate(R.id.gestionVehiculos);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(vista.getContext(),
                                    "HUBO UN ERROR EN EL PROCESO", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return vista;
    }
}