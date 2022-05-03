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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class clienteRegistrarFragment extends Fragment {

    public clienteRegistrarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_cliente_registrar, container, false);

        Button btn1 = vista.findViewById(R.id.btnregresarRegistrarCliente);
        Button btn2 = vista.findViewById(R.id.btnAlmacenarCliente);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.gestionClientesFragment);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txtName, txtDui, txtTel, txtDir;

                txtName = vista.findViewById(R.id.nombreCliente);
                txtDui = vista.findViewById(R.id.duiCliente);
                txtDir = vista.findViewById(R.id.direccionCliente);
                txtTel = vista.findViewById(R.id.telefonoCliente);

                if (txtName.getText().toString().isEmpty() || txtDui.getText().toString().isEmpty() ||
                        txtDir.getText().toString().isEmpty() || txtTel.getText().toString().isEmpty()) {
                    Toast.makeText(vista.getContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();

                    clienteClass nuevoCliente = new clienteClass(txtName.getText().toString(), txtTel.getText().toString(),
                            txtDir.getText().toString());

                    db.collection("clientes")
                            .document(txtDui.getText().toString())
                            .set(nuevoCliente)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(vista.getContext(),
                                            "Registro Exitoso", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(vista).navigate(R.id.gestionClientesFragment);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(vista.getContext(),
                                    "OCURRIO UN ERROR", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });



        return vista;
    }
}