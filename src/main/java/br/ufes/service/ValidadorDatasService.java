package br.ufes.service;

import java.time.LocalDate;

public class ValidadorDatasService{
     private static final int DIAS_LIMITE = 10;

    public static boolean podeCalcular(LocalDate ultimaDataCalculada) {
        if (ultimaDataCalculada == null) {
            return true;
        }
        LocalDate dataPermitida = ultimaDataCalculada.plusDays(DIAS_LIMITE);
        return !LocalDate.now().isBefore(dataPermitida);
    }
}
