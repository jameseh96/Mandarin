package com.superduckinvaders.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.assets.TextureSet;

import java.util.Map;
import java.util.EnumMap;

/**
 * Represents the player of the game.
 */
public class Player extends Character {

    /**
     * Player's maximum health.
     */
    public static final int PLAYER_HEALTH = 6;
    /**
     * Player's standard movement speed in pixels per second.
     */
    public static final float PLAYER_SPEED = 16f;
    /**
     * How much the Player's score increases should be multiplied by if they have the score multiplier powerup.
     */
    public static final float PLAYER_SCORE_MULTIPLIER = 5f;
    /**
     * How much the Player's speed should be multiplied by if they have the super speed powerup.
     */
    public static final float PLAYER_SUPER_SPEED_MULTIPLIER = 3f;
    /**
     * How much the Player's speed should me multiplied by if they are flying.
     */
    public static final float PLAYER_FLIGHT_SPEED_MULTIPLIER = 2f;
    public static final float PLAYER_SWIMMING_SPEED_MULTIPLIER = 1.5f;
    /**
     * How much the Player's attack rate should be multiplied by if they have the rate of fire powerup.
     */
    public static final float PLAYER_RANGED_ATTACK_MULTIPLIER = 5f;
    /**
     * How long the Player can fly for, in seconds.
     */
    public static final float PLAYER_FLIGHT_TIME = 1f;

    public State state = State.DEFAULT;

    /**
     * Player's current score.
     */
    private int points = 0;


    public EnumMap<Pickup, Float> pickupMap = new EnumMap<>(Pickup.class);

    /**
     * Shows if a player is flying. If less than 0, player is flying for -flyingTimer seconds. If less than PLAYER_FLIGHT_COOLDOWN, flying is on cooldown.
     */
    private float flyingTimer = 5;

    public int waterBlockCount = 0;

    protected Pickup currentWeapon = Pickup.GUN;

    /**
     * Initialises this Player at the specified coordinates and with the specified initial health.
     *
     * @param parent the round this Player belongs to
     * @param x      the initial x coordinate
     * @param y      the initial y coordinate
     */
    public Player(Round parent, float x, float y) {
        super(parent, x, y, PLAYER_HEALTH);
        enemyBits = MOB_BITS;
        MELEE_RANGE = 40f;
        MELEE_ATTACK_COOLDOWN = 0.2f;
        STUNNED_DURATION = 0f;
        createDynamicBody(PLAYER_BITS, ALL_BITS, NO_GROUP, false);
        // body.setLinearDamping(10f);
    }

    /**
     * Increases the Player's score by the specified amount.
     *
     * @param amount the amount to increase the score by
     */
    public void addScore(int amount) {
        points += amount;
    }

    /**
     * Gets the Player's current score.
     *
     * @return the current score
     */
    public int getScore() {
        return points;
    }

    /**
     * Gets the Player's current flying timer.
     *
     * @return the current flying timer
     */
    public float getFlyingTimer() {
        return flyingTimer;
    }

    /**
     * Returns if the player is currently flying
     * @return true if the Player is currently flying, false if not
     */
    public boolean isFlying() {
        return flyingTimer > 0 && Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }

    public boolean isSwimming() {
        return waterBlockCount > 0 && !isFlying();
    }

    /**
     * @return the width of this Player
     */
    @Override
    public float getWidth() {
        return Assets.playerNormal.getWidth();
    }

    /**
     * @return the height of this Player
     */
    @Override
    public float getHeight() {
        return Assets.playerNormal.getHeight();
    }

    /**
     * Damages the Player, taking into account the possibility of invulnerability.
     *
     * @param health the number of points to damage
     */
    @Override
    public void damage(int health) {
        // Only apply damage if we don't have the invulnerability powerup.
        if (!pickupMap.containsKey(Pickup.INVULNERABLE)) {
            super.damage(health);
        }
    }

    public void givePickup(Pickup pickup, float duration){
        pickupMap.put(pickup, duration);
    }

    public boolean hasPickup(Pickup pickup){
        return pickupMap.containsKey(pickup);
    }

    @Override
    protected void meleeAttack(Vector2 direction, int damage) {
        currentWeapon = Pickup.LIGHTSABER;
        super.meleeAttack(direction, damage);
    }

    @Override
    protected void rangedAttack(Vector2 direction, int damage) {
        currentWeapon = Pickup.GUN;
        super.rangedAttack(direction, damage);
    }


    /**
     * Updates the state of this Player.
     *
     * @param delta how much time has passed since the last update
     */
    @Override
    public void update(float delta) {
        if (isFlying()){
            state = State.FLYING;
        }
        else if (isSwimming()){
            state = State.SWIMMING;
        }else if (currentWeapon == Pickup.GUN && hasPickup(Pickup.GUN)) {
            state = State.HOLDING_GUN;
        } else if (currentWeapon == Pickup.LIGHTSABER && hasPickup(Pickup.LIGHTSABER)) {
            state = State.HOLDING_GUN;
        } else {
            state = State.DEFAULT;
        }

        if (hasPickup(Pickup.RATE_OF_FIRE)){
            rangedAttackTimer+=delta*(PLAYER_RANGED_ATTACK_MULTIPLIER-1);
        }


        // Decrement pickup timer.
        for (Map.Entry<Pickup, Float> entry : pickupMap.entrySet()){
            float value = entry.getValue();
            if (value < 0) {
                pickupMap.remove(entry.getKey());
            }
            else {
                entry.setValue(value - delta);
            }
        }

        if (hasPickup(Pickup.HEALTH)) {
            heal(1);
        }


        if (! isFlying() && !isSwimming()) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                Vector3 target = parent.unproject(Gdx.input.getX(), Gdx.input.getY());
                meleeAttack(vectorTo(new Vector2(target.x, target.y)), 1);
            }
            else if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
                if (hasPickup(Pickup.GUN)) {

                    Vector3 target = parent.unproject(Gdx.input.getX(), Gdx.input.getY());
                    rangedAttack(vectorTo(new Vector2(target.x, target.y)), 1);
                }
            }
        }

        // Press space to start flying, but only if flying isn't cooling down and we're moving.
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (flyingTimer > 0){
                flyingTimer -= delta;
            }
        }
        else {
            flyingTimer = Math.min((flyingTimer+(delta*0.2f)), PLAYER_FLIGHT_TIME);
        }


        // Left/right movement.
        
        Vector2 targetVelocity = new Vector2();
        
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            targetVelocity.x += -1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            targetVelocity.x += 1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            targetVelocity.y = 1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            targetVelocity.y = -1f;
        }

        // Calculate speed at which to move the player.
        float speed = PLAYER_SPEED * (hasPickup(Pickup.SUPER_SPEED) ? PLAYER_SUPER_SPEED_MULTIPLIER : 1);

        if (state == State.FLYING){
            speed *= PLAYER_FLIGHT_SPEED_MULTIPLIER;
        }
        else if (state == State.SWIMMING){
            speed *= PLAYER_SWIMMING_SPEED_MULTIPLIER;
        }
        
        targetVelocity.setLength(speed);
        setVelocity(targetVelocity, state == State.SWIMMING ? 0.5f : 4f);
        

        // Update movement.
        super.update(delta);
    }


    /**
     * Renders this Player.
     *
     * @param spriteBatch the sprite batch on which to render
     */
    @Override
    public void render(SpriteBatch spriteBatch) {
        // Use the right texture set.
//        TextureSet textureSet = isFlying() ? Assets.playerFlying : ( hasPickup(Pickup.GUN) ? Assets.playerGun : Assets.playerNormal);
        TextureSet textureSet = state.getTextureSet();

        Vector2 pos = getPosition();
        spriteBatch.draw(textureSet.getTexture(facing, stateTime), pos.x, pos.y);
    }

    public enum State {
        DEFAULT       (Assets.playerNormal),
        HOLDING_GUN   (Assets.playerGun),
        HOLDING_SABER (Assets.playerGun), // for now, we don't have all the saber assets!
        SWIMMING      (Assets.playerSwimming),
        FLYING        (Assets.playerFlying);

        private final TextureSet textureSet;

        State (TextureSet textureSet){
            this.textureSet = textureSet;
        }

        public TextureSet getTextureSet(){
            return this.textureSet;
        }
    }

    /**
     * Available pickups.
     */
    public enum Pickup {
        GUN               (Assets.floorItemGun         ),
        LIGHTSABER        (Assets.floorItemSaber       ),
        SCORE_MULTIPLIER  (Assets.floorItemScore       ),
        SUPER_SPEED       (Assets.floorItemSpeed       ),
        RATE_OF_FIRE      (Assets.floorItemFireRate    ),
        HEALTH            (Assets.floorItemHeart       ),
        INVULNERABLE      (Assets.floorItemInvulnerable);

    
        private final TextureRegion texture;
        
        Pickup(TextureRegion texture){
            this.texture = texture;
        }
        
        public TextureRegion getTexture() {
            return texture;
        }

        public static Pickup random(){
            float random = MathUtils.random();
            Pickup pickup = null;

            if (random < 0.05) {
                pickup = Player.Pickup.SCORE_MULTIPLIER;
            } else if (random >= 0.05 && random < 0.1) {
                pickup = Player.Pickup.INVULNERABLE;
            } else if (random >= 0.1 && random < 0.15) {
                pickup = Player.Pickup.SUPER_SPEED;
            } else if (random >= 0.15 && random < 0.2) {
                pickup = Player.Pickup.RATE_OF_FIRE;
            }

            return pickup;
        }
    }
    
}
