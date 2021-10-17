package com.example.garneau.tp2appmobile_gwenaelgalliot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.garneau.tp2appmobile_gwenaelgalliot.data.AppExecutors;
import com.example.garneau.tp2appmobile_gwenaelgalliot.data.ProduitRoomDB;
import com.example.garneau.tp2appmobile_gwenaelgalliot.model.Produit;

import java.util.List;


public class FragmentLstVendeur extends Fragment {

    private List<Produit> m_Produit;

    private ListView list;

    private ProduitRoomDB mDb;

    private Adapteur m_Adapter;

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
        list = view.findViewById(R.id.listeVendeur);
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
                mDb.ProduitDao().deleteAll();
                Produit produit = new Produit("pomme","pomme verte", "1.0", "fruit", "2");
                mDb.ProduitDao().insert(produit);
                produit = new Produit("banane","banane verte", "1.5", "fruit", "5");
                mDb.ProduitDao().insert(produit);

                m_Produit = mDb.ProduitDao().getAllProducts();

                getActivity().runOnUiThread(new Runnable() {
                    @Override

                    public void run() {
                        // Instanciation de l'adapteur
                        m_Adapter = new Adapteur(getActivity(), m_Produit);

                        // Passage de l'adapteur à la liste
                        list.setAdapter(m_Adapter);
                    }
                });
            }
        });

    }
}