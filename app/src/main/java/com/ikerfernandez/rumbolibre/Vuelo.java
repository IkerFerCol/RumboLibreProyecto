package com.ikerfernandez.rumbolibre;


import java.io.Serializable;

public class Vuelo implements Serializable {
    private int id;

    private String ciudadOrigen;

    private String ciudadDestino;

    private String aerolinea;

    private String horaInicioIda;

    private String horaFinIda;

    private String fechaInicioIda;

    private String fechaFinIda;

    private String tiempoIda;

    private String horaInicioVuelta;

    private String horaFinVuelta;

    private String fechaInicioVuelta;

    private String fechaFinVuelta;

    private String tiempoVuelta;

    private double precio;

    public Vuelo() {
    }

    public Vuelo(int id, String ciudadOrigen, String ciudadDestino, String aerolinea,
                 String horaInicioIda, String horaFinIda, String fechaInicioIda,
                 String fechaFinIda, String tiempoIda, String horaInicioVuelta,
                 String horaFinVuelta, String fechaInicioVuelta, String fechaFinVuelta,
                 String tiempoVuelta, double precio) {
        this.id = id;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.aerolinea = aerolinea;
        this.horaInicioIda = horaInicioIda;
        this.horaFinIda = horaFinIda;
        this.fechaInicioIda = fechaInicioIda;
        this.fechaFinIda = fechaFinIda;
        this.tiempoIda = tiempoIda;
        this.horaInicioVuelta = horaInicioVuelta;
        this.horaFinVuelta = horaFinVuelta;
        this.fechaInicioVuelta = fechaInicioVuelta;
        this.fechaFinVuelta = fechaFinVuelta;
        this.tiempoVuelta = tiempoVuelta;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    public String getHoraInicioIda() {
        return horaInicioIda;
    }

    public void setHoraInicioIda(String horaInicioIda) {
        this.horaInicioIda = horaInicioIda;
    }

    public String getHoraFinIda() {
        return horaFinIda;
    }

    public void setHoraFinIda(String horaFinIda) {
        this.horaFinIda = horaFinIda;
    }

    public String getFechaInicioIda() {
        return fechaInicioIda;
    }

    public void setFechaInicioIda(String fechaInicioIda) {
        this.fechaInicioIda = fechaInicioIda;
    }

    public String getFechaFinIda() {
        return fechaFinIda;
    }

    public void setFechaFinIda(String fechaFinIda) {
        this.fechaFinIda = fechaFinIda;
    }

    public String getTiempoIda() {
        return tiempoIda;
    }

    public void setTiempoIda(String tiempoIda) {
        this.tiempoIda = tiempoIda;
    }

    public String getHoraInicioVuelta() {
        return horaInicioVuelta;
    }

    public void setHoraInicioVuelta(String horaInicioVuelta) {
        this.horaInicioVuelta = horaInicioVuelta;
    }

    public String getHoraFinVuelta() {
        return horaFinVuelta;
    }

    public void setHoraFinVuelta(String horaFinVuelta) {
        this.horaFinVuelta = horaFinVuelta;
    }

    public String getFechaInicioVuelta() {
        return fechaInicioVuelta;
    }

    public void setFechaInicioVuelta(String fechaInicioVuelta) {
        this.fechaInicioVuelta = fechaInicioVuelta;
    }

    public String getFechaFinVuelta() {
        return fechaFinVuelta;
    }

    public void setFechaFinVuelta(String fechaFinVuelta) {
        this.fechaFinVuelta = fechaFinVuelta;
    }

    public String getTiempoVuelta() {
        return tiempoVuelta;
    }

    public void setTiempoVuelta(String tiempoVuelta) {
        this.tiempoVuelta = tiempoVuelta;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Vuelo{" +
                "id=" + id +
                ", ciudadOrigen='" + ciudadOrigen + '\'' +
                ", ciudadDestino='" + ciudadDestino + '\'' +
                ", aerolinea='" + aerolinea + '\'' +
                ", precio=" + precio +
                '}';
    }
}