package ccastro.casal.Utils;

import java.util.Calendar;

/**
 * Created by Carlos on 23/11/2017.
 */

public class Utilitats {
    public static String getFechaFormatSpain(String data){
        String fechaMostrar [] =  data.split(" ");
        return fechaMostrar[2]+"/"+fechaMostrar[1]+"/"+fechaMostrar[0];
    }
    public static String obtenerFechaActual (){
        Calendar ahoraCal = Calendar.getInstance();
        // PARECE QUE EL MES EMPIEZA DESDE 0, HAY QUE SUMAR UNO.
        ahoraCal.getTime();
        String fecha;

        fecha = ahoraCal.get(Calendar.YEAR)+" "+(ahoraCal.get(Calendar.MONTH)+1)+
                " "+ahoraCal.get(Calendar.DATE);

        return fecha;
    }
}
