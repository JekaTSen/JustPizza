package it.unimib.justpizza.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import it.unimib.justpizza.model.CartItem;
import it.unimib.justpizza.model.Drink;
import it.unimib.justpizza.model.Order;
import it.unimib.justpizza.model.Pizza;
import it.unimib.justpizza.model.Side;
import it.unimib.justpizza.utils.Constants;
import it.unimib.justpizza.utils.Converters;

@TypeConverters(Converters.class)
@Database(entities = {Pizza.class, Drink.class, Side.class, CartItem.class, Order.class},
version = 3,
exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract PizzaDAO pizzaDao();
    public abstract DrinkDAO drinkDao();
    public abstract SideDAO sideDao();
    public abstract CartDAO cartDao();
    public abstract OrderDAO orderDao();


    public static synchronized AppDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, Constants.JUSTPIZZA_DATABASE)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
