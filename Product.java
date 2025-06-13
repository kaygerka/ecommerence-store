import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Product {
    private String name;
    private String description;
    private int quantity;
    private double price;
    private int id;

    public Product(int id) {
        this.id = id;
    }

    public Product(String name, String description, int quantity, double price, int id) throws Exception {
        this.name = name;
        this.description = description;
        setQuantity(quantity);
        setPrice(price);
        this.id = id;
    }

    /**
     * constructor if information is entered in a string format
     * @param csv
     * @throws Exception
     */
    public Product(String csv) throws Exception {
        List<String> values = Arrays.asList(csv.split(";"));
        if (values.size() < 5)
            System.out.println("Format must be : name; description; quantity; price; ID");
        this.name = values.get(0).trim();
        this.description = values.get(1).trim();
        setQuantity(Integer.parseInt(values.get(2).trim()));
        setPrice(Double.parseDouble(values.get(3).trim()));
        this.id = Integer.parseInt(values.get(4).trim());
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) throws Exception {
        if (quantity < 0)
            System.out.println("Quantity can't be negative");
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws Exception {
        if (price <= 0)
            System.out.println("Price must be greater than 0");
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Product))
            return false;
        Product p = (Product)o;
        return id == p.getId();
    }

    /**
     * removes a quantity
     * @param removeQuantity
     * @throws Exception
     */
    public void removeQuantity(int removeQuantity) throws Exception {
        setQuantity(getQuantity() - removeQuantity);
    }

    @Override
    public String toString() {
        return String.format("%20s | %.2f", name, price);
    }

    /**
     * All actions performable by Store
     */
    public void actions() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Your product information is: ");
                System.out.println(this);

                System.out.print(" Enter [1] to modify description.\n" +
                                "Enter [2] to modify quantity.\n" +
                                "Enter [3] to modify price.\n" +
                                "Enter [4] to back.\n");
                int sellerChoice = scanner.nextInt();
                scanner.nextLine();

                if (sellerChoice == 1) { // if user chooses 1
                    System.out.println("Enter the new description: ");
                    String newDescription = scanner.nextLine();
                    setDescription(newDescription); // sets description
                } else if (sellerChoice == 2) { // if user chooses 2
                    System.out.println("Enter the new quantity: ");
                    int newQuantity = scanner.nextInt();
                    scanner.nextLine();
                    try { // makes sure quantity is an integer
                        setQuantity(newQuantity); // sets quantity
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (sellerChoice == 3) { // if seller choose 3
                    System.out.println("Enter the new price: ");
                    double newPrice = scanner.nextDouble();
                    scanner.nextLine();
                    try { // makes sure quantity is a double
                        setPrice(newPrice); // sets new price
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (sellerChoice == 4) { // if seller choose 4
                    break;
                } else { //if seller chooses another number
                    System.out.println("Invalid choice, please try again.");
                }
            } catch (Exception e) { // if seller inputs a non-integer value
                System.out.println("Please input one of the above options.");
                scanner.nextLine();
            }
        }
    }
}
