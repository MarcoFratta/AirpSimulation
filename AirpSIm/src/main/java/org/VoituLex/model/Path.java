package org.VoituLex.model;

import org.VoituLex.physics.P2d;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class Path {


    private final P2d start;
    private final P2d destination;
    private final int corridor;
    Queue<Direction> queue = new LinkedList<>();

    // X = column
    // Y = rows


    public Path(P2d start, P2d dest, int corridor){
        this.start = start;
        this.destination = dest;
        this.corridor = corridor;
    }

    public void createPath(){

        if(start.x != destination.x){
            MoveToCorridor();
            moveToDestColumn();
            moveToDestRow(corridor);
        } else {
            moveToDestRow((int)start.y);
        }
    }

    private void MoveToCorridor() {
        if(start.y < corridor){
            for(int i=0; i < corridor - start.y; i++){
                queue.add(Direction.DOWN);
            }
        } else {
            for(int i=0; i < start.y - corridor; i++){
                queue.add(Direction.UP);
            }
        }
    }

    private void moveToDestColumn() {
        if(start.x < destination.x){
            for(int i = 0; i < destination.x - start.x; i++){
                queue.add(Direction.RIGHT);
            }
        } else {
            for(int i = 0; i < start.x - destination.x; i++){
                queue.add(Direction.LEFT);
            }
        }
    }

    private void moveToDestRow(int k) {
        if(k > destination.y){
            for(int i = 0 ; i < k - destination.y; i++){
                queue.add(Direction.UP);
            }
        } else {
            for(int i = 0 ; i < destination.y - k; i++){
                queue.add(Direction.DOWN);
            }
        }
    }

    public Optional<Direction> getNextMove(){
        return Optional.ofNullable(queue.poll());
    }

    public void printPath() {
        System.out.println("Start :"+start.toString());
        System.out.println("Destination :"+destination.toString());
        queue.forEach(e -> System.out.println(e.toString()));
    }
}
