package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Eskaera;
import domain.Eskaintza;
import domain.Sale;
import domain.Seller;

public class acceptEskaintzaMockWhiteTest {
    @Mock
    private EntityManager db;

    @Mock
    private EntityTransaction transaction;

    @InjectMocks
    private DataAccess sut;

    @Before
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    	Mockito.when(db.getTransaction()).thenReturn(transaction);
    }

    @Test
    public void test1() {
        try {
            Mockito.when(db.find(Eskaera.class, 1)).thenReturn(null);
            Mockito.when(db.find(Eskaintza.class, 1)).thenReturn(Mockito.mock(Eskaintza.class));

            boolean result = sut.acceptEskaintza(1, 1);

            assertFalse(result);
            verify(transaction).rollback();

        } catch (Exception e) {
        	 fail(e.getMessage());
        }
    }

    @Test
    public void test2() {
        try {
            Eskaera mockEskaera = Mockito.mock(Eskaera.class);
            Mockito.when(db.find(Eskaera.class, 1)).thenReturn(mockEskaera);
            Mockito.when(db.find(Eskaintza.class, 1)).thenReturn(null);

            boolean result = sut.acceptEskaintza(1, 1);

            assertFalse(result);
            verify(transaction).rollback();

        } catch (Exception e) {
        	 fail(e.getMessage());
        }
    }

    @Test
    public void test3() {
        try {
            Eskaera mockEskaera = Mockito.mock(Eskaera.class);
            Eskaintza mockEskaintza = Mockito.mock(Eskaintza.class);

            Mockito.when(mockEskaera.isClosed()).thenReturn(true);
            Mockito.when(db.find(Eskaera.class, 1)).thenReturn(mockEskaera);
            Mockito.when(db.find(Eskaintza.class, 1)).thenReturn(mockEskaintza);

            boolean result = sut.acceptEskaintza(1, 1);

            assertFalse(result);
            verify(transaction).rollback();

        } catch (Exception e) {
        	 fail(e.getMessage());
        }
    }

    @Test
    public void test4() {
        try {
            Eskaera mockEskaera = Mockito.mock(Eskaera.class);
            Eskaintza mockEskaintza = Mockito.mock(Eskaintza.class);
            Seller mockBuyer = Mockito.mock(Seller.class);
            Seller mockSeller = Mockito.mock(Seller.class);

            Mockito.when(mockEskaera.isClosed()).thenReturn(false);
            Mockito.when(mockEskaera.getBuyer()).thenReturn(mockBuyer);
            Mockito.when(mockEskaintza.getSeller()).thenReturn(mockSeller);
            Mockito.when(mockBuyer.getMoney()).thenReturn(10.0f);
            Mockito.when(mockEskaintza.getPrice()).thenReturn(20.0f);

            Mockito.when(db.find(Eskaera.class, 1)).thenReturn(mockEskaera);
            Mockito.when(db.find(Eskaintza.class, 1)).thenReturn(mockEskaintza);

            boolean result = sut.acceptEskaintza(1, 1);

            assertFalse(result);
            verify(transaction).rollback();

        } catch (Exception e) {
        	 fail(e.getMessage());
        }
    }

    @Test
    public void test5() {
        try {
            Eskaera mockEskaera = Mockito.mock(Eskaera.class);
            Eskaintza mockEskaintza = Mockito.mock(Eskaintza.class);
            Seller mockBuyer = Mockito.mock(Seller.class);
            Seller mockSeller = Mockito.mock(Seller.class);
            Sale mockSale = Mockito.mock(Sale.class);

            Mockito.when(mockEskaera.isClosed()).thenReturn(false);
            Mockito.when(mockEskaera.getTitle()).thenReturn("Eskaera B6");
            Mockito.when(mockEskaera.getBuyer()).thenReturn(mockBuyer);
            Mockito.when(mockEskaintza.getSeller()).thenReturn(mockSeller);
            Mockito.when(mockEskaintza.getPrice()).thenReturn(20.0f);
            Mockito.when(mockEskaintza.getMessage()).thenReturn("Deskribapena");
            Mockito.when(mockBuyer.getMoney()).thenReturn(30.0f);

            Mockito.when(db.find(Eskaera.class, 1)).thenReturn(mockEskaera);
            Mockito.when(db.find(Eskaintza.class, 1)).thenReturn(mockEskaintza);
            
            Mockito.when(mockSeller.addSale(Mockito.any(Sale.class), Mockito.any()))
                    .thenReturn(mockSale);

            boolean result = sut.acceptEskaintza(1, 1);

            assertTrue(result);
            verify(transaction).commit();

        } catch (Exception e) {
        	 fail(e.getMessage());
        }
    }
}