package org.VoituLex.model;

import org.VoituLex.physics.BotPhysic;
import org.VoituLex.physics.P2d;

import java.util.Optional;

public class AirportBot extends Bot {

    private static final long TIME_TO_SLEEP = 80;
    Optional<Direction> direction;
    private boolean stopped;
    private boolean waiting;
    private Airport context;
    private BotPhysic physic;
    private Path path;

    private Ticket tmpTicket;
    private boolean standingUp;

    public AirportBot(int xPosition,int yPosition, Airport context,long delay) {
        super(xPosition,yPosition);
        this.context = context;
        this.physic = new BotPhysic(this.context.getBox(this.position.y,this.position.x).getCenter(),
                DEFAULT_BOT_SIZE);
        this.physic.setDelay(delay);
        this.context.getBox(this.position.y, this.position.x).setOccupied(this);
       // System.out.println(this.physic.toString());
    }


    @Override
    public void run() {
        direction = path.getNextMove();
        while(!seated){
            if(standingUp){
                if(direction.isEmpty()){
                    standingUp = false;
                    calculatePath(ticket);
                    continue;
                }
                if(canMove()){
                    move();
                    direction = path.getNextMove();
                }
            }
            else if(direction.isEmpty()){
                seated = true;
            }
            else if(canMove()){
                if(shouldCheckSeat()){
                    if(canSeat()){
                        move();
                    } else {
                        context.freeSeat(ticket,this);
                        move();
                    }
                } else{
                    move();
                }
                // check if i reached the center of the box
                direction = path.getNextMove();
            }

            try {
                Thread.sleep(TIME_TO_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    // arrived to my previous row ( only when bot is in the corridor)
    private boolean shouldCheckSeat() {
        if(this.position.x == context.getCorridorIndex()){
            return false;
        } else if(direction.get().equals(Direction.RIGHT)){
            return this.ticket.getColumnNum() == this.position.x + 1;
        } else if(direction.get().equals(Direction.LEFT)){
            return this.ticket.getColumnNum() == this.position.x - 1;
        }
        return false;
    }

    @Override
    public BotPhysic getPhysic() {
        return this.physic;
    }

    private void move(){
        P2d oldPos =new P2d(this.position.x,this.position.y);
        manageBox();
        Box myBox = this.context.getBox(this.position.y, this.position.x);
        this.physic.updatePosition(direction.get(), myBox.getCenter());
        this.context.getBox(oldPos.y, oldPos.x).setFree();
    }

    private void manageBox(){
        this.position = this.direction.get().nextPosition(position);
        this.context.getBox(this.position.y, this.position.x).setOccupied(this);
    }

    private boolean canSeat() {
        return context.isColumnFree(ticket);
    }

    private boolean amArrived(){
        return this.position.equals(new P2d(ticket.getColumnNum(),ticket.getRowNum()));
    }
    // check if the next box towards the direction is free
    private boolean canMove(){
        P2d nxtPos = this.direction.get().nextPosition(this.position);
        //System.out.println("next pos :"+nxtPos.toString());
        //System.out.println("Libero > "+b);
        return context.isBoxFree(nxtPos.y, nxtPos.x);

    }

    public void setDirection(Direction direction) {
        this.direction = Optional.ofNullable(direction);
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public void stopWaiting() {
        this.waiting = false;
    }

    private void calculatePath(Ticket ticket) {
        path = new Path(this.position
                ,new P2d(ticket.getColumnNum(),ticket.getRowNum())
                ,context.getCorridorIndex());
        path.createPath();
        path.printPath();
    }

    @Override
    public void setTicket(Ticket ticket){
        this.ticket = ticket;
        calculatePath(this.ticket);
    }


    public void standUp(Ticket ticket){
        this.standingUp = true;
        this.tmpTicket = ticket;
        calculatePath(this.tmpTicket);
    }
}

