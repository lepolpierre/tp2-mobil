package com.example.garneau.tp2appmobile_gwenaelgalliot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garneau.tp2appmobile_gwenaelgalliot.data.AppExecutors;
import com.example.garneau.tp2appmobile_gwenaelgalliot.data.ProduitRoomDB;
import com.example.garneau.tp2appmobile_gwenaelgalliot.model.Produit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentLstVendeur.ParlerALActivite {

    private FragmentManager manager;
    static public List<Produit> panier;
    private boolean isAdmin = false;
    MenuItem menuItemSet;
    MenuItem menuItemRemove;
    String totalPanier;
    TextView txtVPanier;
    Double total = 0.0;
    Switch switchAdmin;
    FloatingActionButton fabAjout;
    private List<Produit> m_Produit;
    private ProduitRoomDB mDb;
    private AdapteurVendeur m_Adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtVPanier = findViewById(R.id.txtVPanier);
        panier = new ArrayList<>();
        manager = getSupportFragmentManager();
        mDb = ProduitRoomDB.getDatabase(this);
        fabAjout = findViewById(R.id.fabAjoutProduit);
        m_Produit = mDb.ProduitDao().getAllProducts();
        m_Adapter = new AdapteurVendeur(this, m_Produit);

        fabAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSetProduitMenu();
            }
        });

        // creation des fragment vendeur et client
        FragmentLstVendeur fragmentLstVendeur = new FragmentLstVendeur();
        FragmentLstClient fragmentLstClient = new FragmentLstClient();

        // debut de la transaction des fragment
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.frameVendeur, fragmentLstVendeur, null);
        transaction.add(R.id.frameClient, fragmentLstClient, null);
        transaction.commit();

    }

    private void handleSetProduitMenu() {
        // Création de la View conteneur du popup
        View setView = getLayoutInflater().inflate(R.layout.set, null);
        // Récupération de l'EditText qui contiendra la nouvelle valeur : sur setView
        EditText txtSetNom = (EditText) setView.findViewById(R.id.txtSetNom);
        EditText txtSetDescription = (EditText) setView.findViewById(R.id.txtSetDescription);
        EditText txtSetPrix = (EditText) setView.findViewById(R.id.txtSetPrix);
        EditText txtSetCategorie = (EditText) setView.findViewById(R.id.txtSetCategorie);
        EditText txtSetQuantite = (EditText) setView.findViewById(R.id.txtSetQuantite);

        // Création d'un objet permettant de gérer l'événement sur le bouton "OK" dans l'AlertDialog
        BtnSetHandler setHandler = new BtnSetHandler(txtSetNom, txtSetDescription,
                txtSetPrix, txtSetCategorie, txtSetQuantite);

        /** AlertDialog : c'est le popup
         *  Un objet AlertDialog permet la définition à la volée de 2 boutons : typiquement Ok et Cancel
         */
        new AlertDialog.Builder(this)
                .setTitle("Modifier")
                .setView(setView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", setHandler)
                .show();


    }

    private class BtnSetHandler implements DialogInterface.OnClickListener {
        private EditText m_TxtSetNom;
        private EditText m_TxtSetDescription;
        private EditText m_TxtSetPrix;
        private EditText m_TxtSetCategorie;
        private EditText m_TxtSetQuabtite;


        public BtnSetHandler(EditText p_txtSetNom, EditText p_txtSetDescription,
                             EditText p_txtSetPrix, EditText p_txtSetCategorie, EditText p_txtSetQuantite) {
            this.m_TxtSetNom = p_txtSetNom;
            this.m_TxtSetDescription = p_txtSetDescription;
            this.m_TxtSetPrix = p_txtSetPrix;
            this.m_TxtSetCategorie = p_txtSetCategorie;
            this.m_TxtSetQuabtite = p_txtSetQuantite;

        }

        @Override
        public void onClick(DialogInterface p_dialog, int p_which) {
            Produit unProduit = new Produit(m_TxtSetNom.getText().toString(),
                    m_TxtSetDescription.getText().toString(), m_TxtSetPrix.getText().toString(),
                    m_TxtSetCategorie.getText().toString(), m_TxtSetQuabtite.getText().toString());

            m_Produit.add(unProduit);
            m_Adapter.notifyDataSetChanged();

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.ProduitDao().insert(unProduit);
                }
            });

            //nouveau fragement lstVendeur apres l'ajout du nouveau produit a la base de donnee
            FragmentLstVendeur fragmentLstVendeur = new FragmentLstVendeur();

            // debut de la transaction des fragment
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.add(R.id.frameVendeur, fragmentLstVendeur, null);
            transaction.commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  Creation du option menu
        getMenuInflater().inflate(R.menu.main, menu);

        menuItemSet = menu.findItem(R.id.menu_set);
        menuItemRemove = menu.findItem(R.id.menu_remove);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // Création du menu contextuel.

        getMenuInflater().inflate(R.menu.context, menu);
    }


    //    fonction permetant d'ajouter des produit de la liste vendeur au panier
    public void ajoutPanier(Produit unProduit) {

        //si le produit est deja dans le panier on augmente  la quantite
        if (findProduit(unProduit.getName()) != null) {
            int quantite = Integer.valueOf(unProduit.getQuantite());
            quantite = quantite + 1;
            String nouvelleQuantite = String.valueOf(quantite);
            unProduit.setQuantite(nouvelleQuantite);
        }
        // sinon rajout du produit au panier
        else {
            panier.add(unProduit);
        }

        // augmentation et affichage du prix total
        String stringPanier = "Total panier =" + totalPanier();
        txtVPanier.setText(stringPanier);
    }

    // fonction permettant de recuperer le prix total du panier
    public String totalPanier() {
        // pour tout les produits dans le panier on ajoute sont prix au total
        for (Produit produit : panier) {
            total = total + Double.valueOf(produit.getPrix());
        }
        totalPanier = String.valueOf(total);
        return totalPanier;
    }


    // fonction permettant de trouver un produit dans le panier pour verifier
    // si il y est deja
    public Produit findProduit(
            String name) {

        for (Produit produit : panier) {
            if (produit.getName().equals(name)) {
                return produit;
            }
        }
        return null;
    }

}