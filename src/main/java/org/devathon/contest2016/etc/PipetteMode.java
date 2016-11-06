package org.devathon.contest2016.etc;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @at 11/5/2016
 */
public enum PipetteMode {
    ITEM, LIQUID, BOTH, SEND, DESTINATION;

    public static PipetteMode parse(String line) {
        // if player hasn't given us a mode assume that we want to transport everything
        if (line.isEmpty()) return BOTH;

        // player has given us a mode that they want, grab the first letter to help typos if we were just checking the entire word they give
        switch (line.toLowerCase().substring(0, 1)) {
            case "b": return BOTH; // user wants to transport everything
            case "i": return ITEM; // user wants to only transport items
            case "l": return LIQUID; // user wants to only transport liquids
            case "d": return DESTINATION; // user wants to have items be sent here
            case "s": return SEND; // user wants to have items sent from here
            default: return BOTH; // user is stupid or is smart enough to not put a mode
        }
    }

    @Override
    public String toString() {
        return "Mode{" + name() + "}";
    }

}
