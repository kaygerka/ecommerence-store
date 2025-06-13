import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ClothingMarketPlace {

    private static final String CLOTHING_MARKET_PLACE_FILE = "ClothingMarketPlaceFile.json";
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Seller> sellerList = new ArrayList<>();
    private ArrayList<Customer> customerList = new ArrayList<>();
    private int productCount;
    private int storeCount;

    // constructor
    public ClothingMarketPlace() {
        this.productCount = 1;
        this.storeCount = 1;
    }

    // getters and setters
    public int getProductCount() {
        return productCount;
    }

    public int getStoreCount() {
        return storeCount;
    }

    public ArrayList<Seller> getSellerList() {
        return sellerList;
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public void setStoreCount(int storeCount) {
        this.storeCount = storeCount;
    }

    public void tickProductCount() {
        this.productCount++;
    }

    public void tickStoreCount() {
        this.storeCount++;
    }

    public void addUser(User u) {
        if (u instanceof Customer) {
            if (!customerList.contains(u)) {
                customerList.add((Customer) u);
            }
        } else {
            if (!sellerList.contains(u)) {
                sellerList.add((Seller) u);
            }
        }
    }

    public ArrayList<ShoppingItem> getMarketPlaceItems() {
        ArrayList<ShoppingItem> shoppingItems = new ArrayList<>();


        for (Seller seller : sellerList) {
            if (seller != null) {
                for (Store store : seller.getStores()) {
                    for (Product product : store.getProducts()) {
                        shoppingItems.add(new ShoppingItem(product, seller.getEmail(), store.getName()));
                    }
                }
            }
        }

        return shoppingItems;
    }


    public ArrayList<Store> getAllStores() {
        ArrayList<Store> stores = new ArrayList<>();

        for (Seller seller : sellerList) {
            if (seller != null) {
                stores.addAll(seller.getStores());
            }
        }
        return stores;

    }


    public static ClothingMarketPlace loadMarketPlace() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ClothingMarketPlace mp = null;

        /// JOptionPane.showMessageDialog(null, "Loading market place...");
        try {
            FileReader reader = new FileReader(CLOTHING_MARKET_PLACE_FILE);
            mp = gson.fromJson(reader, ClothingMarketPlace.class);
            reader.close();
            JOptionPane.showMessageDialog(null, "Loading market place...\nLoading complete!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Loading market place...\nMarketplace file not found...");
        }
        if (mp == null) {
            JOptionPane.showMessageDialog(null, "Creating new market place...");
            mp = new ClothingMarketPlace();
        }
        return mp;
    }

    public void saveMarketPlace() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter writer = new FileWriter(CLOTHING_MARKET_PLACE_FILE);
            gson.toJson(this, writer);
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error occurred while saving the marketplace");
        }
    }

    public static void main(String[] args) {
        ClothingMarketPlace mp = loadMarketPlace();

        while (true) {
            ArrayList<User> allUsers = new ArrayList<>();
            allUsers.addAll(mp.getCustomerList());
            allUsers.addAll(mp.getSellerList());
            User logInUser = User.authentication(allUsers);

            if (logInUser == null) {
                JOptionPane.showMessageDialog(null, "Thanks for using the Clothing Market Place.\nGoodbye!");
                return;
            }

            mp.addUser(logInUser);
            if (logInUser instanceof Customer) {
                Customer newCustomer = (Customer) logInUser;
                JOptionPane.showMessageDialog(null, "Welcome Customer " + newCustomer.getEmail());
                newCustomer.actions(mp);
            } else {
                Seller newSeller = (Seller) logInUser;
                JOptionPane.showMessageDialog(null, "Welcome Seller " + newSeller.getEmail());
                newSeller.actions(mp);
            }

            mp.saveMarketPlace();
        }
    }


}
