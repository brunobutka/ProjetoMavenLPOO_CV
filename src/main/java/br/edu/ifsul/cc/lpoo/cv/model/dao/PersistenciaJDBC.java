/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Fornecedor;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Pagamento;
import br.edu.ifsul.cc.lpoo.cv.model.Venda;
import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import br.edu.ifsul.cc.lpoo.cv.model.TipoProduto;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;


/**
 *
 * @author bruno
 */
public class PersistenciaJDBC implements InterfacePersistencia {
    
    private final String DRIVER = "org.postgresql.Driver";
    private final String USER = "postgres";
    private final String SENHA = "Banco123@@";
    public static final String URL = "jdbc:postgresql://localhost:5432/db_cv";
    private Connection con = null;

    public PersistenciaJDBC () throws Exception {
        Class.forName(DRIVER); //carregamento do driver postgresql em tempo de execução
        System.out.println("Tentando estabelecer conexao JDBC com : "+URL+" ...");
            
        this.con = (Connection) DriverManager.getConnection(URL, USER, SENHA); 
        
    }
    
    
    @Override
    public Boolean conexaoAberta() {
        try {
            if(con != null)
                return !con.isClosed();//verifica se a conexao está aberta
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return false;   
    }

    @Override
    public void fecharConexao() {        
        try{                               
            this.con.close();//fecha a conexao.
            System.out.println("Fechou conexao JDBC");
        }catch(SQLException e){            
            e.printStackTrace();//gera uma pilha de erro na saida.
        }   
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
        
        if(c == Venda.class){
            PreparedStatement ps = this.con.prepareStatement("select id, data, observacao, pagamento, valor_total, cliente_cpf, funcionario_cpf from tb_venda where id = ?");
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                Venda v = new Venda();
               
                v.setId(rs.getInt("id"));
               
                Calendar dt = Calendar.getInstance();
                dt.setTimeInMillis(rs.getDate("data").getTime());
                v.setData(dt);
               
                v.setObservacao(rs.getString("observacao"));
                v.setPagamento(Pagamento.getPagamento(rs.getString("pagamento"))); 
                v.setValor_total(rs.getFloat("valor_total"));
               
                Cliente cli = new Cliente();
                cli.setCpf(rs.getString("cliente_cpf"));
                v.setCliente(cli);
               
                Funcionario fun = new Funcionario();
                fun.setCpf(rs.getString("funcionario_cpf"));
                v.setFuncionario(fun);
               
                PreparedStatement ps2 = this.con.prepareStatement("select p.id, p.nome, p.quantidade, p.tipo_produto, p.valor, p.fornecedor_cpf "
                                                                 + "from tb_produto p, "
                                                                 + "tb_venda_produto vp where p.id = vp.produto_id and vp.venda_id = ?");
                ps2.setInt(1, Integer.parseInt(id.toString()));
               
                ResultSet rs2 = ps2.executeQuery();
               
                while(rs2.next()){
                    Produto p = new Produto();
                   
                    p.setId(rs2.getInt("id"));
                    p.setNome(rs2.getString("nome"));
                    p.setQuantidade(rs2.getFloat("quantidade"));
                    p.setTipo_produto(TipoProduto.getTipoProduto(rs2.getString("tipo_produto"))); 
                    p.setValor(rs2.getFloat("valor"));

                    Fornecedor forn = new Fornecedor();
                    forn.setCpf(rs2.getString("fornecedor_cpf"));
                    p.setFornecedor(forn);
                   
                    v.setProduto(p);
                } 
                
            ps.close();
            ps2.close();
                
            return v;    
            }
            
        }else if(c == Produto.class){
            PreparedStatement ps = this.con.prepareStatement("select id, nome, quantidade, tipo_produto, valor, fornecedor_cpf from tb_produto where id = ?");
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                Produto p = new Produto();
                
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setQuantidade(rs.getFloat("quantidade"));
                p.setTipo_produto(TipoProduto.getTipoProduto(rs.getString("tipo_produto"))); 
                
                p.setValor(rs.getFloat("valor"));
                
                Fornecedor forn = new Fornecedor();
                forn.setCpf(rs.getString("fornecedor_cpf"));
                p.setFornecedor(forn);
                
                ps.close();
                
                return p;
            }
            
        }
        
        return null;
    }

    @Override
    public void persist(Object o) throws Exception {
        if(o instanceof Produto){
            Produto p = (Produto) o;
            
            if(p.getId() == null){
                //INSERT
                PreparedStatement ps = this.con.prepareStatement("insert into tb_produto "
                                                                + "(id, nome, quantidade, tipo_produto, valor, fornecedor_cpf) values "
                                                                + "(nextval('seq_produto_id'), ?, ?, ?, ?, ?) returning id");
                
                ps.setString(1, p.getNome());
                ps.setFloat(2, p.getQuantidade());
                ps.setString(3, p.getTipo_produto().toString());
                ps.setFloat(4, p.getValor());
                ps.setString(5, p.getFornecedor().getCpf());
                
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()){
                    p.setId(rs.getInt(1));
                }   
                
                //ps.execute(); Estava fazendo inserir 2 em tb_produto;
                
            }else{
                //UPTADE
                PreparedStatement ps = this.con.prepareStatement("update tb_produto set"
                                                                + "nome = ?, "
                                                                + "quantidade = ?, "
                                                                + "tipo_produto = ?, "
                                                                + "valor = ?, "
                                                                + "fornecedor_cpf = ? "
                                                                + "where id = ?");
                
                ps.setString(1, p.getNome());
                ps.setFloat(2, p.getQuantidade());
                ps.setString(3, p.getTipo_produto().toString());
                ps.setFloat(4, p.getValor());
                ps.setString(5, p.getFornecedor().getCpf());
                ps.setInt(6, p.getId());
                        
                ps.execute();
            }
        }else if(o instanceof Venda){
            Venda v = (Venda) o;
            
            if(v.getId() == null){
                //INSERT
                PreparedStatement ps = this.con.prepareStatement("insert into tb_venda "
                                                                + "(id, data, observacao, pagamento, valor_total, cliente_cpf, funcionario_cpf) values"
                                                                + "(nextval('seq_venda_id'), now(), ?, ?, ?, ?, ?) returning id");
                
                ps.setString(1, v.getObservacao());
                ps.setString(2, v.getPagamento().toString());
                ps.setFloat(3, v.getValor_total());
                ps.setString(4, v.getCliente().getCpf());
                ps.setString(5, v.getFuncionario().getCpf());
                
                //ps.execute();
                
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()){
                    v.setId(rs.getInt(1));
                } 
                
                
                
                if(v.getProdutos() != null && !v.getProdutos().isEmpty()){
                    for(Produto p : v.getProdutos()){
                        PreparedStatement ps2 = this.con.prepareStatement("insert into tb_venda_produto "
                                                                         + "(venda_id, produto_id) values "
                                                                         + "(?, ?)");
                        ps2.setInt(1, v.getId());
                        ps2.setInt(2, p.getId());
                        
                        ps2.execute();
                    }
                }
            }else{
                //UPDATE
                PreparedStatement ps = this.con.prepareStatement("update tb_venda set "
                                                                + "observacao = ?, "
                                                                + "pagamento = ?, "
                                                                + "valor_total = ?, "
                                                                + "cliente_cpf = ?, "
                                                                + "funcionario_cpf = ? where id = ?");
                
                ps.setString(1, v.getObservacao());
                ps.setString(2, v.getPagamento().toString());
                ps.setFloat(3, v.getValor_total());
                ps.setString(4, v.getCliente().getCpf());
                ps.setString(5, v.getFuncionario().getCpf());
                ps.setInt(6, v.getId());
                
                ps.execute();
                
                PreparedStatement ps2 = this.con.prepareStatement("delete from tb_venda_produto where venda_id = ?");
                ps2.setInt(1, v.getId());
                //ps2.execute();
                
                if(v.getProdutos() != null && !v.getProdutos().isEmpty()){
                    for(Produto p : v.getProdutos()){
                        PreparedStatement ps3 = this.con.prepareStatement("insert into tb_venda_produto "
                                                                          + "(venda_id, produto_id) values "
                                                                          + "(?, ?)");
                        
                        ps3.setInt(1, v.getId());
                        ps3.setInt(2, p.getId());
                        
                        ps3.execute();  
                    }
                } 
            }
        }
    }

    @Override
    public void remover(Object o) throws Exception {
        if(o instanceof Produto){
            Produto p = (Produto) o;
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_venda_produto where produto_id = ?");
            ps.setInt(1, p.getId());
            ps.execute();
            
            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_produto where id = ?");
            ps2.setInt(1, p.getId());
            ps2.execute();
        }else if(o instanceof Venda){
            Venda v = (Venda) o;
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_venda_produto where venda_id = ?");
            ps.setInt(1, v.getId());
            ps.execute();
            
            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_venda where id = ?");
            ps2.setInt(1, v.getId());
            ps2.execute();
        }
    }

    @Override
    public List<Venda> listVendas() throws Exception {
        List<Venda> lista = null;
        
        PreparedStatement ps = this.con.prepareStatement("select id, data, observacao, pagamento, valor_total, cliente_cpf, funcionario_cpf from tb_venda order by id asc");
        
        ResultSet rs = ps.executeQuery();
        
        lista = new ArrayList();
        while(rs.next()){
            Venda v = new Venda();
            
            v.setId(rs.getInt("id"));
            
            Calendar dt = Calendar.getInstance();
            dt.setTimeInMillis(rs.getDate("data").getTime());
            v.setData(dt);
            
            v.setObservacao(rs.getString("observacao"));
            v.setPagamento(Pagamento.getPagamento(rs.getString("pagamento")));
            v.setValor_total(rs.getFloat("valor_total"));
            
            Cliente cli = new Cliente();
            cli.setCpf(rs.getString("cliente_cpf"));
            v.setCliente(cli);
            
            Funcionario fun = new Funcionario();
            fun.setCpf(rs.getString("funcionario_cpf"));
            v.setFuncionario(fun);
            
            PreparedStatement ps2 = this.con.prepareStatement("select p.id, p.nome, p.quantidade, p.tipo_produto, p.valor, p.fornecedor_cpf "
                                                                 + "from tb_produto p, "
                                                                 + "tb_venda_produto vp where p.id = vp.produto_id and vp.venda_id = ?");
            ps2.setInt(1, v.getId());
            
            ResultSet rs2 = ps2.executeQuery();
            
            while(rs2.next()){
                Produto p = new Produto();
                
                p.setId(rs2.getInt("id"));
                p.setNome(rs2.getString("nome"));
                p.setQuantidade(rs2.getFloat("quantidade"));
                p.setTipo_produto(TipoProduto.getTipoProduto(rs2.getString("tipo_produto")));
                
                p.setValor(rs2.getFloat("valor"));

                Fornecedor forn = new Fornecedor();
                forn.setCpf(rs2.getString("fornecedor_cpf"));
                p.setFornecedor(forn);

                v.setProduto(p);
                //v.setProdutos((List<Produto>) p);
            }
            lista.add(v);
     
        }
        return lista;
    }
    
    @Override
    public List<Produto> listProdutos() throws Exception {
        
        List<Produto> lista = null;
        
        PreparedStatement ps = this.con.prepareStatement("select id, nome, quantidade, tipo_produto, valor, fornecedor_cpf from tb_produto order by id asc");
        
        ResultSet rs = ps.executeQuery();
        
        lista = new ArrayList();
        
        while(rs.next()){
            Produto pro = new Produto();
            pro.setId(rs.getInt("id"));
            pro.setNome(rs.getString("nome"));
            pro.setQuantidade(rs.getFloat("quantidade"));
            pro.setTipo_produto(TipoProduto.getTipoProduto(rs.getString("tipo_produto")));            
            pro.setValor(rs.getFloat("valor"));
            
            Fornecedor forn = new Fornecedor();
            forn.setCpf(rs.getString("fornecedor_cpf"));
            pro.setFornecedor(forn);
            
            lista.add(pro);
      
        }
        return lista;
    }
    
}