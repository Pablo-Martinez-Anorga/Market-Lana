package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import dataAccess.DataAccess;
import domain.Seller;

public class AddMoneyMockBlackTest {

    DataAccess sut;

    @Mock
    protected EntityManager db;
    @Mock
    protected EntityTransaction et;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        Mockito.doReturn(et).when(db).getTransaction();
        sut = new DataAccess(db);
    }

    @Test
    public void testBlack_BaliozkoPartizioa_Positiboa() {
    	//Erabiltzailea existitzen da eta dirua positiboa da
        String email = "bai@dago.com";
        float zenbat = 10f; //Muga balioaren gainetik (muga balioa 0f)
        
        Seller mockSeller = new Seller(email, "Aitor", "123");
        Mockito.when(db.find(Seller.class, email)).thenReturn(mockSeller);

        boolean result = sut.addMoney(email, zenbat);

        assertTrue("Akatsa Kutxa Beltza: Espero zen balioa 'true' da, baina 'false' lortu da.", result);
    }

    @Test
    public void testBlack_BaliogabeaPartizioa_Zero() {
    	//Erabiltzailea existitzen da baina dirua zero da
        String email = "bai@dago.com";
        float zenbat = 0f; //Muga balioa
        
        Seller mockSeller = new Seller(email, "Aitor", "123");
        Mockito.when(db.find(Seller.class, email)).thenReturn(mockSeller);

        boolean result = sut.addMoney(email, zenbat);

        assertFalse("Akatsa Kutxa Beltza: Espero zen balioa 'false' da, ezin delako 0 euro gehitu, baina 'true' lortu da.", result);
    }

    @Test
    public void testBlack_BaliogabeaPartizioa_Negatiboa() {
    	//Erabiltzailea existitzen da baina dirua negatiboa da
        String email = "bai@dago.com";
        float zenbat = -5f;
        
        Seller mockSeller = new Seller(email, "Aitor", "123");
        Mockito.when(db.find(Seller.class, email)).thenReturn(mockSeller);

        boolean result = sut.addMoney(email, zenbat);

        assertFalse("Akatsa Kutxa Beltza: Espero zen balioa 'false' da, baina sistemak 'true' itzuli du diru negatiboa gehitzean.", result);
    }

    @Test
    public void testBlack_BaliogabeaPartizioa_BezeroNull() {
    	// Erabiltzailea ez da existitzen
        String email = "ez.dago@ehu.eus";
        float zenbat = 50f;
        
        // Mock-ari esaten diogu null itzultzeko, bezeroa ez balego bezala
        Mockito.when(db.find(Seller.class, email)).thenReturn(null);

        boolean result = sut.addMoney(email, zenbat);

        assertFalse("Akatsa Kutxa Beltza (Bezeroa ez dago): Espero zen balioa 'false' da bezeroa ez delako existitzen, baina 'true' lortu da.", result);
    }
}