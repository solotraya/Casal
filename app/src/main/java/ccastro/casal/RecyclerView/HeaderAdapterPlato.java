package ccastro.casal.RecyclerView;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ccastro.casal.PlatoActivity;
import ccastro.casal.R;
import ccastro.casal.Utils.Statics;

/**
 * Created by Carlos on 20/12/2017.
 */

public class HeaderAdapterPlato extends RecyclerView.Adapter<HeaderAdapterPlato.ViewHolder> {
    private ArrayList<HeaderPlato> mDataset;

    /**
     * Constructor de la clase Headeradapter
     * @param myDataset dataSet
     */
    public HeaderAdapterPlato(ArrayList<HeaderPlato> myDataset) {
        mDataset = myDataset;
    }

    /**
     * Mètode que crea vistes noves (invocades pel gestor de disseny)
     * @param parent view parent
     * @param viewType tipus view
     * @return viewHolder
     */
    @Override
    public HeaderAdapterPlato.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // crea una nova vista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_header_adapter_plato, parent, false);
        // Estableix la mida de la vista, els marges, els farcits i els paràmetres de disseny
        return  new ViewHolder(v);
    }

    /**
     * Mètode que reemplaça els continguts d'una vista (invocada pel gestor de disseny)
     * @param holder holder
     * @param position posició
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Obteniu un element del vostre conjunt de dades en aquesta posició
        // Reemplaça els continguts de la vista amb aquest element

        holder.idPlato.setText(mDataset.get(position).getIdPlato());
        holder.nombrePlato.setText(mDataset.get(position).getNombrePlato());
        if (!Statics.esconderGluten1.isEmpty()){
            if(Statics.esconderGluten1.get(position)) holder.gluten.setVisibility(View.GONE);
            else holder.gluten.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderCrustaceo1.isEmpty()){
            if(Statics.esconderCrustaceo1.get(position)) holder.crustaceos.setVisibility(View.GONE);
            else holder.crustaceos.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderHuevos1.isEmpty()){
            if(Statics.esconderHuevos1.get(position)) holder.huevos.setVisibility(View.GONE);
            else holder.huevos.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderPescado1.isEmpty()){
            if(Statics.esconderPescado1.get(position)) holder.pescado.setVisibility(View.GONE);
            else holder.pescado.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderCacahuetes1.isEmpty()){
            if(Statics.esconderCacahuetes1.get(position)) holder.cacahuetes.setVisibility(View.GONE);
            else holder.cacahuetes.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderLacteos1.isEmpty()){
            if(Statics.esconderLacteos1.get(position)) holder.lacteos.setVisibility(View.GONE);
            else holder.lacteos.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderCascaras1.isEmpty()){
            if(Statics.esconderCascaras1.get(position)) holder.cascaras.setVisibility(View.GONE);
            else holder.cascaras.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderApio1.isEmpty()){
            if(Statics.esconderApio1.get(position)) holder.apio.setVisibility(View.GONE);
            else holder.apio.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderSulfitos1.isEmpty()){
            if(Statics.esconderSulfitos1.get(position)) holder.azufre_sulfitos.setVisibility(View.GONE);
            else holder.azufre_sulfitos.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderMoluscos1.isEmpty()){
            if(Statics.esconderMoluscos1.get(position)) holder.moluscos.setVisibility(View.GONE);
            else holder.moluscos.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Mètode per retornar el tamany del dataset invocat per el layout manager
     * @return tamany de mDataset.
     */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView idPlato, nombrePlato;
        TextView gluten, crustaceos, huevos, pescado, cacahuetes, lacteos, cascaras, apio, azufre_sulfitos, moluscos;

        View v;
        Context context;
        /**
         * Constructor de classe statica View Holder
         * @param v view
         */
        public ViewHolder(View v) {
            super(v);
            idPlato = (TextView) v.findViewById(R.id.idPlato);
            nombrePlato=(TextView) v.findViewById(R.id.nombrePlato);
            gluten= (TextView) v.findViewById(R.id.gluten);
            crustaceos = (TextView) v.findViewById(R.id.crustaceos);
            huevos = (TextView) v.findViewById(R.id.huevos);
            pescado = (TextView) v.findViewById(R.id.pescado);
            cacahuetes = (TextView) v.findViewById(R.id.cacahuetes);
            lacteos = (TextView) v.findViewById(R.id.lacteos);
            cascaras = (TextView) v.findViewById(R.id.cascaras);
            apio = (TextView) v.findViewById(R.id.apio);
            azufre_sulfitos = (TextView) v.findViewById(R.id.azufre);
            moluscos = (TextView) v.findViewById(R.id.moluscos);
            context = itemView.getContext();

            v.setOnClickListener(this);

        }

        /**
         * Mètode per gestionar l'esdeveniment onClick
         * @param view que interacturarà
         */
        @Override
        public void onClick(View view) {
            if (PlatoActivity.viewAnterior!=null){
                PlatoActivity.viewAnterior.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            }
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            PlatoActivity.viewAnterior=view;
            PlatoActivity.id_plato = idPlato.getText().toString();
            if (PlatoActivity.seleccionarPlato){
                PlatoActivity.nombrePlato = nombrePlato.getText().toString();
            }
        }
    }

    /**
     * Mètode per actualitzar el recycler un cop canviat el filtratge.
     * @param llistaConsultes arrayList
     */
    public void actualitzaRecycler(ArrayList<HeaderPlato> llistaConsultes) {
        mDataset = llistaConsultes;
        this.notifyDataSetChanged();
    }

}