
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.Venda;
import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author bruno
 */
public class PersistenciaJPA implements InterfacePersistencia {
    
    public EntityManagerFactory factory;    
    public EntityManager entity;            
    
    public PersistenciaJPA(){
        factory = Persistence.createEntityManagerFactory("pu_db_cv");
        entity = factory.createEntityManager();
    }


    @Override
    public Boolean conexaoAberta() {
        return entity.isOpen();   
    }

    @Override
    public void fecharConexao() {   
        entity.close();        
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void persist(Object o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remover(Object o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Venda> listVendas() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Produto> listProdutos() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     @Override
    public List<Pessoa> listPessoas() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Pessoa doLogin(String cpf, String senha) throws Exception {
        
        List<Pessoa> list = entity.createNamedQuery("Pessoa.login").setParameter("paramN", cpf).setParameter("paramS", senha).getResultList();
        if(list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
        
    }
   
}