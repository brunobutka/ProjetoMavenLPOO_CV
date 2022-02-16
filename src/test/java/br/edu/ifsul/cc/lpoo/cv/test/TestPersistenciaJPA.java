
package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJPA;
import java.util.Calendar;
import org.junit.Test;

/**
 *
 * @author bruno
 */
public class TestPersistenciaJPA {
    
    @Test
    public void testConexaoGeracaoTabelas(){
        
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JPA");
            
            persistencia.fecharConexao();
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }
        
    }
    
    @Test
    public void testGeracaoPessoaLogin() throws Exception {
        
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()) {
            System.out.println("\nAbriu a conexão com o BD via JPA.\n");
            
            Pessoa p = persistencia.doLogin("12345678912", "1234");
            // só pra ver se ha cadastro
            if(p == null) {
                p = new Pessoa();
                
                p.setCpf("12345678912");
                p.setSenha("1234");
                
                p.setRg("4563219870");
                
                p.setNome("Thomas");
                
                p.setNumero_celular("54996358745");
                
                p.setEmail("thomas@gmail.com");
                
                Calendar data_convertida_1 = Calendar.getInstance();
                data_convertida_1.set(Calendar.YEAR, 2022);
                data_convertida_1.set(Calendar.MONTH, 02 + 1);
                data_convertida_1.set(Calendar.DAY_OF_MONTH, 03);
                p.setData_cadastro(data_convertida_1);
                
                Calendar data_convertida_2 = Calendar.getInstance();
                data_convertida_2.set(Calendar.YEAR, 2001);
                data_convertida_2.set(Calendar.MONTH, 6 + 1);
                data_convertida_2.set(Calendar.DAY_OF_MONTH, 22);
                p.setData_nascimento(data_convertida_2);
                
                p.setCep("36985241");
                
                p.setEndereco("Rua Luiz II");
                
                p.setComplemento("Nenhum");
                
                p.setTipo("M");
                
                persistencia.persist(p);
                System.out.println("Cadastrou uma nova pessoa.");
            } else {
                System.out.println("Encontrou uma pessoa cadastrada.");
            }
            
            persistencia.fecharConexao();
            
        } else {
            System.out.println("\nNão abriu a conexão com o BD via JPA.\n");
        }
        
    }
    
}
