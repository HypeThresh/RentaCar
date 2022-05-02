package com.example.rentacar;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class alqLista extends Fragment {

    RecyclerView recyclerView;
    alqAdapter mAdapter;

    public alqLista() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_alq_lista, container, false);

        TextView fecha = vista.findViewById(R.id.fechaConsult);
        recyclerView = vista.findViewById(R.id.recyclerViewListAalq);
        ImageView buscar = vista.findViewById(R.id.imageView);
        ArrayList<alquileresClass> list;
        list = new ArrayList<>();

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment newFragment = new DatePickerFragment();

                newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // +1 because January is zero
                        final String selectedDate = day + " / " + (month+1) + " / " + year;
                        fecha.setText(selectedDate);
                    }
                });
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alquileresClass aux = new alquileresClass("","","","","");
                FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                db.collection("alquileres")
                        .whereEqualTo("fechaIni", fecha.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
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
                mAdapter = new alqAdapter(list);
                recyclerView.setAdapter(mAdapter);
            }
        });

        return vista;
    }
}