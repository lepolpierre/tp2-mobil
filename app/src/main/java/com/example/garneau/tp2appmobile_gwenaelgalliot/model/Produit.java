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

    public Produit(String name, String description) {
        this.name = name;
        this.description = description;
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


}

//@Entity(tableName = "person_table")
//public class Person {
//
//    @PrimaryKey(autoGenerate = true)
//    private int id;
//
//    @NonNull
//    @ColumnInfo(name = "name_col")
//    private String name;
//
//    @ColumnInfo(name = "age_col")
//    private int age;
//
//    public Person(String name, int age) {
//        this.name = name;
//        this.age = age;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String nom) {
//        this.name = nom;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//}