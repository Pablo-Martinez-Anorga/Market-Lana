package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Eskaera;
import domain.Eskaintza;

public class acceptEskaintzaBDBlackTest {

    static DataAccess sut = new DataAccess();
    static DataAccess testDA = new DataAccess();

    private Eskaera eskaera;
    private Eskaintza eskaintza;

    @Before
    public void setUp() {
        testDA.open();
        sut.open();
    }
    
    @Test
    public void test1_Muga19() {
        // Muga Balioa: saldoa = 19, prezioa = 20 -> False (Ez dauka diru nahikoa)
        String buyerEmail = "buyer.cp1.19@test.com";
        String sellerEmail = "seller.cp1.19@test.com";

        try {
            testDA.isRegister("Buyer CP1", buyerEmail, "123", "123");
            testDA.isRegister("Seller CP1", sellerEmail, "123", "123");
            testDA.addMoney(buyerEmail, 19);

            testDA.createEskaera(buyerEmail, "Eskaera CP1", "Deskribapena");
            eskaera = testDA.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            testDA.addEskaintza(eskaera.getId(), sellerEmail, 20.0f, "Eskaintza CP1"); // Prezioa: 20
            eskaera = testDA.getOpenEskaerak().stream()
                        .filter(e -> e.getId().equals(eskaera.getId()))
                        .findFirst().orElse(null);
            eskaintza = eskaera.getEskaintzak().get(0);

            boolean result = sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());
            assertFalse(result);
            assertTrue(testDA.getMoney(buyerEmail)==19);

        } catch (Exception e) {
            fail( e.getMessage());
        }
    }

    @Test
    public void test1_Muga20() {
        // Muga Balioa: saldoa = 20, prezioa = 20 -> True (Bidezkoa, saldoa = 0 geratzen da)
        String buyerEmail = "buyer.cp1.20@test.com";
        String sellerEmail = "seller.cp1.20@test.com";

        try {
            testDA.isRegister("Buyer CP1", buyerEmail, "123", "123");
            testDA.isRegister("Seller CP1", sellerEmail, "123", "123");
            testDA.addMoney(buyerEmail, 20); 

            testDA.createEskaera(buyerEmail, "Eskaera CP1", "Deskribapena");
            eskaera = testDA.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            testDA.addEskaintza(eskaera.getId(), sellerEmail, 20.0f, "Eskaintza CP1"); // Prezioa: 20
            eskaera = testDA.getOpenEskaerak().stream()
                        .filter(e -> e.getId().equals(eskaera.getId()))
                        .findFirst().orElse(null);
            eskaintza = eskaera.getEskaintzak().get(0);

            boolean result = sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());
            assertTrue(result);
            assertTrue(sut.getMoney(buyerEmail)==0);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test1_Muga21() {
        String buyerEmail = "buyer.cp1.23@test.com";
        String sellerEmail = "seller.cp1.23@test.com";

        try {
            testDA.isRegister("Buyer CP1", buyerEmail, "123", "123");
            testDA.isRegister("Seller CP1", sellerEmail, "123", "123");
            testDA.addMoney(buyerEmail, 21);

            testDA.createEskaera(buyerEmail, "Eskaera CP1", "Deskribapena");
            eskaera = testDA.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            testDA.addEskaintza(eskaera.getId(), sellerEmail, 20, "Eskaintza CP1");
            eskaera = testDA.getOpenEskaerak().stream()
                        .filter(e -> e.getId().equals(eskaera.getId()))
                        .findFirst().orElse(null);
            eskaintza = eskaera.getEskaintzak().get(0);

            boolean result = sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());
            assertTrue(result);
            assertTrue(sut.getMoney(buyerEmail)==1);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void test2() {
        String buyerEmail = "buyer.cp2@test.com";
        String sellerEmail = "seller.cp2@test.com";

        try {
            testDA.isRegister("Buyer CP2", buyerEmail, "123", "123");
            testDA.isRegister("Seller CP2", sellerEmail, "123", "123");
            testDA.addMoney(buyerEmail, 20);

            testDA.createEskaera(buyerEmail, "Eskaera CP2", "Deskribapena");
            eskaera = testDA.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            testDA.addEskaintza(eskaera.getId(), sellerEmail, 10, "Eskaintza CP2");
            eskaera = testDA.getOpenEskaerak().stream()
                        .filter(e -> e.getId().equals(eskaera.getId()))
                        .findFirst().orElse(null);
            eskaintza = eskaera.getEskaintzak().get(0);

            sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());
            float moneyClosed = testDA.getMoney(buyerEmail);

            boolean result = sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());
            assertFalse(result);
            assertTrue(moneyClosed==20);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void test3() {
        String buyerEmail = "buyer.cp3@test.com";
        String sellerEmail = "seller.cp3@test.com";

        try {
            testDA.isRegister("Buyer CP3", buyerEmail, "123", "123");
            testDA.isRegister("Seller CP3", sellerEmail, "123", "123");
            testDA.addMoney(buyerEmail, 10);

            testDA.createEskaera(buyerEmail, "Eskaera CP3", "Deskribapena");
            eskaera = testDA.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            testDA.addEskaintza(eskaera.getId(), sellerEmail, 20, "Eskaintza CP3"); // Prezioa: 20
            eskaera = testDA.getOpenEskaerak().stream()
                        .filter(e -> e.getId().equals(eskaera.getId()))
                        .findFirst().orElse(null);
            eskaintza = eskaera.getEskaintzak().get(0);

            boolean result = sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());
            assertFalse(result);
            assertTrue(sut.getMoney(buyerEmail)==10);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    // ------------------------------------------------------------------------
    // CP4: eskaeraId null, eskaintzaId = 1 -> False, DB: Ez da aldatzen
    // ------------------------------------------------------------------------
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
        String buyerEmail = "buyer.cp7@test.com";

        try {
            testDA.isRegister("Buyer CP7", buyerEmail, "123", "123");
            testDA.createEskaera(buyerEmail, "Eskaera CP7", "Deskribapena");

            eskaera = testDA.getOpenEskaerak().stream()
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