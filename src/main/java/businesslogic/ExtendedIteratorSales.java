package businesslogic;

import java.util.List;
import domain.Sale;

public class ExtendedIteratorSales implements ExtendedIterator<Sale> {
    private List<Sale> sales;
    private int position = 0;

    public ExtendedIteratorSales(List<Sale> sales) {
        this.sales = sales;
    }

    @Override
    public boolean hasNext() {
        return position < sales.size();
    }

    @Override
    public Sale next() {
        Sale sale = sales.get(position);
        position++;
        return sale;
    }

    @Override
    public boolean hasPrevious() {
        return position >= 0 && position < sales.size();
    }

    @Override
    public Sale previous() {
        Sale sale = sales.get(position);
        position--;
        return sale;
    }

    @Override
    public void goFirst() {
        position = 0;
    }

    @Override
    public void goLast() {
        position = sales.size() - 1;
    }
}