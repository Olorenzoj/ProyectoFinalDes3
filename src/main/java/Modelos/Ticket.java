package Modelos;

import java.util.Date;

public class Ticket {
    private int ticketId;
    private int userId;
    private String title;
    private String description;
    private int statusId;
    private Date createAtTicket;
    private Date updatedAtTicket;

    public Ticket(int userId, String title, String description, int statusId) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.statusId = statusId;
        this.createAtTicket = new Date();
        this.updatedAtTicket = new Date();
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Date getCreateAtTicket() {
        return createAtTicket;
    }

    public void setCreateAtTicket(Date createAtTicket) {
        this.createAtTicket = createAtTicket;
    }

    public Date getUpdatedAtTicket() {
        return updatedAtTicket;
    }

    public void setUpdatedAtTicket(Date updatedAtTicket) {
        this.updatedAtTicket = updatedAtTicket;
    }
}

