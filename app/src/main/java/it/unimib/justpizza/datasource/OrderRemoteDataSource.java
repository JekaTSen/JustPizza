package it.unimib.justpizza.datasource;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unimib.justpizza.model.Order;

public class OrderRemoteDataSource {

    public interface ListCallback {
        void onSuccess(List<Order> orders);
        void onError(Exception e);
    }

    public interface WriteCallback {
        void onSuccess();
        void onError(Exception e);
    }

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void uploadOrder(Order order, WriteCallback callback) {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", order.getUserId());
        data.put("totalPrice", order.getTotalPrice());
        data.put("itemsSummary", order.getItemsSummary());
        data.put("orderDate", order.getOrderDate() != null ? order.getOrderDate() : new Date());

        db.collection("orders")
                .add(data)
                .addOnSuccessListener(ref -> {
                    if (callback != null) callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onError(e);
                });
    }

    public void fetchOrders(String userId, ListCallback callback) {
        if (userId == null || userId.isEmpty()) {
            callback.onSuccess(new ArrayList<>());
            return;
        }

        db.collection("orders")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<Order> orders = new ArrayList<>();
                    for (DocumentSnapshot doc : snapshot.getDocuments()) {
                        Order order = documentToOrder(doc);
                        if (order != null) {
                            orders.add(order);
                        }
                    }
                    callback.onSuccess(orders);
                })
                .addOnFailureListener(callback::onError);
    }

    private Order documentToOrder(DocumentSnapshot doc) {
        String userId = doc.getString("userId");
        Double total = doc.getDouble("totalPrice");
        String summary = doc.getString("itemsSummary");
        Date date = doc.getDate("orderDate");

        if (userId == null || total == null) {
            return null;
        }

        Order order = new Order(userId, total, summary != null ? summary : "");
        if (date != null) {
            order.setOrderDate(date);
        }
        return order;
    }
}