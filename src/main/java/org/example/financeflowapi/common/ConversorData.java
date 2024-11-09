package org.example.financeflowapi.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversorData {

    public static String converterDateParaDataEHora(Date date){

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }
}
