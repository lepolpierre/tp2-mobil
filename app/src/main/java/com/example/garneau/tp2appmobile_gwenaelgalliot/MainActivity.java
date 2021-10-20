package com.example.garneau.tp2appmobile_gwenaelgalliot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.garneau.tp2appmobile_gwenaelgalliot.data.ProduitDao;
import com.example.garneau.tp2appmobile_gwenaelgalliot.data.ProduitRoomDB;
import com.example.garneau.tp2appmobile_gwenaelgalliot.model.Produit;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;

    private Switch switchAdmin ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentLstVendeur fragmentLstVendeur = new FragmentLstVendeur();
        FragmentLstClient fragmentLstClient = new FragmentLstClient();

        manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.frameVendeur, fragmentLstVendeur, null);
        transaction.add(R.id.frameClient, fragmentLstClient, null);
        transaction.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu p_menu) {
        // Création du menu d'options.
        getMenuInflater().inflate(R.menu.main, p_menu);

//        switchAdmin = (Switch) findViewById(R.id.app_bar_switchAdmin);
//        switchAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b == true){
//                    Toast.makeText(getBaseContext(),"on",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
        return true;

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // Création du menu contextuel.
        if (switchAdmin.isChecked()) {
            getMenuInflater().inflate(R.menu.context, menu);
        }
    }


//
//    @Database(entities = {Produit.class}, version = 1)
//    public abstract static class ProduitRoomBD extends RoomDatabase {
//
//        public static ProduitRoomDB INSTANCE;
//        //public abstract ProduitDao ProduitDao();
//
//        public static synchronized  ProduitRoomDB getDatabase(final Context context) {
//            if(INSTANCE == null){
//                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),ProduitRoomDB.class,
//                        "produit_database").build();
//            }
//            return INSTANCE;
//        }
//
//    }
}