package br.ufes.state;

import br.ufes.presenter.ClientePresenter;

public class VisualizacaoClienteState extends ClienteState{
    public VisualizacaoClienteState(ClientePresenter clientePresenter) {
        super(clientePresenter);
        visualizar();
    }

    @Override
    public void visualizar() {
        clientePresenter.modoVisualizacao();
    }

    @Override
    public void novo() {
        clientePresenter.setModo(new InclusaoClienteState(clientePresenter));
    }

    @Override
    public void editar() {
        clientePresenter.setModo(new EdicaoClienteState(clientePresenter));
    }
}
