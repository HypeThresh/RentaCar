package com.example.rentacar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class clienteConsultarFragment extends Fragment {

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

        dui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv1;
                TableLayout tr1;

                tv1 = vista.findViewById(R.id.listadoVehiculosAlquilados);
                tv1.setVisibility(View.VISIBLE);

                tr1 = vista.findViewById(R.id.tablaVehiculosAlquilados);
                tr1.setVisibility(View.VISIBLE);


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