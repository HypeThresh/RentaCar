package com.example.rentacar;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class gestionAlquilerFragment extends Fragment {

    public gestionAlquilerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_gestion_alquiler, container, false);

        ImageView img = vista.findViewById(R.id.imgBtnBack);
        Button btnAddAlq = vista.findViewById(R.id.btnAlmacenarAlq);
        Button btnConsAlq = vista.findViewById(R.id.btnConsultarAlq);
        Button btnEditAlq = vista.findViewById(R.id.btnEditarAlq);
        Button btnDltAlq = vista.findViewById(R.id.btnEliminarAlq);

        btnAddAlq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.add_alq_Fragment);
            }
        });
        btnConsAlq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.list_alq_Fragment);
            }
        });
        btnDltAlq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.del_alq_Fragment);
            }
        });
        btnEditAlq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.edit_alq_Fragment);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.inicioFragment);
            }
        });

        return vista;
    }
}