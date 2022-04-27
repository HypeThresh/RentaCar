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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

        TextView tv1, tv2, tv3;

        tv1 = vista.findViewById(R.id.nombreClienteEditar);
        tv1.setVisibility(View.VISIBLE);
        tv2 = vista.findViewById(R.id.telefonoClienteEditar);
        tv2.setVisibility(View.VISIBLE);
        tv3 = vista.findViewById(R.id.direccionClienteEditar);
        tv3.setVisibility(View.VISIBLE);

        dui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();

                db.collection("clientes").document(dui.getText().toString()).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                String name = ""+documentSnapshot.getData().get("cliente");
                                tv1.setText(name);
                                String tel = ""+documentSnapshot.getData().get("telefono");
                                tv3.setText(tel);
                                String dir = ""+documentSnapshot.getData().get("direccion");
                                tv2.setText(dir);

                            }
                        });

                Toast.makeText(view.getContext(), dui.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.gestionClientesFragment);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clienteClass actualizarCliente = new clienteClass(tv1.getText().toString(),tv2.getText().toString(),
                        tv3.getText().toString());

                FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("clientes").document(dui.getText().toString());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Map<String, Object> update = new HashMap<>();
                                update.put("cliente", tv1.getText().toString());
                                update.put("direccion", tv2.getText().toString());
                                update.put("telefono", tv3.getText().toString());

                                db.collection("clientes")
                                        .document(dui.getText().toString())
                                        .set(update, SetOptions.merge());
                                Toast.makeText(vista.getContext(), "Actualizado con exito", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(vista.getContext(), "Dui no encontrado", Toast.LENGTH_SHORT).show();
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