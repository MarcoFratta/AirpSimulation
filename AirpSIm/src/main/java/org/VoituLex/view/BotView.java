package org.VoituLex.view;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.VoituLex.model.Bot;

import java.io.InputStream;


public class BotView extends Image {

    private final int botSize;
    private Bot bot;

    public BotView(Bot bot) {
        super(BotView.class.getResourceAsStream("/botImage.png"), bot.getSize(), bot.getSize(), true,false);
        this.bot = bot;
        this.botSize = bot.getSize();
        this.bot.getPhysic().setX(bot.getPhysic().getX()-(botSize/2));
        this.bot.getPhysic().setY(bot.getPhysic().getY()-(botSize/2));
    }
    
    public int getX(){
        return this.bot.getPhysic().getX();
    }

    public int getY(){
        return this.bot.getPhysic().getY();
    }

}