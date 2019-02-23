import java.util.*;
import java.awt.*;
import javax.swing.JOptionPane;
import java.lang.*;
/**
 * Una ciudad construida con edificios y con heroes en ella, y en muchos edificios
 * 
 * @author  (Miguel Castellanos - Alejandro Vasquez)
 * @version (2.1)
 */
public class CityOfHeroes  
{ 
    // instance variables - replace the example below with your own
    private boolean ok;
    private ArrayList<Building> towers=new ArrayList<Building>(); 
    private int width;
    private int height;
    private HashMap<String,Heroe> people;
    private ArrayList<String> deads;
    public static boolean isVisible=false;
    private String ultimaAccion = "";
    private ArrayList ultObj = new ArrayList();
                   
    /**
     * Constructor
     * @param width Indica el ancho de la ciudad
     * @param height Indica el alto maximo de los edificios
     */
    public CityOfHeroes(int width, int height)
    {  
        // initialise instance variables
        this.width=width;
        this.height=height; 
        Canvas z =Canvas.getCanvas(width,height);
        people=new HashMap<String,Heroe>();
        deads=new ArrayList<String>();
        ok=true;
    }
                   
    /**
     * Adiciona un nuevo edificio a la ciudad
     * @param x La posición en x del edificio
     * @param width El ancho del edificio
     * @param height El alto del edificio
     * @parem hardness La dureza del edificio
     */
    public void addBuilding(int x, int width, int height, int hardness)
    {
        boolean posible = x+width<=this.width && x>=0 && height<=this.height && width>0 && height>0 && hardness>0 && hardness<=100;
        int pos=-1;
        int max=2147483647;
        for(int i=0;i<towers.size() && posible;i++){
            posible=posible && towers.get(i).encimaDe(x,width);
            if(towers.get(i).getX()+towers.get(i).getWidth()<=x && x-towers.get(i).getX()-towers.get(i).getWidth()<max){
                max=x-towers.get(i).getX()-towers.get(i).getWidth();
                pos=i;
            }
        }
        if(posible){
            towers.add(pos+1,new Building(x,height,width,hardness,this.height));
            if(Canvas.actual == Canvas.colores.size()-1) {
                Canvas.actual = 0;
            }
            towers.get(pos+1).changeColor(Canvas.colores.get(Canvas.actual));
            Canvas.actual +=1; 
            ultimaAccion = "addBuilding";
            ultObj = new ArrayList();
            ultObj.add(x);
            ultObj.add(height);
            ultObj.add(width);
            ultObj.add(hardness);
        } else if(!posible){
            if(isVisible) alerta("No es posible adicionar ese edificio");
        }
        ok = posible;
        
    }
    
    /**
     * Elimina un edificio de la ciudad
     * @param position Representa la posicion en la que quiero que el edificio sea borrado. Será borrado si x<=position<x+width
     */    
    public void removeBuilding(int position){
        int obj=-1;
        for(int i=0;i<towers.size() && obj==-1;i++){
            if(towers.get(i).getX()<=position && position<towers.get(i).getX()+towers.get(i).getWidth()) obj=i;
        }
        if(obj!=-1){
            if(!towers.get(obj).hasHeroe()){
                Building pibote=towers.remove(obj);
                ultObj = new ArrayList();
                ultObj.add(pibote.getX());
                ultObj.add(pibote.getHeight());
                ultObj.add(pibote.getWidth());
                ultObj.add(pibote.getHardness());
                pibote.makeInvisible();
                pibote=null;
                ultimaAccion = "removeBuilding";
            } 
            else if(towers.get(obj).hasHeroe()){
                if(isVisible) alerta("No es posible eliminar ese edificio");
            }
        }
        ok = obj!=-1 && towers.get(obj).hasHeroe();
    }
                    
    /**
     * Añade un nuevo heroe a la simulación
     * @param color Color del nuevo heroe
     * @param hidingBuilding Guarida del heroe
     * @param strength Fuerza del heroe
     */
    public void addHeroe(String color, int hidingBuilding, int strength){
        boolean posible = 1<=hidingBuilding && hidingBuilding<=towers.size() && strength>0 && strength<=100 
            && !towers.get(hidingBuilding-1).hasHeroe() && !deads.contains(color) && !people.containsKey(color) && Canvas.colores.contains(color);
        if(posible){
            Building casa = towers.get(hidingBuilding-1);
            Heroe nuevo = new Heroe(casa.getX()+casa.getWidth()/2,height-casa.getHeight(),color,strength);
            casa.setHeroe(nuevo);
            nuevo.setCasa(casa);
            people.put(color,nuevo);
            ultObj = new ArrayList();
            ultObj.add(towers.indexOf(casa)+1);
            ultObj.add(color);
            ultObj.add(strength);
            ultimaAccion = "addHero";
            
        } else {
            alerta("No es posible adicionar el heroe");
        }
        ok = posible;
    } 
                
    /**
     * Retira un heroe de la simulación
     * @param color Color del heroe a retirar
     */
    public void removeHeroe(String color){
        Heroe pib = people.remove(color);
        ok = pib!=null;
        if(pib!=null){
            ultimaAccion = "removeHero";
            ultObj = new ArrayList();
            ultObj.add(towers.indexOf(pib.getCasa())+1);
            ultObj.add(pib.getColor());
            ultObj.add(pib.getStrength());
            pib.getOut();
        } 
    }                
    /**
     * Hace saltar un heroe
     * @param color Color de heroe a saltar
     * @param angle Angulo de salto
     * @param velocity Velocidad de salto
     * @param slow Animación lenta
    */
    public void jump(String color, int velocity, int angle, boolean slow){
        Heroe casa = people.get(color);
        if(casa!=null){
            Building target = seekTarget(casa,velocity,angle);
            double t1,t2;
            if(target==null){
                parabola(casa, casa.timeToDie(velocity, angle, height,width),velocity, angle, (slow?0.05:0.08));
                deads.add(casa.getColor());
                removeHeroe(casa.getColor());
            } else{
                t1 = casa.timeToLand(target,angle, velocity);
                t2 = casa.timeToDie(velocity, angle, height,width);
                parabola(casa, Math.min(t1,t2),velocity, angle, (slow?0.05:0.08));
                casa.getCasa().byeHeroe();
                if(!casa.aterriza(target,velocity,angle) && casa.choca(target,velocity,angle)){
                    if(target.getHardness()>=casa.getStrength()){
                        casa.hit(target.getHardness());
                        deads.add(casa.getColor());
                        removeHeroe(casa.getColor());   
                    } else{ 
                        casa.getCasa().byeHeroe();
                        target.setHeroe(casa);
                        target.destroy(target.getHeight()-height+casa.getY());
                        casa.hit(target.getHardness());
                        casa.setCasa(target);
                        casa.move(target.getX()+target.getWidth()/2-casa.getX(),height-target.getHeight()-casa.getY());
                    }
                } else { 
                    target.setHeroe(casa);
                    casa.setCasa(target);
                    casa.move(target.getX()+target.getWidth()/2-casa.getX(),height-target.getHeight()-casa.getY());
                }
            }
        }
        ultimaAccion = "jump";
        ultObj = new ArrayList();
        ultObj.add(color);
        ultObj.add(velocity);
        ultObj.add(angle);
    }
                
    /**
     * Vuelve la ciudad invisible
     */
            
    public void makeInvisible(){
        Canvas.getCanvas(width, height).setVisible(false);
        isVisible = false;
    }
    
    /**
     * Vuelve la ciudad visible
     */
    
    public void makeVisible(){
        Canvas.getCanvas(width, height).setVisible(true);
        isVisible = true;
    }
    
    /**
     * Retorna si la estructura está dañada
     * @param x Ubicación de la estructura
     * @return Si el edifico está dañado
     */
    public boolean isDamaged(int x){
        int obj=-1;
        boolean ans=false;
        for(int i=0;i<towers.size() && obj==-1;i++){
            if(towers.get(i).getX()<=x && x<towers.get(i).getX()+towers.get(i).getWidth()) obj=i;
        }
        if(obj!=-1){
            ans=towers.get(obj).isDamaged();
        }
        return ans;
    }
    
    
    /**
     * Retorna la fuerza de un héroe
     * @param color Color del heroe que queremos conocer
     * @return Fuerza del héroe
     */
    public int strength(String color){
        Heroe pib = people.get(color);
        if(pib==null) alerta("Heroe no registrado");
        return (pib!=null?pib.getStrength():-1);
    }
    
    
    /**
     * Retorna la lista de heroes muertos
     */
    public String[] deads(){
        return deads.toArray(new String[deads.size()]);
    }

        
        
        
    /**
     * Finaliza la simulacion
     */
    
    
    public void finish(){
        for(Building a: towers){
            a.makeInvisible();
        }
        towers= new ArrayList<Building>();
        for(Heroe a: people.values()){
            a.makeInvisible();
        }
        people= new HashMap<String,Heroe>();
        ok=true;
        deads= new ArrayList<String>();
        makeInvisible();
    }
    
    /**
     * Da un sonido y mensaje de alerta con un mensaje dado
     * @param a Mensaje a mostrar en pantalla
     */
    private static void alerta(String a){
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null,a,"Alerta", JOptionPane.ERROR_MESSAGE);
    }
    
    
    
    /**
     * Dado un heroe, busca el edificio en el que chocará o aterrizará, sin tener en cuenta si sale o no del escenario
     * @param casa El heroe que piensa saltar
     * @param velocity Velocidad de salto
     * @param angle Ángulo de salto
     * @return Edificio objetivo
     */
    private Building seekTarget(Heroe casa, int velocity, int angle){
        angle = angle%360 +(angle%360<0?360:0);
        int pibote = towers.indexOf(casa.getCasa());
        Building target=null;
        boolean golpe=false,bien=false;
        if((270<angle && angle< 360) || (0<= angle && angle<=90)){
            for(int i=pibote;i<towers.size() && golpe==bien;i++){
                golpe=casa.choca(towers.get(i),velocity,angle);
                bien=casa.aterriza(towers.get(i),velocity,angle);
                if(golpe!=bien) target=towers.get(i); 
            }
        } else if(90<angle && angle<= 270){
            for(int i=pibote;i>=0 && golpe==bien;i--){
                golpe=casa.choca(towers.get(i),velocity,angle);
                bien=casa.aterriza(towers.get(i),velocity,angle);
                if(golpe!=bien) target=towers.get(i);
            }
        }
        return target;
    }
    /**
     * Realiza una parábola de un héroe con unos datos dados
     * @param pib Heroe que va a saltar
     * @param velocity Velocidad de salto
     * @param angle Angulo de salto
     * @param delta Tiempo entre movimientos según ecuaciones
     */
    private void parabola(Heroe pib, double t, int velocity,int angle, double delta){
        double theta = Math.toRadians(angle);
        double i=delta;
        double vel=(double) velocity;
        double lastx=0.0,lasty=0.0;
        
        while(i<t){
            pib.move(Math.round(vel*Math.cos(theta)*i-lastx),Math.round(lasty-Math.sin(theta)*velocity*i+0.5*9.8*i*i));

            lastx=vel*Math.cos(theta)*i;
            
            lasty=Math.sin(theta)*velocity*i-0.5*9.8*i*i;
            i+=delta;
             
            
        }
    }
    
    /**
     * Retorna si la ultima operación se pudo realizar
     * @param Si la última operación se pudo realizar
     */
    
    public boolean ok(){
        return ok;
    }

    /**
     * Dice si un salto es seguro
     * @param heroe Heroe de salto
     * @param velocity Velocidad de salto
     * @param angle Ángulo de salto
     * @return Si el salto es seguro(aterriza en un edificio)
     */
    public boolean isSafeJump(String heroe, int velocity, int angle){
        boolean ans = false;
        Heroe pib = people.get(heroe);
        if(pib!=null){
            Building target = seekTarget(pib, velocity, angle);
            ans = target!=null && pib.aterriza(target,velocity,angle);
        }
        return ans;
    }
    /**
     * Devuleve la información de la ciudad
     * @return Un vector con la información de la ciudad
     */
    public Vector<Vector<Vector<Integer>>> city(){
        Vector<Vector<Vector<Integer>>> ans= new Vector<Vector<Vector<Integer>>>(2);
        ans.add(new Vector<Vector<Integer>>());
        ans.add(new Vector<Vector<Integer>>());
        for(int i =0 ;i<towers.size();i++){
            Vector<Integer> curr = new Vector<Integer>();
            curr.add(towers.get(i).getX());
            curr.add(towers.get(i).getWidth());
            curr.add(towers.get(i).getHeight());
            curr.add(towers.get(i).getHardness());
            ans.get(0).add(curr);
            if(towers.get(i).hasHeroe()){
                curr = new Vector<Integer>();
                curr.add(i+1);
                curr.add(towers.get(i).getHeroeStrength());
                ans.get(1).add(curr);
            }
        }
        return ans;
    }
    
    /**
     * Dado un heroe y un edifico, busca un angulo y velocidad que haga aterrizar al héroe
     * @param heroe Heroe a saltar
     * @param building Edificio objetivo
     * @return Velocidad y angulo de salto
     */
    public int[] jumpPlan(String heroe, int building){
        Building target = (building>0 && building<=towers.size()?towers.get(building-1):null);
        Heroe pib = people.get(heroe);
        int ans[] =  new int[2];
        ans[0]=0;
        ans[1]=0;
        if(target!=null && pib!=null){
            int delta=(target.getX()>=pib.getX()?1:-1);
            int x,y=-height+pib.getY()+target.getHeight();
            int tope;
            int vel;
            int angle;
            for(int angulo = 1; angulo<=89 && ans[0]==0 && ans[1]==0; angulo++){
                angle = target.getX()>=pib.getX()?angulo:180-angulo;
                x = (target.getX()>=pib.getX()?target.getX()-pib.getX():-pib.getX()+target.getX()+target.getWidth());
                tope = (target.getX()>=pib.getX()?target.getX()+target.getWidth()-pib.getX():target.getX()-pib.getX());
                System.out.printf("x: %d    tope:%d   y: %d\n",x,tope,y);
                while(x!=tope && ans[0]==0 && ans[1]==0){
                    vel = velDeAngulo(x,y,angle);
                    System.out.printf("Angulo: %d,   velocidad:  %d\n",angulo,vel);
                    if(vel>0 && seekTarget(pib,vel,angle)==target && isSafeJump(heroe, vel,angle)){

                        ans[1]=angle;
                        ans[0]=vel;
                    }
                    x+=delta;
                }
            }
        }
        return ans;
    }
    
    /**
     * Retorna una velocidad a partir de un angulo y un punto en el plano
     * @param x Punto en x
     * @param y Punto en y
     * @param grados Angulo en grados
     * @return Velocidad positiva(Si es negativa significa que no es posible llegar a ese punto)
     */
    private int velDeAngulo(int x, int y, int grados){
        int ans=-1;
        double theta=Math.toRadians(grados);
        if(y-x*Math.tan(theta)<0){
            ans=(int)Math.sqrt((9.8*x*x)/(2*Math.pow(Math.cos(theta),2)*(-y+x*Math.tan(theta))));
        }
        return ans;
    }
    /**
     * Deshace la ultima accion realizada
     */
    public void undo(){
        if (ultimaAccion.equals("addBuilding")){
            removeBuilding((int)ultObj.get(0));
        }else if(ultimaAccion.equals("removeBuilding")){
            addBuilding((int)ultObj.get(0), (int)ultObj.get(1),(int)ultObj.get(2),(int)ultObj.get(3));
        }else if(ultimaAccion.equals("addHero")){
            removeHeroe((String)ultObj.get(2));
        }else if(ultimaAccion.equals("removeHero")){
            addHeroe((String)ultObj.get(1),(int)ultObj.get(0),(int)ultObj.get(2));
        }else if(ultimaAccion.equals("jump")){
            if((int)ultObj.get(2)>90 && (int)ultObj.get(2)<180){
                jump((String)ultObj.get(0),(int)ultObj.get(1),(int)ultObj.get(2)-90,false);
            } else if ((int)ultObj.get(2)<90 && (int)ultObj.get(2)>0){
                jump((String)ultObj.get(0),(int)ultObj.get(1),(int)ultObj.get(2)+90,false);
            }
        }
    }
    /**
     * hace zoom hacia el centro de la ciudad
     * @param v + si el zoom es hacia adentro y - si el zoom es hacia afuera
     */
    public void zoom(String v){
        Canvas.getCanvas(width,height).zoom(v);

    }
    
}

                    
    