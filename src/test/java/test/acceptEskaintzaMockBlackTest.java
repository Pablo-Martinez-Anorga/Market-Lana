package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Eskaera;
import domain.Eskaintza;
import domain.Sale;
import domain.Seller;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class acceptEskaintzaMockBlackTest {

    @Mock
    private EntityManager db;

    @Mock
    private EntityTransaction transaction;

    private DataAccess sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(db.getTransaction()).thenReturn(transaction);

        sut = new DataAccess(db);
    }

    // ---------------------------------------------------------
    // TEST 1
    // Saldo = 19, precio = 20 -> FALSE
    // ---------------------------------------------------------
    @Test
    public void test1_Muga19() {

        Eskaera mockEskaera = Mockito.mock(Eskaera.class);
        Eskaintza mockEskaintza = Mockito.mock(Eskaintza.class);

        Seller mockBuyer = Mockito.mock(Seller.class);
        Seller mockSeller = Mockito.mock(Seller.class);

        Mockito.when(db.find(Eskaera.class, 1))
                .thenReturn(mockEskaera);

        Mockito.when(db.find(Eskaintza.class, 1))
                .thenReturn(mockEskaintza);

        Mockito.when(mockEskaera.isClosed())
                .thenReturn(false);

        Mockito.when(mockEskaera.getBuyer())
                .thenReturn(mockBuyer);

        Mockito.when(mockEskaintza.getSeller())
                .thenReturn(mockSeller);

        Mockito.when(mockEskaintza.getPrice())
                .thenReturn(20.0f);

        Mockito.when(mockBuyer.getMoney())
                .thenReturn(19.0f);

        boolean result = sut.acceptEskaintza(1, 1);

        assertFalse(result);

        Mockito.verify(transaction).begin();
        Mockito.verify(transaction).rollback();
        Mockito.verify(transaction, Mockito.never()).commit();
    }

    // ---------------------------------------------------------
    // TEST 2
    // Saldo = 20, precio = 20 -> TRUE
    // ---------------------------------------------------------
    @Test
    public void test2_Muga20() {

        Eskaera mockEskaera = Mockito.mock(Eskaera.class);
        Eskaintza mockEskaintza = Mockito.mock(Eskaintza.class);

        Seller mockBuyer = Mockito.mock(Seller.class);
        Seller mockSeller = Mockito.mock(Seller.class);

        Sale mockSale = Mockito.mock(Sale.class);

        Mockito.when(db.find(Eskaera.class, 1))
                .thenReturn(mockEskaera);

        Mockito.when(db.find(Eskaintza.class, 1))
                .thenReturn(mockEskaintza);

        Mockito.when(mockEskaera.isClosed())
                .thenReturn(false);

        Mockito.when(mockEskaera.getBuyer())
                .thenReturn(mockBuyer);

        Mockito.when(mockEskaintza.getSeller())
                .thenReturn(mockSeller);

        Mockito.when(mockEskaintza.getPrice())
                .thenReturn(20.0f);

        Mockito.when(mockBuyer.getMoney())
                .thenReturn(20.0f);

        Mockito.when(mockEskaintza.getMessage())
                .thenReturn("Eskaintza CP2");

        Mockito.when(mockEskaera.getTitle())
                .thenReturn("Eskaera CP2");

        Mockito.when(
                mockSeller.addSale(
                        Mockito.any(Sale.class),
                        Mockito.any()
                )
        ).thenReturn(mockSale);

        boolean result = sut.acceptEskaintza(1, 1);

        assertTrue(result);

        Mockito.verify(transaction).begin();
        Mockito.verify(transaction).commit();
    }

    @Test
    public void test3_Muga21() {

        Eskaera mockEskaera = Mockito.mock(Eskaera.class);
        Eskaintza mockEskaintza = Mockito.mock(Eskaintza.class);

        Seller mockBuyer = Mockito.mock(Seller.class);
        Seller mockSeller = Mockito.mock(Seller.class);

        Sale mockSale = Mockito.mock(Sale.class);

        Mockito.when(db.find(Eskaera.class, 1))
                .thenReturn(mockEskaera);

        Mockito.when(db.find(Eskaintza.class, 1))
                .thenReturn(mockEskaintza);

        Mockito.when(mockEskaera.isClosed())
                .thenReturn(false);

        Mockito.when(mockEskaera.getBuyer())
                .thenReturn(mockBuyer);

        Mockito.when(mockEskaintza.getSeller())
                .thenReturn(mockSeller);

        Mockito.when(mockEskaintza.getPrice())
                .thenReturn(20.0f);

        Mockito.when(mockBuyer.getMoney())
                .thenReturn(21.0f);

        Mockito.when(mockEskaintza.getMessage())
                .thenReturn("Eskaintza CP3");

        Mockito.when(mockEskaera.getTitle())
                .thenReturn("Eskaera CP3");

        Mockito.when(
                mockSeller.addSale(
                        Mockito.any(Sale.class),
                        Mockito.any()
                )
        ).thenReturn(mockSale);

        boolean result = sut.acceptEskaintza(1, 1);

        assertTrue(result);

        Mockito.verify(transaction).begin();
        Mockito.verify(transaction).commit();
    }

  
    @Test
    public void test4() {

        Eskaera mockEskaera = Mockito.mock(Eskaera.class);
        Eskaintza mockEskaintza = Mockito.mock(Eskaintza.class);

        Mockito.when(db.find(Eskaera.class, 1))
                .thenReturn(mockEskaera);

        Mockito.when(db.find(Eskaintza.class, 1))
                .thenReturn(mockEskaintza);

        Mockito.when(mockEskaera.isClosed())
                .thenReturn(true);

        boolean result = sut.acceptEskaintza(1, 1);

        assertFalse(result);

        Mockito.verify(transaction).begin();
        Mockito.verify(transaction).rollback();
        Mockito.verify(transaction, Mockito.never()).commit();
    }
    
    @Test
    public void test5() {

        Eskaera mockEskaera = Mockito.mock(Eskaera.class);
        Eskaintza mockEskaintza = Mockito.mock(Eskaintza.class);

        Seller mockBuyer = Mockito.mock(Seller.class);
        Seller mockSeller = Mockito.mock(Seller.class);

        Mockito.when(db.find(Eskaera.class, 1))
                .thenReturn(mockEskaera);

        Mockito.when(db.find(Eskaintza.class, 1))
                .thenReturn(mockEskaintza);

        Mockito.when(mockEskaera.isClosed())
                .thenReturn(false);

        Mockito.when(mockEskaera.getBuyer())
                .thenReturn(mockBuyer);

        Mockito.when(mockEskaintza.getSeller())
                .thenReturn(mockSeller);

        Mockito.when(mockEskaintza.getPrice())
                .thenReturn(20.0f);

        Mockito.when(mockBuyer.getMoney())
                .thenReturn(10.0f);

        boolean result = sut.acceptEskaintza(1, 1);

        assertFalse(result);

        Mockito.verify(transaction).begin();
        Mockito.verify(transaction).rollback();
        Mockito.verify(transaction, Mockito.never()).commit();
    }

    @Test
    public void test6() {

        Mockito.when(db.find(Eskaera.class, 999999))
                .thenReturn(null);

        Mockito.when(db.find(Eskaintza.class, 1))
                .thenReturn(Mockito.mock(Eskaintza.class));

        boolean result = sut.acceptEskaintza(999999, 1);

        assertFalse(result);

        Mockito.verify(transaction).begin();
        Mockito.verify(transaction).rollback();
        Mockito.verify(transaction, Mockito.never()).commit();
    }

    // ---------------------------------------------------------
    // TEST 7
    // Eskaintza inexistente -> FALSE
    // ---------------------------------------------------------
    @Test
    public void test7() {

        Eskaera mockEskaera = Mockito.mock(Eskaera.class);

        Mockito.when(db.find(Eskaera.class, 1))
                .thenReturn(mockEskaera);

        Mockito.when(db.find(Eskaintza.class, 999999))
                .thenReturn(null);

        boolean result = sut.acceptEskaintza(1, 999999);

        assertFalse(result);

        Mockito.verify(transaction).begin();
        Mockito.verify(transaction).rollback();
        Mockito.verify(transaction, Mockito.never()).commit();
    }
}