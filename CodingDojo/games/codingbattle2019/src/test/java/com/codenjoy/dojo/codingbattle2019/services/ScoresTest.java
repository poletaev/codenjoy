package com.codenjoy.dojo.codingbattle2019.services;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.services.PlayerScores;
import com.codenjoy.dojo.services.settings.Settings;
import com.codenjoy.dojo.services.settings.SettingsImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScoresTest {
    private PlayerScores scores;

    private Settings settings;
    private Integer loosePenalty;
    private Integer removeEnemyScore;

    public void loose() {
        scores.event(Events.LOOSE);
    }

    public void destroyEnemy() {
        scores.event(Events.DESTROY_ENEMY);
    }
    // TODO implement for other cases

    @Before
    public void setup() {
        settings = new SettingsImpl();
        scores = new Scores(0, settings);

        loosePenalty = settings.getParameter("Loose penalty").type(Integer.class).getValue();
        removeEnemyScore = settings.getParameter("Destroy enemy score").type(Integer.class).getValue();
    }

    @Test
    public void shouldCollectScores() {
        scores = new Scores(140, settings);

        destroyEnemy();  //+500
        destroyEnemy();  //+500
        destroyEnemy();  //+500
        destroyEnemy();  //+500

        loose(); //-100

        Assert.assertEquals(140 + 4 * removeEnemyScore - loosePenalty, scores.getScore());
    }

    @Test
    public void shouldStillZeroAfterDead() {
        loose();    //-100

        Assert.assertEquals(0, scores.getScore());
    }

    @Test
    public void shouldClearScore() {
        destroyEnemy();    // +30

        scores.clear();

        Assert.assertEquals(0, scores.getScore());
    }


}
