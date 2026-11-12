package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import domain.Seller;

public class SellerTable extends JFrame {
    private Seller seller;
    private JTable tabla;

    public SellerTable(Seller seller) {
        super(seller.getName() + "'s sales ");
        this.setBounds(100, 100, 700, 200);
        this.seller = seller;
        
        SellerAdapter adapt = new SellerAdapter(seller);
        tabla = new JTable(adapt);
        tabla.setPreferredScrollableViewportSize(new Dimension(500, 70));
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
    
    public static void main(String[] args) {
        boolean isLocal = true;
        businesslogic.BLFacade blFacade = new businesslogic.BLFactory().getBusinessLogicFactory(isLocal);
        domain.Seller s = blFacade.isLogged("Aitor Fernandez", "aurrera");
        SellerTable st = new SellerTable(s);
        st.setVisible(true);
    }
}