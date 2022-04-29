package com.example.rentacar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class clienteEliminarFragment extends Fragment {

    public clienteEliminarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_cliente_eliminar, container, false);

        TextView dui = vista.findViewById(R.id.duiClienteEliminar);
        Button btnRegresar = vista.findViewById(R.id.btnregresarEliminarCliente);
        Button btnDel = vista.findViewById(R.id.btnEliminarClienteDel);
        ImageView search = vista.findViewById(R.id.imageSearchDel);


        dui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv1, tv2, tv3;

                tv1 = vista.findViewById(R.id.nombreClienteEliminar);
                tv1.setVisibility(View.VISIBLE);
                tv2 = vista.findViewById(R.id.telefonoClienteEliminar);
                tv2.setVisibility(View.VISIBLE);
                tv3 = vista.findViewById(R.id.direccionClienteEliminar);
                tv3.setVisibility(View.VISIBLE);

                Toast.makeText(view.getContext(), dui.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dui.getText().toString().isEmpty()) {
                    Toast.makeText(vista.getContext(), "Ingresa un Identificador",
                            Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseFirestore db;
                    String id = dui.getText().toString();
                    db = FirebaseFirestore.getInstance();
                    db.collection("clientes")
                            .document(dui.getText().toString()).get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        TextView tv1, tv2, tv3;

                                        tv1 = vista.findViewById(R.id.nombreClienteEliminar);
                                        tv2 = vista.findViewById(R.id.telefonoClienteEliminar);
                                        tv3 = vista.findViewById(R.id.direccionClienteEliminar);

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


                                            btnDel.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    db.collection("clientes")
                                                            .document(id).update("eliminado", 1) .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    dui.setText("");
                                                                    tv1.setText("");
                                                                    tv1.setVisibility(View.INVISIBLE);
                                                                    tv2.setText("");
                                                                    tv2.setVisibility(View.INVISIBLE);
                                                                    tv3.setText("");
                                                                    tv3.setVisibility(View.INVISIBLE);
                                                                    Toast.makeText(vista.getContext(),"Eliminado Correctamente", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
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

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.gestionClientesFragment);
            }
        });

        return vista;
    }
}