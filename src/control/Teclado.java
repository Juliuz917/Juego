package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Teclado implements KeyListener{
    
    private final static int numeroTeclas = 120;
    private final boolean[] teclas = new boolean [numeroTeclas];
    
    
    //Declaracion de controles
    public boolean arriba;
    public boolean abajo;
    public boolean izquierda;
    public boolean derecha;
    
    //Declaracion de las teclas para controles    
    public void actualizar(){
        arriba = teclas[KeyEvent.VK_W];
        abajo = teclas[KeyEvent.VK_S];
        izquierda = teclas[KeyEvent.VK_A];
        derecha = teclas[KeyEvent.VK_D];
    }
    
    
    
    //Metodo de lectura de la tecla pulsada
    public void keyPressed(KeyEvent e) {
        teclas[e.getKeyCode()] = true;
    }
    
    
    //Metodo de lectura de tecla soltada
    public void keyReleased(KeyEvent e) {
           teclas[e.getKeyCode()] = false;
    }
    
    //Metodo de lectura de la tecla pulsada y soltada
     public void keyTyped(KeyEvent e) {     
    }
    
    
    
}
