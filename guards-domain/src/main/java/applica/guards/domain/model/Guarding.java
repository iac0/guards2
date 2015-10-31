package applica.guards.domain.model;

import applica.framework.IEntity;
import applica.framework.LEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Iaco on 18/10/15.
 */
public class Guarding extends LEntity implements Serializable{

    Place place;
    Guard guard;
    Date watching;
    Date watchingEnd;

    public Date getWatchingEnd() {
        return watchingEnd;
    }

    public void setWatchingEnd(Date watchingEnd) {
        this.watchingEnd = watchingEnd;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Guard getGuard() {
        return guard;
    }

    public void setGuard(Guard guard) {
        this.guard = guard;
    }

    public Date getWatching() {
        return watching;
    }

    public void setWatching(Date watching) {
        this.watching = watching;
    }
}
