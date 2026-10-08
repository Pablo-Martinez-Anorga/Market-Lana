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

public class AddMoneyMockWhiteTest {

    DataAccess sut;

    @Mock
    protected EntityManager db; //DB hutsa
    @Mock
    protected EntityTransaction et; //Transkipszio hutsas

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this); //Objektuak aktibatu
        Mockito.doReturn(et).when(db).getTransaction(); //DB bat ez bilatzeko ema mock-a bueltzatzeko (et)
        sut = new DataAccess(db); //Abiarazi
    }

    @Test
    public void testKasu1_Zuzena() {
    	//Erabiltzailea existitzen da eta dirua positiboa da
        String email = "bai@dago.com";
        float zenbat = 50f;
        
        Seller mockSeller = new Seller(email, "Aitor", "123");
        Mockito.when(db.find(Seller.class, email)).thenReturn(mockSeller); //Erabiltzailea bilatzen denean mockSeller bueltatu

        boolean result = sut.addMoney(email, zenbat);

        // Akatsaren deskribapena
        assertTrue("Akatsa testKasu1: Espero zen balioa 'true' da, baina 'false' lortu da.", result);
        assertEquals("Akatsa testKasu1: Espero zen diru-kopurua 50.0 da.", 50f, mockSeller.getMoney(), 0.01);
        
        // Egiaztatu DBan gorde (merge) eta transakzioa baieztatu (commit) direla
        Mockito.verify(db, Mockito.times(1)).merge(mockSeller);
        Mockito.verify(et, Mockito.times(1)).commit();
    }

    @Test
    public void testKasu2_ZenbatekoNegatiboa() {
    	//Erabiltzailea existitzen da baina dirua negatiboa da
        String email = "bai@dago.com";
        float zenbat = -10f;
        
        Seller mockSeller = new Seller(email, "Aitor", "123");
        Mockito.when(db.find(Seller.class, email)).thenReturn(mockSeller);

        boolean result = sut.addMoney(email, zenbat);

        assertFalse("Akatsa testKasu2: Espero zen balioa 'false' da, baina 'true' lortu da.", result);
        
        //Egiaztatu transakripzioa ez dela egin (rollback)
        Mockito.verify(et, Mockito.times(1)).rollback();
    }

    @Test
    public void testKasu3_BezeroaEzDaExistitzen() {
    	// Erabiltzailea ez da existitzen
        String email = "ez.dago@ehu.eus";
        float zenbat = 50f;
        
        Mockito.when(db.find(Seller.class, email)).thenReturn(null); //Ez dago

        boolean result = sut.addMoney(email, zenbat);

        assertFalse("Akatsa testKasu3: Espero zen balioa 'false' da, bezeroa DBan ez dagoelako.", result);
     
        Mockito.verify(et, Mockito.times(1)).rollback();
    }

    @Test
    public void testKasu4_DBSalbuespena() {
        String email = "bai@dago.com";
        float zenbat = 50f;
        
        // Simulatu DB-ak errore bat ematen duela bezeroa bilatzean
        Mockito.when(db.find(Seller.class, email)).thenThrow(new RuntimeException("DB konexio errorea"));

        boolean result = sut.addMoney(email, zenbat);

        assertFalse("Akatsa testKasu4 (Salbuespena): Espero zen balioa 'false' da, datu-baseak errore bat bota duelako.", result);

        Mockito.verify(et, Mockito.times(1)).rollback();
    }
}
