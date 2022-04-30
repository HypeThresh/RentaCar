package com.example.rentacar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
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

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle("CONSULTAR");
                String mensaje = "Deseas consultar el cliente con el DUI: "+dui.getText().toString();
                builder.setMessage(mensaje);
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (dui.getText().toString().isEmpty()) {
                            Toast.makeText(vista.getContext(), "Ingresa un Identificador",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            FirebaseFirestore db;
                            db = FirebaseFirestore.getInstance();
                            db.collection("clientes").document(dui.getText().toString())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                DocumentSnapshot documentSnapshot = task.getResult();
                                                TextView tv1, tv2, tv3;
                                                tv1 = vista.findViewById(R.id.nombreClienteEliminar);
                                                tv2 = vista.findViewById(R.id.telefonoClienteEliminar);
                                                tv3 = vista.findViewById(R.id.direccionClienteEliminar);

                                                if(documentSnapshot.exists()){
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
                                                    }else{
                                                    tv1.setVisibility(View.INVISIBLE);
                                                    tv2.setVisibility(View.INVISIBLE);
                                                    tv3.setVisibility(View.INVISIBLE);
                                                    tv1.setText("");
                                                    tv2.setText("");
                                                    tv3.setText("");

                                                    Toast.makeText(vista.getContext(),
                                                            "No encontrado", Toast.LENGTH_SHORT).show();


                                                }
                                            }else{
                                                Toast.makeText(vista.getContext(), "Error al realizar la consulta", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Navigation.findNavController(vista).navigate(R.id.gestionClientesFragment);
                    }
                });
                builder.create();
                builder.show();
                }
        });


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.gestionClientesFragment);
            }
        });


        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                

                String id = dui.getText().toString();
                if(dui.getText().toString().isEmpty()){
                    Toast.makeText(vista.getContext(), "Ingrese una placa", Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();
                    db.collection("clientes")
                            .document(id).update("eliminado", 1)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    TextView tv1, tv2, tv3, dui;

                                    dui = vista.findViewById(R.id.duiClienteEliminar);
                                    dui.setText("");
                                    tv1 = vista.findViewById(R.id.nombreClienteEliminar);
                                    tv1.setText("");
                                    tv1.setVisibility(View.INVISIBLE);
                                    tv2 = vista.findViewById(R.id.telefonoClienteEliminar);
                                    tv2.setText("");
                                    tv2.setVisibility(View.INVISIBLE);
                                    tv3 = vista.findViewById(R.id.direccionClienteEliminar);
                                    tv3.setText("");
                                    tv3.setVisibility(View.INVISIBLE);
                                    Toast.makeText(vista.getContext(), "Cliente eliminado correctamente", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(vista.getContext(), "No se pudo realizar la accion", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

        });
        return vista;
    }
}