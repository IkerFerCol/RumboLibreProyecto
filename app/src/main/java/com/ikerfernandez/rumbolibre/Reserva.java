package com.ikerfernandez.rumbolibre;

public class Reserva {
    private Long id;
    private Long vueloId;
    private String usuarioNombre;

    public Reserva(Long vueloId, String usuarioNombre) {
        this.vueloId = vueloId;
        this.usuarioNombre = usuarioNombre;
    }

    public Long getId() { return id; }
    public Long getVueloId() { return vueloId; }
    public String getUsuarioNombre() { return usuarioNombre; }
}
