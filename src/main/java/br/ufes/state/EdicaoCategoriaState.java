package br.ufes.state;

import br.ufes.presenter.CategoriaPresenter;

public class EdicaoCategoriaState extends CategoriaState{
    public EdicaoCategoriaState(CategoriaPresenter categoriaPresenter) {
        super(categoriaPresenter);
        editar();
    }
    
    @Override
    public void editar(){
        categoriaPresenter.modoEdicao();
    }
    
    @Override
    public void visualizar(){
        categoriaPresenter.setModo(new VisualizacaoCategoriaState(categoriaPresenter));
    }
    
    @Override
    public void cancelar(){
        categoriaPresenter.setModo(new VisualizacaoCategoriaState(categoriaPresenter));
    }
}
