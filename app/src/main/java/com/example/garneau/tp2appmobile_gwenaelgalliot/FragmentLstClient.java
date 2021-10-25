package com.example.garneau.tp2appmobile_gwenaelgalliot;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.garneau.tp2appmobile_gwenaelgalliot.data.AppExecutors;
import com.example.garneau.tp2appmobile_gwenaelgalliot.data.ProduitRoomDB;
import com.example.garneau.tp2appmobile_gwenaelgalliot.model.Produit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class FragmentLstClient extends Fragment {
    static private List<Produit> m_Produit;

    private ListView list;

    private AdapteurPanier m_Adapter;

    public FragmentLstClient() {
        // Required empty public constructor
    }


    public static FragmentLstClient newInstance(Produit unProduit) {
        FragmentLstClient fragment = new FragmentLstClient();

        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lst_client, container, false);
        list = view.findViewById(R.id.listeClient);
        m_Produit = new ArrayList<>();
        m_Produit = MainActivity.panier;

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Instanciation de l'adapteur
                if (m_Produit != null){
                    m_Adapter = new AdapteurPanier(getActivity(), m_Produit);

                    // Passage de l'adapteur à la liste
                    list.setAdapter(m_Adapter);
                }

            }
        });
    }
}