
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
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
            
        } /*else if(c == Pessoa.class) {
            
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
            
        }*/ else if(c == Funcionario.class) {
            PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.rg, pes.nome, pes.senha, "
                                                            + "pes.numero_celular, pes.email, pes.data_cadastro, "
                                                            + "pes.data_nascimento, pes.cep, pes.endereco, "
                                                            + "pes.complemento, Func.cargo, "
                                                            + "Func.numero_ctps, Func.numero_pis "
                                                            + "from tb_pessoa as pes "
                                                            + "inner join tb_funcionario as Func on "
                                                            + "pes.cpf = Func.cpf where pes.cpf = ?");
                                                            
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                
                Funcionario f = new Funcionario();
                
                f.setCpf(rs.getString("cpf"));
                f.setRg(rs.getString("rg"));               
                f.setNome(rs.getString("nome"));                
                f.setSenha(rs.getString("senha"));
                f.setNumero_celular(rs.getString("numero_celular"));
                f.setEmail(rs.getString("email"));
                
                if(rs.getDate("data_cadastro") != null) {
                    Calendar data_cadastro_cal = Calendar.getInstance();
                    data_cadastro_cal.setTimeInMillis(rs.getDate("data_cadastro").getTime());
                    f.setData_cadastro(data_cadastro_cal);
                }
                
                Calendar data_nascimento_cal = Calendar.getInstance();
                data_nascimento_cal.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                f.setData_nascimento(data_nascimento_cal);
                
                f.setCep(rs.getString("cep"));
                f.setEndereco(rs.getString("endereco"));
                f.setComplemento(rs.getString("complemento"));
                //f.setTipo(rs.getString("tipo"));
                f.setCargo(Cargo.getCargo(rs.getString("cargo")));
                f.setNumero_ctps(rs.getString("numero_ctps"));
                f.setNumero_pis(rs.getString("numero_pis"));
                
                return f;
            }
        } else if(c == Fornecedor.class) {
            PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.rg, pes.nome, pes.senha, "
                                                            + "pes.numero_celular, pes.email, pes.data_cadastro, "
                                                            + "pes.data_nascimento, pes.cep, pes.endereco, "
                                                            + "pes.complemento, Forn.ie, "
                                                            + "Forn.cnpj "
                                                            + "from tb_pessoa as pes "
                                                            + "inner join tb_fornecedor as Forn on "
                                                            + "pes.cpf = Forn.cpf where pes.cpf = ?");
                                                            
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                
                Fornecedor f = new Fornecedor();
                
                f.setCpf(rs.getString("cpf"));
                f.setRg(rs.getString("rg"));               
                f.setNome(rs.getString("nome"));                
                f.setSenha(rs.getString("senha"));
                f.setNumero_celular(rs.getString("numero_celular"));
                f.setEmail(rs.getString("email"));
                
                Calendar data_cadastro_cal = Calendar.getInstance();
                data_cadastro_cal.setTimeInMillis(rs.getDate("data_cadastro").getTime());
                f.setData_cadastro(data_cadastro_cal);
                
                Calendar data_nascimento_cal = Calendar.getInstance();
                data_nascimento_cal.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                f.setData_nascimento(data_nascimento_cal);
                
                f.setCep(rs.getString("cep"));
                f.setEndereco(rs.getString("endereco"));
                f.setComplemento(rs.getString("complemento"));
                //f.setTipo(rs.getString("tipo"));
                f.setIe(rs.getString("ie"));
                f.setCnpj(rs.getString("cnpj"));
                
                return f;
            }
        } else if(c == Cliente.class) {
            PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.rg, pes.nome, pes.senha, "
                                                            + "pes.numero_celular, pes.email, pes.data_cadastro, "
                                                            + "pes.data_nascimento, pes.cep, pes.endereco, "
                                                            + "pes.complemento, Clie.data_ultima_visita "
                                                            + "from tb_pessoa as pes "
                                                            + "inner join tb_cliente as Clie on "
                                                            + "pes.cpf = Clie.cpf where pes.cpf = ?");
                                                            
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                
                Cliente cli = new Cliente();
                
                cli.setCpf(rs.getString("cpf"));
                cli.setRg(rs.getString("rg"));               
                cli.setNome(rs.getString("nome"));                
                cli.setSenha(rs.getString("senha"));
                cli.setNumero_celular(rs.getString("numero_celular"));
                cli.setEmail(rs.getString("email"));
                
                Calendar data_cadastro_cal = Calendar.getInstance();
                data_cadastro_cal.setTimeInMillis(rs.getDate("data_cadastro").getTime());
                cli.setData_cadastro(data_cadastro_cal);
                
                Calendar data_nascimento_cal = Calendar.getInstance();
                data_nascimento_cal.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                cli.setData_nascimento(data_nascimento_cal);
                
                cli.setCep(rs.getString("cep"));
                cli.setEndereco(rs.getString("endereco"));
                cli.setComplemento(rs.getString("complemento"));
                //f.setTipo(rs.getString("tipo"));
                
                Calendar data_ultima_cal = Calendar.getInstance();
                data_ultima_cal.setTimeInMillis(rs.getDate("data_ultima_visita").getTime());
                cli.setData_ultima_visita(data_ultima_cal);
                
                return cli;
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
                PreparedStatement ps = this.con.prepareStatement("update tb_produto set "
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
                
                ps2.execute();
                
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
        } /*else if(o instanceof Pessoa) {
            
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
            
        }*/ else if(o instanceof Funcionario) {
            
            Funcionario f = (Funcionario) o; // Converter o para o e que eh do tipo Pessoa.
            
            // Descobrir se eh para realiar INSERT ou UPDATE.
            if(f.getData_cadastro() == null) {
             
                // INSERT.
                PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa "
                                                                + "(cpf, rg, nome, senha, numero_celular, "
                                                                + "email, data_cadastro, data_nascimento, "
                                                                + "cep, endereco, complemento, tipo) "
                                                                + "values (?, ?, ?, ?, ?, ?, now(), ?, ?, ?, ?, ?)");
                
                ps.setString(1, f.getCpf());
                ps.setString(2, f.getRg());
                ps.setString(3, f.getNome());
                ps.setString(4, f.getSenha());
                ps.setString(5, f.getNumero_celular());
                ps.setString(6, f.getEmail());
                ps.setDate(7, new java.sql.Date(f.getData_nascimento().getTimeInMillis()));
                ps.setString(8, f.getCep());
                ps.setString(9, f.getEndereco());
                ps.setString(10, f.getComplemento());
                ps.setString(11, "Func");
                //ps.setString(12, "Fun");
                
                ps.execute();
                
                PreparedStatement ps2 = this.con.prepareStatement("insert into tb_funcionario " 
                                                                 + "(cargo, numero_ctps, numero_pis, cpf) values "
                                                                 + "(?, ?, ?, ?)");
                
                ps2.setString(1, f.getCargo().toString());
                ps2.setString(2, f.getNumero_ctps());
                ps2.setString(3, f.getNumero_pis());
                ps2.setString(4, f.getCpf());
                
                /*System.out.println("CPF: " + f.getCpf() + "\n");
                System.out.println("PIS: " + f.getNumero_pis()+ "\n");
                System.out.println("CTPS: " + f.getNumero_ctps() + "\n");
                System.out.println("Cargo: " + f.getCargo().toString() + "\n");*/
                
                ps2.execute();
                
            } else {
                
                // UPDATE.                
                PreparedStatement ps2 = this.con.prepareStatement("update tb_funcionario set "
                                                                + "cargo = ?, "
                                                                + "numero_ctps = ?, "
                                                                + "numero_pis = ? "
                                                                + "where cpf = ?");
                
                ps2.setString(1, f.getCargo().toString());
                ps2.setString(2, f.getNumero_ctps());
                ps2.setString(3, f.getNumero_pis());
                ps2.setString(4, f.getCpf());
                
                ps2.execute(); // Executa o comando.
                
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
                                                                + "tipo = 'Func' "
                                                                + "where cpf = ?");
                
                ps.setString(1, f.getRg());
                ps.setString(2, f.getNome());
                ps.setString(3, f.getSenha());
                ps.setString(4, f.getNumero_celular());
                ps.setString(5, f.getEmail());
                ps.setDate(6, new java.sql.Date(f.getData_nascimento().getTimeInMillis()));
                ps.setString(7, f.getCep());
                ps.setString(8, f.getEndereco());
                ps.setString(9, f.getComplemento());
                //ps.setString(10, f.getTipo());
                ps.setString(10, f.getCpf());
                
                ps.execute(); // Executa o comando.
                
            }
        } else if(o instanceof Fornecedor) {
            
            Fornecedor f = (Fornecedor) o; // Converter o para o e que eh do tipo Pessoa.
            
            // Descobrir se eh para realiar INSERT ou UPDATE.
            if(f.getData_cadastro() == null) {
             
                // INSERT.
                PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa "
                                                                + "(cpf, rg, nome, senha, numero_celular, "
                                                                + "email, data_cadastro, data_nascimento, "
                                                                + "cep, endereco, complemento, tipo) "
                                                                + "values (?, ?, ?, ?, ?, ?, now(), ?, ?, ?, ?, ?)");
                
                ps.setString(1, f.getCpf());
                ps.setString(2, f.getRg());
                ps.setString(3, f.getNome());
                ps.setString(4, f.getSenha());
                ps.setString(5, f.getNumero_celular());
                ps.setString(6, f.getEmail());
                ps.setDate(7, new java.sql.Date(f.getData_nascimento().getTimeInMillis()));
                ps.setString(8, f.getCep());
                ps.setString(9, f.getEndereco());
                ps.setString(10, f.getComplemento());
                ps.setString(11, "Forn");
                //ps.setString(12, "Fun");
                
                ps.execute();
                
                PreparedStatement ps2 = this.con.prepareStatement("insert into tb_fornecedor " 
                                                                 + "(cnpj, ie, cpf) values "
                                                                 + "(?, ?, ?)");
                
                ps2.setString(1, f.getCnpj());
                ps2.setString(2, f.getIe());
                ps2.setString(3, f.getCpf());
                
                /*System.out.println("CPF: " + f.getCpf() + "\n");
                System.out.println("PIS: " + f.getNumero_pis()+ "\n");
                System.out.println("CTPS: " + f.getNumero_ctps() + "\n");
                System.out.println("Cargo: " + f.getCargo().toString() + "\n");*/
                
                ps2.execute();
                
            } else {
                
                // UPDATE.                
                PreparedStatement ps2 = this.con.prepareStatement("update tb_fornecedor set "
                                                                + "cnpj = ?, "
                                                                + "ie = ? "
                                                                + "where cpf = ?");
                
                ps2.setString(1, f.getCnpj());
                ps2.setString(2, f.getIe());
                ps2.setString(3, f.getCpf());
                
                ps2.execute(); // Executa o comando.
                
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
                                                                + "tipo = 'Forn' "
                                                                + "where cpf = ?");
                
                ps.setString(1, f.getRg());
                ps.setString(2, f.getNome());
                ps.setString(3, f.getSenha());
                ps.setString(4, f.getNumero_celular());
                ps.setString(5, f.getEmail());
                ps.setDate(6, new java.sql.Date(f.getData_nascimento().getTimeInMillis()));
                ps.setString(7, f.getCep());
                ps.setString(8, f.getEndereco());
                ps.setString(9, f.getComplemento());
                //ps.setString(10, f.getTipo());
                ps.setString(10, f.getCpf());
                
                ps.execute(); // Executa o comando.
                
            }
        } else if(o instanceof Cliente) {
            
            Cliente cli = (Cliente) o; // Converter o para o e que eh do tipo Pessoa.
            
            // Descobrir se eh para realiar INSERT ou UPDATE.
            if(cli.getData_cadastro() == null) {
             
                // INSERT.
                PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa "
                                                                + "(cpf, rg, nome, senha, numero_celular, "
                                                                + "email, data_cadastro, data_nascimento, "
                                                                + "cep, endereco, complemento, tipo) "
                                                                + "values (?, ?, ?, ?, ?, ?, now(), ?, ?, ?, ?, ?)");
                
                ps.setString(1, cli.getCpf());
                ps.setString(2, cli.getRg());
                ps.setString(3, cli.getNome());
                ps.setString(4, cli.getSenha());
                ps.setString(5, cli.getNumero_celular());
                ps.setString(6, cli.getEmail());
                ps.setDate(7, new java.sql.Date(cli.getData_nascimento().getTimeInMillis()));
                ps.setString(8, cli.getCep());
                ps.setString(9, cli.getEndereco());
                ps.setString(10, cli.getComplemento());
                ps.setString(11, "Clie");
                //ps.setString(12, "Fun");
                
                ps.execute();
                
                PreparedStatement ps2 = this.con.prepareStatement("insert into tb_cliente " 
                                                                 + "(data_ultima_visita, cpf) values "
                                                                 + "(now(), ?)");
                
                ps2.setString(1, cli.getCpf());
                
                /*System.out.println("CPF: " + f.getCpf() + "\n");
                System.out.println("PIS: " + f.getNumero_pis()+ "\n");
                System.out.println("CTPS: " + f.getNumero_ctps() + "\n");
                System.out.println("Cargo: " + f.getCargo().toString() + "\n");*/
                
                ps2.execute();
                
            } else {
                
                // UPDATE.                
                PreparedStatement ps2 = this.con.prepareStatement("update tb_cliente set "
                                                                + "data_ultima_visita = now() "
                                                                + "where cpf = ?");
                
                ps2.setString(1, cli.getCpf());
                
                ps2.execute(); // Executa o comando.
                
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
                                                                + "tipo = 'Clie' "
                                                                + "where cpf = ?");
                
                ps.setString(1, cli.getRg());
                ps.setString(2, cli.getNome());
                ps.setString(3, cli.getSenha());
                ps.setString(4, cli.getNumero_celular());
                ps.setString(5, cli.getEmail());
                ps.setDate(6, new java.sql.Date(cli.getData_nascimento().getTimeInMillis()));
                ps.setString(7, cli.getCep());
                ps.setString(8, cli.getEndereco());
                ps.setString(9, cli.getComplemento());
                //ps.setString(10, f.getTipo());
                ps.setString(10, cli.getCpf());
                
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
        } /*else if(o instanceof Pessoa) {
            
            Pessoa p = (Pessoa) o;
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");
            ps.setString(1, p.getCpf());
            ps.execute();
            
        }*/ else if(o instanceof Funcionario) {
            
            Funcionario f = (Funcionario) o;
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_funcionario where cpf = ?");
            ps.setString(1, f.getCpf());
            ps.execute();
            
            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");
            ps2.setString(1, f.getCpf());
            ps2.execute();
        } else if(o instanceof Fornecedor) {
            
            Fornecedor f = (Fornecedor) o;
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_fornecedor where cpf = ?");
            ps.setString(1, f.getCpf());
            ps.execute();
            
            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");
            ps2.setString(1, f.getCpf());
            ps2.execute();
        } else if(o instanceof Cliente) {
            
            Cliente cli = (Cliente) o;
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_cliente where cpf = ?");
            ps.setString(1, cli.getCpf());
            ps.execute();
            
            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");
            ps2.setString(1, cli.getCpf());
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

            if(rs.getDate("data_cadastro") != null){
                Calendar dtCad = Calendar.getInstance();
                dtCad.setTimeInMillis(rs.getDate("data_cadastro").getTime());                        
                p.setData_cadastro(dtCad);
            }

            Calendar dtU = Calendar.getInstance();
            dtU.setTimeInMillis(rs.getDate("data_nascimento").getTime());
            p.setData_nascimento(dtU);
            
            p.setCep(rs.getString("cep"));
            
            p.setEndereco(rs.getString("endereco"));
            
            p.setComplemento(rs.getString("complemento"));
            
            //p.setTipo(rs.getString("tipo"));

            
            lista.add(p);
        
        }
        
        return lista;
        
    }
    
    @Override
    public List<Funcionario> listFuncionarios() throws Exception {
                
        List<Funcionario> lista = null;
                        
        PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.rg, "
                                                        + "pes.nome, pes.senha, pes.numero_celular, "
                                                        + "pes.email, pes.data_cadastro, "
                                                        + "pes.data_nascimento, pes.cep, pes.endereco, "
                                                        + "pes.complemento, Func.cargo, "
                                                        + "Func.numero_ctps, Func.numero_pis from tb_pessoa "
                                                        + "as pes inner join tb_funcionario as Func on "
                                                        + "pes.cpf = Func.cpf");
        
        ResultSet rs = ps.executeQuery(); // Executa a query.

        lista = new ArrayList();
        while(rs.next()){
            
            Funcionario f = new Funcionario();
            
            f.setCpf(rs.getString("cpf"));  
            f.setRg(rs.getString("rg"));
            f.setNome(rs.getString("nome"));
            f.setSenha(rs.getString("senha"));
            f.setNumero_celular(rs.getString("numero_celular"));
            f.setEmail(rs.getString("email"));

            if(rs.getDate("data_cadastro") != null){
                Calendar dtCad = Calendar.getInstance();
                dtCad.setTimeInMillis(rs.getDate("data_cadastro").getTime());                        
                f.setData_cadastro(dtCad);
            }

            Calendar dtU = Calendar.getInstance();
            dtU.setTimeInMillis(rs.getDate("data_nascimento").getTime());
            f.setData_nascimento(dtU);
            
            f.setCep(rs.getString("cep"));
            f.setEndereco(rs.getString("endereco"));
            f.setComplemento(rs.getString("complemento"));
            //f.setTipo(rs.getString("tipo"));
            f.setCargo(Cargo.getCargo(rs.getString("cargo")));
            f.setNumero_ctps(rs.getString("numero_ctps"));
            f.setNumero_pis(rs.getString("numero_pis"));
            
            lista.add(f);
        
        }
        
        return lista;
        
    }
    
    @Override
    public List<Fornecedor> listFornecedores() throws Exception {
                
        List<Fornecedor> lista = null;
                        
        PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.rg, "
                                                        + "pes.nome, pes.senha, pes.numero_celular, "
                                                        + "pes.email, pes.data_cadastro, "
                                                        + "pes.data_nascimento, pes.cep, pes.endereco, "
                                                        + "pes.complemento, Forn.ie, "
                                                        + "Forn.cnpj from tb_pessoa "
                                                        + "as pes inner join tb_fornecedor as Forn on "
                                                        + "pes.cpf = Forn.cpf");
        
        ResultSet rs = ps.executeQuery(); // Executa a query.

        lista = new ArrayList();
        while(rs.next()){
            
            Fornecedor f = new Fornecedor();
            
            f.setCpf(rs.getString("cpf"));  
            f.setRg(rs.getString("rg"));
            f.setNome(rs.getString("nome"));
            f.setSenha(rs.getString("senha"));
            f.setNumero_celular(rs.getString("numero_celular"));
            f.setEmail(rs.getString("email"));

            Calendar dtCad = Calendar.getInstance();
            dtCad.setTimeInMillis(rs.getDate("data_cadastro").getTime());                        
            f.setData_cadastro(dtCad);

            Calendar dtU = Calendar.getInstance();
            dtU.setTimeInMillis(rs.getDate("data_nascimento").getTime());
            f.setData_nascimento(dtU);
            
            f.setCep(rs.getString("cep"));
            f.setEndereco(rs.getString("endereco"));
            f.setComplemento(rs.getString("complemento"));
            //f.setTipo(rs.getString("tipo"));
            f.setCnpj(rs.getString("cnpj"));
            f.setIe(rs.getString("ie"));
            
            lista.add(f);
        
        }
        
        return lista;
        
    }
    
    @Override
    public List<Cliente> listClientes() throws Exception {
                
        List<Cliente> lista = null;
                        
        PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.rg, "
                                                        + "pes.nome, pes.senha, pes.numero_celular, "
                                                        + "pes.email, pes.data_cadastro, "
                                                        + "pes.data_nascimento, pes.cep, pes.endereco, "
                                                        + "pes.complemento, Clie.data_ultima_visita "
                                                        + "from tb_pessoa "
                                                        + "as pes inner join tb_cliente as Clie on "
                                                        + "pes.cpf = Clie.cpf");
        
        ResultSet rs = ps.executeQuery(); // Executa a query.

        lista = new ArrayList();
        while(rs.next()){
            
            Cliente clie = new Cliente();
            
            clie.setCpf(rs.getString("cpf"));  
            clie.setRg(rs.getString("rg"));
            clie.setNome(rs.getString("nome"));
            clie.setSenha(rs.getString("senha"));
            clie.setNumero_celular(rs.getString("numero_celular"));
            clie.setEmail(rs.getString("email"));

            Calendar dtCad = Calendar.getInstance();
            dtCad.setTimeInMillis(rs.getDate("data_cadastro").getTime());                        
            clie.setData_cadastro(dtCad);

            Calendar dtU = Calendar.getInstance();
            dtU.setTimeInMillis(rs.getDate("data_nascimento").getTime());
            clie.setData_nascimento(dtU);
            
            clie.setCep(rs.getString("cep"));
            clie.setEndereco(rs.getString("endereco"));
            clie.setComplemento(rs.getString("complemento"));
            //f.setTipo(rs.getString("tipo"));
            
            Calendar dtV = Calendar.getInstance();
            dtV.setTimeInMillis(rs.getDate("data_ultima_visita").getTime());
            clie.setData_nascimento(dtV);
            
            lista.add(clie);
        
        }
        
        return lista;
        
    }
    
    @Override
    public Funcionario doLogin(String cpf, String senha) throws Exception {
                
        Funcionario funcionario = null;
        
         PreparedStatement ps = 
            this.con.prepareStatement("select p.cpf, p.senha from tb_pessoa p where p.cpf = ? and p.senha = ? ");
                        
            ps.setString(1, cpf);
            ps.setString(2, senha);
            
            ResultSet rs = ps.executeQuery(); // O ponteiro do ResultSet inicialmente está na linha -1.
            
            if(rs.next()) { // Se a matriz (ResultSet) tem uma linha.

                funcionario = new Funcionario();
                funcionario.setCpf(rs.getString("cpf"));                
            }
        
            ps.close();
            return funcionario;
        
    }
    
}