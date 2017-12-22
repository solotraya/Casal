package ccastro.casal.RecyclerView;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ccastro.casal.MenuActivity;
import ccastro.casal.R;
import ccastro.casal.Utils.Statics;

/**
 * @author Carlos Alberto Castro Cañabate
 *
 * Classe adaptador que segueix el patró de disseny viewHolder i defineix classe interna que extend
 * de RecyclerView.ViewHolder
 */


public class HeaderAdapterMenu extends RecyclerView.Adapter<HeaderAdapterMenu.ViewHolder> {
    private ArrayList<HeaderMenu> mDataset;

    /**
     * Constructor de la clase Headeradapter
     * @param myDataset dataSet
     */
    public HeaderAdapterMenu(ArrayList<HeaderMenu> myDataset) {
        mDataset = myDataset;
    }

    /**
     * Mètode que crea vistes noves (invocades pel gestor de disseny)
     * @param parent view parent
     * @param viewType tipus view
     * @return viewHolder
     */
    @Override
    public HeaderAdapterMenu.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // crea una nova vista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_header_adapter_menu, parent, false);
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
        holder.diaMenu.setText(mDataset.get(position).getDiaMenu());
        holder.idMenuPlato.setText(mDataset.get(position).getIdMenuPlato());
        holder.idMenu.setText(mDataset.get(position).getIdMenu());
        String segundoPlato = mDataset.get(position).getSegundoPlato();
        if (segundoPlato==null)holder.segundoPlatoTitulo.setVisibility(View.GONE);
        else {
            holder.segundoPlatoTitulo.setVisibility(View.VISIBLE);
            holder.segundoPlato.setText(segundoPlato);
        }
        String primerPlato = mDataset.get(position).getPrimerPlato();
        if (primerPlato==null)holder.primerPlatoTitulo.setVisibility(View.GONE);
        else {
            holder.primerPlatoTitulo.setVisibility(View.VISIBLE);
            holder.primerPlato.setText(primerPlato);
        }
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

        if (!Statics.esconderGluten2.isEmpty()){
            if(Statics.esconderGluten2.get(position)) holder.gluten2.setVisibility(View.GONE);
            else holder.gluten2.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderCrustaceo2.isEmpty()){
            if(Statics.esconderCrustaceo2.get(position)) holder.crustaceos2.setVisibility(View.GONE);
            else holder.crustaceos2.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderHuevos2.isEmpty()){
            if(Statics.esconderHuevos2.get(position)) holder.huevos2.setVisibility(View.GONE);
            else holder.huevos2.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderPescado2.isEmpty()){
            if(Statics.esconderPescado2.get(position)) holder.pescado2.setVisibility(View.GONE);
            else holder.pescado2.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderCacahuetes2.isEmpty()){
            if(Statics.esconderCacahuetes2.get(position)) holder.cacahuetes2.setVisibility(View.GONE);
            else holder.cacahuetes2.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderLacteos2.isEmpty()){
            if(Statics.esconderLacteos2.get(position)) holder.lacteos2.setVisibility(View.GONE);
            else holder.lacteos2.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderCascaras2.isEmpty()){
            if(Statics.esconderCascaras2.get(position)) holder.cascaras2.setVisibility(View.GONE);
            else holder.cascaras2.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderApio2.isEmpty()){
            if(Statics.esconderApio2.get(position)) holder.apio2.setVisibility(View.GONE);
            else holder.apio2.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderSulfitos2.isEmpty()){
            if(Statics.esconderSulfitos2.get(position)) holder.azufre_sulfitos2.setVisibility(View.GONE);
            else holder.azufre_sulfitos2.setVisibility(View.VISIBLE);
        }
        if (!Statics.esconderMoluscos2.isEmpty()){
            if(Statics.esconderMoluscos2.get(position)) holder.moluscos2.setVisibility(View.GONE);
            else holder.moluscos2.setVisibility(View.VISIBLE);
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
        TextView idMenuPlato,idMenu, diaMenu, primerPlato, segundoPlato, primerPlatoTitulo, segundoPlatoTitulo;
        TextView gluten, crustaceos, huevos, pescado, cacahuetes, lacteos, cascaras, apio, azufre_sulfitos, moluscos;
       // Button crustaceos;
        TextView gluten2, crustaceos2, huevos2, pescado2, cacahuetes2, lacteos2, cascaras2, apio2, azufre_sulfitos2, moluscos2;

        View v;
        Context context;
        /**
         * Constructor de classe statica View Holder
         * @param v view
         */
        public ViewHolder(View v) {
            super(v);
            idMenuPlato = (TextView) v.findViewById(R.id.idMenuPlato);
            idMenu = (TextView) v.findViewById(R.id.idMenu);
            diaMenu=(TextView)v.findViewById(R.id.diaMenu);
            diaMenu.setPaintFlags(diaMenu.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            primerPlato=(TextView) v.findViewById(R.id.primerPlato); primerPlatoTitulo=(TextView) v.findViewById(R.id.textViewPrimerPlatoTitulo);
            segundoPlato=(TextView) v.findViewById(R.id.segundoPlato); segundoPlatoTitulo=(TextView) v.findViewById(R.id.textViewSegundoPlatoTitulo);
            gluten= (TextView) v.findViewById(R.id.gluten); gluten2=(TextView) v.findViewById(R.id.gluten2);
            crustaceos = (TextView) v.findViewById(R.id.crustaceos); crustaceos2 = (TextView) v.findViewById(R.id.crustaceos2);
            huevos = (TextView) v.findViewById(R.id.huevos); huevos2 = (TextView) v.findViewById(R.id.huevos2);
            pescado = (TextView) v.findViewById(R.id.pescado); pescado2 = (TextView) v.findViewById(R.id.pescado2);
            cacahuetes = (TextView) v.findViewById(R.id.cacahuetes); cacahuetes2 = (TextView) v.findViewById(R.id.cacahuetes2);
            lacteos = (TextView) v.findViewById(R.id.lacteos); lacteos2 = (TextView) v.findViewById(R.id.lacteos2);
            cascaras = (TextView) v.findViewById(R.id.cascaras); cascaras2 = (TextView) v.findViewById(R.id.cascaras2);
            apio = (TextView) v.findViewById(R.id.apio); apio2 = (TextView) v.findViewById(R.id.apio2);
            azufre_sulfitos = (TextView) v.findViewById(R.id.azufre); azufre_sulfitos2 = (TextView) v.findViewById(R.id.azufre2);
            moluscos = (TextView) v.findViewById(R.id.moluscos); moluscos2 = (TextView) v.findViewById(R.id.moluscos2);
            context = itemView.getContext();
            v.setOnClickListener(this);

        }

        /**
         * Mètode per gestionar l'esdeveniment onClick
         * @param view que interacturarà
         */
        @Override
        public void onClick(View view) {
            if (MenuActivity.idMenu!=null){
                if (MenuActivity.viewAnterior!=null){
                   MenuActivity.viewAnterior.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                }
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                MenuActivity.viewAnterior=view;
                MenuActivity.idMenuPlato = idMenuPlato.getText().toString();
            }
        }
    }

    /**
     * Mètode per actualitzar el recycler un cop canviat el filtratge.
     * @param llistaConsultes arrayList
     */
    public void actualitzaRecycler(ArrayList<HeaderMenu> llistaConsultes) {
        mDataset = llistaConsultes;
        this.notifyDataSetChanged();
    }

}