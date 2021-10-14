package com.example.garneau.tp2appmobile_gwenaelgalliot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.example.garneau.tp2appmobile_gwenaelgalliot.data.ProduitDao;
import com.example.garneau.tp2appmobile_gwenaelgalliot.data.ProduitRoomDB;
import com.example.garneau.tp2appmobile_gwenaelgalliot.model.Produit;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentLstVendeur fragmentLstVendeur = new FragmentLstVendeur();

        manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.fragmentContainerView, fragmentLstVendeur, null);

        transaction.commit();

    }

    @Database(entities = {Produit.class}, version = 1)
    public abstract static class ProduitRoomBD extends RoomDatabase {

        public static ProduitRoomDB INSTANCE;
        //public abstract ProduitDao ProduitDao();

        public static synchronized  ProduitRoomDB getDatabase(final Context context) {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),ProduitRoomDB.class,
                        "produit_database").build();
            }
            return INSTANCE;
        }

    }
}