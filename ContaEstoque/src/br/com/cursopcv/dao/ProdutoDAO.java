package br.com.cursopcv.dao;
import br.com.cursopcv.modelo.Produto;

import javax.persistence.EntityManager;
import java.util.List;

public class ProdutoDAO {

    private final EntityManager entityManager;

    public ProdutoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void salvar(Produto produto) {
        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();
    }

    public void atualizar(Produto produto) {
        entityManager.getTransaction().begin();
        entityManager.merge(produto);
        entityManager.getTransaction().commit();
    }

    public void remover(Produto produto) {
        entityManager.getTransaction().begin();
        entityManager.remove(produto);
        entityManager.getTransaction().commit();
    }

    public Produto buscarPorCodigo(Long codigo) {
        return entityManager.find(Produto.class, codigo);
    }

    public List<Produto>listarTodos() {
        return entityManager.createQuery("select p from Produto p", Produto.class).getResultList();
    }

}
