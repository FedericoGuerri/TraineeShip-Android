package com.unifi.federicoguerri.traineeship_android.core.database_active_android;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="Prices")
public class DatabasePrice extends Model {
    @Column(name = "price")
    public String price;
    @Column(name = "path")
    public String path;
    @Column(name = "id_")
    public int id;

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
