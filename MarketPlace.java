import java.util.ArrayList;
/*
The marketplace listing page will show the store, product name, and price of the available goods.
Customers can select a specific product to be taken to that product's page,
which will include a description and the quantity available.
When items are purchased, the quantity available for all users decreases by the amount being purchased.
 */
public class MarketPlace {

    private ArrayList<ShoppingItem> shoppingItemList = new ArrayList<>();


    //CONSTRUCTOR - WITH PARAMETERS
    public MarketPlace(ArrayList<ShoppingItem> shoppingItemList) {
        this.shoppingItemList = shoppingItemList;
    }

    public void setShoppingItemList(ArrayList<ShoppingItem> shoppingItemList) {
        this.shoppingItemList = shoppingItemList;
    }

    public ArrayList<ShoppingItem> getShoppingItemList() {
        return shoppingItemList;
    }

    /*
    PRINTS EACH PRODUCT IN THE ARRAYLIST'S
    STORE, NAME OF PRODUCT, AND PRICE
        Kroger Eggs 3.99
        Walmart Milk 2.99
        Marsh Cheese 2.99
 */


    /*
    TAKES IN THE STRING NAME OF A PRODUCT
    COMPARES THE INPUTTED NAME TO EACH PRODUCT'S NAME IN THE ARRAYLIST
    IF NAME EQUALS (NO MATTER THE CASING) IT RETURNS
        Eggs-
        Description: Egg from chicken
        Quantity: 12
    IF THE NAME DOES NOT EQUAL IT RETURNS
        Sorry, we do not have (searchTerm) at this marketplace
     */

    public String shoppingItemPage(int productID) {
        for (int i = 0; i < this.shoppingItemList.size(); i++) {
            if (shoppingItemList.get(i).getProduct().getId() == productID) {
                return (shoppingItemList.get(i).getProduct().getName() + "-\nDescription: " +
                        this.shoppingItemList.get(i).getProduct().getDescription() +
                        "\nQuantity: " + this.shoppingItemList.get(i).getProduct().getQuantity());
            }
        }
        return("Sorry, we do not have " + productID + " at this marketplace");
    }

    //decrements a product's quantity by one when purchased
    public void isPurchased(String product) throws Exception {
        for (int i = 0; i < this.shoppingItemList.size(); i++) {
            if (shoppingItemList.get(i).getProduct().getName().equalsIgnoreCase(product)) {
                shoppingItemList.get(i).getProduct().
                        setQuantity(shoppingItemList.get(i).getProduct().getQuantity() - 1);
            }
        }
    }


    //SORTS ALPHABETICALLY BY THE NAME OF THE STORE
    public void sortStoresAlphabetically() {
        int productsCounter = shoppingItemList.size();  //3
        ShoppingItem[] shopItemsList = new ShoppingItem[productsCounter];    //new Array of Products
        ShoppingItem temp = new ShoppingItem();

        /*
        convert products in Array products productsList to
        an arrayList called prodList
         */
        for (int i = 0; i < this.shoppingItemList.size(); i++) {    //adds products from arraylist to array
            shopItemsList[i] = this.shoppingItemList.get(i);
        }

        //alphabetize the array by store name
        for (int i = 0; i < productsCounter; i++) {
            //productsList.remove(i);
            for (int j = i + 1; j < productsCounter; j++) {

                // to compare one string with other strings
                if (shopItemsList[i].getProduct().getName().compareTo(shopItemsList[j].getProduct().getName()) > 0) {
                    // swapping
                    temp = shopItemsList[i];
                    shopItemsList[i] = shopItemsList[j];
                    shopItemsList[j] = temp;
                }
            }
        }
        //sets shoppingItemList arraylist equal to the products in array
        for (int i = 0; i < productsCounter; i++) {
            shoppingItemList.set(i, shopItemsList[i]);
            //System.out.println(shoppingItemList.get(i).getProduct().getStore());
        }
    }



    //SORTS THE PRICE BY LOWEST TO HIGHEST
    public void sortPriceLowestToHighest(ArrayList<ShoppingItem> storeItems) {
        int productsCounter = storeItems.size();
        ShoppingItem[] shopItemsList = new ShoppingItem[productsCounter];    //new Array
        ShoppingItem temp = new ShoppingItem();

        //convert to array
        for (int i = 0; i < storeItems.size(); i++) {    //adds products from arraylist to array
            shopItemsList[i] = storeItems.get(i);
        }

        //alphabetize the array by store name
        for (int i = 0; i < productsCounter; i++) {
            for (int j = i + 1; j < productsCounter; j++) {
                // to compare one string with other strings
                if (shopItemsList[i].getProduct().getPrice() > (shopItemsList[j].getProduct().getPrice())) {

                    // swapping
                    temp = shopItemsList[i];
                    shopItemsList[i] = shopItemsList[j];
                    shopItemsList[j] = temp;
                }
            }
        }
        //sets shoppingItemList arraylist equal to the products in array
        for (int i = 0; i < productsCounter; i++) {
            storeItems.set(i, shopItemsList[i]);
            System.out.println(storeItems.get(i));
        }

    }



    //SORTS SHOPPING ITEMS BY ALPHABETICALLY
    public void sortShoppingItem(ArrayList<ShoppingItem> storeItems) {
        //int productsCounter = shoppingItemList.size();  //3
        int productsCounter = storeItems.size();
        ShoppingItem[] shopItemsList = new ShoppingItem[productsCounter];    //new Array of Products
        ShoppingItem temp = new ShoppingItem();


        for (int i = 0; i < storeItems.size(); i++) {    //adds products from arraylist to array
            shopItemsList[i] = storeItems.get(i);
        }

        //alphabetize the array by store name
        for (int i = 0; i < productsCounter; i++) {
            //productsList.remove(i);
            for (int j = i + 1; j < productsCounter; j++) {
                // to compare one string with other strings
                if (shopItemsList[i].getProduct().getName().compareTo(shopItemsList[j].getProduct().getName()) > 0) {
                    // swapping
                    temp = shopItemsList[i];
                    shopItemsList[i] = shopItemsList[j];
                    shopItemsList[j] = temp;
                }
            }
        }
        //sets shoppingItemList arraylist equal to the products in array
        for (int i = 0; i < productsCounter; i++) {
            //System.out.println(shopItemsList[i]);
            storeItems.set(i, shopItemsList[i]);
            //System.out.println(shoppingItemList.get(i).getProduct().getName());
        }


        //sets shoppingItemList arraylist equal to the products in array
        for (int i = 0; i < productsCounter; i++) {
            storeItems.set(i, shopItemsList[i]);
            System.out.println(storeItems.get(i));
        }
    }

    //LISTS STORES IN SHOPPING ITEM LIST
    public void listStores() {
        ClothingMarketPlace mp = new ClothingMarketPlace();
        int listLength = mp.getAllStores().size();
        System.out.println("Stores: ");
        for (int i = 0; i < listLength; i++) {
            System.out.println(mp.getAllStores().get(i).getName());
        }
    }

    //LISTS PRODUCT'S NAME IN SHOPPING ITEM LIST
    public void listShoppingItem() {
        int listLength = shoppingItemList.size();
        System.out.println("Shopping Item: ");
        for (int i = 0; i < listLength; i++) {
            System.out.println(shoppingItemList.get(i));
        }

    }

}
