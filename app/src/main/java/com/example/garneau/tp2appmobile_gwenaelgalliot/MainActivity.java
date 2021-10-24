package com.example.garneau.tp2appmobile_gwenaelgalliot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.garneau.tp2appmobile_gwenaelgalliot.model.Produit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentLstVendeur.ParlerALActivite{

    private FragmentManager manager;
    static public List<Produit> panier;
    MenuItem menuItem;
    MenuItem menuItemSet;
    MenuItem menuItemRemove;

    static public Switch switchAdmin ;
    boolean switchState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentLstVendeur fragmentLstVendeur = new FragmentLstVendeur();
        FragmentLstClient fragmentLstClient = new FragmentLstClient();
        panier = new ArrayList<>();
        manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.frameVendeur, fragmentLstVendeur, null);
        transaction.add(R.id.frameClient, fragmentLstClient, null);
        transaction.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menuItem = menu.findItem(R.id.switchAdmin);
        View view = MenuItemCompat.getActionView(menuItem);
        switchAdmin = (Switch) view.findViewById(R.id.Switch);
        menuItemSet = menu.findItem(R.id.menu_set);
        menuItemRemove = menu.findItem(R.id.menu_remove);


        switchAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(MainActivity.this, "This is on", Toast.LENGTH_SHORT).show();
                    menuItemSet.setVisible(true);
                    menuItemRemove.setVisible(true);

                } else {
                    Toast.makeText(MainActivity.this, "This is off", Toast.LENGTH_SHORT).show();
                    menuItemSet.setVisible(false);
                    menuItemRemove.setVisible(false);
                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // Création du menu contextuel.
        getMenuInflater().inflate(R.menu.context, menu);

    }

    public void ajoutPanier(Produit unProduit) {

        if (findProduit(unProduit.getName())!= null){
            int quantite = Integer.valueOf(unProduit.getQuantite());
            quantite = quantite + 1;
            String nouvelleQuantite = String.valueOf(quantite);
            unProduit.setQuantite(nouvelleQuantite);
        }
        else{
            panier.add(unProduit);
        }

    }

    public Produit findProduit (
            String name) {

        for (Produit produit : panier) {
            if (produit.getName().equals(name)) {
                return produit;
            }
        }
        return null;
    }

}