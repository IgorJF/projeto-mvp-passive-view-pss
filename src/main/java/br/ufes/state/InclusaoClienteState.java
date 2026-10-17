package br.ufes.state;

import br.ufes.presenter.ClientePresenter;

public class InclusaoClienteState extends ClienteState{
    public InclusaoClienteState(ClientePresenter clientePresenter) {
        super(clientePresenter);
        novo();
    }

    @Override
    public void novo(){
        clientePresenter.modoInclusao();
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
