
/**
 * Write a description of class Building here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
     */
    public int getX()
    {
        return x;
    }
    
    
    /**
     * Retorna el ancho del edificio
     */
    public int getWidth()
    {
        return width;
    }
    
    
    /**
     * Retorna la altura del edificio
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
     */
    public boolean isDamaged(){
        return isDamaged;
    }
    
    public void destroy(int delta){
        if(delta<=height){
            wall.changeSize(-delta+height,width);
            wall.moveVertical(delta);
            height-=delta;
            isDamaged=true;
        }
    }
    
    
    public int getHardness(){
        return hardness;
    }
    
    
    public boolean encimaDe(int xp, int widthp){
        return xp+widthp<=x || x+width<=xp;
    }
    
    public boolean hasHeroe(){
        return heroe!=null;
    }
    
    public void setHeroe(Heroe a){
        heroe = a;
    }
    
    public void byeHeroe(){
        heroe=null;
    }
    
}
