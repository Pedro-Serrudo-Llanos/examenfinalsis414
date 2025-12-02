package com.example.Examen1.models;

import jakarta.persistence.*;

@Entity
@Table(name = "bicicleta")
public class Bicicleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image")   private String marca;
    @Column(name = "color")   private String tipo;
    @Column(name = "price")   private Double precio;
    @Column(name = "disponible") private Boolean disponible;


    public Bicicleta() {}
    public Bicicleta(Long id, String marca, String tipo, Double precio, Boolean disponible) {
        this.id = id; this.marca = marca; this.tipo = tipo; this.precio = precio; this.disponible = disponible;
    }

    @PrePersist @PreUpdate
    void defaults(){ if(precio==null) precio=0.0; if(disponible==null) disponible=false; }

    public Long getId(){ return id; } public void setId(Long id){ this.id=id; }
    public String getMarca(){ return marca; } public void setMarca(String marca){ this.marca=marca; }
    public String getTipo(){ return tipo; } public void setTipo(String tipo){ this.tipo=tipo; }
    public Double getPrecio(){ return precio; } public void setPrecio(Double precio){ this.precio=precio; }
    public Boolean getDisponible(){ return disponible; } public void setDisponible(Boolean d){ this.disponible=d; }


}
