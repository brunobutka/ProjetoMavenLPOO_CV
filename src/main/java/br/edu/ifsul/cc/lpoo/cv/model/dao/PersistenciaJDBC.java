
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Fornecedor;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Pagamento;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
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
        Class.forName(DRIVER); 
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
            
        } else if(c == Pessoa.class) {
            
            // tb_pessoa
            PreparedStatement ps = this.con.prepareStatement("select cpf, rg, nome, senha, numero_celular, "
                                                            + "email, data_cadastro, data_nascimento, cep, "
                                                            + "endereco, complemento, tipo from tb_pessoa "
                                                            + "where cpf = ?");
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
            
                Pessoa pes = new Pessoa();
                
                pes.setCpf(rs.getString("cpf"));
                
                pes.setRg(rs.getString("rg"));
                
                pes.setNome(rs.getString("nome"));
                
                pes.setSenha(rs.getString("senha"));
                
                pes.setNumero_celular(rs.getString("numero_celular"));
                
                pes.setEmail(rs.getString("email"));
                
                Calendar data_cadastro_cal = Calendar.getInstance();
                data_cadastro_cal.setTimeInMillis(rs.getDate("data_cadastro").getTime());
                pes.setData_cadastro(data_cadastro_cal);
                
                Calendar data_nascimento_cal = Calendar.getInstance();
                data_nascimento_cal.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                pes.setData_nascimento(data_nascimento_cal);
                
                pes.setCep(rs.getString("cep"));
                
                pes.setEndereco(rs.getString("endereco"));
                
                pes.setComplemento(rs.getString("complemento"));
                
                pes.setTipo(rs.getString("tipo"));
                
                return pes;                
            }
            
        } 
        
        return null;
    }

    @Override
    public void persist(Object o) throws Exception {
        if(o instanceof Produto){
            Produto p = (Produto) o;
            
            //Insert ou Update
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
        } else if(o instanceof Pessoa) {
            
            Pessoa p = (Pessoa) o; // Converter o para o e que eh do tipo Pessoa.
            
            // Descobrir se eh para realiar INSERT ou UPDATE.
            if(p.getData_cadastro()== null) {
             
                // INSERT.
                PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa "
                                                                + "(cpf, rg, nome, senha, numero_celular, "
                                                                + "email, data_cadastro, data_nascimento, "
                                                                + "cep, endereco, complemento, tipo) "
                                                                + "values (?, ?, ?, ?, ?, ?, now(), ?, ?, ?, ?, ?)");
                
                ps.setString(1, p.getCpf());
                ps.setString(2, p.getRg());
                ps.setString(3, p.getNome());
                ps.setString(4, p.getSenha());
                ps.setString(5, p.getNumero_celular());
                ps.setString(6, p.getEmail());
                //ps.setDate(7, new java.sql.Date(p.getData_cadastro().getTimeInMillis()));
                //ps.setString(7, "now()");
                ps.setDate(7, new java.sql.Date(p.getData_nascimento().getTimeInMillis()));
                ps.setString(8, p.getCep());
                ps.setString(9, p.getEndereco());
                ps.setString(10, p.getComplemento());
                ps.setString(11, p.getTipo());
                
                ps.execute();
                
            } else {
                
                // UPDATE.
                PreparedStatement ps = this.con.prepareStatement("update tb_pessoa set "
                                                                + "rg = ?, "
                                                                + "nome = ?, "
                                                                + "senha = ?,"
                                                                + "numero_celular = ?, "
                                                                + "email = ?, "
                                                                + "data_nascimento = ?, "
                                                                + "cep = ?, "
                                                                + "endereco = ?, "
                                                                + "complemento = ?, "
                                                                + "tipo = ? "
                                                                + "where cpf = ?");
                
                ps.setString(1, p.getRg());
                ps.setString(2, p.getNome());
                ps.setString(3, p.getSenha());
                ps.setString(4, p.getNumero_celular());
                ps.setString(5, p.getEmail());
                //ps.setDate(6, new java.sql.Date(p.getData_cadastro().getTimeInMillis()));
                ps.setDate(6, new java.sql.Date(p.getData_nascimento().getTimeInMillis()));
                ps.setString(7, p.getCep());
                ps.setString(8, p.getEndereco());
                ps.setString(9, p.getComplemento());
                ps.setString(10, p.getTipo());
                ps.setString(11, p.getCpf());
                
                ps.execute(); // Executa o comando.
                
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
        } else if(o instanceof Pessoa) {
            
            Pessoa p = (Pessoa) o;
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");
            ps.setString(1, p.getCpf());
            ps.execute();
            
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
    
    @Override
    public List<Pessoa> listPessoas() throws Exception {
                
        List<Pessoa> lista = null;
                        
        PreparedStatement ps = this.con.prepareStatement("select cpf, rg, "
                                                        + "nome, senha, numero_celular, "
                                                        + "email, data_cadastro, data_nascimento, cep, endereco, "
                                                        + "complemento, tipo from tb_pessoa "
                                                        + "order by data_cadastro asc");
        
        ResultSet rs = ps.executeQuery(); // Executa a query.

        lista = new ArrayList();
        while(rs.next()){
            
            Pessoa p = new Pessoa();
            
            p.setCpf(rs.getString("cpf"));
            
            p.setRg(rs.getString("rg"));
            
            p.setNome(rs.getString("nome"));
            
            p.setSenha(rs.getString("senha"));
            
            p.setNumero_celular(rs.getString("numero_celular"));
            
            p.setEmail(rs.getString("email"));

            Calendar dtCad = Calendar.getInstance();
            dtCad.setTimeInMillis(rs.getDate("data_cadastro").getTime());                        
            p.setData_cadastro(dtCad);

            Calendar dtU = Calendar.getInstance();
            dtU.setTimeInMillis(rs.getDate("data_nascimento").getTime());
            p.setData_nascimento(dtU);
            
            p.setCep(rs.getString("cep"));
            
            p.setEndereco(rs.getString("endereco"));
            
            p.setComplemento(rs.getString("complemento"));
            
            p.setTipo(rs.getString("tipo"));

            
            lista.add(p);
        
        }
        
        return lista;
        
    }
    
    @Override
    public Pessoa doLogin(String cpf, String senha) throws Exception {
                
        Pessoa pessoa = null;
        
         PreparedStatement ps = 
            this.con.prepareStatement("select p.cpf, p.senha from tb_pessoa p where p.cpf = ? and p.senha = ? ");
                        
            ps.setString(1, cpf);
            ps.setString(2, senha);
            
            ResultSet rs = ps.executeQuery(); // O ponteiro do ResultSet inicialmente está na linha -1.
            
            if(rs.next()) { // Se a matriz (ResultSet) tem uma linha.

                pessoa = new Pessoa();
                pessoa.setCpf(rs.getString("cpf"));                
            }
        
            ps.close();
            return pessoa;
        
    }
    
}