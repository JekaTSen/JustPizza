package it.unimib.justpizza;

import org.junit.Test;

import it.unimib.justpizza.model.CartItem;
import it.unimib.justpizza.model.Pizza;

import static org.junit.Assert.assertEquals;

public class CartItemTest {

    @Test
    public void totalPrice_isPriceTimesQuantity() {
        Pizza pizza = new Pizza(
                "p001", "Margherita", 6.50, "",
                "normale", "pomodoro", "classica");
        CartItem item = new CartItem(pizza, 2);
        assertEquals(13.0, item.getTotalPrice(), 0.001);
    }

    @Test
    public void quantityOne_equalsUnitPrice() {
        Pizza pizza = new Pizza(
                "p002", "Diavola", 8.00, "",
                "normale", "salame", "piccante");
        CartItem item = new CartItem(pizza, 1);
        assertEquals(8.00, item.getTotalPrice(), 0.001);
        assertEquals("p002", item.getItemId());
        assertEquals("Pizza", item.getItemType());
    }
}