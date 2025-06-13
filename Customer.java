import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class Customer extends User {
    ArrayList<ShoppingItem> cart = new ArrayList<ShoppingItem>();
    ArrayList<ShoppingItem> orders = new ArrayList<ShoppingItem>();

    /**
     * Constructor with empty cart and no orders
     * @param email
     * @param password
     */
    public Customer(String email, String password) {
        super(email, password);
    }

    /**
     * Constructor with cart but no orders
     * @param email
     * @param password
     * @param cart
     */
    public Customer(String email, String password, ArrayList<ShoppingItem> cart) {
        super(email, password);
        this.cart = cart;
    }

    /**
     * Constructor with cart and orders
     * @param email
     * @param password
     * @param cart
     * @param orders
     */
    public Customer(String email, String password, ArrayList<ShoppingItem> cart, ArrayList<ShoppingItem> orders) {
        super(email, password);
        this.cart = cart;
        this.orders = orders;
    }

    // getters and setters
    public ArrayList<ShoppingItem> getCart() {
        return cart;
    }

    public void setCart(ArrayList<ShoppingItem> cart) {
        this.cart = cart;
    }

    public ArrayList<ShoppingItem> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<ShoppingItem> orders) {
        this.orders = orders;
    }

    /**
     * Asks Customer if they would like to add an item to a cart or sort the items shown from the itemList
     * @param marketPlaceItems
     * @param scanner
     * @throws Exception
     */
    public void addToCart(ArrayList<ShoppingItem> marketPlaceItems, Scanner scanner) throws Exception {
        MarketPlace marketPlace = new MarketPlace(marketPlaceItems);
        System.out.println("Enter [1] to add an item to your cart \n" +
                        "Enter [2] to sort all products by price \n" +
                        "Enter [3] to sort all products by name \n" +
                        "Enter [4] to go to a product page \n" +
                        "Enter [5] to go back");
        int buyChoice = scanner.nextInt();
        if(buyChoice == 1) { // add an item to cart
            System.out.println("What is the product ID of the item you would like to buy: ");
            int buyItem = scanner.nextInt(); // buyItem = productID
            scanner.nextLine();
            int index = marketPlaceItems.indexOf(new ShoppingItem(buyItem)); // get index in marketPlaceItems
            if (index == -1) { // make sure selection is valid
                System.out.println("This selection is invalid. Please try again");
            } else {
                ShoppingItem item = marketPlaceItems.get(index);

                System.out.println(item);
                System.out.println("How many do you want to add to cart: "); // ask for quantity
                int buyQuantity = scanner.nextInt();
                scanner.nextLine();

                if (buyQuantity < item.getProduct().getQuantity()) {
                    Product cartProduct = new Product(item.getProduct().getName(),
                            item.getProduct().getDescription(),
                            buyQuantity,
                            item.getProduct().getPrice(),
                            item.getProduct().getId());
                    ShoppingItem cartItem = new ShoppingItem(cartProduct, item.getSellerName(), item.getStoreName());
                    item.getProduct().removeQuantity(buyQuantity);
                    cart.add(cartItem);

                } else {
                    System.out.println("The quantity is too high");
                }
            }
        } else if (buyChoice == 2) {
            marketPlace.sortPriceLowestToHighest(marketPlaceItems);
            addToCart(marketPlaceItems, scanner);

        } else if (buyChoice == 3) {
            marketPlace.sortShoppingItem(marketPlaceItems);
            addToCart(marketPlaceItems, scanner);

        } else if (buyChoice == 4) {
            System.out.println("What is the product id: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            try {
                System.out.println(marketPlace.shoppingItemPage(id));
                addToCart(marketPlaceItems, scanner);
            } catch (Exception e){
                System.out.println("Please enter a number!");
            }

        } else if (buyChoice == 5) {
            return;
        }
    }

    /**
     * displays items
     * @param marketPlaceItems
     */
    public void displayProducts(ArrayList<ShoppingItem> marketPlaceItems) {
        if (marketPlaceItems.size() == 0) {
            System.out.println("No items to be displayed");
        }

        for (ShoppingItem item : marketPlaceItems) {
            System.out.println(item);
        }
    }

    /**
     * filters items by name
     * will show multiple items if there are multiple different stores that each have an item with the same name
     * otherwise will only show one item
     * @param scanner
     * @param marketPlaceItems
     * @return
     */
    public ArrayList<ShoppingItem> filterByName(Scanner scanner, ArrayList<ShoppingItem> marketPlaceItems) {
        ArrayList<ShoppingItem> searchedItems = new ArrayList<>();

        System.out.println("Enter the product name to search: ");
        String productName = scanner.nextLine().toLowerCase();
        for (ShoppingItem item : marketPlaceItems) {
            if (item.getProduct().getName().toLowerCase().contains(productName)) {
                searchedItems.add(item);
            }
        }

        return searchedItems;
    }

    /**
     * searches if a word in the description is in the name of the item
     * @param scanner
     * @param marketPlaceItems
     * @return
     */
    public ArrayList<ShoppingItem> filterByDescription(Scanner scanner, ArrayList<ShoppingItem> marketPlaceItems) {
        ArrayList<ShoppingItem> searchedItems = new ArrayList<>();

        System.out.println("Enter the product description to search: ");
        String productDescription = scanner.nextLine().toLowerCase();
        for (ShoppingItem item : marketPlaceItems) {
            if (item.getProduct().getDescription().toLowerCase().contains(productDescription)) {
                searchedItems.add(item);
            }
        }

        return searchedItems;
    }

    /**
     * returns all items from a specific store
     * @param scanner
     * @param marketPlaceItems
     * @return
     */
    public ArrayList<ShoppingItem> filterByStore(Scanner scanner, ArrayList<ShoppingItem> marketPlaceItems) {
        ArrayList<ShoppingItem> searchedItems = new ArrayList<>();

        System.out.println("Enter the product store to search: ");
        String productStore = scanner.nextLine().toLowerCase();

        for (ShoppingItem item : marketPlaceItems) {
            if (item.getStoreName().toLowerCase().contains(productStore)) {
                searchedItems.add(item);
            }
        }

        return searchedItems;
    }

    /**
     * prints all items in the cart
     */
    public void printCart() {
        if (cart.size() == 0) {
            System.out.println("The Shopping Cart is empty");
        }
        for(ShoppingItem item : cart) {
            System.out.println(item + " | " + item.getProduct().getQuantity());
        }
    }

    /**
     * viewing statistics for customer
     */

    public void viewStatistics(Scanner scanner) {
        Statistics stats = new Statistics();
        System.out.println("Enter the path to the data file");
        String dataFilePath = scanner.nextLine();
        stats.displayCustomerStatistics(stats, dataFilePath);
    }

    /**
     * removes items from the cart
     * @param scanner
     * @param marketPlaceItems
     * @throws Exception
     */
    public void removeFromCart(Scanner scanner, ArrayList<ShoppingItem> marketPlaceItems) throws Exception {

        if (cart.size() == 0) {
            System.out.println("No products in the cart to remove.");
            return;
        }

        System.out.println("Enter the product ID of the product you want to remove: ");
        int removeId = scanner.nextInt();
        scanner.nextLine();
        ShoppingItem removeShoppingItem = new ShoppingItem(removeId);
        int index = cart.indexOf(removeShoppingItem);

        if (index == -1) {
            System.out.println("This item is not in the cart");
            return;
        }

        removeShoppingItem = cart.get(index);
        cart.remove(index);
        index = marketPlaceItems.indexOf(removeShoppingItem);
        if (index != -1) {
            ShoppingItem item = marketPlaceItems.get(index);
            item.getProduct().setQuantity(item.getProduct().getQuantity() + removeShoppingItem.getProduct().getQuantity());
        }
    }

    /**
     * prints the orders
     */
    public void printOrders() {
        if(orders.size() == 0) {
            System.out.println("You haven't made any orders");
        }
        for(ShoppingItem item : orders) {
            System.out.println(item + " | " + item.getProduct().getQuantity());
        }
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
     * exports all orders to a file (filename inputted by the user)
     * @param scanner
     */
    public void exportPurchaseHistory(Scanner scanner) {
        System.out.println("Enter export filename: ");
        String fileName = scanner.nextLine();
        try {
            FileWriter f = new FileWriter(fileName);
            for (ShoppingItem item: orders) {
                Product p = item.getProduct();
                f.write(item.getStoreName() + " | " + p.getId() + " | " + p.getName() + " | " + p.getDescription() +
                        " | " + p.getQuantity() + " | " + p.getPrice());
                f.write(System.lineSeparator());
            }
            f.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * buys all items in the cart
     */
    public void checkOut() {
        orders.addAll(cart);
        cart = new ArrayList<>();
        System.out.println("All items from cart have been purchased");
    }

    /**
     * All actions performable by the Customer
     * @param cmp
     */
    public void actions(ClothingMarketPlace cmp){
        MarketPlace marketPlace = new MarketPlace(cmp.getMarketPlaceItems());
        ArrayList<ShoppingItem> marketPlaceItems = cmp.getMarketPlaceItems();
        ArrayList<Store> marketPlaceStores = cmp.getAllStores();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Enter [1] to list all products. \n" +
                        "Enter [2] to search product by name.\n" +
                        "Enter [3] to search product by description. \n" +
                        "Enter [4] to search product by store.\n" +
                        "Enter [5] to view shopping cart \n" +
                        "Enter [6] to remove product from cart \n" +
                        "Enter [7] to look at purchase history \n" +
                        "Enter [8] to export purchase history \n" +
                        "Enter [9] to checkout \n" +
                        "Enter [10] to change password \n" +
                        "Enter [11] to go back \n");
                int customerChoice = scanner.nextInt();
                scanner.nextLine();

                if (customerChoice == 1) { // if user choose 1
                    marketPlace.listShoppingItem();
                    addToCart(marketPlaceItems, scanner);
                } else if (customerChoice == 2) { // if user choose 2
                    ArrayList<ShoppingItem> searchByNames = filterByName(scanner, marketPlaceItems);
                    displayProducts(searchByNames);
                    addToCart(searchByNames, scanner);
                } else if (customerChoice == 3) { // if user choose 3
                    ArrayList<ShoppingItem> searchByDescription = filterByDescription(scanner, marketPlaceItems);
                    displayProducts(searchByDescription);
                    addToCart(searchByDescription, scanner);
                } else if (customerChoice == 4) { // if user choose 4
                    ArrayList<ShoppingItem> searchByStore = filterByStore(scanner, marketPlaceItems);
                    displayProducts(searchByStore);
                    addToCart(searchByStore, scanner);
                } else if (customerChoice == 5) { // if user choose 5
                    printCart();
                } else if (customerChoice == 6) { // if user choose 6
                    printCart();
                    removeFromCart(scanner, marketPlaceItems);
                } else if (customerChoice == 7) { // if user choose 7
                    printOrders();
                } else if (customerChoice == 8) { // if user choose 8
                    exportPurchaseHistory(scanner);
                } else if (customerChoice == 9) { // if user choose 9
                    printCart();
                    checkOut();
                } else if (customerChoice == 10) { // if user choose 10
                    changePassword(scanner);
                } else if (customerChoice == 11) { // if user chooses 11
                    break;
                } else { // if user inputs another number
                    System.out.println("Invalid choice, please try again.");
                }
            } catch (Exception e) { // if user inputs a non-integer value
                System.out.println("Please enter one of the above options");
                scanner.nextLine();
            }
        }
    }

}
