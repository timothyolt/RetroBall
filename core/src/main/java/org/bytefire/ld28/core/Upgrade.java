/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bytefire.ld28.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import java.util.Random;
import org.bytefire.ld28.core.asset.Sprite;
import org.bytefire.ld28.core.screen.AbstractScreen;
import org.bytefire.ld28.core.screen.GameScreen;

/**
 *
 * @author timn
 */
public class Upgrade extends Actor implements CollisionManager{
    private final LD28 game;
    private Type type;
    public enum Type { LOW_GRAV, LARGE_BALL, BOUNCE, MAGNET, FLY }
    private final TextureRegion tex;
    //private final Body body;
    private Random rand;
    
    public Upgrade(LD28 game, Type type){
        this.game = game;
        this.type = type;
        switch(type){
            case BOUNCE: tex = game.getSpriteHandler().getRegion(Sprite.BOUNCE_ON);
                break;
            case LOW_GRAV: tex = game.getSpriteHandler().getRegion(Sprite.GRAV_ON);
                break;
            case LARGE_BALL: tex = game.getSpriteHandler().getRegion(Sprite.SIZE_ON);
                break;
            case MAGNET: tex = game.getSpriteHandler().getRegion(Sprite.MAGNET_ON);
                break;
            case FLY: tex = game.getSpriteHandler().getRegion(Sprite.FLY_ON);
                break;
            default: tex = game.getSpriteHandler().getRegion(Sprite.SIZE_ON);
                break;
                
        }
        ((AbstractScreen) game.getScreen()).getStage().addActor(this);
        setTouchable(Touchable.enabled);
        rand = new Random(System.nanoTime());
        
        setX( ((GameScreen) game.getScreen()).getPlayer().getX() + GameScreen.WINDOW_WIDTH);
        setY( ((GameScreen) game.getScreen()).getPlayer().getY() + (rand.nextInt(300) - 100));
    }
    
    @Override
    public void draw (SpriteBatch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(tex, getX() - (tex.getRegionWidth() / 2), getY() - (tex.getRegionHeight() / 2));
    }

    @Override
    public void act(float delta){
        if (((GameScreen) game.getScreen()).getPlayer().getPosition().dst(getX(), getY()) < 6){
            switch (type){
                case BOUNCE: ((GameScreen) game.getScreen()).getPlayer().getFix().setRestitution(2.0f);
                    break;
                default: break;
            }
            remove();
        }
    }

    @Override
    public void beginContact(Contact contact) {}

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}

    @Override
    public Class getType() { return this.getClass(); }

    public Type getUpgradeType() {
        return type;
    }
    
    
    
}