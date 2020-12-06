package org.VoituLex.model;

import org.VoituLex.physics.BotPhysic;
import org.VoituLex.physics.P2d;

public abstract class Bot extends Thread {


    public static int DEFAULT_BOT_SIZE = 30;
    protected Ticket ticket;
    protected P2d position;
    protected boolean seated;

    public Bot(int xPosition,int vPosition) {
        this.position = new P2d(xPosition,vPosition);
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void setPosition(P2d p) {
        this.position = p;
    }

    public int getHorizontalPosition() {
        return (int)this.position.x;
    }

    public int getVerticalPosition(){
        return (int) this.position.y;
    }

    abstract public BotPhysic getPhysic();

    public int getSize(){
        return this.getPhysic().getSize();
    }

    public boolean isArrived(){
        return this.seated;
    }

    abstract public void run();

}
