package applica.guards.domain.model;

import applica.framework.IEntity;
import applica.framework.data.hibernate.annotations.IgnoreMapping;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Iaco on 18/10/15.
 */
public class Place extends IEntity implements Serializable{

    String name;
    String address;
    Date dateOfGuarding;
    Date finishOfGuarding;

    public Date getFinishOfGuarding() {
        return finishOfGuarding;
    }

    public void setFinishOfGuarding(Date finishOfGuarding) {
        this.finishOfGuarding = finishOfGuarding;
    }


   transient String timeOfGuarding;
    transient String timeFinishOfGuarding;

    public String getTimeFinishOfGuarding() {
        if ( finishOfGuarding != null ) {
            SimpleDateFormat printFormat = new SimpleDateFormat("HH:mm");
            return printFormat.format(finishOfGuarding);
        } else {
            return " ND";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfGuarding() {
        return dateOfGuarding;
    }

    public String getTimeOfGuarding(){
        SimpleDateFormat printFormat = new SimpleDateFormat("HH:mm");
        return printFormat.format(dateOfGuarding);
    }
    public void setDateOfGuarding(Date dateOfGuarding) {
        this.dateOfGuarding = dateOfGuarding;
    }

    public String toString(){
        return name;
    }
}
