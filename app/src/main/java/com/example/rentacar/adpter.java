package com.example.rentacar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class adpter extends BaseAdapter {

    public ArrayList<alquileresClass> data;
    public Context context;
    public TextView txt1, txt2, txt3;

    public  adpter (ArrayList<alquileresClass> lista, Context context){

        this.data = lista;
        this.context = context;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        alquileresClass p = (alquileresClass)getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.fragment_list_alq_, null);
        txt1 = view.findViewById(R.id.txtId);
        txt2 = view.findViewById(R.id.txtClient);
        txt3 = view.findViewById(R.id.txtVehicle);
        txt1.setText(p.cliente);
        txt2.setText(p.vehiculo);


        return view;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
