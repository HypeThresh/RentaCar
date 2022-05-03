package com.example.rentacar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.MoreObjects;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class clienteEditarFragment extends Fragment {

    public clienteEditarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_cliente_editar, container, false);

        TextView dui = vista.findViewById(R.id.duiClienteEditar);
        Button btnBack = vista.findViewById(R.id.btnRegresarEditarCliente);
        Button btnEdit = vista.findViewById(R.id.btnEditarClienteEdit);
        ImageView search = vista.findViewById(R.id.imageSearch);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dui.getText().toString().isEmpty()) {
                    Toast.makeText(vista.getContext(), "Ingresa un Identificador",
                            Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();
                    db.collection("clientes")
                            .document(dui.getText().toString()).get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        TextView tv1, tv2, tv3;

                                        tv1 = vista.findViewById(R.id.nombreClienteEditar);
                                        tv2 = vista.findViewById(R.id.telefonoClienteEditar);
                                        tv3 = vista.findViewById(R.id.direccionClienteEditar);

                                        if (document.exists()) {
                                            tv1.setVisibility(View.VISIBLE);
                                            tv2.setVisibility(View.VISIBLE);
                                            tv3.setVisibility(View.VISIBLE);

                                            String nombre = "" + task.getResult().get("cliente").toString();
                                            tv1.setText(nombre);
                                            String tel = "" + task.getResult().get("telefono").toString();
                                            tv2.setText(tel);
                                            String dir = "" + task.getResult().get("direccion").toString();
                                            tv3.setText(dir);

                                            Toast.makeText(vista.getContext(), "Datos Encontrados",
                                                    Toast.LENGTH_SHORT).show();


                                            btnEdit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    clienteClass actCliente = new clienteClass(tv1.getText().toString(),
                                                            tv2.getText().toString(),tv3.getText().toString());

                                                    Map<String, Object> update = new HashMap<>();
                                                    update.put("cliente",tv1.getText().toString());
                                                    update.put("telefono",tv2.getText().toString());
                                                    update.put("direccion",tv3.getText().toString());

                                                    db.collection("clientes")
                                                            .document(dui.getText().toString())
                                                            .set(update, SetOptions.merge());
                                                    Toast.makeText(vista.getContext(),"Actualizado con Exito", Toast.LENGTH_SHORT).show();
                                                    Navigation.findNavController(vista).navigate(R.id.gestionClientesFragment);
                                                }
                                            });

                                        } else {
                                            tv1.setVisibility(View.INVISIBLE);
                                            tv2.setVisibility(View.INVISIBLE);
                                            tv3.setVisibility(View.INVISIBLE);
                                            tv1.setText("");
                                            tv2.setText("");
                                            tv3.setText("");

                                            dui.setText("");
                                            Toast.makeText(vista.getContext(), "Datos no Encontrados",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(vista.getContext(), "Error en la Consulta",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.gestionClientesFragment);
            }
        });

        return vista;
    }
}