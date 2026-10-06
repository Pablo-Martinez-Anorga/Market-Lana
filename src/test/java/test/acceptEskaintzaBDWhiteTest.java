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
    public void test1() {
        try {
            boolean result = sut.acceptEskaintza(null, null);
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
            testDA.isRegister("Buyer B3", buyerEmail, "123", "123");
            testDA.createEskaera(buyerEmail, "Eskaera B3", "Deskribapena");

            eskaera = testDA.getOpenEskaerak().stream()
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
            testDA.isRegister("Buyer B4", buyerEmail, "123", "123");
            testDA.isRegister("Seller B4", sellerEmail, "123", "123");
            testDA.addMoney(buyerEmail, 100);

            testDA.createEskaera(buyerEmail, "Eskaera B4", "Deskribapena");
            eskaera = testDA.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            testDA.addEskaintza(eskaera.getId(), sellerEmail, 20, "Eskaintza B4");

            eskaera = sut.getOpenEskaerak().stream()
                        .filter(e -> e.getId().equals(eskaera.getId()))
                        .findFirst().orElse(null);
            eskaintza = eskaera.getEskaintzak().get(0);

            sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());

            float diruaOndoren = sut.getMoney(buyerEmail);

            boolean result = sut.acceptEskaintza(eskaera.getId(), eskaintza.getId());
            assertFalse(result);
            assertTrue(sut.getMoney(buyerEmail) == diruaOndoren);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void tes5() {
        String buyerEmail = "buyer.b5@test.com";
        String sellerEmail = "seller.b5@test.com";

        try {
            testDA.isRegister("Buyer B5", buyerEmail, "123", "123");
            testDA.isRegister("Seller B5", sellerEmail, "123", "123");
            testDA.addMoney(buyerEmail, 10);

            testDA.createEskaera(buyerEmail, "Eskaera B5", "Deskribapena");
            eskaera = testDA.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            testDA.addEskaintza(eskaera.getId(), sellerEmail, 20, "Eskaintza B5");

            eskaera = sut.getOpenEskaerak().stream()
                        .filter(e -> e.getId().equals(eskaera.getId()))
                        .findFirst().orElse(null);
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
            testDA.isRegister("Buyer B6", buyerEmail, "123", "123");
            testDA.isRegister("Seller B6", sellerEmail, "123", "123");
            testDA.addMoney(buyerEmail, 30);

            testDA.createEskaera(buyerEmail, "Eskaera B6", "Deskribapena");
            eskaera = testDA.getOpenEskaerak().stream()
                        .filter(e -> e.getBuyer().getEmail().equals(buyerEmail))
                        .findFirst().orElse(null);

            testDA.addEskaintza(eskaera.getId(), sellerEmail, 20, "Eskaintza B6");

            eskaera = sut.getOpenEskaerak().stream()
                        .filter(e -> e.getId().equals(eskaera.getId()))
                        .findFirst().orElse(null);
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