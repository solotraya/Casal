package ccastro.casal.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ccastro.casal.R;

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

        holder.idMenu.setText(mDataset.get(position).getIdMenu());
        holder.segundoPlato.setText(mDataset.get(position).getSegundoPlato());
        holder.primerPlato.setText(mDataset.get(position).getPrimerPlato());
        String gluten = mDataset.get(position).getGluten();
        if (gluten.equalsIgnoreCase("1")) holder.gluten.setVisibility(View.VISIBLE);
        else holder.gluten.setVisibility(View.GONE);
        holder.crustaceos.setText(mDataset.get(position).getCrustaceos());
        holder.huevos.setText(mDataset.get(position).getHuevos());
        holder.cacahuetes.setText(mDataset.get(position).getCacahuetes());
        holder.lacteos.setText(mDataset.get(position).getLacteos());
        holder.cascaras.setText(mDataset.get(position).getCascaras());
        holder.apio.setText(mDataset.get(position).getApio());
        holder.azufre_sulfitos.setText(mDataset.get(position).getAzufre_sulfitos());
        holder.moluscos.setText(mDataset.get(position).getMoluscos());

        holder.gluten2.setText(mDataset.get(position).getGluten2());
        holder.crustaceos2.setText(mDataset.get(position).getCrustaceos2());
        holder.huevos2.setText(mDataset.get(position).getHuevos2());
        holder.cacahuetes2.setText(mDataset.get(position).getCacahuetes2());
        holder.lacteos2.setText(mDataset.get(position).getLacteos2());
        holder.cascaras2.setText(mDataset.get(position).getCascaras2());
        holder.apio2.setText(mDataset.get(position).getApio2());
        holder.azufre_sulfitos2.setText(mDataset.get(position).getAzufre_sulfitos2());
        holder.moluscos2.setText(mDataset.get(position).getMoluscos2());


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
        TextView idMenu, diaMenu, primerPlato, segundoPlato;
        TextView crustaceos, huevos, cacahuetes, lacteos, cascaras, apio, azufre_sulfitos, moluscos;
        Button gluten;
        TextView gluten2, crustaceos2, huevos2, cacahuetes2, lacteos2, cascaras2, apio2, azufre_sulfitos2, moluscos2;

        View v;
        Context context;
        /**
         * Constructor de classe statica View Holder
         * @param v view
         */
        public ViewHolder(View v) {
            super(v);
            idMenu = (TextView) v.findViewById(R.id.idMenu);
            diaMenu=(TextView)v.findViewById(R.id.diaMenu);
            primerPlato=(TextView) v.findViewById(R.id.primerPlato);
            segundoPlato=(TextView) v.findViewById(R.id.segundoPlato);
            gluten= (Button) v.findViewById(R.id.gluten); gluten2=(TextView) v.findViewById(R.id.gluten2);
           // buttonGluten = (Button) v.findViewById(R.id.buttonGluten);
            crustaceos = (TextView) v.findViewById(R.id.crustaceos); crustaceos2 = (TextView) v.findViewById(R.id.crustaceos2);
            huevos = (TextView) v.findViewById(R.id.huevos); huevos2 = (TextView) v.findViewById(R.id.huevos2);
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
            /*
            Intent intent = new Intent(context,FacturaActivity.class);
            intent.putExtra("ID_VENTA",idVenta.getText().toString());
            intent.putExtra("DATA_VENTA",dataVenta.getText().toString());
            intent.putExtra("HORA_VENTA",horaVenta.getText().toString());
            intent.putExtra("ESTAT_VENTA",ventaPagada.getText().toString());
            intent.putExtra("NOM_CLIENT",nomClient.getText().toString());
            intent.putExtra("NOM_TREBALLADOR",nomTreballador.getText().toString());
            context.startActivity(intent); */
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