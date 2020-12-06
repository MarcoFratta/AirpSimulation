package org.VoituLex.model;

public class Counter  {


    private int count;

    Counter(int start){
        this.count= start;
    }

    public void inc(){
        this.count++;
    }
    public void dec(){
        this.count--;
    }
    public int getCount(){
        return this.count;
    }

}
