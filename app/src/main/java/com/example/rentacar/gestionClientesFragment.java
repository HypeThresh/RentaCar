package com.example.rentacar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class gestionClientesFragment extends Fragment {

    public gestionClientesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_gestion_clientes, container, false);

        Button btn1 = vista.findViewById(R.id.btnRegistrarCliente);
        Button btn2 = vista.findViewById(R.id.btnEditarCliente);
        Button btn3 = vista.findViewById(R.id.btnEliminarCliente);
        Button btn4 = vista.findViewById(R.id.btnConsultarCliente);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.clienteRegistrarFragment);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.clienteEditarFragment);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.clienteEliminarFragment);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.clienteConsultarFragment);
            }
        });

        return vista;
    }
}