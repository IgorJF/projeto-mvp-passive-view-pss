package br.ufes.model;

import br.projeto.painelpedidos.model.PedidoAbstract;
import br.ufes.cupom.APITaxaDescontoMock;
import br.ufes.cupom.CupomDescontoEntrega;
import br.ufes.cupom.CupomDescontoPedido;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends PedidoAbstract{
    private List<ProdutoPedido> produtos;
    private Cliente cliente;
    private double taxaEntrega = APITaxaDescontoMock.getTaxaEntregaPadrao();
    private List<CupomDescontoEntrega> cuponsTaxaEntrega = new ArrayList<>();
    private CupomDescontoPedido cupomDescontoPedido;
    private double valorDescontoPedido;
    private Pagamento pagamento;

    public Pedido(String identificador, LocalDateTime dataHora, String nomeCliente, String enderecoEntrega, double valorTotal){
        super(identificador, dataHora, nomeCliente, enderecoEntrega, valorTotal);
        produtos = new ArrayList<>();
    }
    
    @Override
    public PedidoAbstract criarCopia(String novoIdentificador, LocalDateTime novaDataHora) {
        return new Pedido(novoIdentificador, novaDataHora, getNomeCliente(), getEnderecoEntrega(), getValorTotal());
    }

    public Cliente getCliente(){
        return cliente;
    }

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public List<ProdutoPedido> getProdutos(){
        return produtos;
    }

    public void adicionarProdutoPedido(ProdutoPedido produtoPedido){
        if(produtoPedido == null){
            throw new IllegalArgumentException("Produto nao pode ser nulo");
        }
        for(ProdutoPedido produtoJaExistente : produtos){
            if(produtoJaExistente.getProduto() == produtoPedido.getProduto()){
                produtoJaExistente.setQuantidade(produtoJaExistente.getQuantidade() + produtoPedido.getQuantidade());
                return;
            }
        }
        produtos.add(produtoPedido);
    }

    public void removerProdutoPedido(ProdutoPedido produto){
        produtos.remove(produto);
    }

    public double getValorTotalProdutos(){
        double valorTotalProdutos = 0;
        for(ProdutoPedido produto : produtos){
            valorTotalProdutos += produto.getSubtotal();
        }
        return valorTotalProdutos;
    }

    public double getTaxaEntrega(){
        return taxaEntrega;
    }

    public double getValorTotalPedido(){
        return getValorTotalProdutos() + APITaxaDescontoMock.getTaxaEntregaPadrao();
    }

    public double getDescontoTaxaEntrega(){
        return APITaxaDescontoMock.getTaxaEntregaPadrao() - taxaEntrega;
    }

    public void aplicarDescontoTaxaEntrega(double desconto){
        double limite = APITaxaDescontoMock.getTaxaEntregaPadrao();
        double concedido = limite - taxaEntrega;
        double disponivel = limite - concedido;
        if(desconto > disponivel){
            desconto = disponivel;
        }
        taxaEntrega -= desconto;
    }

    public List<CupomDescontoEntrega> getCuponsDescontoEntrega(){
        return cuponsTaxaEntrega;
    }

    public CupomDescontoPedido getCupomDescontoPedido(){
        return cupomDescontoPedido;
    }

    public void adicionarCupomDescontoPedido(CupomDescontoPedido cupom){
        this.cupomDescontoPedido = cupom;
    }

    public void aplicarDescontoCupomPedido(double desconto){
        this.valorDescontoPedido = desconto;
    }

    public double getDescontoPedido(){
        return valorDescontoPedido;
    }

    public double getValorPedidoComDesconto(){
        return getValorTotalPedido() - getDescontoPedido();
    }

    public double getValorFinalPedido(){
        return getValorPedidoComDesconto() - getDescontoTaxaEntrega();
    }

    public Pagamento getPagamento(){
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento){
        this.pagamento = pagamento;
    }

}
