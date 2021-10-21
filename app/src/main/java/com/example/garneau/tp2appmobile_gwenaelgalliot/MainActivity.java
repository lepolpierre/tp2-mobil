package com.example.garneau.tp2appmobile_gwenaelgalliot;

import androidx.appcompat.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;

    public Switch switchAdmin ;
    boolean switchState;


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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.switchAdmin);
        View view = MenuItemCompat.getActionView(menuItem);
        Switch switchAdmin = (Switch) view.findViewById(R.id.Switch);
        switchAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(MainActivity.this, "This is on", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "This is off", Toast.LENGTH_SHORT).show();
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


}