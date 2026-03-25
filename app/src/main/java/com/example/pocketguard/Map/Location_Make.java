package com.example.pocketguard.Map;
/**
 * Represents a location with latitude and longitude coordinates.
 *
 * <p><b>Design Patterns Used:</b></p>
 * <ul>
 *     <li><b>Plain Old Java Object (POJO):</b> This class is a simple data holder with fields and corresponding getter and setter methods.</li>
 * </ul>
 */
public class Location_Make {

    /** Latitude coordinate. */
    public String latitute;

    /** Longitude coordinate. */
    public String longitute;
    /** Default constructor. */
    public Location_Make(){}
    /**
     * Constructor to initialize latitude and longitude coordinates.
     *
     * @param latitute The latitude coordinate.
     * @param longitute The longitude coordinate.
     */
    public Location_Make(String latitute, String longitute) {
        this.latitute = latitute;
        this.longitute = longitute;
    }

    /**
     * Get the latitude coordinate.
     *
     * @return The latitude coordinate.
     */
    public String getLatitute() {
        return latitute;
    }
    /**
     * Set the latitude coordinate.
     *
     * @param latitute The latitude coordinate to set.
     */
    public void setLatitute(String latitute) {
        this.latitute = latitute;
    }
    /**
     * Get the longitude coordinate.
     *
     * @return The longitude coordinate.
     */
    public String getLongitute() {
        return longitute;
    }
    /**
     * Set the longitude coordinate.
     *
     * @param longitute The longitude coordinate to set.
     */
    public void setLongitute(String longitute) {
        this.longitute = longitute;
    }
}
