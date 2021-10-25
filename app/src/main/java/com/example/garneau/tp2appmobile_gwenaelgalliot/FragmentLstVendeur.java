package com.example.garneau.tp2appmobile_gwenaelgalliot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import com.example.garneau.tp2appmobile_gwenaelgalliot.data.AppExecutors;
import com.example.garneau.tp2appmobile_gwenaelgalliot.data.ProduitRoomDB;
import com.example.garneau.tp2appmobile_gwenaelgalliot.model.Produit;

import java.io.Serializable;
import java.util.List;


public class FragmentLstVendeur extends Fragment {

    private ParlerALActivite mListener;
    private List<Produit> m_Produit;
    private Switch switchAdmin;
    private ListView list;

    private ProduitRoomDB mDb;

    private AdapteurVendeur m_Adapter;


    public FragmentLstVendeur() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lst_vendeur, container, false);

        switchAdmin = (Switch) view.findViewById(R.id.switchAdmin);

        list = view.findViewById(R.id.listeVendeur);

        // ajout d'un click sur les item de la liste de produit
        // pour les envoyer dans le panier
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //passage du produit dans un bundle pour cree un nouveau fragemeent Client
                // avec le produit en paramettre dans le bundle
                Produit unProduit = m_Adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("produit",(Serializable)unProduit);
                FragmentLstClient frag = new FragmentLstClient();
                frag.setArguments(bundle);

                mListener.ajoutPanier(unProduit);

                // creation d'un nouveau fragement client pour afficher l'objet ajouter
                getFragmentManager().beginTransaction().add(R.id.frameClient, frag).commit();
            }
        });
        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);

        // instance de BD
        mDb = ProduitRoomDB.getDatabase(getContext());

        // Démarrage du fil d'exécution BD
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override

            public void run() {


//                 AJOUT DES DONNES DANS LA BD

                mDb.ProduitDao().deleteAll();
                Produit produit = new Produit("pomme","pomme verte", "1.0", "fruit", "1");
                mDb.ProduitDao().insert(produit);
                produit = new Produit("banane","banane verte", "1.5", "fruit", "1");
                mDb.ProduitDao().insert(produit);
                produit = new Produit("poire","jolie poire", "1.2", "fruit", "1");
                mDb.ProduitDao().insert(produit);
                produit = new Produit("pizza","toute garnie", "7.3", "congeler", "1");

                mDb.ProduitDao().insert(produit);


//                recuperation des donnees dans la bd
                m_Produit = mDb.ProduitDao().getAllProducts();

//                passage de la liste de la bd dans le liste view par l'adaptateur
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Instanciation de l'adapteur
                        m_Adapter = new AdapteurVendeur(getActivity(), m_Produit);

                        // Passage de l'adapteur à la liste
                        list.setAdapter(m_Adapter);
                    }
                });
            }
        });
        registerForContextMenu(list);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Lors du clic sur un item du menu contextuel associé à un élément de la liste.

            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            switch (item.getItemId()) {
                // Modifier.
                case R.id.menu_set:
                    // popup pour gérer l'interaction
                    handleSetProduitMenu(menuInfo.position);
                    return true;

                // Supprimer.
                case R.id.menu_remove:
                    m_Produit.remove(menuInfo.position);
                    // notification de l'adapteur
                    m_Adapter.notifyDataSetChanged();
                    return true;

                default:
                    Log.w("MainActivity", "Menu inconnu : " + item.getTitle());
            }


        return super.onContextItemSelected(item);
    }


    private void handleSetProduitMenu(int p_Position) {
        // Création de la View conteneur du popup
        View setView = getLayoutInflater().inflate(R.layout.set, null);

        // Récupération de l'EditText qui contiendra la nouvelle valeur : sur setView
        EditText txtSetNom = (EditText) setView.findViewById(R.id.txtSetNom);
        EditText txtSetDescription = (EditText) setView.findViewById(R.id.txtSetDescription);
        EditText txtSetPrix = (EditText) setView.findViewById(R.id.txtSetPrix);
        EditText txtSetCategorie = (EditText) setView.findViewById(R.id.txtSetCategorie);
        EditText txtSetQuantite = (EditText) setView.findViewById(R.id.txtSetQuantite);

        // on y attache la valeur actuel
        Produit c_row = m_Produit.get(p_Position);
        txtSetNom.setText(c_row.getName());
        txtSetDescription.setText(c_row.getDescription());
        txtSetPrix.setText(c_row.getPrix());
        txtSetCategorie.setText(c_row.getCategorie());
        txtSetQuantite.setText(c_row.getQuantite());

        // Création d'un objet permettant de gérer l'événement sur le bouton "OK" dans l'AlertDialog
        BtnSetHandler setHandler = new BtnSetHandler(p_Position, txtSetNom, txtSetDescription,
                txtSetPrix, txtSetCategorie, txtSetQuantite);

        /** AlertDialog : c'est le popup
         *  Un objet AlertDialog permet la définition à la volée de 2 boutons : typiquement Ok et Cancel
         */
        new AlertDialog.Builder(getActivity())
                .setTitle("Modifier")
                .setView(setView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", setHandler)
                .show();


    }

    private class BtnSetHandler implements DialogInterface.OnClickListener {
        private int m_Position;
        private EditText m_TxtSetNom;
        private EditText m_TxtSetDescription;
        private EditText m_TxtSetPrix;
        private EditText m_TxtSetCategorie;
        private EditText m_TxtSetQuabtite;


        /**
         * BtnSetHandler
         *
         * @param p_position
         * @param p_txtSetNom
         */
        public BtnSetHandler(int p_position, EditText p_txtSetNom, EditText p_txtSetDescription,
                             EditText p_txtSetPrix, EditText p_txtSetCategorie, EditText p_txtSetQuantite) {
            this.m_Position = p_position;
            this.m_TxtSetNom = p_txtSetNom;
            this.m_TxtSetDescription = p_txtSetDescription;
            this.m_TxtSetPrix = p_txtSetPrix;
            this.m_TxtSetCategorie = p_txtSetCategorie;
            this.m_TxtSetQuabtite = p_txtSetQuantite;

        }

        /**
         * onClick
         * modifiera la liste m_Produit (
         * l'Adapter rechargera la ListView
         *
         * @param p_dialog
         * @param p_which
         */
        @Override
        public void onClick(DialogInterface p_dialog, int p_which) {
            m_Produit.remove(m_Position);
            m_Produit.add(m_Position, new Produit(m_TxtSetNom.getText().toString(),
                    m_TxtSetDescription.getText().toString(), m_TxtSetPrix.getText().toString(),
                    m_TxtSetCategorie.getText().toString(), m_TxtSetQuabtite.getText().toString()));
            m_Adapter.notifyDataSetChanged();


            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.ProduitDao().updateItem(m_Position + 1, m_TxtSetNom.getText().toString(),
                            m_TxtSetDescription.getText().toString());
                }
            });
        }


    }

    //interface pour comuniquer avec le mainActivity
    public interface ParlerALActivite {
        void ajoutPanier(Produit unProduit);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Si MainActivity implémente l'interface (=devient une instance d'OnFragmentInteractionListener)
        if (context instanceof ParlerALActivite) {
            // alors l'objet mListener devient le contexte appelant (= l'activité)
            mListener = (ParlerALActivite) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    // méthode inverse de la précédente dans le cycle de vie du Fragment
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}