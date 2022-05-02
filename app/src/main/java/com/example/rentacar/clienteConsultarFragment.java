package com.example.rentacar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class clienteConsultarFragment extends Fragment {

    RecyclerView recyclerView;
    clienteAdapter mAdapter;

    public clienteConsultarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_cliente_consultar, container, false);

        TextView dui = vista.findViewById(R.id.duiClienteConsultar);
        Button btnRegresar = vista.findViewById(R.id.btnregresarConsultarCliente);
        Button btnConsultar = vista.findViewById(R.id.btnConsultarCliente);

        recyclerView = vista.findViewById(R.id.clienteConsultarRecycler);
        ArrayList<alquileresClass> list;
        list = new ArrayList<>();

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alquileresClass aux = new alquileresClass("","","","","");
                FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                db.collection("alquileres")
                        .whereEqualTo("clienteDui", dui.getText().toString())
                        .whereEqualTo("alquilado", "Alquilado")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if(task.getResult().isEmpty()){
                                        Toast.makeText(vista.getContext(), "No se encontraron alquileres activos", Toast.LENGTH_SHORT).show();
                                    }
                                    list.clear();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                            aux.setDocumento(document.getId());
                                            aux.setClienteDui(document.get("clienteDui").toString());
                                            aux.setVehiculoPlaca(document.get("vehiculoPlaca").toString());
                                            aux.setAlquilado(document.get("alquilado").toString());
                                            aux.setFechaIni(document.get("fechaIni").toString());
                                            aux.setFechaFin(document.get("fechaFin").toString());
                                            list.add(aux);
                                    }
                                }else {
                                    Toast.makeText(vista.getContext(), "Error al realizar la consulta", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                recyclerView.setLayoutManager(new LinearLayoutManager(vista.getContext()));
                mAdapter = new clienteAdapter(list);
                recyclerView.setAdapter(mAdapter);
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