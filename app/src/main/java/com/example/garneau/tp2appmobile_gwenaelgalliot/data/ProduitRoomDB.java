package com.example.garneau.tp2appmobile_gwenaelgalliot.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.garneau.tp2appmobile_gwenaelgalliot.model.Produit;

@Database(entities = {Produit.class}, version = 1)
public abstract class ProduitRoomDB extends RoomDatabase {

    // Singleton
    public static ProduitRoomDB INSTANCE;

    // DAO
    public abstract ProduitDao ProduitDao();

    public static synchronized ProduitRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            // Crée la BDD
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ProduitRoomDB.class, "Produit_database")
                    .build();
        }
        return INSTANCE;
    }
}
