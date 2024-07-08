package Modelos;

import java.util.Date;

public class Asignacion {
    private int assigmentId;
    private int ticketId;
    private int operatorId;
    private Date assignedAt;

    public Asignacion(int assigmentId, int ticketId, int operatorId, Date assignedAt) {
        this.assigmentId = assigmentId;
        this.ticketId = ticketId;
        this.operatorId = operatorId;
        this.assignedAt = assignedAt;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public Date getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Date assignedAt) {
        this.assignedAt = assignedAt;
    }

    public int getAssigmentId() {
        return assigmentId;
    }

    public void setAssigmentId(int assigmentId) {
        this.assigmentId = assigmentId;
    }
}
