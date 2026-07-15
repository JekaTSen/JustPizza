package it.unimib.justpizza.model;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "sides")
public class Side extends MenuItem {
    //patatine e altro..
    public Side(){}

    @Ignore
    public Side(String id, String name, double price, String imageURL, String description){
        super(id, name, price, imageURL, description);
    }

}
