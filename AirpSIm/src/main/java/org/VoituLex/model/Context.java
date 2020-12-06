package org.VoituLex.model;


import java.util.List;

public interface Context {

    public void initBots(SortingMethod sortingMethod);
    public void initBoxes();
    public int getColumnsNum();
    public ContextState getState();
    public List<? extends Bot> getBots();
    public List<List<Box>> getBoxes();
    void startBots();
    List<Box> getStartingQueue();

    int getCorridorIndex();
}
