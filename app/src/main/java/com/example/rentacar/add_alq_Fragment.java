package com.example.rentacar;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class add_alq_Fragment extends Fragment {

    public add_alq_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View bas= inflater.inflate(R.layout.fragment_add_alq_, container, false);

        Button btn = bas.findViewById(R.id.btnBck4);
        ImageButton btn2 = bas.findViewById(R.id.imageBtnAdd);
        EditText txtFechaIni = bas.findViewById(R.id.addDateIn);
        EditText txtFechaFin = bas.findViewById(R.id.addDateFin);

        txtFechaIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment newFragment = new DatePickerFragment();

                newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // +1 because January is zero
                        final String selectedDate = day + " / " + (month+1) + " / " + year;
                        txtFechaIni.setText(selectedDate);
                    }
                });
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        txtFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment newFragment = new DatePickerFragment();

                newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // +1 because January is zero
                        final String selectedDate = day + " / " + (month+1) + " / " + year;
                        txtFechaFin.setText(selectedDate);
                    }
                });
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.gestionAlquilerFragment);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txtClient, txtVehicle;

                txtClient = bas.findViewById(R.id.clienteDuiAlq);
                txtVehicle = bas.findViewById(R.id.placaVehAlq);

                if (txtClient.getText().toString().isEmpty() || txtVehicle.getText().toString().isEmpty() ||
                        txtFechaIni.getText().toString().isEmpty() || txtFechaFin.getText().toString().isEmpty()) {
                    Toast.makeText(bas.getContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();

                    db.collection("clientes").document(txtClient.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        // -------------------------------------------------------------
                                        db.collection("vehiculos").document(txtVehicle.getText().toString())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()) {
                                                            long dias = 0;
                                                            String tipo = document.get("tipo").toString();
                                                            long total = 0;

                                                            try {
                                                                Date fechaInicio;
                                                                Date fechaFin;
                                                                SimpleDateFormat date = new SimpleDateFormat("dd / MM / yyyy");
                                                                fechaInicio = date.parse(txtFechaIni.getText().toString());
                                                                fechaFin = date.parse(txtFechaFin.getText().toString());
                                                                long diff = fechaFin.getTime() - fechaInicio.getTime();
                                                                dias = diff / (1000 * 60 * 60 * 24);
                                                            } catch (ParseException e) {
                                                                e.printStackTrace();
                                                            }

                                                            switch (tipo) {
                                                                case "Coche":
                                                                    total = (dias * 65);
                                                                    break;
                                                                case "Microbus":
                                                                    total = (dias * 50) + (20);
                                                                    break;
                                                                case "Furgonetas de carga":
                                                                    total = (dias * 70);
                                                                    break;
                                                                case "Camion":
                                                                    total = (dias * 75);
                                                                    break;
                                                            }
                                                            //Toast.makeText(bas.getContext(), "Total a pagar " + total, Toast.LENGTH_LONG).show();
                                                            // -------------------------------------------------------------
                                                            alquileresClass nuevoAlquiler = new alquileresClass(txtClient.getText().toString(),
                                                                    txtVehicle.getText().toString(), txtFechaIni.getText().toString(),
                                                                    txtFechaFin.getText().toString(), "Alquilado");
                                                            nuevoAlquiler.setPago(total + "");

                                                            db.collection("alquileres")
                                                                    .add(nuevoAlquiler)// objeto nuevo
                                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                        @Override
                                                                        public void onSuccess(DocumentReference documentReference) {
                                                                            Toast.makeText(bas.getContext(),
                                                                                    "REGISTRO INSERTADO CON EXITO", Toast.LENGTH_SHORT).show();
                                                                            Navigation.findNavController(view).navigate(R.id.gestionAlquilerFragment);
                                                                        }
                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(bas.getContext(),
                                                                            "HUBO UN ERROR EN EL PROCESO", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                            // -------------------------------------------------------------
                                                        } else {
                                                            Toast.makeText(bas.getContext(), "Error: Vehiculo no registrado", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(bas.getContext(), "Error: No se encontro el registro", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        //--------------------------------------------------------------
                                    } else {
                                        Toast.makeText(bas.getContext(), "Error: Cliente no registrado", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(bas.getContext(), "Error: No se encontro el registro", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }
        });

        return bas;
    }
}