
package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Fornecedor;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Pagamento;
import br.edu.ifsul.cc.lpoo.cv.model.Venda;
import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import br.edu.ifsul.cc.lpoo.cv.model.TipoProduto;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJPA;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author bruno
 */
public class TestePersistenciaJDBC {
    
    //Teste principal, adiciona 2 produtos e 1 venda, faz a adição dos produtos a lista de produtos da venda.
    //Faz a o "loop" ao ficar executando o teste.
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
                clie.setCpf("55555555555");
                ven.setCliente(clie);
                Funcionario func = new Funcionario();
                func.setCpf("12345678999");
                ven.setFuncionario(func);
                
                /// Adicionando um produto
                pro.setNome("Bisacodil");
                pro.setQuantidade(2F);
                pro.setValor(12.50F);
                Fornecedor forn = new Fornecedor();
                forn.setCpf("88888888888");
                pro.setFornecedor(forn);
                pro.setTipo_produto(TipoProduto.MEDICAMENTO);
                ven.setProduto(pro);
                persistencia.persist(pro);
                
                System.out.println("Cadastrou o produto de ID: " + pro.getId());

                pro = new Produto();
                pro.setNome("Raio X");
                pro.setQuantidade(1F);
                pro.setValor(40F);
                forn.setCpf("88888888888");
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
                forn.setCpf("88888888888");
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
    
    @Test
    public void testListPersistenciaFuncionario() throws Exception {
        
        DateFormat formatada = new SimpleDateFormat("dd/MM/yyyy");
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
        
            List<Funcionario> lista = persistencia.listFuncionarios();
            
            if(!lista.isEmpty()) {
            
                for(Funcionario f : lista) {
                
                    System.out.println("\n-- CPF da pessoa: " + f.getCpf()
                                       + " \n-- RG da pessoa: " +  f.getRg()
                                       + " \n-- Nome da pessoa: " + f.getNome()
                                       + " \n-- Senha: " + f.getSenha()
                                       + " \n-- Número de celular: " + f.getNumero_celular()
                                       + " \n-- Email: " + f.getEmail()
                                       + " \n-- Data do cadastro: " + formatada.format(f.getData_cadastro().getTime())
                                       + " \n-- Data de nascimento: " + formatada.format(f.getData_nascimento().getTime())
                                       + " \n-- CEP: " + f.getCep()
                                       + " \n-- Endereço: " + f.getEndereco()
                                       + " \n-- Complemento: " + f.getComplemento()
                                       //+ " \n-- Tipo: " + f.getTipo()
                                       + " \n-- Cargo: " + f.getCargo()
                                       + " \n-- Número CTPS: " + f.getNumero_ctps()
                                       + " \n-- Número PIS: " + f.getNumero_pis() + "\n");
                    
                    persistencia.remover(f);
                    System.out.println("Funcionário de CPF " + f.getCpf() + " removido.\n");
                
                }
                
            } else {
                
                System.out.println("\nNão encontrou o funcinoário.");
                
                Funcionario f = new Funcionario();
                
                f.setCpf("11111111199");
                f.setRg("1111111188");
                f.setNome("Pedro");
                f.setSenha("12345");
                f.setNumero_celular("54991312244");
                f.setEmail("pedro@gmail.com");
                
                Calendar data_convertida_2 = Calendar.getInstance();
                data_convertida_2.set(Calendar.YEAR, 2002);
                data_convertida_2.set(Calendar.MONTH, 1 + 1);
                data_convertida_2.set(Calendar.DAY_OF_MONTH, 12);
                f.setData_nascimento(data_convertida_2);
                
                f.setCep("99900000");
                f.setEndereco("Rua Salgado Filho");
                f.setComplemento("Casa");
                //f.setTipo("Func");
                f.setCargo(Cargo.ATENDENTE);
                f.setNumero_ctps("11111177");
                f.setNumero_pis("1111111166");
                
                persistencia.persist(f); // INSERT na tabela.
                System.out.println("Cadastrou o Funcionário de CPF " + f.getCpf() + ".\n");
            }
        
        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC.");
        }        
                       
    }
    
    //@Test
    public void testListPersistenciaFornecedor() throws Exception {
        
        DateFormat formatada = new SimpleDateFormat("dd/MM/yyyy");
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
        
            List<Fornecedor> lista = persistencia.listFornecedores();
            
            if(!lista.isEmpty()) {
            
                for(Fornecedor f : lista) {
                
                    System.out.println("\n-- CPF da pessoa: " + f.getCpf()
                                       + " \n-- RG da pessoa: " +  f.getRg()
                                       + " \n-- Nome da pessoa: " + f.getNome()
                                       + " \n-- Senha: " + f.getSenha()
                                       + " \n-- Número de celular: " + f.getNumero_celular()
                                       + " \n-- Email: " + f.getEmail()
                                       + " \n-- Data do cadastro: " + formatada.format(f.getData_cadastro().getTime())
                                       + " \n-- Data de nascimento: " + formatada.format(f.getData_nascimento().getTime())
                                       + " \n-- CEP: " + f.getCep()
                                       + " \n-- Endereço: " + f.getEndereco()
                                       + " \n-- Complemento: " + f.getComplemento()
                                       + " \n-- CNPJ: " + f.getCnpj()
                                       + " \n-- IE: " + f.getIe() + "\n");
                    
                    persistencia.remover(f);
                    System.out.println("Fornecedor de CPF " + f.getCpf() + " removido.\n");
                
                }
                
            } else {
                
                System.out.println("\nNão encontrou o fornecedor.");
                
                Fornecedor f = new Fornecedor();
                
                f.setCpf("33333333399");
                f.setRg("3333333388");
                f.setNome("Carlos");
                f.setSenha("12345");
                f.setNumero_celular("54991347611");
                f.setEmail("Carlos@gmail.com");
                
                Calendar data_convertida_2 = Calendar.getInstance();
                data_convertida_2.set(Calendar.YEAR, 2002);
                data_convertida_2.set(Calendar.MONTH, 1 + 1);
                data_convertida_2.set(Calendar.DAY_OF_MONTH, 12);
                f.setData_nascimento(data_convertida_2);
                
                f.setCep("99900000");
                f.setEndereco("Rua São João");
                f.setComplemento("Casa");
                f.setCnpj("33333333333377");
                f.setIe("366");
                
                persistencia.persist(f);
                System.out.println("Cadastrou o fornecedor de CPF " + f.getCpf() + ".\n");
                
                f = new Fornecedor();
                
                f.setCpf("44444444499");
                f.setRg("4444444488");
                f.setNome("Antonio");
                f.setSenha("12345");
                f.setNumero_celular("54991548777");
                f.setEmail("Antonio@gmail.com");
                
                data_convertida_2.set(Calendar.YEAR, 2002);
                data_convertida_2.set(Calendar.MONTH, 1 + 1);
                data_convertida_2.set(Calendar.DAY_OF_MONTH, 12);
                f.setData_nascimento(data_convertida_2);
                
                f.setCep("99900000");
                f.setEndereco("Rua Dona Florinda");
                f.setComplemento("Casa");
                f.setCnpj("44444444444477");
                f.setIe("466");
                
                persistencia.persist(f); // INSERT na tabela.
                System.out.println("Cadastrou o fornecedor de CPF " + f.getCpf() + ".\n");
            }
        
        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC.");
        }        
                       
    }
    
    //@Test
    public void testListPersistenciaCliente() throws Exception {
        
        DateFormat formatada = new SimpleDateFormat("dd/MM/yyyy");
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
        
            List<Cliente> lista = persistencia.listClientes();
            
            if(!lista.isEmpty()) {
            
                for(Cliente cli : lista) {
                
                    System.out.println("\n-- CPF da pessoa: " + cli.getCpf()
                                       + " \n-- RG da pessoa: " +  cli.getRg()
                                       + " \n-- Nome da pessoa: " + cli.getNome()
                                       + " \n-- Senha: " + cli.getSenha()
                                       + " \n-- Número de celular: " + cli.getNumero_celular()
                                       + " \n-- Email: " + cli.getEmail()
                                       + " \n-- Data do cadastro: " + formatada.format(cli.getData_cadastro().getTime())
                                       + " \n-- Data de nascimento: " + formatada.format(cli.getData_nascimento().getTime())
                                       + " \n-- CEP: " + cli.getCep()
                                       + " \n-- Endereço: " + cli.getEndereco()
                                       + " \n-- Complemento: " + cli.getComplemento()
                                       + " \n-- Data da ultima visita: " + formatada.format(cli.getData_ultima_visita().getTime()) + "\n");
                    
                    persistencia.remover(cli);
                    System.out.println("Cliente de CPF " + cli.getCpf() + " removido.\n");
                
                }
                
            } else {
                
                System.out.println("\nNão encontrou o cliente.");
                
                Cliente cli = new Cliente();
                
                cli.setCpf("55555555599");
                cli.setRg("5555555588");
                cli.setNome("Henrique");
                cli.setSenha("12345");
                cli.setNumero_celular("54991007699");
                cli.setEmail("henrique@gmail.com");
                
                Calendar data_convertida_2 = Calendar.getInstance();
                data_convertida_2.set(Calendar.YEAR, 2002);
                data_convertida_2.set(Calendar.MONTH, 1 + 1);
                data_convertida_2.set(Calendar.DAY_OF_MONTH, 12);
                cli.setData_nascimento(data_convertida_2);
                
                cli.setCep("99900000");
                cli.setEndereco("Rua Santo Antonio");
                cli.setComplemento("Casa");
                
                persistencia.persist(cli); // INSERT na tabela.
                System.out.println("Cadastrou o cliente de CPF " + cli.getCpf() + ".\n");
                
                
                cli = new Cliente();
                
                cli.setCpf("66666666699");
                cli.setRg("6666666688");
                cli.setNome("Jose");
                cli.setSenha("12345");
                cli.setNumero_celular("54991318809");
                cli.setEmail("Jose@gmail.com");
                
                Calendar data_convertida_3 = Calendar.getInstance();
                data_convertida_3.set(Calendar.YEAR, 2002);
                data_convertida_3.set(Calendar.MONTH, 1 + 1);
                data_convertida_3.set(Calendar.DAY_OF_MONTH, 12);
                cli.setData_nascimento(data_convertida_3);
                
                cli.setCep("99900000");
                cli.setEndereco("Rua Miguel Almeida");
                cli.setComplemento("Casa");
                
                persistencia.persist(cli); // INSERT na tabela.
                System.out.println("Cadastrou o cliente de CPF " + cli.getCpf() + ".\n");
            }
        
        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC.");
        }        
                       
    }
    
    //@Test
    public void testGeracaoFuncionarioLogin() throws Exception {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
            System.out.println("\nAbriu a conexão com o BD via JDBC.\n");
            
            Funcionario f = persistencia.doLogin("11111111199", "12345");

            if(f == null) {
                
                System.out.println("Não há nenhum funcionario cadastrado.\n");
                
            } else {
                System.out.println("Encontrou um funcionario cadastrado.\n");
            }
            
            persistencia.fecharConexao();
            
        } else {
            System.out.println("\nNão abriu a conexão com o BD via JDBC.\n");
        }
        
    }
    
}