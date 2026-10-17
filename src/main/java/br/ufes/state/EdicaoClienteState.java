package br.ufes.state;

import br.ufes.presenter.ClientePresenter;

public class EdicaoClienteState extends ClienteState{
    public EdicaoClienteState(ClientePresenter clientePresenter) {
        super(clientePresenter);
        editar();
    }

    @Override
    public void editar(){
        clientePresenter.modoEdicao();
    }

    @Override
    public void visualizar(){
        clientePresenter.setModo(new VisualizacaoClienteState(clientePresenter));
    }

    @Override
    public void cancelar(){
        clientePresenter.setModo(new VisualizacaoClienteState(clientePresenter));
    }
}
