package com.example.rentacar;

public class alquileresClass {
    public int userId;
    public String vehiculo;
    public String cliente;
    public String fechaIni;
    public String fechaFin;

    public alquileresClass(String vehiculo, String cliente, String fechaIni, String fechaFin, Integer userId){
        this.userId = userId;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
    }
}
