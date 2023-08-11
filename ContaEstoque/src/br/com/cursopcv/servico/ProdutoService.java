package br.com.cursopcv.servico;
import br.com.cursopcv.modelo.*;

import java.util.List;

/*
    Classe de serviço para encapsular as operações de negócios.
    Isso mantém a lógica de negócios separada da lógica de acesso ao banco de dados.
    Tornando o código mais modular e mais fácil de entender.
 */

public class ProdutoService implements AutoCloseable {
    /*
    Implementação da interface AutoCloseable para
    gerenciar a abertura e o fechamento do ProdutoService.
    */
    private final Encapsulamento repositorio;

    public ProdutoService() {
        repositorio = new Encapsulamento();
    }

    public Produto buscarProdutoPorCodigo(Long cod) {
        return repositorio.acharProdutoCod(cod);
    }

    public List<Produto> listarTodosProdutos() {
        return repositorio.listarTodosProdutos();
    }

    public void incluirNovoProduto(String nome, String descricao, double preco) {
        Produto produto = new Produto(nome, descricao, preco);
        repositorio.incluir(produto);
        repositorio.transacaoCommit();
    }

    public void removerProdutoPorCodigo(Long codigo) {
        try {
            repositorio.iniciarTransacao();
            Produto produto = buscarProdutoPorCodigo(codigo);

            if (produto != null) {
                repositorio.remove(produto);
                repositorio.transacaoCommit();
                System.out.println("O produto foi removido com sucesso.");
            } else {
                System.out.println("O produto não foi encontrado. Tente novamente");
            }
        } catch (Exception e) {
            repositorio.reverterTransacao();
            e.printStackTrace();
        } finally {
            repositorio.closeEntityManager();
        }
    }

    public void alterarPrecoProduto(Long codigo, double novoPreco) {
        try {
            repositorio.iniciarTransacao();
            Produto produto = buscarProdutoPorCodigo(codigo);

            if (produto != null) {
                produto.setPreco(novoPreco);
                repositorio.transacaoCommit();
            } else {
                System.out.println("O produto não foi encontrado. Tente novamente");
            }
        } catch (Exception e) {
            repositorio.reverterTransacao();
            e.printStackTrace();
        } finally {
            repositorio.closeEntityManager();
        }
    }

    @Override
    public void close() {
        repositorio.closeEntityManager();
    }

}




