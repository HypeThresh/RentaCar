package com.example.rentacar;

public class alquileresClass {
    public String documento;
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

    public String getDocumento() { return documento; }

    public void setDocumento(String documento) { this.documento = documento; }

    public String getVehiculoPlaca() {
        return vehiculoPlaca;
    }

    public void setVehiculoPlaca(String vehiculoPlaca) {
        this.vehiculoPlaca = vehiculoPlaca;
    }

    public String getClienteDui() {
        return clienteDui;
    }

    public void setClienteDui(String clienteDui) {
        this.clienteDui = clienteDui;
    }

    public String getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getAlquilado() {
        return alquilado;
    }

    public void setAlquilado(String alquilado) {
        this.alquilado = alquilado;
    }
}
