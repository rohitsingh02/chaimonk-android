package www.chaayos.com.chaimonkbluetoothapp.domain.model;

/**
 * Created by rohitsingh on 02/08/16.
 */
public class DayClose {
    private int eventId;
    private String eventDate;
    private String eventStatus;
    private int startOrderId;
    private int endOrderId;
    private String eventTime;
    private String createdBy;
    private String cancelledBy;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public int getStartOrderId() {
        return startOrderId;
    }

    public void setStartOrderId(int startOrderId) {
        this.startOrderId = startOrderId;
    }

    public int getEndOrderId() {
        return endOrderId;
    }

    public void setEndOrderId(int endOrderId) {
        this.endOrderId = endOrderId;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }
}
