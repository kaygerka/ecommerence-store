import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Project 04 --Seller
 *
 * This program models each seller and the actions they can perform
 *
 * @author Harini Muthu
 *
 * @version November 12, 2023
 *
 */
public class Seller extends User {

    private ArrayList<Store> stores = new ArrayList<Store>();
    private static final String INVALID_FORMAT = "StoreName, ProductName, Description, Quantity, Price";

    public Seller(String email, String password) {
        super(email, password);
    }

    public Seller(String email, String password, ArrayList<Store> stores) {
        super(email, password);
        this.stores = stores;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores() {
        this.stores = stores;
    }

    /**
     * adds a store so the list of stores if the store list doesn't already contain the store. If it does, return null
     * @param store
     * @return
     */
    public Store addStore(Store store){
        if (!stores.contains(store)) {
            stores.add(store);
            return store;
        } else {
            return null;
        }
    }

    /**
     * creates a store
     * @param store
     * @return
     */
    public Store addStore(String store) {
        Store newStore = new Store(getEmail(), store);
        return addStore(newStore);
    }

    public Store getStore(String store)  {
        int index = stores.indexOf(new Store(getEmail(), store));
        if (index == -1)
            return null;
        return stores.get(index);
    }

    public Store getStore(int id) {
        int index = stores.indexOf(new Store(id));
        if (index == -1)
            return null;
        return stores.get(index);
    }

    /**
     * asks for store name to create store
     * @param scanner
     * @return
     */
    public Store createStore(Scanner scanner) {
        System.out.println("Enter your store name: ");
        String storeName = scanner.nextLine();
        Store newStore = addStore(storeName);

        if (newStore == null) {
            System.out.println("Store name already exists. Please choose another store name");
            return null;
        }

        return newStore;
    }

    /**
     * opens a store
     * @param scanner
     * @return
     */
    public Store loginStore(Scanner scanner) {
        System.out.println("Enter your store ID: ");
        int storeID = scanner.nextInt();
        Store newStore = getStore(storeID);

        if (newStore == null) {
            System.out.println("Store name already exists. Please choose another store name");
            return null;
        }

        return newStore;
    }

    /**
     * changes user password
     * @param scanner
     */
    public void changePassword(Scanner scanner) {
        System.out.println("Enter new password: ");
        String newPassword = scanner.nextLine();
        setPassword(newPassword);
    }

    /**
     * viewing statistics for seller
     * @param scanner
     */

    public void viewStatistics(Scanner scanner) {
        Statistics stats = new Statistics();
        System.out.println("Enter the path to the data file");
        String dataFilePath = scanner.nextLine();
        stats.displaySellerStatistics(stats, dataFilePath);
    }

    /**
     * imports data from a file
     * @param scanner
     * @param mp
     */
    public void importProduct(Scanner scanner, ClothingMarketPlace mp) {
        System.out.println("Enter import filename: ");
        String fileName = scanner.nextLine();
        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.isEmpty())
                    continue;
                List<String> values = Arrays.asList(line.split(";"));
                if (values.size() < 5)
                    throw new Exception(INVALID_FORMAT);
                String storeName = values.get(0).trim();
                String productName = values.get(1).trim();
                String description = values.get(2).trim();
                int quantity = Integer.parseInt(values.get(3).trim());
                double price = Double.parseDouble(values.get(4).trim());

                Store store = getStore(storeName);
                if (store == null) {
                    store = addStore(storeName);
                    store.setId(mp.getStoreCount());
                    mp.tickStoreCount();
                }

                Product p = store.getProduct(productName);
                if (p == null) {
                    p = new Product(productName, description, quantity, price, 0);
                    p.setId(mp.getProductCount());
                    mp.tickProductCount();
                    store.addProduct(p);
                } else {
                    p.setDescription(description);
                    p.setQuantity(quantity);
                    p.setPrice(price);
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * writes a file
     * @param scanner
     */
    public void exportProduct(ClothingMarketPlace mp, Scanner scanner) {
        System.out.println("Enter the store ID for which you would like to print stats");
        int storeID = scanner.nextInt();
        scanner.nextLine();

        Store searchedStore = null;
        for(Store store : getStores()) {
            if (store.getId() == storeID) {
                searchedStore = store;
                break;
            }
        }

        if (searchedStore == null) {
            System.out.println("Store ID did not match any stores.");
            return;
        }

        System.out.println("Enter export filename: ");
        String fileName = scanner.nextLine();
        try {
            FileWriter f = new FileWriter(fileName);
            ArrayList<ShoppingItem> allOrders = new ArrayList<>();
            for (Customer c : mp.getCustomerList()) {
                allOrders.addAll(c.getOrders());
            }

            for (int i = 0; i< allOrders.size(); i++) {
                if (allOrders.get(i).getStoreName().equals(searchedStore.getName()) &&
                        allOrders.get(i).getSellerName().equals(searchedStore.getSellerEmail())) {
                    f.write(i);
                }
            }
            f.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * returns an arrayList of the store names (strings)
     * @return
     */
    public ArrayList<String> getStoreNames() {
        ArrayList<String> storeNames = new ArrayList<>();
        for (Store s: stores) {
            storeNames.add(s.getName());
        }
        return storeNames;
    }

    public void printStats(ClothingMarketPlace mp, Scanner scanner) {
        System.out.println("Enter the store ID for which you would like to print stats");
        int storeID = scanner.nextInt();
        scanner.nextLine();

        Store searchedStore = null;
        for(Store store : getStores()) {
            if (store.getId() == storeID) {
                searchedStore = store;
                break;
            }
        }

        if (searchedStore == null) {
            System.out.println("Store ID did not match any stores.");
            return;
        }

        ArrayList<ShoppingItem> allOrders = new ArrayList<>();
        for (Customer c : mp.getCustomerList()) {
            allOrders.addAll(c.getOrders());
        }

        for (ShoppingItem item : allOrders) {
            if (item.getStoreName().equals(searchedStore.getName()) && item.getSellerName().equals(searchedStore.getSellerEmail())) {
                System.out.println(item);
            }
        }
    }


    /**
     * All Actions done by seller
     * @param mp
     */
    public void actions(ClothingMarketPlace mp) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                if (stores.size() == 0) {
                    System.out.println("You have no stores");
                } else {
                    System.out.println("Your stores are:"); // prints stores
                    for (Store store : stores) {
                        System.out.println(store.getId() + ". " + store.getName());
                    }
                }

                // options
                System.out.print("Enter [1] to create a store.\n" +
                        "Enter [2] to open a store. \n" +
                        "Enter [3] to import file \n" +
                        "Enter [4] to export stats \n" +
                        "Enter [5] to print store stats \n" +
                        "Enter [6] to change password\n" +
                        "Enter [7] to go back.\n");
                int sellerChoice = scanner.nextInt();
                scanner.nextLine();

                if (sellerChoice == 1) { // if seller chooses 1
                    Store store = createStore(scanner);

                    if (store != null) {
                        store.setId(mp.getStoreCount());
                        mp.tickStoreCount();
                    }
                } else if(sellerChoice == 2) { // if seller chooses 2
                    System.out.println("Your stores are:");
                    for (Store store : stores) {
                        System.out.println(store.getId() + ". " + store.getName());
                    }

                    Store store = loginStore(scanner);
                    if (store != null) {
                        store.actions(mp);
                    }

                } else if (sellerChoice == 3) { // if seller chooses 3
                    importProduct(scanner, mp);
                } else if(sellerChoice == 4) { // if seller choose 4
                    exportProduct(mp, scanner);
                } else if (sellerChoice == 5) { // if seller chooses 5
                    printStats(mp, scanner);
                } else if (sellerChoice == 6) { // if seller chooses 6
                    changePassword(scanner);
                } else if (sellerChoice == 7) {
                    break;
                } else { // if seller chooses anything else
                    System.out.println("Invalid choice. Please try again.");
                }

            } catch (Exception e) { // if seller enters a non integer-value
                System.out.println("Please input one of the above options");
                scanner.nextLine();
            }
        }
    }

}
