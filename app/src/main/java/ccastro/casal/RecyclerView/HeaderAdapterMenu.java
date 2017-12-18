package ccastro.casal.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

      /*  holder.idComida.setText(mDataset.get(position).getIdComida());
        holder.nombreComida.setText(mDataset.get(position).getNombreComida());
        holder.gluten.setText(mDataset.get(position).getGluten());
        holder.crustaceos.setText(mDataset.get(position).getCrustaceos());
        holder.huevos.setText(mDataset.get(position).getHuevos());
        holder.cacahuetes.setText(mDataset.get(position).getCacahuetes());
        holder.lacteos.setText(mDataset.get(position).getLacteos());
        holder.cascaras.setText(mDataset.get(position).getCascaras());
        holder.apio.setText(mDataset.get(position).getApio());
        holder.azufre_sulfitos.setText(mDataset.get(position).getAzufre_sulfitos());
        holder.moluscos.setText(mDataset.get(position).getMoluscos()); */
        holder.idMenu.setText(mDataset.get(position).getIdMenu());
  //      holder.fechaMenu.setText(mDataset.get(position).getFechaMenu());
        holder.lunesPrimero.setText(mDataset.get(position).getLunesPrimero());
        holder.lunesSegundo.setText(mDataset.get(position).getLunesSegundo());
        holder.martesPrimero.setText(mDataset.get(position).getMartesPrimero());
        holder.martesSegundo.setText(mDataset.get(position).getMartesSegundo());
        holder.miercolesPrimero.setText(mDataset.get(position).getMiercolesPrimero());
        holder.miercolesSegundo.setText(mDataset.get(position).getMiercolesSegundo());
        holder.juevesPrimero.setText(mDataset.get(position).getJuevesPrimero());
        holder.juevesSegundo.setText(mDataset.get(position).getJuevesSegundo());
        holder.viernesPrimero.setText(mDataset.get(position).getViernesPrimero());
        holder.viernesSegundo.setText(mDataset.get(position).getViernesSegundo());
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
        TextView idComida, nombreComida, gluten, crustaceos, huevos, cacahuetes, lacteos, cascaras, apio, azufre_sulfitos, moluscos,
                idMenu, fechaMenu, lunesPrimero, lunesSegundo, martesPrimero, martesSegundo, miercolesPrimero, miercolesSegundo, juevesPrimero, juevesSegundo, viernesPrimero, viernesSegundo;
        View v;
        Context context;
        /**
         * Constructor de classe statica View Holder
         * @param v view
         */
        public ViewHolder(View v) {
            super(v);
            /*
            idComida=(TextView)v.findViewById(R.id.idComida);
            nombreComida=(TextView)v.findViewById(R.id.nombreComida);
            gluten=(TextView) v.findViewById(R.id.gluten);
            crustaceos = (TextView) v.findViewById(R.id.crustaceos);
            huevos = (TextView) v.findViewById(R.id.huevos);
            cacahuetes = (TextView) v.findViewById(R.id.cacahuetes);
            lacteos = (TextView) v.findViewById(R.id.lacteos);
            cascaras = (TextView) v.findViewById(R.id.cascaras);
            apio = (TextView) v.findViewById(R.id.apio);
            azufre_sulfitos = (TextView) v.findViewById(R.id.azufre);
            moluscos = (TextView) v.findViewById(R.id.moluscos); */
            idMenu = (TextView) v.findViewById(R.id.idMenu);
            //fechaMenu = (TextView) v.findViewById(R.id.fechaMenu);
            lunesPrimero = (TextView) v.findViewById(R.id.lunesPrimero);
            lunesSegundo = (TextView) v.findViewById(R.id.lunesSegundo);
            martesPrimero = (TextView) v.findViewById(R.id.martesPrimero);
            martesSegundo = (TextView) v.findViewById(R.id.martesSegundo);
            miercolesPrimero = (TextView) v.findViewById(R.id.miercolesPrimero);
            miercolesSegundo = (TextView) v.findViewById(R.id.miercolesSegundo);
            juevesPrimero = (TextView) v.findViewById(R.id.juevesPrimero);
            juevesSegundo = (TextView) v.findViewById(R.id.juevesSegundo);
            viernesPrimero = (TextView) v.findViewById(R.id.viernesPrimero);
            viernesSegundo = (TextView) v.findViewById(R.id.viernesSegundo);


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