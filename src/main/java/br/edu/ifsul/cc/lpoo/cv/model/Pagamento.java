
package br.edu.ifsul.cc.lpoo.cv.model;

/**
 *
 * @author bruno
 */
public enum Pagamento {
    CARTAO_DEBITO, CARTAO_CREDITO, DINHEIRO, PIX, BOLETO;
    
    public static Pagamento getPagamento(String nameEnum2){
        if(nameEnum2.equals(Pagamento.CARTAO_DEBITO.toString()))
            
            return Pagamento.CARTAO_DEBITO;
        
        else if(nameEnum2.equals(Pagamento.CARTAO_CREDITO.toString())){
            
            return Pagamento.CARTAO_CREDITO;
            
        }else if(nameEnum2.equals(Pagamento.DINHEIRO.toString())){
            
            return Pagamento.DINHEIRO;
            
        }else if(nameEnum2.equals(Pagamento.PIX.toString())){
            
            return Pagamento.PIX;
            
        }else if(nameEnum2.equals(Pagamento.BOLETO.toString())){
            
            return Pagamento.BOLETO;
            
        }else{
            return null;
        }
    
    }
}
