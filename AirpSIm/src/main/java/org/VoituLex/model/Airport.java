package org.VoituLex.model;

import org.VoituLex.physics.P2d;

import java.util.*;
import java.util.function.Predicate;

public class Airport implements Context{


    public static final int N_COLUMNS = 5;
    private static final int N_BOTS = 10;
    private List<AirportBot> bots ;
    private List<List<Box>> grid;
    private List<Box> startingQueue;
    private final int corridorIndex;
    private final int seatsPerRow;
    private final int nBots;
    private final int nColumns;

    private final ContextState state;

    public Airport(int nBots, int seatsPerRow, int nColumns){
        this.seatsPerRow = seatsPerRow;
        this.nBots = nBots;
        this.state = new ContextState();
        this.corridorIndex = seatsPerRow;
        this.nColumns = nColumns;

    }


    @Override
    public void initBoxes() {
       // System.out.println("Seats per row:"+seatsPerRow);
        this.grid = new ArrayList<>() ;
        for (int i = 0; i < (seatsPerRow*2) + 1; i++) {
            grid.add(new ArrayList<>());
            for (int j = 0; j < nColumns; j++) {
                grid.get(i).add(new Box(i, i == corridorIndex));
            }
        }
        this.startingQueue = new ArrayList<>();
        for(int i = 0; i< nBots ;i++){
            startingQueue.add(new Box(corridorIndex, true));
        }
    }

    @Override
    public void initBots(SortingMethod sortingMethod) {
        Random r = new Random();
        Set<P2d> s = new HashSet<>() ;
        boolean exit = false;
        this.bots = new ArrayList<>(this.nBots);
        P2d p = null;
        for(int i = 1; i <= nBots; i++) {
           /* do{
                p = new P2d(r.nextInt(nColumns),r.nextInt((seatsPerRow*2)+1));
            }while (!s.add(p));*/
            this.bots.add(new AirportBot(-i,corridorIndex,this,20));//-i
        } // [-1, -2, -3, -4]

        System.out.println(grid.toString());
        sortingMethod.assignTicket(this.bots);
    }

    public void startBots(){
        bots.forEach(Thread::start);
    }

    @Override
    public ContextState getState() {
        return this.state;
    }

    public int getColumnsNum(){
        return this.nColumns;
    }

    private Predicate<Box> getPredicate(Ticket ticket) {
        final int i = getAbsoluteGridRowIndex(ticket);
        return ticket.getDirection().equals(Direction.UP) ?
                b -> b.getPosOnColumn() > i && b.getPosOnColumn() < corridorIndex
                : b -> b.getPosOnColumn() < i && b.getPosOnColumn() > corridorIndex;
    }

    @Override
    public List<? extends Bot> getBots() {
        return this.bots;
    }

    @Override
    public List<List<Box>> getBoxes() {
        return this.grid;
    }

    public boolean isBoxFree(int row, int col){
        System.out.println("row ->"+row+" col->"+col);
        if(col >=0) {
            return !grid.get(row).get(col).isOccupied();
        } else {
            return !startingQueue.get((-col)-1).isOccupied();
        }
    }

    public void freeSeat(Ticket ticket, AirportBot bot) {

      grid.get(corridorIndex).get(ticket.getColumnNum() - 1);
      Counter c = new Counter(countBotsToMove(ticket));
        grid.stream()
                .map(l -> l.get(ticket.getColumnNum()))
                .filter(l -> l.getPosOnColumn() != corridorIndex)
                .filter(getPredicate(ticket))
                .sorted(Comparator.comparingInt(Box::getPosOnColumn))
                .forEachOrdered(b -> {
                    routeToNewDestination(b.getGuest(), ticket.getColumnNum()+c.getCount());
                    c.dec();
                });

      bot.stopWaiting();
    }

    private void routeToNewDestination(Optional<Bot> b, int i) {

    }

    private int countBotsToMove(Ticket ticket){
        return (int) grid.stream()
                .map(l -> l.get(ticket.getColumnNum()))
                .filter(l -> l.getPosOnColumn() != corridorIndex)
                .filter(getPredicate(ticket))
                .count();
    }

    public int getAbsoluteGridRowIndex(Ticket ticket){
        if(ticket.getDirection().equals(Direction.RIGHT) || ticket.getDirection().equals(Direction.LEFT)){
            return corridorIndex;
        }
        if(ticket.getDirection().equals(Direction.UP)){
            return (seatsPerRow - ticket.getRowNum());
        } else{
            return (seatsPerRow + ticket.getRowNum());
        }
    }

    public boolean isColumnFree(final Ticket ticket) {
        return grid.stream()
                .map(l -> l.get(ticket.getColumnNum()))
                .filter(l -> l.getPosOnColumn() != corridorIndex)
                .filter(getPredicate(ticket))
                .noneMatch(Box::isOccupied);
    }

    public Box getBox(int row, int col) {
        System.out.println("x>"+row+"y>"+col);
        if(col >= 0){
            return grid.get(row).get(col);
        } else {
            return startingQueue.get((-col)-1);
        }

    }

    public List<Box> getStartingQueue(){
        return this.startingQueue;
    }

    public int getCorridorIndex() {
        return  this.corridorIndex;
    }
}
