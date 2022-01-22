package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Fornecedor;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Pagamento;
import br.edu.ifsul.cc.lpoo.cv.model.Venda;
import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import br.edu.ifsul.cc.lpoo.cv.model.TipoProduto;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author bruno
 */
public class TestePersistenciaJDBC {
        
    //@Test
    public void testConexao() throws Exception {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JDBC");
            
            persistencia.fecharConexao();
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }
    
    //@Test
    public void testPersistenciaProduto() throws Exception {
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        
        if (persistencia.conexaoAberta()) {
            
            List<Produto> lista = persistencia.listProdutos();

            if (!lista.isEmpty()) {
                
                for(Produto p : lista){
                    System.out.println("Id: " + p.getId() +
                                       " | Nome: " + p.getNome() +
                                       " | Quantidade: " + p.getQuantidade() +
                                       " | Tipo produto: " + p.getTipo_produto() +
                                       " | Valor: " + p.getValor() +
                                       " | Fornecedor CPF: " + p.getFornecedor().getCpf());
                
                    System.out.println("Removendo o produto de ID: " + p.getId());
                    persistencia.remover(p);
               }
                
            } else {
                System.out.println("Produtos nao encontrados!");
                
                Produto pro = new Produto();
                //Fornecedor forn = new Fornecedor();
                //forn.getCpf();
                //pro.setFornecedor(forn);
                            //Fornecedor forn = new Fornecedor();
                            //forn.setCpf(forn.getCpf());
                            //pro.setFornecedor(forn);
                //pro.setFornecedor(forn.getCpf());
                Fornecedor forn = new Fornecedor();
                forn.setCpf("033.505.023-12");
                pro.setFornecedor(forn);
                /*Fornecedor forn = new Fornecedor();
                forn.getCpf();
                pro.setFornecedor(forn);*/

                pro.setNome("Bisacodil");
                pro.setQuantidade(Float.parseFloat("2"));
                pro.setValor(Float.parseFloat("12.50"));
                pro.setTipo_produto(TipoProduto.MEDICAMENTO);
                
                
                persistencia.persist(pro);
                
            }

            persistencia.fecharConexao();
        } else {
            System.out.println("A conexao com o Banco de Dados nao foi estabelecida.");
        }
    }
    
    @Test
    public void testPersistenciaVenda() throws Exception {
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        
        if (persistencia.conexaoAberta()){
            List<Venda> lista = persistencia.listVendas();
            List<Produto> lista2 = persistencia.listProdutos();
            
            if (!lista.isEmpty()) {
                DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
                
                for(Venda v : lista){
                    System.out.println("Id: " + v.getId() +
                                       " | Observacao: " + v.getObservacao() +
                                       " | Valor Total: " + v.getValor_total() +
                                       " | Data: " + formatador.format(v.getData().getTime()) +
                                       " | Pagamento: " + v.getPagamento() +
                                       " | Cliente CPF: " + v.getCliente().getCpf() +
                                       " | Funcionario CPF: " + v.getFuncionario().getCpf() +
                                       " | Produtos: " + v.getProdutos());
                                        
                    
                    if(v.getProdutos() != null && !v.getProdutos().isEmpty()){
                        for(Produto p : v.getProdutos()){
                           System.out.println("Id: " + p.getId() +
                                       " | Nome: " + p.getNome() +
                                       " | Quantidade: " + p.getQuantidade() +
                                       " | Tipo produto: " + p.getTipo_produto() +
                                       " | Valor: " + p.getValor() +
                                       " | Fornecedor CPF: " + p.getFornecedor().getCpf());
                
                            System.out.println("Removendo o produto de ID: " + p.getId());
                            persistencia.remover(p);
                        }
                    }
                    
                    for(Produto p : lista2){
                        persistencia.remover(p);
                    }
                    /*for(Produto p : lista2){
                        System.out.println("Id: " + p.getId() +
                                       " | Nome: " + p.getNome() +
                                       " | Quantidade: " + p.getQuantidade() +
                                       " | Tipo produto: " + p.getTipo_produto() +
                                       " | Valor: " + p.getValor() +
                                       " | Fornecedor CPF: " + p.getFornecedor().getCpf());
                
                        System.out.println("Removendo o produto de ID: " + p.getId());
                        persistencia.remover(p);
                    }*/
                    
                    System.out.println("Removendo a venda de ID: " + v.getId());
                    persistencia.remover(v);
                    
                    
               }
                
            } else {
                System.out.println("Venda nao encontrada!");
                
                Venda ven = new Venda();                
                Produto pro = new Produto();

                ven.setObservacao("Venda realizada.");
                ven.setValor_total(Float.parseFloat("25.00"));
                ven.setPagamento(Pagamento.DINHEIRO);
                
                Cliente clie = new Cliente();
                clie.setCpf("044.444.040-12");
                ven.setCliente(clie);
                
                Funcionario func = new Funcionario();
                func.setCpf("055.555.050-12");
                ven.setFuncionario(func);
                
                /// Adicionando um produto
                    
                pro.setNome("Bisacodil");
                pro.setQuantidade(Float.parseFloat("2"));
                pro.setValor(Float.parseFloat("12.50"));
                Fornecedor forn = new Fornecedor();
                forn.setCpf("033.505.023-12");
                pro.setFornecedor(forn);
                pro.setTipo_produto(TipoProduto.MEDICAMENTO);
                
                
                
                ven.setProduto(pro);
                persistencia.persist(pro);
                
                persistencia.persist(ven);
            }
            
            
        }else {
            System.out.println("A conexao com o Banco de Dados nao foi estabelecida.");
        }        
    }
    
    
}