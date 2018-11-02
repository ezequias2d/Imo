package imo.menu.actions;

import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.sale.Sale;
import elfo.sale.SaleControl;
import elfo.users.UserControl;
import elfo.users.UserTools;
import imo.property.Property;
import imo.property.PropertyControl;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class SaleScreen {
    private Menu menu;
    private int menuListIndex;
    private MenuList menuList;
    private Scanner sc;
    private UserControl userControl;
    private SaleControl saleControl;
    private PropertyControl propertyControl;
    public SaleScreen(){
        menu = Menu.getMenu();
        menuListIndex = menu.creatMenu();
        menuList = menu.getMenuList(menuListIndex);
        sc = UserTools.getScanner();
        userControl = UserControl.getUserControl();
        saleControl = SaleControl.getInstace();
        propertyControl = PropertyControl.getPropertyControl();
    }
    public void saleScreenClient(){
        menuList.removeAll();
        menuList.addOption("Buy",this::newSale);
        menuList.addOption("Sales", this::seeSales);
    }
    public void saleScreenRealEstateManager(){
        menuList.removeAll();
        menuList.addOption("Confirm Sale", this::confirmSale);
        menuList.addOption("Relatory 30 days",this::relatory30Days);
        menuList.addOption("Pending Purchases", this::pendingPurchases);
        menuList.addOption("Show All Confirmed Sales", this::showAllConfirmedSales);
    }
    public int getMenuListIndex(){
        return menuListIndex;
    }
    public void seeSales(Menu menu){
        ArrayList<Sale> sales = saleControl.getSalesOfCpf(userControl.getCpfCurrent());
        for(Sale s: sales) {
            System.out.printf("%s\n", s.getInfo());
        }
    }

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
    public void confirmSale(Menu menu){
        System.out.printf(saleControl.getVisualPendingPurchases() + "\n");
        Sale confirmedSale = saleControl.confirmSale();
        if(confirmedSale != null){
            confirmedSale.setAproved(true);
            Property property = propertyControl.getProperty(confirmedSale.getProductCode());
            property.setAvailability(false);
        }
    }
    public void relatory30Days(Menu menu){
        System.out.printf(saleControl.getVisualRelatory(30)+ "\n");
    }
    public void pendingPurchases(Menu menu){
        System.out.printf(saleControl.getVisualPendingPurchases() + "\n");
    }
    public void showAllConfirmedSales(Menu menu){
        System.out.printf(saleControl.getVisualAprovedPurchases());
    }

}
