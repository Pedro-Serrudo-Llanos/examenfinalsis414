package com.example.Examen1.models;

import jakarta.persistence.*;

@Entity
@Table(name = "rueda")
public class Rueda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruedaId;

    private String material;
    private String color;
    private Double price;

    // tu campo real que usas
    @Column(name = "width", nullable = false)
    private double width;

    // 👇 Campo “fantasma” para satisfacer el NOT NULL que quedó en la BD
    @Column(name = "diameter", nullable = false)
    private double diameter = 0.0; // siempre 0, no lo exponemos en la API

    @ManyToOne
    @JoinColumn(name = "bicicleta_id")
    private Bicicleta bicicleta;

    public Rueda() {}

    public Rueda(Long ruedaId, String material, String color, Double price, double width) {
        this.ruedaId = ruedaId;
        this.material = material;
        this.color = color;
        this.price = price;
        this.width = width;
        this.diameter = 0.0;
    }

    public Long getRuedaId() { return ruedaId; }
    public void setRuedaId(Long ruedaId) { this.ruedaId = ruedaId; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    public double getDiameter() { return diameter; }
    public void setDiameter(double diameter) { this.diameter = diameter; }

    public Bicicleta getBicicleta() { return bicicleta; }
    public void setBicicleta(Bicicleta bicicleta) { this.bicicleta = bicicleta; }
}
