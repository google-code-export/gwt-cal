package com.bradrydzewski.gwt.calendar.client.monthview;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test cases for the {@link com.bradrydzewski.gwt.calendar.client.monthview.AppointmentStackingManager}
 * component.
 *
 * @author Carlos D. Morales
 */
public class AppointmentStackingManagerTest {

    private AppointmentStackingManager stackManager =
            new AppointmentStackingManager();

    @Test
    public void assignFullWeekDescriptions() {
        stackManager.assignLayer(new MultiDayLayoutDescription(0, 6, null));
        assertEquals("Expected 1 description in the first layer", 1,
                stackManager.getDescriptionsInLayer(0).size());
        stackManager.assignLayer(new MultiDayLayoutDescription(0, 6, null));
        assertEquals("Expected 1 description in the first layer", 1,
                stackManager.getDescriptionsInLayer(0).size());
        assertEquals("Expected 1 description in the second layer", 1,
                stackManager.getDescriptionsInLayer(1).size());
        stackManager.assignLayer(new MultiDayLayoutDescription(0, 6, null));
        assertEquals("Expected 1 description in the second layer", 1,
                stackManager.getDescriptionsInLayer(2).size());
    }

    @Test
    public void assignTwoDescriptionsInLayer() {
        stackManager.assignLayer(new MultiDayLayoutDescription(0, 2, null));
        stackManager.assignLayer(new MultiDayLayoutDescription(3, 6, null));
        assertEquals("Expected 2 descriptions in the first layer", 2,
                stackManager.getDescriptionsInLayer(0).size());
    }

    @Test
    public void assignSevenDescriptionsPerLayer() {
        stackManager.assignLayer(new AllDayLayoutDescription(0, null));
        stackManager.assignLayer(new AllDayLayoutDescription(1, null));
        stackManager.assignLayer(new AllDayLayoutDescription(2, null));
        stackManager.assignLayer(new AllDayLayoutDescription(3, null));
        stackManager.assignLayer(new AllDayLayoutDescription(4, null));
        stackManager.assignLayer(new AllDayLayoutDescription(5, null));
        stackManager.assignLayer(new AllDayLayoutDescription(6, null));

        assertEquals("Expected 7 descriptions in the first layer", 7,
                stackManager.getDescriptionsInLayer(0).size());
    }

    @Test
    public void assignPartiallyOverlappingDescriptionsResultsInTwoLayers() {
        stackManager.assignLayer(new MultiDayLayoutDescription(0, 3, null));
        stackManager.assignLayer(new MultiDayLayoutDescription(3, 6, null));
        assertEquals("Expected 1 descriptions in the first layer", 1,
                stackManager.getDescriptionsInLayer(0).size());
        assertEquals("Expected 1 descriptions in the second layer", 1,
                stackManager.getDescriptionsInLayer(1).size());
    }

    @Test
    public void oneInLayerOneAndTwoInLayerTwo() {
        stackManager.assignLayer(new MultiDayLayoutDescription(0, 6, null));
        stackManager.assignLayer(new MultiDayLayoutDescription(0, 3, null));
        stackManager.assignLayer(new MultiDayLayoutDescription(4, 6, null));
        assertEquals("Expected 1 descriptions in the first layer", 1,
                stackManager.getDescriptionsInLayer(0).size());
        assertEquals("Expected 2 descriptions in the second layer", 2,
                stackManager.getDescriptionsInLayer(1).size());
    }

    @Test
    public void buildBrickWall() {
        stackManager.assignLayer(new MultiDayLayoutDescription(0, 1, null));
        stackManager.assignLayer(new MultiDayLayoutDescription(1, 2, null));
        stackManager.assignLayer(new MultiDayLayoutDescription(3, 4, null));
        stackManager.assignLayer(new MultiDayLayoutDescription(4, 5, null));
        stackManager.assignLayer(new MultiDayLayoutDescription(6, 6, null));

        assertCurrentStackingOrder(0, 1);
        assertCurrentStackingOrder(1, 2);
        assertCurrentStackingOrder(2, 0);
        assertCurrentStackingOrder(3, 1);
        assertCurrentStackingOrder(4, 2);
        assertCurrentStackingOrder(5, 0);
        assertCurrentStackingOrder(6, 1);

        assertEquals("Expected 3 descriptions in the first layer", 3,
                stackManager.getDescriptionsInLayer(0).size());
        assertEquals("Expected 2 descriptions in the second layer", 2,
                stackManager.getDescriptionsInLayer(1).size());
    }

    private void assertCurrentStackingOrder(int day, int expected) {
        assertEquals("Unexpected lowest stack order for day " + day, expected,
                stackManager.singleDayLowestOrder(day));
    }
}
