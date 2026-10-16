package br.ufes.service;

import br.ufes.model.HistoricoPreco;
import br.ufes.model.Produto;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.repository.IProdutoRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class CalculoPrecoService {
    private IProdutoRepository produtoRepository;
    private IHistoricoPrecoRepository historicoRepository;

    public CalculoPrecoService(IProdutoRepository produtoRepository, IHistoricoPrecoRepository historicoRepository) {
        this.produtoRepository = produtoRepository;
        this.historicoRepository = historicoRepository;
    }

    //preco de venda = preco de custo x (1 + percentual / 100), arredondado para duas casas decimais
    //usado tambem pelo Seeder para que o preco inicial siga a mesma regra da tela de calculo
    public static double calcularPrecoVenda(double precoCusto, double percentualLucro) {
        double precoVenda = precoCusto * (1 + percentualLucro / 100);
        return BigDecimal.valueOf(precoVenda).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    //o intervalo de 10 dias eh global: se o ultimo calculo (de qualquer produto) foi ha menos de 10 dias, nada eh calculado
    //retorna false quando o calculo foi bloqueado, sem alterar nenhum produto nem salvar nenhum historico
    public boolean calcularPrecos() {
        LocalDate ultimaData = historicoRepository.buscarDataUltimoCalculo();
        if (!ValidadorDatasService.podeCalcular(ultimaData)) {
            return false;
        }

        LocalDate hoje = LocalDate.now();
        for (Produto produto : produtoRepository.listar()){
            double percentualLucro = produto.getCategoria().getPercentualLucro();
            double precoVenda = calcularPrecoVenda(produto.getPrecoCusto(), percentualLucro);

            produto.setPrecoVenda(precoVenda);
            produto.setMargemLucro(percentualLucro);
            historicoRepository.salvar(new HistoricoPreco(produto.getIdProduto(), hoje, percentualLucro, precoVenda));
        }
        return true;
    }
}
