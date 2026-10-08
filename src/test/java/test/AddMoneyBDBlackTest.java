package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dataAccess.DataAccess;
import domain.Seller;

public class AddMoneyBDBlackTest {

    DataAccess sut;
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    private String testEmail = "bdblack@test.com";

    @Before
    public void setUp() {
        sut = new DataAccess();   
        sut.open();
        
        emf = Persistence.createEntityManagerFactory("objectdb:src/main/resources/products.temp");
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Seller s = em.find(Seller.class, testEmail);
        if (s == null) {
            s = new Seller(testEmail, "Test BD Black", "123");
            em.persist(s);
        } else {
            s.setMoney(0f);
        }
        em.getTransaction().commit();
    }

    @After
    public void tearDown() {
    	em.clear();
    	
        em.getTransaction().begin();
        Seller s = em.find(Seller.class, testEmail);
        if (s != null) {
            em.remove(s);
        }
        em.getTransaction().commit();
        
        em.close();
        emf.close();
        sut.close();
    }

    @Test
    public void testBlackBD_BaliozkoPartizioa_Positiboa() {
    	//Erabiltzailea existitzen da eta dirua positiboa da
        float zenbat = 15f;
        boolean result = sut.addMoney(testEmail, zenbat);
        assertTrue("Akatsa Kutxa Beltza DB: Espero zen balioa 'true' da, baina 'false' lortu da.", result);
    }

    @Test
    public void testBlackBD_BaliogabeaPartizioa_Zero() {
    	//Erabiltzailea existitzen da baina dirua zero da
        float zenbat = 0f;
        boolean result = sut.addMoney(testEmail, zenbat);
        assertFalse("Akatsa Kutxa Beltza DB: Espero zen balioa 'false' da, baina 'true' lortu da.", result);
    }

    @Test
    public void testBlackBD_BaliogabeaPartizioa_Negatiboa() {
    	//Erabiltzailea existitzen da baina dirua negatiboa da
        float zenbat = -5f;
        boolean result = sut.addMoney(testEmail, zenbat);
        assertFalse("Akatsa Kutxa Beltza DB: Espero zen balioa 'false' da, baina 'true' lortu da.", result);
    }

    @Test
    public void testBlackBD_BaliogabeaPartizioa_BezeroaEzDaExistitzen() {
    	// Erabiltzailea ez da existitzen
        String emailFalso = "ez.dago.hemen@ehu.eus";
        float zenbat = 15f;
        boolean result = sut.addMoney(emailFalso, zenbat);
        assertFalse("Akatsa Kutxa Beltza DB: Espero zen balioa 'false' da bezeroa ez delako existitzen DBan, baina 'true' lortu da.", result);
    }
}
