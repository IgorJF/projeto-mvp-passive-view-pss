package br.ufes.state;

import br.ufes.presenter.ProdutoPresenter;

public class EdicaoProdutoState extends ProdutoState{
    public EdicaoProdutoState(ProdutoPresenter produtoPresenter) {
        super(produtoPresenter);
        editar();
    }
    
    @Override
    public void editar(){
        produtoPresenter.modoEdicao();
    }
    
    @Override
    public void visualizar(){
        produtoPresenter.setModo(new VisualizacaoProdutoState(produtoPresenter));
    }
    
    @Override
    public void cancelar(){
        produtoPresenter.fechar();
    }
}
