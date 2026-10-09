package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Bidalketa;
import domain.Eskaera;
import domain.Eskaintza;
import domain.Sale;
import domain.Seller;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class acceptEskaintzaMockBlackTtest {

    @Mock
    private EntityManager db;

    @Mock
    private EntityTransaction transaction;

    private DataAccess sut;

    @Before
    public void setUp() {
    	
    	MockitoAnnotations.openMocks(this);

    	Mockito.when(db.getTransaction()).thenReturn(transaction);
     
        sut = new DataAccess(db);
    }

    @Test
    public void test1() {

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
                .thenReturn(20f);

        Mockito.when(mockBuyer.getMoney())
                .thenReturn(30f);

        Mockito.when(mockEskaintza.getMessage())
                .thenReturn("Oferta de prueba");

        Mockito.when(mockEskaera.getTitle())
                .thenReturn("Producto de prueba");

        Mockito.when(
                mockSeller.addSale(
                		Mockito.any(Sale.class),
                        Mockito.any()
                )
        ).thenReturn(mockSale);

        boolean emaitza = sut.acceptEskaintza(1, 1);

        assertTrue(emaitza);

        Mockito.verify(transaction).begin();
        Mockito.verify(transaction).commit();
    }

    @Test
    public void test2() {

        Eskaera mockEskaera = Mockito.mock(Eskaera.class);
        Eskaintza mockEskaintza = Mockito.mock(Eskaintza.class);

        Mockito.when(db.find(Eskaera.class, 1))
                .thenReturn(mockEskaera);

        Mockito.when(db.find(Eskaintza.class, 1))
                .thenReturn(mockEskaintza);

        Mockito.when(mockEskaera.isClosed())
                .thenReturn(true);

        boolean emaitza = sut.acceptEskaintza(1, 1);

        assertFalse(emaitza);

        Mockito.verify(transaction).begin();
        Mockito.verify(transaction).rollback();
        Mockito.verify(transaction, Mockito.never()).commit();
    }
    @Test
    public void test3() {

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

        boolean emaitza = sut.acceptEskaintza(1, 1);

        assertFalse(emaitza);

        Mockito.verify(transaction).begin();
        Mockito.verify(transaction).rollback();
    }
    @Test
    public void test4() {

        Mockito.when(db.find(Eskaera.class, null))
                .thenReturn(null);

        Mockito.when(db.find(Eskaintza.class, 1))
                .thenReturn(Mockito.mock(Eskaintza.class));

        boolean emaitza = sut.acceptEskaintza(null, 1);

        assertFalse(emaitza);

        Mockito.verify(transaction).begin();
        Mockito.verify(transaction).rollback();
    }

    @Test
    public void test5() {

        Mockito.when(db.find(Eskaera.class, 1))
                .thenReturn(Mockito.mock(Eskaera.class));

        Mockito.when(db.find(Eskaintza.class, null))
                .thenReturn(null);

        boolean emaitza = sut.acceptEskaintza(1, null);

        assertFalse(emaitza);

        Mockito.verify(transaction).begin();
        Mockito.verify(transaction).rollback();
    }

    @Test
    public void test6() {

        Mockito.when(db.find(Eskaera.class, 999999))
                .thenReturn(null);

        Mockito.when(db.find(Eskaintza.class, 1))
                .thenReturn(Mockito.mock(Eskaintza.class));

        boolean emaitza = sut.acceptEskaintza(999999, 1);

        assertFalse(emaitza);

        Mockito.verify(transaction).begin();
        Mockito.verify(transaction).rollback();
    }

    @Test
    public void test7() {

        Eskaera mockEskaera = Mockito.mock(Eskaera.class);

        Mockito.when(db.find(Eskaera.class, 1))
                .thenReturn(mockEskaera);

        Mockito.when(db.find(Eskaintza.class, 999999))
                .thenReturn(null);

        boolean emaitza = sut.acceptEskaintza(1, 999999);

        assertFalse(emaitza);

        Mockito.verify(transaction).begin();
        Mockito.verify(transaction).rollback();
    }
}