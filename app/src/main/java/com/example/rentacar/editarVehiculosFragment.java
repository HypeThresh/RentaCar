package com.example.rentacar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class editarVehiculosFragment extends Fragment {


    public editarVehiculosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_editar_vehiculos, container, false);

        TextView placa = vista.findViewById(R.id.numeroPlacaVehiculoEditar);
        Button btnRegresar = vista.findViewById(R.id.btnRegresarEditarVehiculo);

        placa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv1, tv2, tv3;
                Spinner sp1, sp2;

                tv1 = vista.findViewById(R.id.nombreVehiculoEditar);
                tv1.setVisibility(View.VISIBLE);
                tv2 = vista.findViewById(R.id.modeloVehiculoEditar);
                tv2.setVisibility(View.VISIBLE);
                tv3 = vista.findViewById(R.id.MarcaVehiculoEditar);
                tv3.setVisibility(View.VISIBLE);
                sp1 = vista.findViewById(R.id.tipoVehiculoEditarSpinner);
                sp1.setVisibility(View.VISIBLE);
                sp2 = vista.findViewById(R.id.estadoVehiculoEditarSpinner);
                sp2.setVisibility(View.VISIBLE);

                Toast.makeText(view.getContext(), placa.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.gestionVehiculos);
            }
        });

        return vista;
    }
}