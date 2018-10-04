package com.unifi.federicoguerri.traineeship_android.core.database;


import com.reactiveandroid.Model;
import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;

@Table(name = "Prices", database = DatabaseReactiveAndroid.class)
public class DatabasePrice extends Model {
    @Column(name = "price")
    public String price;
    @Column(name = "path")
    public String path;
    @Column(name = "id_")
    public int id;
    @PrimaryKey
    private Long tableID;

    public DatabasePrice(){
        super();
    }

    public DatabasePrice(String price, String path,int id){
        super();
        this.price = price;
        this.path = path;
        this.id=id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (this.getClass()!=obj.getClass()) {
            return false;
        }
        DatabasePrice otherPrice = (DatabasePrice) obj;
        return this.getPriceId().equals(otherPrice.getPriceId());
    }

    public Integer getPriceId(){
        return id;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
