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

public class AddMoneyBDWhiteTest {

    DataAccess sut; //Instantzia
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    private String testEmail = "bdwhite@test.com";

    @Before //Teste bakoitza baino lehen egikaritu
    public void setUp() {
        sut = new DataAccess(); //DB hasieratu
        sut.open();
        //DBren konexikoa sortu
        emf = Persistence.createEntityManagerFactory("objectdb:src/main/resources/products.temp");
        em = emf.createEntityManager();
        
        em.getTransaction().begin(); //Datuak aldatzeko
        
        Seller s = em.find(Seller.class, testEmail); //Erabiltzailea bilatu
        if (s == null) {
            s = new Seller(testEmail, "Test BD White", "123"); //Erabiltzailea sortu
            em.persist(s); //Erabiltzailea gorde
        } else {
            s.setMoney(0f); //Aurretik sortuta badago dirua 0an jarri
        }
        em.getTransaction().commit(); //Transaction amaitu eta aldaketak gorde
    }

    @After //Sortutako datuk ezabatu behar dira
    public void tearDown() {
    	em.clear(); //Cache garbitu
    	
        em.getTransaction().begin(); //Sortutako erabiltzailea ezabatu
        Seller s = em.find(Seller.class, testEmail);
        if (s != null) {
            em.remove(s);
        }
        em.getTransaction().commit();
        //Dena itxi
        em.close();
        emf.close();
        sut.close();
    }

    //Proba kasiak
    @Test
    public void testKasu1_Zuzena() {
    	//Erabiltzailea existitzen da eta dirua positiboa da
        float zenbat = 50f;
        boolean result = sut.addMoney(testEmail, zenbat);
        //False bada errore testua
        assertTrue("Akatsa testKasu1: Espero zen balioa 'true' da, baina 'false' lortu da.", result);
    }

    @Test
    public void testKasu2_ZenbatekoNegatiboa() {
    	//Erabiltzailea existitzen da baina dirua negatiboa da
        float zenbat = -10f;
        boolean result = sut.addMoney(testEmail, zenbat);
        assertFalse("Akatsa testKasu2: Espero zen balioa 'false' da, baina 'true' lortu da.", result);
    }

    @Test
    public void testKasu3_BezeroaEzDaExistitzen() {
    	// Erabiltzailea ez da existitzen
        String emailFalso = "ez.dago.inon@ehu.eus";
        float zenbat = 50f;
        boolean result = sut.addMoney(emailFalso, zenbat);
        assertFalse("Akatsa testKasu3: Espero zen balioa 'false' da, bezero hori ez delako existitzen DB-an.", result);
    }    
}
