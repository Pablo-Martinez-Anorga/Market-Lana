package gui;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import domain.Seller;
import domain.Sale;

public class SellerAdapter extends AbstractTableModel {
    private Seller seller;
    private List<Sale> sales;
    private String[] columnNames = {"title", "description", "price"};

    public SellerAdapter(Seller seller) {
        this.seller = seller;
        this.sales = seller.getSales(); 
    }

    @Override
    public int getColumnCount() { 
        return 3; 
    }

    @Override
    public int getRowCount() { 
        return sales.size(); 
    }
    
    @Override
    public String getColumnName(int col) { 
        return columnNames[col]; 
    }

    @Override
    public Object getValueAt(int row, int col) {
        Sale sale = sales.get(row);
        switch(col) {
            case 0: return sale.getTitle();
            case 1: return sale.getDescription();
            case 2: return sale.getPrice();
            default: return null;
        }
    }
}