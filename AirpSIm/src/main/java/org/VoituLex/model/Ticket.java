package org.VoituLex.model;

import java.util.Objects;

public class Ticket {


    private final int rowNum;
    private final int seatsNumFromCorridor;
    private final Direction direction;


    public Ticket(int rowNum , int seatsNum, Direction dir){
        this.rowNum = rowNum;
        this.seatsNumFromCorridor = seatsNum;
        this.direction = dir;
    }

    public int getColumnNum() {
        return rowNum;
    }

    public int getRowNum() {
        return seatsNumFromCorridor;
    }

    public Direction getDirection() { return direction; }

    @Override
    public String toString() {
        return "Ticket{" +
                "rowNum=" + rowNum +
                ", seatsNumFromCorridor=" + seatsNumFromCorridor +
                ", direction=" + direction +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        System.out.println(ticket.toString()+"\n"+this.toString());
        return rowNum == ticket.rowNum &&
                seatsNumFromCorridor == ticket.seatsNumFromCorridor &&
                direction == ticket.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowNum, seatsNumFromCorridor, direction);
    }
}
