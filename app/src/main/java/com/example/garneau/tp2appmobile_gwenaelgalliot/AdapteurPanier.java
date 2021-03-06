package com.example.garneau.tp2appmobile_gwenaelgalliot;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.garneau.tp2appmobile_gwenaelgalliot.model.Produit;

import java.util.List;

public class AdapteurPanier extends ArrayAdapter<Produit> {

    private Context contexte;
    private List<Produit> rowModels;


    public AdapteurPanier(Activity context, List<Produit> m_RowModels) {

        super(context, R.layout.row_panier, R.id.txtVNom2, m_RowModels);

        contexte = context;
        rowModels = m_RowModels;

    }

    public void setList(List<Produit> liste) {
        this.rowModels = liste;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) contexte.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_panier, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else { viewHolder = (ViewHolder) convertView.getTag(); }

        View row = super.getView(position, convertView, parent);

        Produit rowData = rowModels.get(position);
        viewHolder.textViewDescription.setText(rowData.getDescription());
        viewHolder.textViewNom.setText(rowData.getName());
        viewHolder.textViewPrix.setText(rowData.getPrix());
        viewHolder.textViewQuantite.setText(rowData.getQuantite());

        return row;
    }

    private static class ViewHolder {

        TextView textViewNom;
        TextView textViewDescription;
        TextView textViewPrix;
        TextView textViewQuantite;

        public ViewHolder(View view) {
            textViewNom = (TextView) view.findViewById(R.id.txtVNom2);
            textViewDescription = (TextView) view.findViewById(R.id.txtVDescription2);
            textViewPrix = (TextView) view.findViewById(R.id.txtVPrix2);
            textViewQuantite = (TextView) view.findViewById(R.id.txtVQuantite);
        }
    }

}

