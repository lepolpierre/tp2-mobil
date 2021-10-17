package com.example.garneau.tp2appmobile_gwenaelgalliot;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.garneau.tp2appmobile_gwenaelgalliot.data.ProduitRoomDB;
import com.example.garneau.tp2appmobile_gwenaelgalliot.model.Produit;

import java.util.List;


public class FragmentLstClient extends Fragment {

    private List<Produit> m_Produit;

    private ListView list;

    private ProduitRoomDB mDb;

    private Adapteur m_Adapter;

    public FragmentLstClient() {
        // Required empty public constructor
    }


    public static FragmentLstClient newInstance(String produit) {
        FragmentLstClient fragment = new FragmentLstClient();
        Bundle args = new Bundle();
        args.putString("produit", produit);
        fragment.setArguments(args);
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
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);

    }
}