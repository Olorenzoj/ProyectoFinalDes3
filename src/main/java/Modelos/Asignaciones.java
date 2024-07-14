package Modelos;

import java.util.Date;

public class Asignaciones {
    private int assignmentId;
    private int ticketId;
    private int operatorId;
    private Date assignedAt;

    public Asignaciones(int assignmentId, int ticketId, int operatorId, Date assignedAt) {
        this.assignmentId = assignmentId;
        this.ticketId = ticketId;
        this.operatorId = operatorId;
        this.assignedAt = assignedAt;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
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
    @Override
    public String toString() {
        return "Asignaciones{" +
                "assignmentId=" + assignmentId +
                ", ticketId=" + ticketId +
                ", operatorId=" + operatorId +
                ", assignedAt=" + assignedAt +
                '}';
    }

}
