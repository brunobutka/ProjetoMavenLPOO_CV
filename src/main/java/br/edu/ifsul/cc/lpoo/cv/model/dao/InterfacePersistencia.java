
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Fornecedor;
import br.edu.ifsul.cc.lpoo.cv.model.Venda;
import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import java.util.List;

/**
 *
 * @author bruno
 */
public interface InterfacePersistencia {
            
    public Boolean conexaoAberta();
    
    public void fecharConexao();
    
    public Object find(Class c, Object id) throws Exception;//select.
    
    public void persist(Object o) throws Exception;//insert ou update.
    
    public void remover(Object o) throws Exception;//delete.
    
    public List<Venda> listVendas() throws Exception;
    
    public List<Produto> listProdutos() throws Exception;
    
    public List<Funcionario> listFuncionarios() throws Exception;
    
    public List<Fornecedor> listFornecedores() throws Exception;
    
    public List<Cliente> listClientes() throws Exception;
    
    public Funcionario doLogin(String nome, String senha) throws Exception;
        
}