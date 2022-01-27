
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
    
    //Teste principal, adiciona 2 produtos e 1 venda, faz a adição dos produtos a lista de produtos da venda.
    //@Test
    public void testPersistenciaVendaProduto() throws Exception {
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        
        if (persistencia.conexaoAberta()){
            List<Venda> lista = persistencia.listVendas();
            
            if (!lista.isEmpty()) {
                DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
                
                for(Venda v : lista){
                    System.out.println("\n---- Venda ----\n" +
                                       "Id: " + v.getId() +
                                       " | Observacao: " + v.getObservacao() +
                                       " | Valor Total: " + v.getValor_total() +
                                       " | Data: " + formatador.format(v.getData().getTime()) +
                                       " | Pagamento: " + v.getPagamento() +
                                       " | Cliente CPF: " + v.getCliente().getCpf() +
                                       " | Funcionario CPF: " + v.getFuncionario().getCpf());
                                        
                    
                    if(v.getProdutos() != null && !v.getProdutos().isEmpty()){
                        System.out.println("\n---- Produtos ----");
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
                    
                    System.out.println("\nRemovendo a venda de ID: " + v.getId());
                    persistencia.remover(v);
                    
               }
                
            } else {
                System.out.println("Venda nao encontrada!");
                
                Venda ven = new Venda();                
                Produto pro = new Produto();

                ven.setObservacao("Venda realizada.");
                ven.setValor_total(25F);
                ven.setPagamento(Pagamento.DINHEIRO); 
                Cliente clie = new Cliente();
                clie.setCpf("044.444.040-12");
                ven.setCliente(clie);
                Funcionario func = new Funcionario();
                func.setCpf("055.555.050-12");
                ven.setFuncionario(func);
                
                /// Adicionando um produto
                pro.setNome("Bisacodil");
                pro.setQuantidade(2F);
                pro.setValor(12.50F);
                Fornecedor forn = new Fornecedor();
                forn.setCpf("033.505.023-12");
                pro.setFornecedor(forn);
                pro.setTipo_produto(TipoProduto.MEDICAMENTO);
                ven.setProduto(pro);
                persistencia.persist(pro);
                
                System.out.println("Cadastrou o produto de ID: " + pro.getId());

                pro = new Produto();
                pro.setNome("Raio X");
                pro.setQuantidade(1F);
                pro.setValor(40F);
                forn.setCpf("033.505.023-12");
                pro.setFornecedor(forn);
                pro.setTipo_produto(TipoProduto.CONSULTA);
                ven.setProduto(pro);
                persistencia.persist(pro);
                    
                System.out.println("Cadastrou o produto de ID: " + pro.getId());
                
                persistencia.persist(ven);
                
                System.out.println("Cadastrou a venda de ID: " + ven.getId());       
            }
            
            
        }else {
            System.out.println("Não abriu conexão com o BD via JDBC.");
        }        
    }
    
    //Teste apenas para produto.
    //@Test
    public void testPersistenciaProduto() throws Exception {
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        
        if(persistencia.conexaoAberta()) {
            
            List<Produto> lista = persistencia.listProdutos();

            if(!lista.isEmpty()) {
                System.out.println("\n---- Produtos ----\n");
                for(Produto p : lista){
                    System.out.println("Id: " + p.getId() +
                                       " | Nome: " + p.getNome() +
                                       " | Quantidade: " + p.getQuantidade() +
                                       " | Tipo produto: " + p.getTipo_produto() +
                                       " | Valor: " + p.getValor() +
                                       " | Fornecedor CPF: " + p.getFornecedor().getCpf() +
                                       "\n");
                
                    System.out.println("Removendo o produto de ID: " + p.getId());
                    persistencia.remover(p);
               }
                
            } else {
                System.out.println("Produtos nao encontrados!");
                
                Produto pro = new Produto();
                Fornecedor forn = new Fornecedor();
                forn.setCpf("033.505.023-12");
                pro.setFornecedor(forn);
                pro.setNome("Bisacodil");
                pro.setQuantidade(2F);
                pro.setValor(12.50F);
                pro.setTipo_produto(TipoProduto.MEDICAMENTO);
                
                persistencia.persist(pro);  
                
                System.out.println("Cadastrou o produto " + pro.getId());
            }

            persistencia.fecharConexao();
        } else {
            System.out.println("Não abriu conexão com o BD via JDBC.");
        }
    }
}