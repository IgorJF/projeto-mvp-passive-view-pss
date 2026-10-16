package br.ufes.state;

import br.ufes.presenter.CategoriaPresenter;

public class VisualizacaoCategoriaState extends CategoriaState{
    public VisualizacaoCategoriaState(CategoriaPresenter categoriaPresenter) {
        super(categoriaPresenter);
        visualizar();
    }
    
    @Override
    public void visualizar() {
        categoriaPresenter.modoVisualizacao();
    }
    
    @Override
    public void novo() {
        categoriaPresenter.setModo(new InclusaoCategoriaState(categoriaPresenter));
    }
    
    @Override
    public void editar() {
        categoriaPresenter.setModo(new EdicaoCategoriaState(categoriaPresenter));
    }
}
