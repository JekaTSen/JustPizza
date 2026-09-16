package it.unimib.justpizza.datasource;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import it.unimib.justpizza.model.Drink;
import it.unimib.justpizza.model.Pizza;
import it.unimib.justpizza.model.Side;

public class MenuRemoteDataSource {

    public interface Callback {
        void onSuccess(List<Pizza> pizzas, List<Drink> drinks, List<Side> sides);
        void onError(Exception e);
    }

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void fetchMenu(Callback callback) {
        db.collection("menu")
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<Pizza> pizzas = new ArrayList<>();
                    List<Drink> drinks = new ArrayList<>();
                    List<Side> sides = new ArrayList<>();

                    for (DocumentSnapshot doc : snapshot.getDocuments()) {
                        String category = doc.getString("category");
                        if (category == null) {
                            category = "pizza";
                        }

                        switch (category) {
                            case "drink":
                                Drink drink = documentToDrink(doc);
                                if (drink != null) drinks.add(drink);
                                break;
                            case "side":
                                Side side = documentToSide(doc);
                                if (side != null) sides.add(side);
                                break;
                            default:
                                Pizza pizza = documentToPizza(doc);
                                if (pizza != null) pizzas.add(pizza);
                                break;
                        }
                    }
                    callback.onSuccess(pizzas, drinks, sides);
                })
                .addOnFailureListener(callback::onError);
    }

    private double readPrice(DocumentSnapshot doc) {
        Object raw = doc.get("price");
        if (raw instanceof Number) {
            return ((Number) raw).doubleValue();
        }
        return 0;
    }

    private String readId(DocumentSnapshot doc) {
        String id = doc.getString("id");
        return id != null ? id : doc.getId();
    }

    private Pizza documentToPizza(DocumentSnapshot doc) {
        String name = doc.getString("name");
        if (name == null) return null;
        String imageUrl = doc.getString("imageUrl");
        String type = doc.getString("type");
        String ingredients = doc.getString("ingredients");
        String description = doc.getString("description");
        return new Pizza(
                readId(doc),
                name,
                readPrice(doc),
                imageUrl != null ? imageUrl : "",
                type != null ? type : "",
                ingredients != null ? ingredients : "",
                description != null ? description : ""
        );
    }

    private Drink documentToDrink(DocumentSnapshot doc) {
        String name = doc.getString("name");
        if (name == null) return null;
        String imageUrl = doc.getString("imageUrl");
        String description = doc.getString("description");
        return new Drink(
                readId(doc),
                name,
                readPrice(doc),
                imageUrl != null ? imageUrl : "",
                description != null ? description : ""
        );
    }

    private Side documentToSide(DocumentSnapshot doc) {
        String name = doc.getString("name");
        if (name == null) return null;
        String imageUrl = doc.getString("imageUrl");
        String description = doc.getString("description");
        return new Side(
                readId(doc),
                name,
                readPrice(doc),
                imageUrl != null ? imageUrl : "",
                description != null ? description : ""
        );
    }
}