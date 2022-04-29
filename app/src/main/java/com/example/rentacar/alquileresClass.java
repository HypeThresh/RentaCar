package com.example.rentacar;

public class alquileresClass {
    public String vehiculoPlaca;
    public String clienteDui;
    public String fechaIni;
    public String fechaFin;
    public String alquilado;

    public alquileresClass(String vehiculo, String cliente, String fechaIni, String fechaFin,String alquilado){
        this.clienteDui = cliente;
        this.vehiculoPlaca = vehiculo;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
        this.alquilado = alquilado;
    }
}
