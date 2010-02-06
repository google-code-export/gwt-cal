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

   private AppointmentStackingManager stackManager = new AppointmentStackingManager();

   @Test
   public void assignFullWeekDescriptions() {
      stackManager.assignLayer(new AppointmentLayoutDescription(0, 6, null));
      assertEquals("Expected 1 description in the first layer", 1,
                   stackManager.getDescriptionsInLayer(0).size());
      stackManager.assignLayer(new AppointmentLayoutDescription(0, 6, null));
      assertEquals("Expected 1 description in the first layer", 1,
                   stackManager.getDescriptionsInLayer(0).size());
      assertEquals("Expected 1 description in the second layer", 1,
                   stackManager.getDescriptionsInLayer(1).size());
      stackManager.assignLayer(new AppointmentLayoutDescription(0, 6, null));
      assertEquals("Expected 1 description in the second layer", 1,
                   stackManager.getDescriptionsInLayer(2).size());
   }

   @Test
   public void assignTwoDescriptionsInLayer() {
      stackManager.assignLayer(new AppointmentLayoutDescription(0, 2, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(3, 6, null));
      assertEquals("Expected 2 descriptions in the first layer", 2,
                   stackManager.getDescriptionsInLayer(0).size());
   }

   @Test
   public void assignSevenDescriptionsPerLayer() {
      stackManager.assignLayer(new AppointmentLayoutDescription(0, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(1, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(2, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(3, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(4, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(5, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(6, null));

      assertEquals("Expected 7 descriptions in the first layer", 7,
                   stackManager.getDescriptionsInLayer(0).size());
   }

   @Test
   public void assignPartiallyOverlappingDescriptionsResultsInTwoLayers() {
      stackManager.assignLayer(new AppointmentLayoutDescription(0, 3, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(3, 6, null));
      assertEquals("Expected 1 descriptions in the first layer", 1,
                   stackManager.getDescriptionsInLayer(0).size());
      assertEquals("Expected 1 descriptions in the second layer", 1,
                   stackManager.getDescriptionsInLayer(1).size());
   }

   @Test
   public void oneInLayerOneAndTwoInLayerTwo() {
      stackManager.assignLayer(new AppointmentLayoutDescription(0, 6, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(0, 3, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(4, 6, null));
      assertEquals("Expected 1 descriptions in the first layer", 1,
                   stackManager.getDescriptionsInLayer(0).size());
      assertEquals("Expected 2 descriptions in the second layer", 2,
                   stackManager.getDescriptionsInLayer(1).size());
   }

   @Test
   public void buildBrickWall() {
      stackManager.assignLayer(new AppointmentLayoutDescription(0, 1, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(1, 2, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(3, 4, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(4, 5, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(6, 6, null));

      assertCurrentlyAvailableStackingOrder(0, 1);
      assertCurrentlyAvailableStackingOrder(1, 2);
      assertCurrentlyAvailableStackingOrder(2, 0);
      assertCurrentlyAvailableStackingOrder(3, 1);
      assertCurrentlyAvailableStackingOrder(4, 2);
      assertCurrentlyAvailableStackingOrder(5, 0);
      assertCurrentlyAvailableStackingOrder(6, 1);

      assertEquals("Expected 3 descriptions in the first layer", 3,
                   stackManager.getDescriptionsInLayer(0).size());
      assertEquals("Expected 2 descriptions in the second layer", 2,
                   stackManager.getDescriptionsInLayer(1).size());
   }

   @Test
   public void singleDaysSlipThroughMultidays() {
      stackManager.assignLayer(new AppointmentLayoutDescription(0, 0, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(0, 1, null));

      assertCurrentlyAvailableStackingOrder(0, 2);
      assertCurrentlyAvailableStackingOrder(1, 0);

      assertEquals("Next available layer should be after the 2nd. multi-day",
                   2, stackManager.nextLowestLayerIndex(1, 1));
   }

   @Test
   public void multidayAboveMaxLayerGetsSplit() {
      stackManager.setLayerOverflowLimit(2);
      stackManager.assignLayer(new AppointmentLayoutDescription(0, 5, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(0, 5, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(0, 5, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(0, 6, null));

      assertCurrentlyAvailableStackingOrder(0, 4);
      assertCurrentlyAvailableStackingOrder(1, 4);
      assertCurrentlyAvailableStackingOrder(2, 4);
      assertCurrentlyAvailableStackingOrder(3, 4);
      assertCurrentlyAvailableStackingOrder(4, 4);
      assertCurrentlyAvailableStackingOrder(5, 4);
      assertCurrentlyAvailableStackingOrder(6, 1);

   }

   @Test
   public void multidayLandsMountains() {

      stackManager.setLayerOverflowLimit(2);

      stackManager.assignLayer(new AppointmentLayoutDescription(0, 0, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(0, 0, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(0, 0, null));

      stackManager.assignLayer(new AppointmentLayoutDescription(1, 1, null));
      stackManager.assignLayer(new AppointmentLayoutDescription(1, 1, null));

      stackManager.assignLayer(new AppointmentLayoutDescription(0, 1, null));

      assertCurrentlyAvailableStackingOrder(0, 4);
      assertCurrentlyAvailableStackingOrder(1, 3);

   }

   private void assertCurrentlyAvailableStackingOrder(int day, int expected) {
      assertEquals("Unexpected lowest stack order for day " + day, expected,
                   stackManager.lowestLayerIndex(day));
   }
}
