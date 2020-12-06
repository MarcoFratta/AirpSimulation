package org.VoituLex.model;

import org.VoituLex.physics.P2d;

import java.util.Optional;

public class Box {


    private  P2d center;
    private final int posOnColumn;
    private  Optional<Bot> guest;
    private boolean onCorridor;

    public Box(P2d center, int posOnColumn, boolean onCorridor) {
        this.center = center;
        this.posOnColumn = posOnColumn;
        this.guest = Optional.empty();
        this.onCorridor = onCorridor;
    }

    public Box(int posOnColumn, boolean onCorridor) {
        this.onCorridor = onCorridor;
        this.posOnColumn = posOnColumn;
        this.guest = Optional.empty();
    }

    public P2d getCenter() {
        return this.center;
    }

    public boolean isOccupied() {
        return this.guest.isPresent();
    }

    public void setFree() {
        this.guest = Optional.empty();
    }

    public void setOccupied(Bot bot) {
        this.guest = Optional.of(bot);
    }

    public int getPosOnColumn() {
        return posOnColumn;
    }

    public void setCenter(P2d center) {
        this.center = center;
    }

    public boolean isOnCorridor() {
        return onCorridor;
    }


    @Override
    public String toString(){
        return String.valueOf(this.posOnColumn);
     }

    public Optional<Bot> getGuest() {
        return this.guest;
    }
}