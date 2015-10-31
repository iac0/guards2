package applica.guards.domain.model;

import applica.framework.IEntity;
import applica.framework.annotations.OneToMany;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Iaco on 18/10/15.
 */
public class Guard extends IEntity implements Serializable{
    String name;
    String surname;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String guardia(){
        return String.format("%s %s",name, surname);
    }

}
