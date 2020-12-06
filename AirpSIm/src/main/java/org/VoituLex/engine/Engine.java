package org.VoituLex.engine;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import org.VoituLex.model.*;
import org.VoituLex.physics.P2d;
import org.VoituLex.view.SimulationView;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Engine {

    private static final int DEFAULT_BOTS = 10;
    private static final int DEFAULT_SEATS_PER_ROW = 6;
    private static final int DEFAULT_COLUMNS = 10;

    private final int nBots;
    private final int seatsPerRow;
    private final int nColumns;
    private Context context;
    private  SimulationView view;
    private final SortingMethod sortingMethod;
    private Ticket p;

    Engine(SortingMethod sortingMethod, int nBot, int disp, int nColumns){
        this.sortingMethod = sortingMethod;
        this.nBots = nBot;
        this.seatsPerRow = disp;
        this.nColumns = nColumns;
    }
    Engine(SortingMethod sortingMethod){
        this(sortingMethod , DEFAULT_BOTS, DEFAULT_SEATS_PER_ROW, DEFAULT_COLUMNS);
    }

    public void start(){
        createScene();
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                render();
            }
        }.start();
    }


    // initialize the context and assign tickets to bots
    private void createScene() { 
        context = new Airport(nBots,seatsPerRow,nColumns);
        context.initBoxes();
        view = new SimulationView(context);
        view.createRender();
        view.fillBoxes();
        Random r = new Random();
        Set<Ticket> s = new HashSet<>();
        context.initBots(bots -> {
            bots.forEach(b -> {
            do {
                p = new Ticket(r.nextInt(nColumns),
                        r.nextInt((seatsPerRow)),
                        (r.nextInt(2) == 1 ? Direction.UP : Direction.DOWN));
            } while (!s.add(p));
            b.setTicket(p);
        });
    });

        view.fillBots();
        context.startBots();
    }

    private void render() {

       view.updateRender();
    }
}
