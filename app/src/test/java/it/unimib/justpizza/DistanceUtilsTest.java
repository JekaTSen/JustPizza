package it.unimib.justpizza;

import org.junit.Test;

import it.unimib.justpizza.utils.Constants;
import it.unimib.justpizza.utils.DistanceUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DistanceUtilsTest {

    @Test
    public void samePoint_isAlmostZero() {
        double km = DistanceUtils.distanceKm(
                Constants.PIZZERIA_LAT, Constants.PIZZERIA_LON,
                Constants.PIZZERIA_LAT, Constants.PIZZERIA_LON);
        assertEquals(0.0, km, 0.01);
    }

    @Test
    public void duomoMilano_isFartherThan5Km() {
        // Duomo Milano
        double km = DistanceUtils.distanceKm(
                Constants.PIZZERIA_LAT, Constants.PIZZERIA_LON,
                45.4642, 9.1900);
        assertTrue("Attesi ~15 km, trovati " + km, km > 5.0);
        assertTrue(km < 25.0);
    }

    @Test
    public void stazioneMonza_isWithin5Km() {
        double km = DistanceUtils.distanceKm(
                Constants.PIZZERIA_LAT, Constants.PIZZERIA_LON,
                45.5764, 9.2726);
        assertTrue("Atteso sotto 5 km, trovati " + km, km <= 5.0);
    }
}