package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Eskaera;
import domain.Eskaintza;

public class acceptEskaintzaBDBlackTest {

    private DataAccess sut = new DataAccess();

    private Eskaera eskaera;
    private Eskaintza eskaintza;

    @Before
    public void setUp() {
        sut.open();
    }
    @After
    public void tearDown() {
        sut.close();
    }

    @Test
    public void test1_Muga19() {
    	 String buyerEmail = "buyer1@gmail.com";
         String sellerEmail = "selle1r@gmail.com";

        try {
            sut.isRegister("Buyer CP1", buyerEmail, "123", "123");
            sut.isRegister("Seller CP1", sellerEmail, "123", "123");
            sut.addMoney(buyerEmail, 19);

            sut.createEskaera(buyerEmail, "Eskaera CP1", "Deskribapena");
            eskaera = sut.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            sut.addEskaintza(eskaera.getId(), sellerEmail, 20, "Eskaintza CP1");

            eskaintza = eskaera.getEskaintzak().get(0);

            boolean result = sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());
            assertFalse(result);
            assertTrue(sut.getMoney(buyerEmail)==19);
            assertTrue(sut.getOpenEskaerak().stream().anyMatch(e -> e.getId().equals(eskaera.getId())));

        } catch (Exception e) {
            fail( e.getMessage());
        }
    }

    @Test
    public void test1_Muga20() {
    	 String buyerEmail = "buyer2@gmail.com";
         String sellerEmail = "selle2r@gmail.com";

        try {
        	sut.isRegister("Buyer CP1", buyerEmail, "123", "123");
        	sut.isRegister("Seller CP1", sellerEmail, "123", "123");
        	sut.addMoney(buyerEmail, 20); 

        	sut.createEskaera(buyerEmail, "Eskaera CP1", "Deskribapena");
            eskaera = sut.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            sut.addEskaintza(eskaera.getId(), sellerEmail, 20.0f, "Eskaintza CP1"); // Prezioa: 20
            eskaintza = eskaera.getEskaintzak().get(0);

            boolean result = sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());
            assertTrue(result);
            assertTrue(sut.getMoney(buyerEmail)==0);
            assertTrue(sut.getOpenEskaerak().stream().noneMatch(e -> e.getId().equals(eskaera.getId())));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test1_Muga21() {
    	 String buyerEmail = "buyer3@gmail.com";
         String sellerEmail = "seller3@gmail.com";

        try {
        	sut.isRegister("Buyer CP1", buyerEmail, "123", "123");
        	sut.isRegister("Seller CP1", sellerEmail, "123", "123");
        	sut.addMoney(buyerEmail, 21);

        	sut.createEskaera(buyerEmail, "Eskaera CP1", "Deskribapena");
            eskaera = sut.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            sut.addEskaintza(eskaera.getId(), sellerEmail, 20, "Eskaintza CP1");
            
            eskaintza = eskaera.getEskaintzak().get(0);

            boolean result = sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());
            assertTrue(result);
            assertTrue(sut.getMoney(buyerEmail)==1);
            assertTrue(sut.getOpenEskaerak().stream().noneMatch(e -> e.getId().equals(eskaera.getId())));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void test2() {
    	String buyerEmail = "buyer@gmail.com";
        String sellerEmail = "seller@gmail.com";
        
        try {
        	sut.isRegister("Buyer CP2", buyerEmail, "123", "123");
        	sut.isRegister("Seller CP2", sellerEmail, "123", "123");
        	sut.addMoney(buyerEmail, 20);

        	sut.createEskaera(buyerEmail, "Eskaera CP2", "Deskribapena");
            eskaera = sut.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            sut.addEskaintza(eskaera.getId(), sellerEmail, 10, "Eskaintza CP2");
            
            eskaera.setClosed(true);
            
            eskaintza = eskaera.getEskaintzak().get(0);

            sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());
            float moneyClosed = sut.getMoney(buyerEmail);

            boolean result = sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());
            assertFalse(result);
            assertTrue(moneyClosed==20);
            assertTrue(eskaera.isClosed());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void test3() {
        String buyerEmail = "buyer4@gmail.com";
        String sellerEmail = "seller4@gmail.com";

        try {
        	sut.isRegister("Buyer CP3", buyerEmail, "123", "123");
        	sut.isRegister("Seller CP3", sellerEmail, "123", "123");
        	sut.addMoney(buyerEmail, 10);

        	sut.createEskaera(buyerEmail, "Eskaera CP3", "Deskribapena");
            eskaera = sut.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            sut.addEskaintza(eskaera.getId(), sellerEmail, 20, "Eskaintza CP3"); // Prezioa: 20
            
            eskaintza = eskaera.getEskaintzak().get(0);

            boolean result = sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());
            assertFalse(result);
            assertTrue(sut.getMoney(buyerEmail)==10);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test4() {
        try {
            boolean result = sut.acceptEskaintza(null, 1);
            assertFalse(result);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test5() {
        try {
            boolean result = sut.acceptEskaintza(1, null);
            assertFalse(result);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

   @Test
    public void test6() {
        try {
            boolean result = sut.acceptEskaintza(999999, 1);
            assertFalse(result);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test7() {
        String buyerEmail = "buyer.cp7@gmail.com";

        try {
        	sut.isRegister("Buyer CP7", buyerEmail, "123", "123");
        	sut.createEskaera(buyerEmail, "Eskaera CP7", "Deskribapena");

            eskaera = sut.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            assertNotNull("Eskaera DBan egon behar da", eskaera);

            boolean result = sut.acceptEskaintza(eskaera.getId(), 999999);
            assertFalse(result);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
