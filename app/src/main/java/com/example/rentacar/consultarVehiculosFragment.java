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

public class consultarVehiculosFragment extends Fragment {

    public consultarVehiculosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_consultar_vehiculos, container, false);

        TextView placa = vista.findViewById(R.id.numeroPlacaVehiculoConsutar);
        Button btnRegresar = vista.findViewById(R.id.btnRegresarConsultarVehiculo);

        placa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv1, tv2, tv3, tv4, tv5;
                tv1 = vista.findViewById(R.id.nombreVehiculoConsultar);
                tv1.setVisibility(View.VISIBLE);
                tv2 = vista.findViewById(R.id.modeloVehiculoConsultar);
                tv2.setVisibility(View.VISIBLE);
                tv3 = vista.findViewById(R.id.MarcaVehiculoConsultar);
                tv3.setVisibility(View.VISIBLE);
                tv4 = vista.findViewById(R.id.tipoVehiculoConsultarSpinner);
                tv4.setVisibility(View.VISIBLE);
                tv5 = vista.findViewById(R.id.estadoVehiculoConsultarSpinner);
                tv5.setVisibility(View.VISIBLE);

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