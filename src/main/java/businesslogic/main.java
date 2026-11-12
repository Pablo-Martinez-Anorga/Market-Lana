package businesslogic;

import domain.Sale;

public class main {

	public static void main(String[] args) {
	    boolean isLocal = true;
	    BLFacade blFacade = new BLFactory().getBusinessLogicFactory(isLocal);
	    
	    ExtendedIterator<Sale> it = blFacade.getSalesIterator("a");
	    Sale sale;

	    System.out.println("FROM LAST TO FIRST");
	    it.goLast();
	    while (it.hasPrevious()) {
	        sale = it.previous();
	        System.out.println(sale);
	    }

	    System.out.println();
	    System.out.println("FROM FIRST TO LAST");
	    it.goFirst();
	    while (it.hasNext()) {
	        sale = it.next();
	        System.out.println(sale);
	    }
	}

}
