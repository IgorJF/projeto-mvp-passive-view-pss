package br.ufes.strategy;

public class PagamentoCartaoCreditoStrategy implements IFormaPagamentoStrategy {
    private String numeroCartao;
    private String nomeImpresso;
    private String validade;
    private String codigoSeguranca;

    public PagamentoCartaoCreditoStrategy(String numeroCartao, String nomeImpresso, String validade, String codigoSeguranca) {
        this.numeroCartao = numeroCartao;
        this.nomeImpresso = nomeImpresso;
        this.validade = validade;
        this.codigoSeguranca = codigoSeguranca;
    }

    @Override
    public String formaPagamento() {
        return "Cartao de credito";
    }
    
    @Override
    public void validarDadosPagamento() {
        if (numeroCartao == null || numeroCartao.isBlank()) {
            throw new IllegalArgumentException("Informe o numero do cartao.");
        }
        if (nomeImpresso == null || nomeImpresso.isBlank()) {
            throw new IllegalArgumentException("Informe o nome impresso no cartao.");
        }
        if (validade == null || validade.isBlank()) {
            throw new IllegalArgumentException("Informe a validade do cartao.");
        }
        if (codigoSeguranca == null || codigoSeguranca.isBlank()) {
            throw new IllegalArgumentException("Informe o codigo de seguranca do cartao.");
        }
    }
}
