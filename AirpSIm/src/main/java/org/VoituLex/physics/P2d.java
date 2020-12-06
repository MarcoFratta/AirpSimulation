package org.VoituLex.physics;

import java.util.Objects;

public class P2d {

    public int x,y;

    public P2d(int x,int y){
        this.x=x;
        this.y=y;
    }

    public P2d(double v, double v1) {
        this.x = (int) v;
        this.y = (int) v1;
    }

    public P2d(P2d point) {
        this.x = point.x;
        this.y = point.y;
    }

    public P2d sum(V2d v){

        return new P2d(x+v.x,y+v.y);
    }

    public V2d sub(P2d v){
        return new V2d(x-v.x,y-v.y);
    }

    public String toString(){
        return "P2d("+x+","+y+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        P2d p2d = (P2d) o;
        return x == p2d.x &&
                y == p2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

