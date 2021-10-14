package com.example.garneau.tp2appmobile_gwenaelgalliot.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.garneau.tp2appmobile_gwenaelgalliot.model.Produit;

import java.util.List;

@Dao
public interface ProduitDao {

    @Insert
    void insert(Produit produit);

    @Query("DELETE FROM produit_table")
    void deleteAll();

    @Query("SELECT * FROM produit_table")
    List<Produit> getAllProducts();

//    @Query("UPDATE produit_table SET age_col = :age WHERE id = :id")
//    int updateTodoItem(int id, int age);

}

