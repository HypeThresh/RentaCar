package com.example.rentacar;

public class vehiculosClass {
    public String nombre;
    public String marca;
    public String modelo;
    public String tipo;
    public String estado;
    public int eliminado;

    public vehiculosClass(String nombre,String marca,String modelo,String tipo,String estado,int eliminado){
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.estado = estado;
        this.eliminado = eliminado;
    }

}
