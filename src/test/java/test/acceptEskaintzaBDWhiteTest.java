package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Eskaera;
import domain.Eskaintza;

public class acceptEskaintzaBDWhiteTest {

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
    public void test1() {
        try {
            boolean result = sut.acceptEskaintza(99999999, 999999999);
            assertFalse(result);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test2() {
        try {
            boolean result = sut.acceptEskaintza(1, 1);
            assertFalse(result);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test3() {
        String buyerEmail = "buyer.b3@test.com";

        try {
            sut.isRegister("Buyer B3", buyerEmail, "123", "123");
            sut.createEskaera(buyerEmail, "Eskaera B3", "Deskribapena");

            eskaera = sut.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            boolean result = sut.acceptEskaintza(eskaera.getId(), 999999);
            assertFalse(result);
            assertTrue(sut.getOpenEskaerak().stream().anyMatch(e -> e.getId().equals(eskaera.getId())));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test4() {
        String buyerEmail = "buyer.b4@test.com";
        String sellerEmail = "seller.b4@test.com";

        try {
            sut.isRegister("Buyer B4", buyerEmail, "123", "123");
            sut.isRegister("Seller B4", sellerEmail, "123", "123");
            sut.addMoney(buyerEmail, 20);

            sut.createEskaera(buyerEmail, "Eskaera B4", "Deskribapena");
            eskaera = sut.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            sut.addEskaintza(eskaera.getId(), sellerEmail, 20, "Eskaintza B4");

            eskaintza = eskaera.getEskaintzak().get(0);
            
            eskaera.setClosed(true);
            
            float diruaOndoren = sut.getMoney(buyerEmail);
            
            sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());

            boolean result = sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());
            assertFalse(result);
            assertTrue(sut.getMoney(buyerEmail) == diruaOndoren);
            assertTrue(sut.getOpenEskaerak().stream().anyMatch(e -> e.getId().equals(eskaera.getId())));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void tes5() {
        String buyerEmail = "buyer.b5@test.com";
        String sellerEmail = "seller.b5@test.com";

        try {
            sut.isRegister("Buyer B5", buyerEmail, "123", "123");
            sut.isRegister("Seller B5", sellerEmail, "123", "123");
            sut.addMoney(buyerEmail, 10);

            sut.createEskaera(buyerEmail, "Eskaera B5", "Deskribapena");
            eskaera = sut.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            sut.addEskaintza(eskaera.getId(), sellerEmail, 20, "Eskaintza B5");

            eskaintza = eskaera.getEskaintzak().get(0);

            boolean result = sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());
            assertFalse(result);
            assertTrue(sut.getMoney(buyerEmail) == 10);
            assertTrue(sut.getOpenEskaerak().stream().anyMatch(e -> e.getId().equals(eskaera.getId())));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test6() {
        String buyerEmail = "buyer.b6@test.com";
        String sellerEmail = "seller.b6@test.com";

        try {
            sut.isRegister("Buyer B6", buyerEmail, "123", "123");
            sut.isRegister("Seller B6", sellerEmail, "123", "123");
            sut.addMoney(buyerEmail, 30);

            sut.createEskaera(buyerEmail, "Eskaera B6", "Deskribapena");
            eskaera = sut.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            sut.addEskaintza(eskaera.getId(), sellerEmail, 20, "Eskaintza B6");

            eskaintza = eskaera.getEskaintzak().get(0);

            boolean result = sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());
            assertTrue(result);
            assertTrue(sut.getMoney(buyerEmail) == 10);
            assertTrue(sut.getOpenEskaerak().stream().noneMatch(e -> e.getId().equals(eskaera.getId())));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
