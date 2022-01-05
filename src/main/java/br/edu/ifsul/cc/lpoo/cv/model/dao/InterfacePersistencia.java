/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Venda;
import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import java.util.List;

/**
 *
 * @author bruno
 */
public interface InterfacePersistencia {
            
    public Boolean conexaoAberta();
    
    public void fecharConexao();
    
    /*public Object find(Class c, Object id) throws Exception;//select.
    
    public void persist(Object o) throws Exception;//insert ou update.
    
    public void remover(Object o) throws Exception;//delete.
    
    public List<Endereco> listEnderecos() throws Exception;
    
    public List<Patente> listPatentes() throws Exception;*/
        
}