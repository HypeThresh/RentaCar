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

public class clienteEditarFragment extends Fragment {

    public clienteEditarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_cliente_editar, container, false);

        TextView dui = vista.findViewById(R.id.duiClienteEditar);
        Button btnRegresar = vista.findViewById(R.id.btnRegresarEditarCliente);

        dui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv1, tv2, tv3;

                tv1 = vista.findViewById(R.id.nombreClienteEditar);
                tv1.setVisibility(View.VISIBLE);

                tv2 = vista.findViewById(R.id.telefonoClienteEditar);
                tv2.setVisibility(View.VISIBLE);

                tv3 = vista.findViewById(R.id.direccionClienteEditar);
                tv3.setVisibility(View.VISIBLE);

                Toast.makeText(view.getContext(), dui.getText().toString(), Toast.LENGTH_SHORT).show();
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