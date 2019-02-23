

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
        CityOfHeroes c1 = new CityOfHeroes(600,600);
        CityOfHeroes c2 = new CityOfHeroes(100,100);
    }
    @Test
    public void segunCVdeberiaCrearCiudad(){
        
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
