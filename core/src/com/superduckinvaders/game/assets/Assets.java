package com.superduckinvaders.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;

/**
 * Responsible for loading game assets.
 */
public class Assets {

    /**
     *  Player texture sets for normal and flying.
     */
    public static TextureSet playerNormal, playerGun, playerFlying;

    /**
     *  Bad guy texture set.
     */
    public static TextureSet rangedBadGuyNormal, badGuyNormal;

    /**
     *  Texture for Projectile.
     */
    public static TextureRegion projectile;
    
    /**
     *  Textures for Hearts
     */
    public static TextureRegion heartFull, heartHalf, heartEmpty;

    /**
     *  Textures for stamina.
     */
    public static TextureRegion staminaFull, staminaEmpty;

    /**
     *  Textures for powerup.
     */
    public static TextureRegion powerupFull, powerupEmpty;

    /**
     *  Animation for explosion.
     */
    public static Animation explosionAnimation;

    /**
     *  Tile map for level one.
     */
    public static TiledMap levelOneMap;

    /**
     *  The font for the UI.
     */
    public static BitmapFont font;

    /**
     * The texture for the button.
     */
    public static TextureRegion button;

    /**
     *  Textures for floor items.
     */
    public static TextureRegion floorItemGun, floorItemSaber, floorItemSpeed, floorItemInvulnerable, floorItemScore, floorItemFireRate, floorItemHeart;

    /**
     *  Texture for objective flag.
     */
    public static TextureRegion flag;

    /**
     *  Texture for the game logo.
     */
    public static TextureRegion logo;

    /**
     * Responsible for loading maps.
     */
    private static TmxMapLoader mapLoader = new TmxMapLoader();

    /**
     * Loads all assets.
     */
    public static void load() {
        loadPlayerTextureSets();
        loadBadGuyTextureSet();
        loadFloorItems();

        projectile = new TextureRegion(loadTexture("textures/projectile.png"));

        explosionAnimation = loadAnimation("textures/explosion.png", 2, 0.3f);

        levelOneMap = loadTiledMap("maps/map.tmx");

        font = loadFont("font/gamefont.fnt", "font/gamefont.png");

        Texture hearts = loadTexture("textures/hearts.png");
        heartFull = new TextureRegion(hearts, 0, 0, 32, 28);
        heartHalf = new TextureRegion(hearts, 32, 0, 32, 28);
        heartEmpty = new TextureRegion(hearts, 64, 0, 32, 28);

        Texture stamina = loadTexture("textures/stamina.png");
        staminaFull = new TextureRegion(stamina, 0, 0, 192, 28);
        staminaEmpty = new TextureRegion(stamina, 0, 28, 192, 28);

        Texture powerup = loadTexture("textures/powerup.png");
        powerupFull = new TextureRegion(powerup, 0, 0, 192, 28);
        powerupEmpty = new TextureRegion(powerup, 0, 28, 192, 28);

        button = new TextureRegion(loadTexture("textures/button.png"));

        flag = new TextureRegion(loadTexture("textures/flag.png"));
        logo = new TextureRegion(loadTexture("textures/logo.png"));
    }

    /**
     * Loads assets relating to the player in the normal state.
     * If you change the player texture size, be sure to change the values here.
     */
    private static void loadPlayerTextureSets() {
        // Load idle texture map.
        Texture playerIdle = loadTexture("textures/player_idle.png");

        // Cut idle textures from texture map.
        TextureRegion front = new TextureRegion(playerIdle, 0, 0, 14, 18);
        TextureRegion back = new TextureRegion(playerIdle, 14, 0, 14, 18);
        TextureRegion left = new TextureRegion(playerIdle, 28, 0, 14, 18);
        TextureRegion right = new TextureRegion(playerIdle, 42, 0, 14, 18);

        // Load idle gun texture map.
        Texture playerIdleGun = loadTexture("textures/player_idle_gun.png");

        // Cut idle textures from texture map.

        TextureRegion frontGun = new TextureRegion(playerIdleGun,    0, 0, 18, 18);
        TextureRegion backGun = new TextureRegion(playerIdleGun,    18, 0, 18, 18);
        TextureRegion leftGun = new TextureRegion(playerIdleGun,  18*2, 0, 18, 18);
        TextureRegion rightGun = new TextureRegion(playerIdleGun, 18*3, 0, 18, 18);

        // Load walking animations.
        Animation walkingFront = loadAnimation("textures/player_walking_front.png", 4, 0.2f);
        Animation walkingBack = loadAnimation("textures/player_walking_back.png", 4, 0.2f);
        Animation walkingLeft = loadAnimation("textures/player_walking_left.png", 4, 0.2f);
        Animation walkingRight = loadAnimation("textures/player_walking_right.png", 4, 0.2f);

        Animation walkingFrontGun = loadAnimation("textures/player_walking_front_gun.png", 4, 0.2f);
        Animation walkingBackGun = loadAnimation("textures/player_walking_back_gun.png", 4, 0.2f);
        Animation walkingLeftGun = loadAnimation("textures/player_walking_left_gun.png", 4, 0.2f);
        Animation walkingRightGun = loadAnimation("textures/player_walking_right_gun.png", 4, 0.2f);

        // Load flying animations.
        Animation flyingFront = loadAnimation("textures/player_flying_front.png", 2, 0.2f);
        Animation flyingBack = loadAnimation("textures/player_flying_back.png", 2, 0.2f);
        Animation flyingLeft = loadAnimation("textures/player_flying_left.png", 2, 0.2f);
        Animation flyingRight = loadAnimation("textures/player_flying_right.png", 2, 0.2f);

        playerNormal = new TextureSet(front, back, left, right, walkingFront, walkingBack, walkingLeft, walkingRight);
        playerGun = new TextureSet(frontGun, backGun, leftGun, rightGun, walkingFrontGun, walkingBackGun, walkingLeftGun, walkingRightGun);
        playerFlying = new TextureSet(front, back, left, right, flyingFront, flyingBack, flyingLeft, flyingRight);
    }

    /**
     * Loads the textures from the bad guy textures file.
     */
    private static void loadBadGuyTextureSet() {
        // Load idle texture map.
        Texture badGuyIdle = loadTexture("textures/badguy_idle.png");

        // Cut idle textures from texture map.
        TextureRegion front = new TextureRegion(badGuyIdle, 0, 0, 21, 24);
        TextureRegion back = new TextureRegion(badGuyIdle, 21, 0, 21, 24);
        TextureRegion left = new TextureRegion(badGuyIdle, 42, 0, 21, 24);
        TextureRegion right = new TextureRegion(badGuyIdle, 63, 0, 21, 24);

        // Load walking animations.
        Animation walkingFront = loadAnimation("textures/badguy_walking_front.png", 4, 0.2f);
        Animation walkingBack = loadAnimation("textures/badguy_walking_back.png", 4, 0.2f);
        Animation walkingLeft = loadAnimation("textures/badguy_walking_left.png", 4, 0.2f);
        Animation walkingRight = loadAnimation("textures/badguy_walking_right.png", 4, 0.2f);

        // Load idle texture map.
        Texture rangedBadguyIdle = loadTexture("textures/ranged_badguy_idle.png");

        // Cut idle textures from texture map.
        TextureRegion rangedFront = new TextureRegion(rangedBadguyIdle, 0, 0, 21, 24);
        TextureRegion rangedBack = new TextureRegion(rangedBadguyIdle, 21, 0, 21, 24);
        TextureRegion rangedLeft = new TextureRegion(rangedBadguyIdle, 42, 0, 21, 24);
        TextureRegion rangedRight = new TextureRegion(rangedBadguyIdle, 63, 0, 21, 24);

        // Load walking animations.
        Animation rangedWalkingFront = loadAnimation("textures/ranged_badguy_walking_front.png", 4, 0.2f);
        Animation rangedWalkingBack = loadAnimation("textures/ranged_badguy_walking_back.png", 4, 0.2f);
        Animation rangedWalkingLeft = loadAnimation("textures/ranged_badguy_walking_left.png", 4, 0.2f);
        Animation rangedWalkingRight = loadAnimation("textures/ranged_badguy_walking_right.png", 4, 0.2f);

        badGuyNormal = new TextureSet(front, back, left, right, walkingFront, walkingBack, walkingLeft, walkingRight);
        rangedBadGuyNormal = new TextureSet(rangedFront, rangedBack, rangedLeft, rangedRight, rangedWalkingFront, rangedWalkingBack, rangedWalkingLeft, rangedWalkingRight);
    }

    /**
     * Loads the texture from the floor items file.
     */
    public static void loadFloorItems() {
        Texture floorItems = loadTexture("textures/floor_items.png");

        floorItemGun =          new TextureRegion(floorItems,    0, 0, 15, 15);
        floorItemSaber =        new TextureRegion(floorItems,   15, 0, 15, 15);
        floorItemSpeed =        new TextureRegion(floorItems, 15*2, 0, 15, 15);
        floorItemInvulnerable = new TextureRegion(floorItems, 15*3, 0, 15, 15);
        floorItemScore =        new TextureRegion(floorItems, 15*4, 0, 15, 15);
        floorItemFireRate =     new TextureRegion(floorItems, 15*5, 0, 15, 15);
        floorItemHeart =        new TextureRegion(floorItems, 15*6, 0, 15, 15);
    }

    /**
     * Loads the texture from the specified file.
     *
     * @param file the file to load from
     * @return the texture
     */
    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    /**
     * Loads the tile map from the specifed file.
     *
     * @param file the file to load from
     * @return the tile map
     */
    public static TiledMap loadTiledMap(String file) {
        return mapLoader.load(file);
    }

    /**
     * Loads the animation from the specified file.
     *
     * @param file          the file to load from
     * @param count         how many frames are in the file
     * @param frameDuration how long each frame should be shown for in seconds
     * @return the animation
     */
    public static Animation loadAnimation(String file, int count, float frameDuration) {
        Texture texture = loadTexture(file);

        int frameWidth = texture.getWidth()/count;

        Array<TextureRegion> keyFrames = new Array<TextureRegion>();

        for (int i = 0; i < count; i++) {
            keyFrames.add(new TextureRegion(texture, i * frameWidth, 0, frameWidth, texture.getHeight()));
        }

        return new Animation(frameDuration, keyFrames);
    }

    /**
     * Loads the bitmap font from the specified files.
     *
     * @param fontFile  the file containing information about the glyphs stored on the image file
     * @param imageFile the file containing the actual glyphs
     * @return the bitmap font
     */
    public static BitmapFont loadFont(String fontFile, String imageFile) {
        return new BitmapFont(Gdx.files.internal(fontFile), Gdx.files.internal(imageFile), false);
    }


}
