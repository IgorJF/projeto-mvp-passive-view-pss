package br.ufes.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistoricoPreco {

    private List<Double> precos;
    private List<Double> percentuaisLucro;
    private List<LocalDate> datas;

    public HistoricoPreco() {
        this.precos = new ArrayList<>();
        this.percentuaisLucro = new ArrayList<>();
        this.datas = new ArrayList<>();
    }

    public void adicionarPreco(double preco, double percentualLucro, LocalDate data) {
        precos.add(preco);
        percentuaisLucro.add(percentualLucro);
        datas.add(data);
    }

    public List<Double> getPrecos() {
        return precos;
    }

    public List<Double> getPercentuaisLucro() {
        return percentuaisLucro;
    }

    public List<LocalDate> getDatas() {
        return datas;
    }
}



