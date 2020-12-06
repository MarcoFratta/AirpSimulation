package org.VoituLex.model;

import org.VoituLex.physics.P2d;

import java.util.function.BiPredicate;
import java.util.function.Consumer;


public enum Direction {

    RIGHT(((a,b) -> a.x == b.x),(a) -> a.x++,"Destra"),
    LEFT (((a,b) -> a.x == b.x),(a) -> a.x--,"Sinistra"),
    UP   (((a,b) -> a.y == b.y),(a) -> a.y--,"Su"),
    DOWN (((a,b) -> a.y == b.y),(a) -> a.y++,"Giu");


    public final BiPredicate<P2d, P2d> predicate;
    public final Consumer<P2d> consumer;
    public final String string;

    Direction(BiPredicate<P2d , P2d> predicate , Consumer<P2d> consumer, String string){
        this.predicate = predicate;
        this.consumer = consumer;
        this.string = string;
    }
    public P2d nextPosition(P2d point){
        P2d p2d = new P2d(point);
        this.consumer.accept(p2d);
        return p2d;
    }


    @Override
    public String toString() {
        return "[" + string + "]";
    }
}
