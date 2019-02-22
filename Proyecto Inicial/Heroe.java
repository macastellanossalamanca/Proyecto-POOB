import java.util.*;
/**
 * Write a description of class Heroe here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Heroe
{
    // instance variables - replace the example below with your own
    private int posx;
    private int posy;
    private String color;
    private int strength; 

    private Triangle body;
    private Circle head;
    private Triangle life;
    private Building home;
    /**
     * Constructor
     * @param posx Posicion en x del heroe
     * @param posy Posicion en y del heroe
     * @param color color del heroe
     * @param fuerza Fuerza del heroe
     */
    public Heroe(int posx, int posy , String color, int fuerza)
    {
        
        this.posx=0;
        this.posy=0;
        this.color=color;
        strength=100;

        body=new Triangle();
        head=new Circle();
        life=new Triangle();
        body.changeColor(color);
        head.changeColor(color);
        life.changeColor("red");
        
        head.moveHorizontal(-head.getDiameter()/2);
        head.moveVertical(-head.getDiameter()-body.getAltura());
        
        life.moveVertical(-body.getAltura());
        
        body.moveVertical(-body.getAltura());
        home=null;
        move(posx,posy);
        if(fuerza>=0) hit(100-fuerza);
        makeVisible();
    } 

    /**
     * Mueve el heroe
     * @param x movimiento en x
     * @param y movimiento en y
     */
    public void move(double x,double y){
        makeInvisible();
        body.moveVertical(y);
        life.moveVertical(y);
        head.moveVertical(y);
        
        body.moveHorizontal(x);
        life.moveHorizontal(x);
        head.moveHorizontal(x);
        makeVisible();
        posx+=x; 
        posy+=y;
    }
    
    /**
     * Hace el heroe visible
     */
    public void makeVisible(){
        body.makeVisible();
        head.makeVisible();
        life.makeVisible();
    }
    
    /**
     * Hace el heroe invisible
     */
    
    public void makeInvisible(){
        body.makeInvisible();
        head.makeInvisible();
        life.makeInvisible();
    }
     
    /**
     * Le hace daño al heroe
     * @param golpe Daño que se le inflingirá
     */
    public void hit(int golpe){
        if(strength>golpe){
            int w=life.getAncho()*(strength-golpe)/strength;
            int h=life.getAltura()*(strength-golpe)/strength;
            //System.out.printf("%d %d\n", life.getAltura(),h);
            life.moveVertical((life.getAltura()-h)*2/3);
            life.changeSize(h,w);
        
            strength-=golpe;
        } else{
            life.changeSize(0,0);
        }
    }
    
    /**
     * Retorna el color del heroe
     * @param Color del heroe
     */
    public String getColor(){
        return color;
    }
    
    /**
     * Dice si un heroe choca contra un edificio dado
     * @param target Edifico objetivo
     * @param velocity Velocidad de disparo
     * @param angle Angulo de disparo
     * @return Si el heroe choca contra el edifico(suponiendo que no hay obstaculos en la trayectoria)
     */
    public boolean choca(Building target, int velocity, int angle){
        angle = angle%360 + (angle%360<0?360:0);
        double x=target.getX()-posx;
        double theta=Math.toRadians(angle);
        double y;
        int k= home==null?target.getHeight():target.getHeight()-home.getHeight();
        boolean ans=false;
        if(angle == 90 || angle ==270){ 
            ans=false;
        } else if((270<angle && angle< 360) || (0<= angle && angle<90)){
            y=x*Math.tan(theta)-9.8*Math.pow(x,2)/(2*Math.pow(velocity,2)*Math.pow(Math.cos(theta),2));
            ans=(-home.getHeight()<=y && y<k);
        } else if(90<angle && angle< 270 ){
            x=-posx+target.getX()+target.getWidth();
            y=x*Math.tan(theta)-9.8*Math.pow(x,2)/(2*Math.pow(velocity,2)*Math.pow(Math.cos(theta),2));
            ans=(-home.getHeight()<=y && y<k);
        }
        return ans && home!=target;     
    } 
            
            
    /**
     * Dice si un heroe aterriza en un edificio dado
     * @param target Edifico objetivo
     * @param velocity Velocidad de disparo
     * @param angle Angulo de disparo
     * @return Si el heroe aterriza en el edifico (suponiendo que no hay obstáculos en la trayectoria)
    */
    public boolean aterriza(Building target, int velocity, int angle){
        angle = angle%360 + (angle%360<0?360:0);
        double theta=Math.toRadians(angle);
        double a=9.8/(2*Math.pow(velocity,2)*Math.pow(Math.cos(theta),2));
        double b=-Math.tan(theta);
        double c= home==null?target.getHeight():target.getHeight()-home.getHeight();
        boolean ans=false;
        double cal;  
        if(Math.pow(b,2)-4*a*c>=0){
            if(angle == 90 || angle ==270){
                ans=true;
            } else if((270<angle && angle< 360) || (0<= angle && angle<90)){
                cal=(-b+Math.sqrt(Math.pow(b,2)-4*a*c))/(2*a);
                ans=target.getX()-posx<=cal && cal<=target.getX()+target.getWidth()-posx;
            } else if(90<angle && angle< 270 ){
                cal=(-b-Math.sqrt(Math.pow(b,2)-4*a*c))/(2*a);
                ans=target.getX()-posx<=cal && cal<=-posx+target.getX()+target.getWidth();
            }
        }
        return ans && !choca(target,velocity,angle);
    }
    /**
     * Retorna la posicion en x del heroe       
     * @return Posición en x del héroe
     */
    public int getX(){
        return posx;
    }
    
    /**
     * Retorna la posicion en y del heroe
     * @return Posición en y del héroe
    */
    public int getY(){
        return posy;
    }
    
    /**
     * Retorna la fuerza del héroe
     * @return Fuerza del héroe
     */
    public int getStrength(){
        return strength;
    }
    
    /**
     * Modifica el edificio casa
     * @param n Nuevo edificio casa
     */
    public void setCasa(Building n){
        home = n;
    }
    
    /**
     * Elimina la casa para el salto
     */
    public void mudar(){
        home=null;
    }
    
    /**
     * Saca al héroe de la simulación
     */
    public void getOut(){
        home.byeHeroe();
        makeInvisible();
    }
    
    /**
     * Retorna la casa del héroe
     * @param Casa del héroe
     */
    public Building getCasa(){
        return home;
    }
    
    /**
     * Tiempo que le toma al héroe en alcanzar los limites de la simulación en un salto
     * @param velocity Velocidad del salto
     * @param angle Ángulo del salto
     * @param heigth Altura de la ciudad
     * @param width Ancho de la simulación
     * @return Tiempo en alcanzar los limites de la ciudad
     */
    public double timeToDie(int velocity, int angle, int heigth, int width){
        double t;
        double theta = Math.toRadians(angle);
        angle = angle%360 +(angle%360<0?360:0);
        t = (90<angle && angle< 270?-posx:width-posx)/(velocity*Math.cos(theta));
        double a=0.5*9.8,b=-velocity*Math.sin(theta),c=-home.getHeight();
        if(b*b-4*a*c>=0){
            if( (-b+Math.sqrt(b*b-4*a*c))/(2*a) >0 ){
                t = Math.min(t,(-b+Math.sqrt(b*b-4*a*c))/(2*a) );
            }
        }
        c=heigth-home.getHeight();
        if(b*b-4*a*c>=0){
            if( (-b-Math.sqrt(b*b-4*a*c))/(2*a) >0 ){
                t = Math.min(t,(-b-Math.sqrt(b*b-4*a*c))/(2*a) );
            }
        } 
        return t;
    }
    
    /**
     * Tiempo que le toma al héroe en alcanzar un edificio
     * @param target Edificio Objetivo
     * @param angle Ángulo del salto
     * @param velocity Velocidad del salto
     * @return Tiempo en alcanzar un edificio dado(si se estrella o no)
     */
    public double timeToLand(Building target, int angle, int velocity){
        double t;
        double theta = Math.toRadians(angle);
        angle = angle%360 +(angle%360<0?360:0);
        double a=0.5*9.8,b=-velocity*Math.sin(theta),c=target.getHeight()-home.getHeight();
        if(choca(target,velocity,angle)){
            t = (90<angle && angle< 270?target.getX()+target.getWidth()-posx:target.getX()-posx)/(velocity*Math.cos(theta));
        }
        else{ 
            t = (-b+Math.sqrt(b*b-4*a*c))/(2*a);
        }
        return t;
    }
    
    
   
}





