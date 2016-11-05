package org.devathon.contest2016.listeners;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @at 11/5/2016
 */
public enum PipetteMode {
    ITEM, LIQUID, BOTH;

    public static PipetteMode parse(String line) {
        // if player hasn't given us a mode assume that we want to transport everything
        if (line.isEmpty()) return BOTH;

        // player has given us a mode that they want, grab the first letter to help typos if we were just checking the entire word they give
        switch (line.toLowerCase().substring(0, 1)) {
            case "b": return BOTH; // user wants to transport everything
            case "i": return ITEM; // user wants to only transport items
            case "l": return LIQUID; // user wants to only transport liquids
            default: return BOTH; // user is stupid
        }
    }

}
