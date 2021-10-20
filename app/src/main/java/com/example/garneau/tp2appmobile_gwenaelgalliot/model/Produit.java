package com.example.garneau.tp2appmobile_gwenaelgalliot.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "produit_table")
public class Produit {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name_col")
    private String name;

    @ColumnInfo(name = "description_col")
    private String description;

    @ColumnInfo(name = "Prix_col")
    private String prix;

    @ColumnInfo(name = "Categorie_col")
    private String categorie;

    @ColumnInfo(name = "Quantite_col")
    private String quantite;

    public Produit(String name, String description, String prix, String categorie, String quantite) {
        this.name = name;
        this.description = description;
        this.prix = prix;
        this.quantite = quantite;
        this.categorie = categorie;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nom) {
        this.name = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }



}
