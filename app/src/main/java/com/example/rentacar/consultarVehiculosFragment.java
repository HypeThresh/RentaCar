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
        View vista = inflater.inflate(R.layout.fragment_consultar_vehiculos, container, false);

        TextView placa = vista.findViewById(R.id.numeroPlacaVehiculoConsutar);
        Button btnRegresar = vista.findViewById(R.id.btnRegresarConsultarVehiculo);

        placa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv1, tv2, tv3, tv4, tv5 , tvPlaca;
                tvPlaca = vista.findViewById(R.id.numeroPlacaVehiculoConsutar);
                tv1 = vista.findViewById(R.id.nombreVehiculoConsultar);
                tv1.setVisibility(View.VISIBLE);
                tv2 = vista.findViewById(R.id.modeloVehiculoConsultar);
                tv2.setVisibility(View.VISIBLE);
                tv3 = vista.findViewById(R.id.MarcaVehiculoConsultar);
                tv3.setVisibility(View.VISIBLE);
                tv4 = vista.findViewById(R.id.tipoVehiculoConsultarSpinner);
                tv4.setVisibility(View.VISIBLE);
                tv5 = vista.findViewById(R.id.estadoVehiculoConsultarSpinner);
                tv5.setVisibility(View.VISIBLE);

                FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                DocumentReference documentReference = db.collection("vehiculos").document(tvPlaca.getText().toString());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String nombre = ""+documentSnapshot.getData().get("nombre");
                        tv1.setText(nombre);
                        String modelo = ""+documentSnapshot.getData().get("modelo");
                        tv2.setText(modelo);
                        String marca = ""+documentSnapshot.getData().get("marca");
                        tv3.setText(marca);
                        String tipo = ""+documentSnapshot.getData().get("tipo");
                        tv4.setText(tipo);
                        String estado = ""+documentSnapshot.getData().get("estado");
                        tv5.setText(estado);
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

        return vista;
    }
}