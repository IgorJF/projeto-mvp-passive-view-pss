package br.ufes.model;

public class ProdutoPedido {
  private Produto produto;
    private int quantidade;

    public ProdutoPedido(Produto produto, int quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto nao pode ser nulo");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade nao pode ser negativa");
        }
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return produto.getPrecoVenda() * quantidade;
    }
}
