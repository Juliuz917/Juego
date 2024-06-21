package graficos;

public class Pantalla {
    private final int ancho;
    private final int alto;
    
    public final int[] pixeles;
    
    //TEMPORAL
    private final static int LADO_SPRITE = 32;
    private final static int MASCARA_SPRITE = LADO_SPRITE - 1;
    //fin temporal        
    
    //Constructor de pantalla
    public Pantalla(final int ancho, final int alto) {
        this.ancho = ancho;
        this.alto = alto;

        pixeles = new int[ancho * alto];
    }
    
    //Metodo para limpiar todos los graficos de la pantalla 
    public void limpiar(){
        for(int i = 0; i < pixeles.length; i++){
            pixeles[i] = 0;
        }
    }

    //Metodo para dibujar los graficos de la pantalla
    public void mostrar(final int compensacionX, final int compensacionY){
        for (int y = 0; y < alto; y++){
            int posicionY = y + compensacionY;
            if(posicionY < 0 || posicionY >= alto){
                continue;
            }

            for(int x = 0; x < ancho; x++){
                int posicionX = x + compensacionX;
                 if(posicionX < 0 || posicionX >= ancho){
                continue;
                }
                 
                 //TEMPORAL
                pixeles[posicionX + posicionY * ancho] = Sprite.cesped.pixeles[(
                        x & MASCARA_SPRITE) + (y & MASCARA_SPRITE) * LADO_SPRITE];
                 
            }
        }
    }
}
