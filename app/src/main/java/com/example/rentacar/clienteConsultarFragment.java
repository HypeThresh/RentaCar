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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
                TextView tv1, tvDui, tv2, tv3;
                TableLayout tr1;

                tvDui = vista.findViewById(R.id.duiClienteConsultar);

                tv1 = vista.findViewById(R.id.listadoVehiculosAlquilados);
                tv1.setVisibility(View.VISIBLE);
                tv2 = vista.findViewById(R.id.clConsult);
                tv2.setVisibility(view.VISIBLE);
                tv3 = vista.findViewById(R.id.vhConsult);
                tv3.setVisibility(view.VISIBLE);
                tr1 = vista.findViewById(R.id.tablaVehiculosAlquilados);
                tr1.setVisibility(View.VISIBLE);


                Toast.makeText(view.getContext(), dui.getText().toString(), Toast.LENGTH_SHORT).show();

                FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                DocumentReference documentReference = db.collection("alquileres")
                        .document(tvDui.getText().toString());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        String car = ""+documentSnapshot.getData().get("vehiculos");
                        tv1.setText(car);
                        String name = ""+documentSnapshot.getData().get("cliente");
                        tv2.setText(name);
                        String vh = ""+documentSnapshot.getData().get("vehiculos");
                        tv3.setText(vh);


                    }
                });

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