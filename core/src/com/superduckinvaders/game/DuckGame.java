package com.superduckinvaders.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.screen.StartScreen;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DuckGame extends Game {
	
    /**
     * The width of the world.
     */
    public static final int GAME_WIDTH = 1280;

    /**
     * The height of the world.
     */
    public static final int GAME_HEIGHT = 720;

    /**
     * The Session singleton.
     */
    public static final Session session = new Session();

    /**
     * The Session stores information meant to persist throughout the game.
     */
     public static class Session {
        public int currentLevel = 1;
        public int maxUnlocked = 8;
        public int healthCounter = 8;
        public int totalScore = 0;
        public void incrementLevelCounter(){
            currentLevel += 1;
        }

        public void setLevel(int level){
             currentLevel = level;
        }
        public void unlock(int level){
             maxUnlocked = Math.max(maxUnlocked, level);
        }
        public boolean isUnlocked(int level){
             return maxUnlocked >= level;
        }
        public void unlockNext(){
            unlock(currentLevel+1);
        }
        public void setHealthCounter(int health){
            healthCounter = health;
        }
    }

    /**
     * Initialises the startScreen. Called by libGDX to set up the graphics.
     */
    @Override
    public void create() {
        Assets.load();
        Gdx.graphics.setVSync(true);
        this.setScreen(new StartScreen(this));

        Assets.menuTheme.play();
        Assets.menuTheme.setVolume(0.2f);
        Assets.menuTheme.setLooping(true);
    }

    /**
     * Called by libGDX to set up the graphics.
     */
    @Override
    public void render() {
        super.render();
        // Take a screenshot if V is pressed.
        if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
            byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

            Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
            BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
            PixmapIO.writePNG(Gdx.files.external("DuckInvaders/" + new SimpleDateFormat("SS-ss-mm-HH").format(new Date()) + ".png"), pixmap);
            pixmap.dispose();
        }
    }
}
