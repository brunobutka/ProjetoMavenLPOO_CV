
package br.edu.ifsul.cc.lpoo.cv;

import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import br.edu.ifsul.cc.lpoo.cv.gui.JFramePrincipal;
import br.edu.ifsul.cc.lpoo.cv.gui.JMenuBarHome;
import br.edu.ifsul.cc.lpoo.cv.gui.JPanelHome;
import br.edu.ifsul.cc.lpoo.cv.gui.autenticacao.JPanelAutenticacao;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import javax.swing.JOptionPane;

/**
 *
 * @author bruno
 */
public class Controle {
    
    private PersistenciaJDBC conexaoJDBC;
    
    private JFramePrincipal frame; // frame principal da minha aplicação gráfica
    
    private JPanelAutenticacao pnlAutenticacao; // Painel para a autenticação da Pessoa.
    
    private JMenuBarHome menuBar; // Menu principal.
    
    private JPanelHome pnlHome; // Painel de boas vindas (home).
    
        
    //construtor.
    public Controle(){
                        
    }
    
    public boolean conectarBD() throws Exception {

            conexaoJDBC = new PersistenciaJDBC();

            if(conexaoJDBC != null){

                        return conexaoJDBC.conexaoAberta();
            }

            return false;
    }
    
    public void fecharBD(){

        System.out.println("Fechando conexao com o Banco de Dados");
        conexaoJDBC.fecharConexao();

    }
    
    public void initComponents(){
    
        
        //inicia a interface gráfica.
        //"caminho feliz" : passo 5
        
        frame = new JFramePrincipal();
        
        pnlAutenticacao = new JPanelAutenticacao(this);
        
        menuBar = new JMenuBarHome(this);
        
        pnlHome = new JPanelHome(this);
        
        frame.addTela(pnlAutenticacao, "tela_autenticacao"); // Carta 1.
        frame.addTela(pnlHome, "tela_home"); // Carta 2.
        
        frame.showTela("tela_autenticacao"); // Apresenta a carta cujo nome é "tela_autenticacao".
        
        frame.setVisible(true); // torna visível o jframe
                
    }
    
    public void autenticar(String cpf, String senha) {
        
        try {

            Pessoa p =  conexaoJDBC.doLogin(cpf, senha);
            
            if(p != null) {

                JOptionPane.showMessageDialog(pnlAutenticacao, "Pessoa de CPF " + p.getCpf()+ 
                                             " autenticada com sucesso.", "Autenticação", 
                                             JOptionPane.INFORMATION_MESSAGE);

                frame.setJMenuBar(menuBar); // Adiciona o menu de barra no frame.
                frame.showTela("tela_home"); // Muda a tela para o painel de boas vindas (home).

            } else {
                JOptionPane.showMessageDialog(pnlAutenticacao, "Dados inválidos.", "Autenticação", 
                                             JOptionPane.INFORMATION_MESSAGE);
            }

        } catch(Exception e) {
            JOptionPane.showMessageDialog(pnlAutenticacao, "Erro ao executar a autenticação no Banco de Dados.", 
                                         "Autenticação", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void showTela(String nomeTela) {
        frame.showTela(nomeTela);
    }
    
    
}
