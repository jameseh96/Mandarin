package com.superduckinvaders.game.objective;

import com.superduckinvaders.game.Round;

/**
 * Represents a time based objective.
 */

public class SurviveObjective extends Objective {

    private float duration;
    /**
     * Stores the amount of time the player has survived for.
     */
    private float timer = 0;

    /**
     * Initialise the SurviveObjective.
     *
     * @param parent the round which SurviveObjective belongs to.
     */
    public SurviveObjective(Round parent) {
        this(parent, 120);
    }
    public SurviveObjective(Round parent, float duration) {
        super(parent);
        this.duration=duration;
    }

    /**
     * Updates the status towards SurviveObjective completion.
     *
     * @param delta how much time has passed since the last update.
     */
    @Override
    public void update(float delta) {
        if (timer > duration) {
            status = ObjectiveStatus.COMPLETED;
        } else {
            this.timer += delta;
        }
    }

    public float remaining() {
        return Math.round(duration - timer);
    }

    /**
     * Gets a string that represents SurviveObjective, the amount of time remaining is updated from timer.
     *
     * @return a string representation of SurviveObjective.
     */
    @Override
    public String getObjectiveString() {
        return "Survive " + remaining()  + " seconds";
    }
}
