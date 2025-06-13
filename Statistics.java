import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    private final ArrayList<ShoppingItem> shoppingItems;

    public Statistics() {
        this.shoppingItems = new ArrayList<>();
    }

    public void addShoppingItem(ShoppingItem shoppingItem) {
        shoppingItems.add(shoppingItem);
    }

    public void readDataFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            ArrayList<String> fileContents = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContents.add(line);
                if (line != null) {
                    String[] data = line.split(";");
                    if (data.length == 7) {
                        Product product = new Product(fileName);
                        ShoppingItem shoppingItem = new ShoppingItem(product, data[5], data[0]);
                        addShoppingItem(shoppingItem);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean displayCustomerStatistics(Statistics statistics, String customerOutputPath) {
        try (PrintWriter fileWriter = new PrintWriter(new FileWriter(customerOutputPath, true))) {
            Map<String, Integer> productsSoldByStore = new HashMap<>();
            Map<String, List<String>> productsPurchasedByCustomer = new HashMap<>();

            for (ShoppingItem item : statistics.shoppingItems) {
                String storeName = item.getStoreName();
                Product product = item.getProduct();

                if (product != null) {
                    String productName = product.getName();
                    String customerName = item.getSellerName();

                    productsSoldByStore.put(storeName, productsSoldByStore.getOrDefault(storeName, 0) + 1);

                    if (!productsPurchasedByCustomer.containsKey(customerName)) {
                        productsPurchasedByCustomer.put(customerName, new ArrayList<>());
                    }
                    productsPurchasedByCustomer.get(customerName).add(productName);
                }
            }

            fileWriter.println("\nProducts Sold by Store:");
            System.out.println("\nProducts Sold by Store:");
            for (Map.Entry<String, Integer> entry : productsSoldByStore.entrySet()) {
                String header = "Store: " + entry.getKey() + ", Products Sold: " + entry.getValue();
                fileWriter.println(header);
                System.out.println(header);
            }

            fileWriter.println("\nProducts Purchased by Customer:");
            System.out.println("\nProducts Purchased by Customer:");
            for (Map.Entry<String, List<String>> entry : productsPurchasedByCustomer.entrySet()) {
                String header = "Customer: " + entry.getKey() + ", Products Purchased: " + entry.getValue();
                fileWriter.println(header);
                System.out.println(header);
            }

            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean displaySellerStatistics(Statistics statistics, String sellerOutputPath) {
        try (PrintWriter fileWriter = new PrintWriter(new FileWriter(sellerOutputPath, true))) {
            Map<String, Integer> itemsPurchasedByCustomer = new HashMap<>();
            Map<String, Integer> salesByProduct = new HashMap<>();

            for (ShoppingItem item : statistics.shoppingItems) {
                Product product = item.getProduct();

                if (product != null) {
                    String productName = product.getName();
                    String customerName = item.getSellerName();

                    itemsPurchasedByCustomer.put(customerName, itemsPurchasedByCustomer.getOrDefault(customerName, 0) + 1);
                    salesByProduct.put(productName, salesByProduct.getOrDefault(productName, 0) + 1);
                }
            }

            fileWriter.println("\nItems Purchased by Customer:");
            System.out.println("\nItems Purchased by Customer:");
            for (Map.Entry<String, Integer> entry : itemsPurchasedByCustomer.entrySet()) {
                String stats = "Customer: " + entry.getKey() + ", Items Purchased: " + entry.getValue();
                fileWriter.println(stats);
                System.out.println(stats);
            }

            fileWriter.println("\nSales by Product:");
            System.out.println("\nSales by Product:");
            for (Map.Entry<String, Integer> entry : salesByProduct.entrySet()) {
                String stats = "Product: " + entry.getKey() + ", Sales: " + entry.getValue();
                fileWriter.println(stats);
                System.out.println(stats);
            }

            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
