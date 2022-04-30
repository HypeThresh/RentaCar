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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class eliminarVehiculosFragment extends Fragment {

    public eliminarVehiculosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_eliminar_vehiculos, container, false);

        EditText placa = vista.findViewById(R.id.numeroPlacaVehiculoEliminar);
        Button btnRegresar = vista.findViewById(R.id.btnregresarEliminarVehiculo);
        Button btnEliminar = vista.findViewById(R.id.btnEliminarVehiculo);

        placa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                                    TextView tv1, tv2, tv3, tv4, tv5;
                                    tv1 = vista.findViewById(R.id.nombreVehiculoEliminar);
                                    tv2 = vista.findViewById(R.id.modeloVehiculoEliminar);
                                    tv3 = vista.findViewById(R.id.marcaVehiculoEliminar);
                                    tv4 = vista.findViewById(R.id.tipoVehiculoEliminar);
                                    tv5 = vista.findViewById(R.id.estadoVehiculoEliminar);

                                    if (document.exists()){

                                        tv1.setVisibility(View.VISIBLE);
                                        tv2.setVisibility(View.VISIBLE);
                                        tv3.setVisibility(View.VISIBLE);
                                        tv4.setVisibility(View.VISIBLE);
                                        tv5.setVisibility(View.VISIBLE);

                                        String nombre = ""+task.getResult().getData().get("nombre");
                                        tv1.setText(nombre);
                                        String modelo = ""+task.getResult().getData().get("modelo");
                                        tv2.setText(modelo);
                                        String marca = ""+task.getResult().getData().get("marca");
                                        tv3.setText(marca);
                                        String tipo = ""+task.getResult().getData().get("tipo");
                                        tv4.setText(tipo);
                                        String estado = ""+task.getResult().getData().get("estado");
                                        tv5.setText(estado);

                                        Toast.makeText(vista.getContext(), "Datos encontrados", Toast.LENGTH_SHORT).show();

                                    }else{

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

                                        Toast.makeText(vista.getContext(), "No encontrado", Toast.LENGTH_SHORT).show();

                                    }
                                }else {
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

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = placa.getText().toString();
                FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                db.collection("vehiculos").document(id)
                        .update("eliminado", 1)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                TextView tv1, tv2, tv3, tv4, tv5, tvplaca;
                                tvplaca = vista.findViewById(R.id.nombreVehiculoEliminar);
                                tvplaca.setText("");
                                tv1 = vista.findViewById(R.id.nombreVehiculoEliminar);
                                tv1.setText("");
                                tv1.setVisibility(View.INVISIBLE);
                                tv2 = vista.findViewById(R.id.modeloVehiculoEliminar);
                                tv2.setText("");
                                tv2.setVisibility(View.INVISIBLE);
                                tv3 = vista.findViewById(R.id.marcaVehiculoEliminar);
                                tv3.setText("");
                                tv3.setVisibility(View.INVISIBLE);
                                tv4 = vista.findViewById(R.id.tipoVehiculoEliminar);
                                tv4.setText("");
                                tv4.setVisibility(View.INVISIBLE);
                                tv5 = vista.findViewById(R.id.estadoVehiculoEliminar);
                                tv5.setText("");
                                tv5.setVisibility(View.INVISIBLE);
                                Toast.makeText(vista.getContext(), "Vehiculo eliminado correctamente", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(vista.getContext(), "No se pudo realizar la accion", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return vista;
    }
}