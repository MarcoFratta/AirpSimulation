package org.VoituLex.physics;

import org.VoituLex.model.Direction;

import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class BotPhysic {


    private static final long TIME_TO_SLEEP = 15;
    private P2d p2d;
    private int size;
    private long delay = TIME_TO_SLEEP;


    public BotPhysic(P2d p2d, int size) {
        this.p2d = p2d;
        this.size = size;
    }

    public void updatePosition(Direction direction, P2d destination){
        System.out.println("start >"+p2d.toString());
        System.out.println("destination >"+destination.toString());
        System.out.println("Condition>"+(direction.predicate.test(p2d,destination)));
        P2d dest = new P2d(destination);
        dest.x -= this.size/2;
        dest.y -= this.size/2;
        while(!direction.predicate.test(p2d,dest)){
            direction.consumer.accept(p2d);
            try {
                //System.out.println("p>"+p2d.toString());
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getX() {
        return this.p2d.x;
    }

    public int getY() {
        return this.p2d.y;
    }

    @Override
    public String toString() {
        return "Bot phisic ->"+this.p2d.toString();
    }

    public void setX(int x) {
        this.p2d.x = x;
    }
    public void setY(int y) {
        this.p2d.y = y;
    }

    public int getSize() {
        return this.size;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}

