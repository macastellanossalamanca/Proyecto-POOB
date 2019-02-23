
import java.util.*;
/**
 * Clase que modela un edificio para la ciudad.
 * 
 * @author  (Miguel Castellanos - Alejandro Vasquez)
 * @version (2.0)
 */
public class Building
{
    // instance variables - replace the example below with your own
    private Rectangle wall;
    private int x;
    private int height;
    private int width;
    private boolean isDamaged;
    private int hardness;
    private Heroe heroe;

    /**
     * Constructor - Representación de un edificio
     * @param x posicion en x del edificio
     * @param height Altura del edificio
     * @param width Ancho del edificio
     * @param hardness Resistencia del edifico
     * @param help Alto de pantalla para visualización del edificio
     */
    public Building(int x, int height, int width, int hardness, int help)
    {
        // initialise instance variables
        this.x=x;
        this.height=height;
        this.width=width;
        this.hardness=hardness;
        isDamaged=false;
        wall=new Rectangle();
        wall.changeSize(height,width);
        wall.moveHorizontal(x);
        wall.moveVertical(help-height);
        makeVisible();
        heroe=null;
        
    }

    /**
     * Retorna la posicion en x del edificio
     * @return Posición en x del edificio
     */
    public int getX()
    {
        return x;
    }
    
    
    /**
     * Retorna el ancho del edificio
     * @return Ancho del edificio
     */
    public int getWidth()
    {
        return width;
    }
    
    
    /**
     * Retorna la altura del edificio
     * @return Altura del edifico
     */
    public int getHeight(){
    
        return height; 
    }
    
    /**
     * Hace el edifico invisible
     */
    public void makeInvisible(){
        wall.makeInvisible();
    }
    /**
     * Hace el edificio visible
     */
    public void makeVisible(){
        wall.makeVisible();
    }
    
    /**
     * Cambia el edificio del color
     */
    public void changeColor(String c){
        wall.changeColor(c);
    }
    
    /**
     * Retorna si el edifico está dañado
     * @return Si el edificio está dañado
     */
    public boolean isDamaged(){
        return isDamaged;
    }
    
    /**
     * Destruye la parte superior del edificio
     * @param delta Cantidad a destruir
     */
    public void destroy(int delta){
        if(delta<=height){
            wall.changeSize(-delta+height,width);
            wall.moveVertical(delta);
            height-=delta;
            isDamaged=true;
        }
    }
    
    /**
     * Retorna la fuerza de un edificio
     * @return Fuerza del edificio
     */
    public int getHardness(){
        return hardness;
    }
    
    /**
     * Dice si un edificio no está asolapado con otro
     * @param xp Posición en x del segundo edificio
     * @param widthp Ancho del segundo edificio
     * @return Si el edificio no se asolapa con el segundo edificio
     */
    public boolean encimaDe(int xp, int widthp){
        return xp+widthp<=x || x+width<=xp;
    }
    
    /**
     * Dice si el edificio tiene un héroe en él
     * @return Si el edificio tiene un héroe en él
     */
    public boolean hasHeroe(){
        return heroe!=null;
    }
    
    /**
     * Modifica el héroe
     * @param a Nuevo héroe
     */
    public void setHeroe(Heroe a){
        heroe = a;
    }
    
    /**
     * Elimina el héroe
     */
    public void byeHeroe(){
        heroe=null;
    }
    
    
    /**
     * Retorna la fuerza del héroe en el edificio
     * @return Fuerza del héroe en el edificio
     */
    public int getHeroeStrength(){
        return (heroe!=null?heroe.getStrength():-1);
    }
}
