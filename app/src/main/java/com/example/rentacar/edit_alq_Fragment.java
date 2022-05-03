package com.example.rentacar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class edit_alq_Fragment extends Fragment {

    public edit_alq_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_edit_alq_, container, false);

        EditText edt1,edt2,edt3,edt4;
        edt1 = vista.findViewById(R.id.spinCln);
        edt2 = vista.findViewById(R.id.spinVeh);
        edt3 = vista.findViewById(R.id.editTextDate);
        edt4 = vista.findViewById(R.id.delDateReg);

        String documento = getArguments().getString("documento");
        String cliente = getArguments().getString("cliente");
        String vehiculo = getArguments().getString("vehiculo");
        String fecha = getArguments().getString("fecha");
        String fechaFin = getArguments().getString("fechaFin");
        String txtEstado = getArguments().getString("estado");

        edt1.setText(cliente);
        edt2.setText(vehiculo);
        edt3.setText(fecha);
        edt4.setText(fechaFin);

        Switch estado = vista.findViewById(R.id.switch1);

        if(txtEstado == "Entregado"){
            estado.setChecked(true);
        }else{
            estado.setChecked(false);
        }

        Button btn = vista.findViewById(R.id.btnBck2);
        ImageButton btnImagen = vista.findViewById(R.id.imageButton);

        btnImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle("EDITAR");
                String mensaje = "Deseas editar el registro";
                builder.setMessage(mensaje);
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Map<String, Object> update = new HashMap<>();
                        update.put("fechaFin",edt4.getText().toString());
                        if(estado.isChecked()){
                            update.put("alquilado","Entregado");
                        }else{
                            update.put("alquilado","Alquilado");
                        }

                        FirebaseFirestore db;
                        db = FirebaseFirestore.getInstance();
                        db.collection("alquileres")
                                .document(documento)
                                .set(update, SetOptions.merge());
                        Toast.makeText(vista.getContext(),"Actualizado con Exito", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Navigation.findNavController(vista).navigate(R.id.alqLista);
                    }
                });
                builder.create();
                builder.show();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.alqLista);
            }
        });
        edt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment newFragment = new DatePickerFragment();

                newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // +1 because January is zero
                        final String selectedDate = day + " / " + (month+1) + " / " + year;
                        edt4.setText(selectedDate);
                    }
                });
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        return vista;
    }
}