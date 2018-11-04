package imo.menu.screens;

import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.sale.Sale;
import elfo.sale.SaleControl;
import elfo.users.UserControl;
import elfo.users.UserTools;
import imo.menu.actions.SaleView;
import imo.property.Property;
import imo.property.PropertyControl;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.4
 */
public class SaleScreen extends MenuList{
    private int menuListIndex;
    private Scanner sc;
    private UserControl userControl;
    private SaleControl saleControl;
    private PropertyControl propertyControl;
    private SaleView saleView;

    /**
     * Constructor
     */
    public SaleScreen(){
        super(Menu.getInstance());
        menuListIndex = menuHome.addMenu(this);
        sc = UserTools.getScanner();
        userControl = UserControl.getInstance();
        saleControl = SaleControl.getInstace();
        propertyControl = PropertyControl.getInstance();
        saleView = new SaleView();
    }

    /**
     * builds client screen
     */
    public void saleScreenClient(){
        this.removeAll();
        this.addOption("Buy",this::newSale);
        this.addOption("Sales", this::seeSales);
    }

    /**
     * builds RealEstateManager screen
     */
    public void saleScreenRealEstateManager(){
        this.removeAll();
        this.addOption("Confirm Sale", this::confirmSale);
        this.addOption("Relatory 30 days",this::relatory30Days);
        this.addOption("Pending Purchases", this::pendingPurchases);
        this.addOption("Show All Confirmed Sales", this::showAllConfirmedSales);
    }

    /**
     * @return Index of MenuList in Menu
     */
    public int getMenuListIndex(){
        return menuListIndex;
    }

    /**
     * MenuCommand
     * Show all Sales of Current CPF
     * @param menu MenuHome
     */
    public void seeSales(Menu menu){
        ArrayList<Sale> sales = saleControl.getSalesOfCpf(userControl.getCpfCurrent());
        for(Sale s: sales) {
            System.out.printf("%s\n", s);
        }
    }

    /**
     * MenuCommand
     * Create a New Sale
     * @param menu MenuHome
     */
    public void newSale(Menu menu){
        System.out.printf("Property CODE>");
        int code = sc.nextInt();
        Property property = propertyControl.getProperty(code);
        if(property != null){
            System.out.printf("0 - CASH\n 1 - FINANSE\n>");
            int method = sc.nextInt();
            Sale sale = saleControl.newSale(userControl.getCpfCurrent(),property.getPrice().getValue(),method,code);
            System.out.println(sale);
            System.out.println("Sale create! Confirm with ADM1");
        }
    }

    /**
     * MenuCommand
     * Confim Sale
     * @param menu MenuHome
     */
    public void confirmSale(Menu menu){
        System.out.printf(saleView.getVisualPendingPurchases() + "\n");
        Sale confirmedSale = saleView.confirmSale();
        if(confirmedSale != null){
            confirmedSale.setAproved(true);
            Property property = propertyControl.getProperty(confirmedSale.getProductCode());
            property.setAvailability(false);
        }
    }

    /**
     * MenuCommand
     * Relatory before 30 days
     * @param menu MenuHome
     */
    public void relatory30Days(Menu menu){
        System.out.printf(saleView.getVisualRelatory(30)+ "\n");
    }

    /**
     * MenuCommand
     * Print Pending Purchases
     * @param menu MenuHome
     */
    public void pendingPurchases(Menu menu){
        System.out.printf(saleView.getVisualPendingPurchases() + "\n");
    }

    /**
     * MenuCommand
     * Show All Confirmed Sales
     * @param menu MenuHome
     */
    public void showAllConfirmedSales(Menu menu){
        System.out.printf(saleView.getVisualAprovedPurchases());
    }

}
