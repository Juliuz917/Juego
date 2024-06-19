package Juego;

import control.Teclado;
import graficos.Pantalla;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

public class Juego extends Canvas implements Runnable {
    
    private static final long serialVersionUID = 1L;
    
    private static final int ANCHO = 800;
    private static final int ALTO = 600;
    
    private static volatile boolean enFuncionamiento = false;
    
    private static final String NOMBRE = "Juego";
    
    private static int aps = 0;
    private static int fps = 0;
    
    private static int x = 0;
    private static int y = 0;
    
    private static JFrame ventana;
    private static Thread thread;
    private static Teclado teclado;
    private static Pantalla pantalla;
    
    //manera para que el array de pixeles pueda manipular la imagen, los numeros 
    //y toda la informacion presentada en pantalla
    private static BufferedImage imagen = new BufferedImage(
            ANCHO, ALTO, BufferedImage.TYPE_INT_RGB);
    private static int [] pixeles = ((DataBufferInt) imagen.getRaster().getDataBuffer()).getData();
    
    //CONSTRUCTOR PRINCIPAL DEL JUEGO
    public Juego(){
        setPreferredSize(new Dimension(ANCHO,ALTO));
        
        pantalla = new Pantalla(ANCHO, ALTO);
        
        teclado = new Teclado();
        addKeyListener(teclado);
        
        ventana = new JFrame(NOMBRE);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLayout(new BorderLayout());
        ventana.add(this,BorderLayout.CENTER);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        
    }
    public static void main(String[] args) {
        Juego juego = new Juego();
        juego.iniciar();
        
    }

    private synchronized void iniciar (){
        enFuncionamiento = true;
        
        thread = new Thread (this, "Graficos");
        thread.start();
    }
   
    private synchronized void detener(){
        enFuncionamiento = false;
        
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void actualizar(){
        teclado.actualizar();
        
        if (teclado.arriba){
            System.out.println("arriba");
        }
         if (teclado.abajo){
            System.out.println("abajo");
        }
          if (teclado.derecha){
            System.out.println("derecha");
        }
           if (teclado.izquierda){
            System.out.println("izquierda");
        }
        
        aps++;
    }
    
    private void mostrar(){
        BufferStrategy estrategia = getBufferStrategy();
        
        if (estrategia == null){
            createBufferStrategy(3);
            return;
        }
        //Metodos del Buffer
        pantalla.limpiar();
        pantalla.mostrar(x,y);
        
        //Copiar los arrays
        System.arraycopy(pantalla.pixeles, 0, pixeles, 0, pixeles.length);
        
        
        /* Metodo opcional de prueba
        for(int i = 0; i < pixeles.length; i++){
            pixeles[i]= pantalla.pixeles[i];
        }
        */
        
        //Dibujar Graficos en pantalla
        Graphics g = estrategia.getDrawGraphics();
        
        g.drawImage(imagen , 0, 0, getWidth(), getHeight(), null);
        //destruir memoria de la variable
        g.dispose();       
        //Para que la imagen se vea en pantalla
        estrategia.show();
        
        
        
        fps++;
    }
    
    
    
   
    public void run() {
        System.out.println("El Thread 2 se esta ejecutando con exito!");
        System.nanoTime();
        final int NS_POR_SEGUNDO = 1000000000;
        final byte APS_OBJETIVO = 60;
        final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;
        
        long referenciaActualizacion = System.nanoTime();
        long referenciaContador = System.nanoTime();
        
        double tiempoTranscurrido;
        double delta = 0;
        
        //Metodo para que el sistema haga focus en la pantalla 
        requestFocus();
        
        //BUCLE PRINCIPAL DEL JUEGO        
        while (enFuncionamiento){
            final long inicioBucle = System.nanoTime();
            
            tiempoTranscurrido = inicioBucle - referenciaActualizacion;
            referenciaActualizacion = inicioBucle;
            
            delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;
            
            while (delta >=1){
                actualizar();
                delta--;
            }
            
            
            mostrar();
        
            if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO){
                ventana.setTitle(NOMBRE+ "||| APS: " + aps + " || FPS: " + fps);
                aps = 0;
                fps = 0;
                referenciaContador = System.nanoTime();
            }
        }
    }
}
