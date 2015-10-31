package applica.guards.admin.viewmodel;

import applica.guards.domain.model.Guarding;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Iaco on 18/10/15.
 */
public class UICalendarEvent implements Serializable{
    Long id;
    String title;
    String guard;
    String start;
    String end;
    String startHour;
    String endHour;
    long guardid;
    long placeid;
    boolean allDay = false;

    public UICalendarEvent(Guarding guarding) {
        id = guarding.getLid();
        title = guarding.getPlace().getName();

        TimeZone tz = TimeZone.getTimeZone("CEST");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        start = df.format(guarding.getWatching());
        end = df.format(guarding.getWatchingEnd());
        startHour = guarding.getPlace().getTimeOfGuarding();
        endHour = guarding.getPlace().getTimeFinishOfGuarding();
        guard = guarding.getGuard().guardia();
        guardid = guarding.getGuard().getIid();
        placeid = guarding.getPlace().getIid();

    }

    public UICalendarEvent(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
       return String.format("%s<br>%s<br> %s %s",guard,title, startHour, endHour);
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public long getGuardid() {
        return guardid;
    }

    public void setGuardid(long guardid) {
        this.guardid = guardid;
    }

    public long getPlaceid() {
        return placeid;
    }

    public void setPlaceid(long placeid) {
        this.placeid = placeid;
    }
}
