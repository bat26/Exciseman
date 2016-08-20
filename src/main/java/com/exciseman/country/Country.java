package com.exciseman.country;

/**
 * Created by bruce on 20/08/16.
 */
public class Country {
    private String name;
    private String abbrev;

    // Getters and setters are not required for this example.
    // GSON sets the fields directly using reflection.

    @Override
    public String toString() {
        return name + " - " + abbrev;
    }
}