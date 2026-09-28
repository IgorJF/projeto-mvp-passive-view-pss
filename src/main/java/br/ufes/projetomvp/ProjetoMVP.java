package br.ufes.projetomvp;

import br.ufes.presenter.TelaPrincipalPresenter;
import br.ufes.repository.CategoriaRepository;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.seeder.Seeder;

public class ProjetoMVP {
    public static void main(String[] args) {
        ICategoriaRepository categorias = new CategoriaRepository();
        Seeder seeder = new Seeder(categorias);
        TelaPrincipalPresenter telaPrincipal = new TelaPrincipalPresenter(categorias);
    }
}
