package com.example.rentacar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

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


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.gestionAlquilerFragment);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner clientSp, vehicleSp;
                EditText txtFechaIni, txtFechaFin, idUser;


                clientSp = bas.findViewById(R.id.spinner2);
                vehicleSp = bas.findViewById(R.id.spinner);
                idUser = bas.findViewById(R.id.txtIdAlq);
                txtFechaIni = bas.findViewById(R.id.addDateIn);
                txtFechaFin = bas.findViewById(R.id.addDateFin);

                FirebaseFirestore db;

                db = FirebaseFirestore.getInstance();


                alquileresClass nuevoAlquiler = new alquileresClass(clientSp.getSelectedItem().toString(),
                        vehicleSp.getSelectedItem().toString(),txtFechaIni.getText().toString(),txtFechaFin.getText().toString(),idUser.getId());
                db.collection("alquileres").document();

            }
        });

        return bas;
    }
}