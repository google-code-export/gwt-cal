package com.bradrydzewski.gwt.calendar.client.monthview;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages the <code>MultiDayLayoutDescription</code> and <code>
 */
public class AppointmentStackingManager {

    private int highestLayer = 0;

    private HashMap<Integer, ArrayList<WeekTopStackableDescription>>
            layeredDescriptions =
            new HashMap<Integer, ArrayList<WeekTopStackableDescription>>();

    public void assignLayer(WeekTopStackableDescription description) {
        boolean layerAssigned;
        int currentlyInspectedLayer = 0;
        do {
            initLayer(currentlyInspectedLayer);
            layerAssigned = assignLayer(currentlyInspectedLayer, description);
            currentlyInspectedLayer++;
        } while (!layerAssigned);
    }

    public int singleDayLowestOrder(int day) {
        boolean layerFound = false;
        int currentlyInspectedLayer = 0;
        do {
            boolean isLayerAllocated =
                    isLayerAllocated(currentlyInspectedLayer);
            if (isLayerAllocated) {
                if (overlapsWithDescriptionInLayer(
                        layeredDescriptions.get(currentlyInspectedLayer), day,
                        day)) {
                    currentlyInspectedLayer++;
                } else {
                    layerFound = true;
                }
            } else {
                layerFound = true;
            }

        } while (!layerFound);
        return currentlyInspectedLayer;
    }

    public ArrayList<WeekTopStackableDescription> getDescriptionsInLayer(
            int layer) {
        return layeredDescriptions.get(layer);
    }

    private boolean overlapsWithDescriptionInLayer(
            ArrayList<WeekTopStackableDescription> layerDescriptions, int start,
            int end) {
        if (layerDescriptions != null) {
            for (WeekTopStackableDescription layerDescription : layerDescriptions) {
                if (layerDescription
                        .overlapsWithRange(start, end)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean assignLayer(int layer,
                                WeekTopStackableDescription description) {
        ArrayList<WeekTopStackableDescription> layerDescriptions =
                layeredDescriptions.get(layer);

        boolean assigned = false;
        if (!overlapsWithDescriptionInLayer(layerDescriptions,
                description.getWeekStartDay(), description.getWeekEndDay())) {
            layerDescriptions.add(description);
            highestLayer = Math.max(highestLayer, layer);
            assigned = true;
        }
        return assigned;
    }

    private boolean isLayerAllocated(int layer) {
        return layeredDescriptions.get(layer) != null;
    }

    private void initLayer(int layerNumber) {
        if (!isLayerAllocated(layerNumber)) {
            layeredDescriptions.put(layerNumber,
                    new ArrayList<WeekTopStackableDescription>());
        }
    }

    public boolean areThereAppointmentsOn(int day) {
        boolean thereAre = false;
        for (int layersIndex = 0; layersIndex <= highestLayer; layersIndex++) {
            ArrayList<WeekTopStackableDescription> layerDescriptions =
                    layeredDescriptions.get(layersIndex);
            if (overlapsWithDescriptionInLayer(layerDescriptions, day, day)) {
                thereAre = true;
                break;
            }
        }
        return thereAre;
    }
}
