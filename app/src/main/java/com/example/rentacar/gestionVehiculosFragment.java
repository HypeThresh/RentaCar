package com.example.rentacar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class gestionVehiculosFragment extends Fragment {

    public gestionVehiculosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_gestion_vehiculos, container, false);

        Button btn1 = vista.findViewById(R.id.btnAlmacenarVehiculo);
        Button btn2 = vista.findViewById(R.id.btnConsultarVehiculo);
        Button btn3 = vista.findViewById(R.id.btnEditarVehiculo);
        Button btn4 = vista.findViewById(R.id.btnEliminarVehiculo);
        Button btn5 = vista.findViewById(R.id.btnAsignarEstadoVehiculo);
        Button btn6 = vista.findViewById(R.id.btnRegresarHomeVehiculo);
        ImageView btn7 = vista.findViewById(R.id.imgBtnBack);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.almacenarVehiculosFragment);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.consultarVehiculosFragment);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.editarVehiculosFragment);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.eliminarVehiculosFragment);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.estadoVehiculosFragment);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.inicioFragment);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.inicioFragment);
            }
        });

        return vista;
    }
}