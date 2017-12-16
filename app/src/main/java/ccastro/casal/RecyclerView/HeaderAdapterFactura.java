package ccastro.casal.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ccastro.casal.FacturaActivity;
import ccastro.casal.R;
import ccastro.casal.SQLite.DBInterface;

/**
 * @author Carlos Alberto Castro Cañabate
 *
 * Classe adaptador que segueix el patró de disseny viewHolder i defineix classe interna que extend
 * de RecyclerView.ViewHolder
 */


public class HeaderAdapterFactura extends RecyclerView.Adapter<HeaderAdapterFactura.ViewHolder> {
    private ArrayList<HeaderFactura> mDataset;

    /**
     * Constructor de la clase Headeradapter
     * @param myDataset dataSet
     */
    public HeaderAdapterFactura(ArrayList<HeaderFactura> myDataset) {
        mDataset = myDataset;
    }

    /**
     * Mètode que crea vistes noves (invocades pel gestor de disseny)
     * @param parent view parent
     * @param viewType tipus view
     * @return viewHolder
     */
    @Override
    public HeaderAdapterFactura.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // crea una nova vista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_header_adapter_factura, parent, false);
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
        holder.idFactura.setText(mDataset.get(position).getIdFactura());
        holder.nomProducte.setText(mDataset.get(position).getNombreProducto());
        holder.tipusProducte.setText(mDataset.get(position).getTipoProducto());
        holder.preuProducte.setText(mDataset.get(position).getPrecioProducto());
        holder.quantitat.setText(mDataset.get(position).getCantidadProducto());
        holder.total.setText(mDataset.get(position).getPrecioLinea()+"€");
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
        LinearLayout layoutProducte;
        TextView nomProducte,preuProducte,tipusProducte,quantitat,total,quantitatProducte,totalModificar;
        TextView idFactura;
        View vi;
        Context context;
        /**
         * Constructor de classe statica View Holder
         * @param v view
         */
        public ViewHolder(View v) {
            super(v);
            layoutProducte = (LinearLayout) v.findViewById(R.id.layoutButtonsProducte);
            idFactura=(TextView)v.findViewById(R.id.idFactura);
            nomProducte=(TextView)v.findViewById(R.id.nomProducte);
            preuProducte=(TextView) v.findViewById(R.id.preuProducte);
            tipusProducte = (TextView) v.findViewById(R.id.tipusProducte);
            quantitat = (TextView) v.findViewById(R.id.quantitat);
            quantitatProducte = (TextView) v.findViewById(R.id.quantitatProducte);
            total = (TextView) v.findViewById(R.id.total);
            totalModificar = (TextView) v.findViewById(R.id.preuTotalProductes);
            context = itemView.getContext();
            layoutProducte.setVisibility(View.GONE);
            v.setOnClickListener(this);

            v.findViewById(R.id.masProducte).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Integer cantidad = Integer.parseInt(quantitatProducte.getText().toString());
                    if (cantidad<100){
                        cantidad++;
                        quantitatProducte.setText(Integer.toString(cantidad));
                        DecimalFormat df = new DecimalFormat("0.00");
                        df.setMaximumFractionDigits(2);
                        Float preuTotal = cantidad*Float.parseFloat(preuProducte.getText().toString());
                        totalModificar.setText(df.format(preuTotal));
                    }
                }
            });
            v.findViewById(R.id.menosProducte).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Integer cantidad = Integer.parseInt(quantitatProducte.getText().toString());
                    if (cantidad>0){
                        cantidad--;
                        quantitatProducte.setText(Integer.toString(cantidad));
                        DecimalFormat df = new DecimalFormat("0.00");
                        df.setMaximumFractionDigits(2);
                        Float preuTotal = cantidad*Float.parseFloat(preuProducte.getText().toString());
                        totalModificar.setText(df.format(preuTotal));
                    }
                }
            });
            v.findViewById(R.id.seleccionarProducte).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    vi = view;
                    DBInterface db=new DBInterface(vi.getContext());
                    db.obre();
                    db.ActualitzarFactura(idFactura.getText().toString(),quantitatProducte.getText().toString());
                    db.tanca();
                    layoutProducte.setVisibility(View.GONE);
                    Intent intent = new Intent(context,FacturaActivity.class);
                    context.startActivity(intent);
                    ((FacturaActivity) context).finish();
                    ((FacturaActivity) context).overridePendingTransition(0, 0);
                }
            });
        }

        /**
         * Mètode per gestionar l'esdeveniment onClick
         * @param view que interacturarà
         */
        @Override
        public void onClick(View view) {

            if (FacturaActivity.estadoVenta.equalsIgnoreCase("Falta Pagar")){
                if (!FacturaActivity.estadoVenta.equalsIgnoreCase("Anulado")){
                    layoutProducte.setVisibility(View.VISIBLE);
                    totalModificar.setText(total.getText().toString());
                    quantitatProducte.setText(quantitat.getText().toString());
                }
            }
        }
    }

    /**
     * Mètode per actualitzar el recycler un cop canviat el filtratge.
     * @param llistaConsultes arrayList
     */
    public void actualitzaRecycler(ArrayList<HeaderFactura> llistaConsultes) {
        mDataset = llistaConsultes;
        this.notifyDataSetChanged();
    }
}