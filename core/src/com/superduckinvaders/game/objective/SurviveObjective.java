package com.superduckinvaders.game.objective;

import com.superduckinvaders.game.Round;

/**
 * Represents a time based objective.
 */

public class SurviveObjective extends Objective {

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
        super(parent);
    }

    /**
     * Updates the status towards SurviveObjective completion.
     *
     * @param delta how much time has passed since the last update.
     */
    @Override
    public void update(float delta) {
        if (timer >= (120)) {
            status = ObjectiveStatus.COMPLETED;
        } else {
            this.timer += delta;
        }
    }

    /**
     * Gets a string that represents SurviveObjective, the amount of time remaining is updated from timer.
     *
     * @return a string representation of SurviveObjective.
     */
    @Override
    public String getObjectiveString() {
        return "Survive " + (20- Math.round(timer))  + " seconds";
    }
}
