package com.ikerfernandez.rumbolibre;

public class Vuelo {
    int id;
    String origen;
    String destino;
    String tiempo;
    String aerolinea;
    double precio;
    String imagen;

    public Vuelo(int id, String origen, String destino, String tiempo, String aerolinea, double precio, String imagen) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.tiempo = tiempo;
        this.aerolinea = aerolinea;
        this.precio = precio;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Vuelo{" +
                "id=" + id +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", tiempo='" + tiempo + '\'' +
                ", aerolinea='" + aerolinea + '\'' +
                ", precio=" + precio +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
