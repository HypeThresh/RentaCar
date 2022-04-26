package com.example.rentacar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        dui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv1, tv2, tv3;

                tv1 = vista.findViewById(R.id.nombreClienteEliminar);
                tv1.setVisibility(View.VISIBLE);

                tv2 = vista.findViewById(R.id.telefonoClienteEliminar);
                tv2.setVisibility(View.VISIBLE);

                tv3 = vista.findViewById(R.id.direccionClienteEliminar);
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