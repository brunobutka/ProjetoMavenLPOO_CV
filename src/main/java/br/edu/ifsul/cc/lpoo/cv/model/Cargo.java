
package br.edu.ifsul.cc.lpoo.cv.model;

/**
 *
 * @author bruno
 */
public enum Cargo {
    ADESTRADOR, ATENDENTE, AUXILIAR_VETERINARIO;
    
     public static Cargo getCargo(String nameEnum) {
        if(nameEnum.equals(Cargo.ADESTRADOR.toString()))
            
            return Cargo.ADESTRADOR;
        
        else if(nameEnum.equals(Cargo.ATENDENTE.toString())) {
            
            return Cargo.ATENDENTE;
            
        }else if(nameEnum.equals(Cargo.AUXILIAR_VETERINARIO.toString())) {
            
            return Cargo.AUXILIAR_VETERINARIO;
            
        } else {
            return null;
        }
    
    }
}
