package com.example.rentacar;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class list_alq_Fragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View vista =  inflater.inflate(R.layout.fragment_list_alq_, container, false);
        TextView idalq = vista.findViewById(R.id.txtIdAlq);
        Button consult = vista.findViewById(R.id.buttonConsult);

        consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView id, vehiculo, cliente;

                FirebaseFirestore db;

                db = FirebaseFirestore.getInstance();


                id = vista.findViewById(R.id.txtIdAlq);
                cliente = vista.findViewById(R.id.txtClient);
                vehiculo = vista.findViewById(R.id.txtVehicle);
            }
        });

       return vista;
    }


}