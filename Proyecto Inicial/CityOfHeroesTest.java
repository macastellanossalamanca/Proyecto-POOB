

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class CityOfHeroesTest.
 *
 * @author  (Miguel Castellanos - Alejandro Vasquez)
 * @version (2.0)
 */
public class CityOfHeroesTest
{
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        CityOfHeroes c1 = new CityOfHeroes(100,250);
        CityOfHeroes c2 = new CityOfHeroes(100,100);
    }
    @Test
    public void deberiaAñadirEdificios() {
        CityOfHeroes c1 = new CityOfHeroes(100,250);
        c1.addBuilding(0, 10, 20, 5);
        assertTrue(c1.ok());
        c1.addBuilding(11, 10, 20, 10);
        assertTrue(c1.ok());
    }
    @Test
    public void noDeberiaAñadirEdificios() {
        CityOfHeroes c1 = new CityOfHeroes(100,250);
        c1.addBuilding(0, 10, 20, 5);
        assertTrue(c1.ok());
        c1.addBuilding(0, 10, 20, 10);
        assertFalse(c1.ok());
        c1.addBuilding(20, 150, 20, 10);
        assertFalse(c1.ok());
    }
    @Test
    public void deberiaAgregarHeroes() {
        CityOfHeroes c1 = new CityOfHeroes(100,250);
        c1.addBuilding(0, 10, 20, 5);
        assertTrue(c1.ok());
        c1.addBuilding(11, 10, 20, 10);
        assertTrue(c1.ok());
        
        c1.addHeroe("blue", 1, 100);
        assertTrue(c1.ok());
        c1.addHeroe("yellow", 2, 100);
        assertTrue(c1.ok());
    }
    @Test
    public void noDeberiaAgregarHeroes() {
        CityOfHeroes c1 = new CityOfHeroes(100,250);
        c1.addBuilding(0, 10, 20, 5);
        assertTrue(c1.ok());
        c1.addBuilding(11, 10, 20, 10);
        assertTrue(c1.ok());
        
        c1.addHeroe("blue", 1, 100);
        assertTrue(c1.ok());
        c1.addHeroe("yElLoW", 3, 100);
        assertFalse(c1.ok());
    }
    @Test
    public void noDeberianHaberMuertos() {
        CityOfHeroes ch = new CityOfHeroes(100, 250);
        ch.addBuilding(0, 10, 20, 5);
        assertTrue(ch.ok());
        ch.addBuilding(11, 10, 20, 10);
        assertTrue(ch.ok());
        
        ch.addHeroe("blue", 1, 100);
        assertTrue(ch.ok());
        ch.addHeroe("yellow", 2, 100);
        assertTrue(ch.ok());
        
        assertArrayEquals(ch.deads(), new String[]{});
    }
    @Test
    public void deberiaHaberMuertos() {
        CityOfHeroes c1 = new CityOfHeroes(25, 250);
        c1.addBuilding(0, 10, 20, 5);
        assertTrue(c1.ok());
        c1.addBuilding(11, 10, 20, 10);
        assertTrue(c1.ok());
        
        c1.addHeroe("blue", 1, 100);
        assertTrue(c1.ok());
        c1.addHeroe("yellow", 2, 100);
        assertTrue(c1.ok());
        
        c1.jump("blue", 200, 45, true);
        assertArrayEquals(c1.deads(), new String[]{"blue"});
    }
    @Test
    public void deberiaSaltar() {
        CityOfHeroes c1 = new CityOfHeroes(100, 250);
        c1.addBuilding(0, 10, 20, 5);
        assertTrue(c1.ok());
        c1.addBuilding(11, 10, 20, 10);
        assertTrue(c1.ok());
        c1.addBuilding(22, 10, 20, 10);
        assertTrue(c1.ok());
        c1.addBuilding(33, 10, 20, 10);
        assertTrue(c1.ok());
        c1.addBuilding(44, 10, 20, 10);
        assertTrue(c1.ok());
        c1.addBuilding(55, 10, 20, 10);
        assertTrue(c1.ok());
        c1.addBuilding(66, 10, 20, 10);
        assertTrue(c1.ok());
        c1.addBuilding(77, 10, 20, 10);
        assertTrue(c1.ok());
        
        c1.addHeroe("blue", 1, 100);
        assertTrue(c1.ok());
        c1.addHeroe("red", 5, 100);
        assertTrue(c1.ok());
        
        c1.jump("blue", 5, 10, true);
        assertTrue(c1.ok());
        c1.jump("red", 5, 10, true);
        assertTrue(c1.ok());
    }
    
    
    
    @Test
    public void noDeberiaIndicarDaño() {
        CityOfHeroes ch = new CityOfHeroes(100, 250);
        ch.addBuilding(0, 10, 10, 5);
        assertTrue(ch.ok());
        ch.addBuilding(11, 10, 200, 10);
        assertTrue(ch.ok());
        
        ch.addHeroe("blue", 1, 100);
        assertTrue(ch.ok());
        
        assertFalse(ch.isDamaged(1));
        assertFalse(ch.isDamaged(2));
    }
    
    @Test
    public void deberiaSerSafeJump() {
        CityOfHeroes ch = new CityOfHeroes(100, 250);
        ch.addBuilding(0, 10, 20, 5);
        assertTrue(ch.ok());
        ch.addBuilding(11, 10, 20, 10);
        assertTrue(ch.ok());
        ch.addBuilding(22, 10, 20, 10);
        assertTrue(ch.ok());
        ch.addBuilding(33, 10, 20, 10);
        assertTrue(ch.ok());
        ch.addBuilding(44, 10, 20, 10);
        assertTrue(ch.ok());
        ch.addBuilding(55, 10, 20, 10);
        assertTrue(ch.ok());
        ch.addBuilding(66, 10, 20, 10);
        assertTrue(ch.ok());
        
        ch.addHeroe("blue", 1, 100);
        assertTrue(ch.ok());
        
        assertTrue(ch.isSafeJump("blue", 5, 20));
    }
    
    @Test
    public void noDeberiaSerSafeJump() {
        CityOfHeroes ch = new CityOfHeroes(100, 250);
        ch.addBuilding(0, 10, 10, 5);
        assertTrue(ch.ok());
        ch.addBuilding(11, 10, 200, 10);
        assertTrue(ch.ok());
        
        ch.addHeroe("blue", 1, 100);
        assertTrue(ch.ok());
        
        assertFalse(ch.isSafeJump("blue", 200, 20));
    }
    
  
    
    @Test
    public void deberiaQuitarHeroes() {
        CityOfHeroes ch = new CityOfHeroes(100, 250);
        ch.addBuilding(0, 10, 20, 5);
        assertTrue(ch.ok());
        ch.addBuilding(11, 10, 20, 10);
        assertTrue(ch.ok());
        
        ch.addHeroe("blue", 1, 100);
        assertTrue(ch.ok());
        ch.addHeroe("yellow", 2, 100);
        assertTrue(ch.ok());
        
        ch.removeHeroe("blue");
        assertTrue(ch.ok());
        ch.removeHeroe("yellow");
        assertTrue(ch.ok());
    }
    
    @Test
    public void noDeberiaQuitarHeroes() {
        CityOfHeroes ch = new CityOfHeroes(100, 250);
        ch.addBuilding(0, 10, 20, 5);
        assertTrue(ch.ok());
        ch.addBuilding(11, 10, 20, 10);
        assertTrue(ch.ok());
        
        ch.addHeroe("blue", 1, 100);
        assertTrue(ch.ok());
        ch.addHeroe("yellow", 2, 100);
        assertTrue(ch.ok());
        
        ch.removeHeroe("blue");
        assertTrue(ch.ok());
        ch.removeHeroe("blue");
        assertFalse(ch.ok());
        
        ch.removeHeroe("yellow");
        assertTrue(ch.ok());
        ch.removeHeroe("yellow");
        assertFalse(ch.ok());
        
        ch.removeHeroe("green");
        assertFalse(ch.ok());
    }
    
    @Test
    public void deberiaStrength() {
        CityOfHeroes ch = new CityOfHeroes(100, 250);
        ch.addBuilding(0, 10, 20, 5);
        assertTrue(ch.ok());
        ch.addBuilding(11, 10, 20, 10);
        assertTrue(ch.ok());
        
        ch.addHeroe("blue", 1, 100);
        assertTrue(ch.ok());
        ch.addHeroe("yellow", 2, 50);
        assertTrue(ch.ok());
        
        assertEquals(ch.strength("blue"), 100);
        assertEquals(ch.strength("yellow"), 50);
    }
    
    @Test
    public void deberiaQuitarStrength() {
        CityOfHeroes ch = new CityOfHeroes(100, 250);
        ch.addBuilding(0, 10, 10, 5);
        assertTrue(ch.ok());
        ch.addBuilding(11, 10, 200, 10);
        assertTrue(ch.ok());
        ch.addBuilding(22, 10, 20, 10);
        assertTrue(ch.ok());
        ch.addBuilding(33, 10, 20, 10);
        assertTrue(ch.ok());
        ch.addBuilding(44, 10, 20, 10);
        assertTrue(ch.ok());
        ch.addBuilding(55, 10, 20, 10);
        assertTrue(ch.ok());
        ch.addBuilding(66, 10, 20, 10);
        assertTrue(ch.ok());
        
        ch.addHeroe("blue", 1, 100);
        assertTrue(ch.ok());
        
        assertEquals(ch.strength("blue"), 100);
        ch.jump("blue", 200, 20, true);
        assertEquals(ch.strength("blue"), 90);
    }
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}
