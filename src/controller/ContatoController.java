package controller;

import dao.ContatoDAO;
import model.Contato;
import java.util.List;

public class ContatoController {
    private ContatoDAO dao = new ContatoDAO();

    public void adicionar(Contato c) { dao.adicionarContato(c); }
    public void remover(String nome) { dao.removerContato(nome); }
    public Contato buscar(String nome) { return dao.buscarContato(nome); }
    public List<Contato> listar() { return dao.listarContatos(); }
}
