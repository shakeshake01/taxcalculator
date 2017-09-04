package max.taxcalculator.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Basket {

    List<BasketItem> items;

    public Basket() {
        items = new ArrayList<>();
    }

    public void addItem(double quantity, Item item) {
        if (quantity < 1) {
            throw new InvalidParameterException("Quantity must be a positive number");
        }
        BasketItem basketItem = new BasketItem(quantity, item);
        int index = this.items.indexOf(basketItem);
        if (index >= 0) {
            basketItem = items.get(index);
            basketItem.setQuantity(quantity + basketItem.getQuantity());
        } else {
            this.items.add(basketItem);
        }
    }

    public List<BasketItem> getItems() {
        return items;
    }
}
