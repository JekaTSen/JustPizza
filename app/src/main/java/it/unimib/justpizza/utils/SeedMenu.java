package it.unimib.justpizza.utils;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeedMenu {

    public static void uploadAll() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        for (Map<String, Object> item : allItems()) {
            String id = (String) item.get("id");
            db.collection("menu").document(id).set(item);
        }
    }

    private static Map<String, Object> item(String id, String category, String name,
                                            double price, String imageUrl, String description,
                                            String type, String ingredients) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", id);
        m.put("category", category);
        m.put("name", name);
        m.put("price", price);
        m.put("imageUrl", imageUrl);
        m.put("description", description);
        m.put("type", type);
        m.put("ingredients", ingredients);
        return m;
    }

    private static List<Map<String, Object>> allItems() {
        List<Map<String, Object>> list = new ArrayList<>();

        // --- PIZZE ---
        list.add(item("p001", "pizza", "Margherita", 6.50,
                "https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=400&h=300&fit=crop",
                "La classica", "normale", "Pomodoro, mozzarella, basilico"));
        list.add(item("p002", "pizza", "Diavola", 8.00,
                "https://images.unsplash.com/photo-1628840042765-356cda07504e?w=400&h=300&fit=crop",
                "Piccante", "normale", "Pomodoro, mozzarella, salame piccante"));
        list.add(item("p003", "pizza", "Marinara", 6.00,
                "https://images.unsplash.com/photo-1593560708920-61dd98c46a4e?w=400&h=300&fit=crop",
                "Senza mozzarella", "normale", "Pomodoro, aglio, origano, olio"));
        list.add(item("p004", "pizza", "Quattro Formaggi", 8.50,
                "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400&h=300&fit=crop",
                "Bianca", "normale", "Mozzarella, gorgonzola, fontina, parmigiano"));
        list.add(item("p005", "pizza", "Prosciutto e Funghi", 8.00,
                "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400&h=300&fit=crop",
                "Classica farcita", "normale", "Pomodoro, mozzarella, prosciutto cotto, funghi"));
        list.add(item("p006", "pizza", "Capricciosa", 9.00,
                "https://images.unsplash.com/photo-1604068549290-dea0d8ed9ca5?w=400&h=300&fit=crop",
                "Ricca", "normale", "Pomodoro, mozzarella, cotto, funghi, carciofi, olive"));
        list.add(item("p007", "pizza", "Quattro Stagioni", 9.00,
                "https://images.unsplash.com/photo-1571407970349-bc81e7e912e8?w=400&h=300&fit=crop",
                "Quattro gusti", "normale", "Pomodoro, mozzarella, cotto, funghi, carciofi, olive"));
        list.add(item("p008", "pizza", "Vegetariana", 8.00,
                "https://images.unsplash.com/photo-1565299507177-b0ac66763828?w=400&h=300&fit=crop",
                "Verdure grigliate", "normale", "Pomodoro, mozzarella, zucchine, melanzane, peperoni"));
        list.add(item("p009", "pizza", "Bufala", 9.50,
                "https://images.unsplash.com/photo-1571997478779-2adcbbe9ab2f?w=400&h=300&fit=crop",
                "Mozzarella di bufala", "normale", "Pomodoro, bufala, basilico"));
        list.add(item("p010", "pizza", "Calzone", 9.00,
                "https://images.unsplash.com/photo-1534308983496-4fabb1a015ee?w=400&h=300&fit=crop",
                "Ripieno", "calzone", "Pomodoro, mozzarella, prosciutto, ricotta"));

        // --- DRINK ---
        list.add(item("d001", "drink", "Coca-Cola 33cl", 2.50,
                "https://images.unsplash.com/photo-1629203851122-3726ecdf080e?w=400&h=300&fit=crop",
                "Lattina", "", ""));
        list.add(item("d002", "drink", "Fanta 33cl", 2.50,
                "https://images.unsplash.com/photo-1624517452488-04869289c4ca?w=400&h=300&fit=crop",
                "Lattina", "", ""));
        list.add(item("d003", "drink", "Acqua naturale 50cl", 1.50,
                "https://images.unsplash.com/photo-1548835326-97109d93639a?w=400&h=300&fit=crop",
                "Bottiglia", "", ""));
        list.add(item("d004", "drink", "Acqua frizzante 50cl", 1.50,
                "https://images.unsplash.com/photo-1548835326-97109d93639a?w=400&h=300&fit=crop",
                "Bottiglia", "", ""));
        list.add(item("d005", "drink", "Birra Moretti 33cl", 3.50,
                "https://images.unsplash.com/photo-1608270586620-248524c67de9?w=400&h=300&fit=crop",
                "Bottiglia", "", ""));
        list.add(item("d006", "drink", "Birra Ichnusa 33cl", 4.00,
                "https://images.unsplash.com/photo-1535958636474-b021ee887b13?w=400&h=300&fit=crop",
                "Bottiglia", "", ""));

        // --- SIDE ---
        list.add(item("s001", "side", "Patatine fritte", 3.50,
                "https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=400&h=300&fit=crop",
                "Porzione", "", "Patate, sale"));
        list.add(item("s002", "side", "Gnocco fritto", 4.50,
                "https://images.unsplash.com/photo-1608039755401-742074f0548d?w=400&h=300&fit=crop",
                "Porzione", "", "Impasto fritto"));
        list.add(item("s003", "side", "Olive ascolane", 4.00,
                "https://images.unsplash.com/photo-1505253716362-afaea1d3d27e?w=400&h=300&fit=crop",
                "Porzione", "", "Olive impanate"));

        return list;
    }
}