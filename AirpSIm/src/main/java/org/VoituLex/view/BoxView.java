package org.VoituLex.view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.VoituLex.model.Box;


public class BoxView  {


    public static final int DEFAULT_BOX_SIZE = 100;
    public static final int DEFAULT_BORDER_SIZE = 1;
    private final Box box;
    public final int x;
    public final int y;
    private  Paint fill = Paint.valueOf("white");

    public BoxView(Box box) {
        super();
        this.box = box;
        this.x =  (box.getCenter().x - (DEFAULT_BOX_SIZE/ 2));
        this.y =  (box.getCenter().y - (DEFAULT_BOX_SIZE/ 2));
        if(this.box.isOnCorridor()){
            this.fill = Paint.valueOf("pink");
        }
    }

    @Override
    public String toString(){
        return this.box.getCenter().toString();
    }

    public Box getBox() {
        return box;
    }

    public Paint getFill() {
        return this.fill;
    }
}
