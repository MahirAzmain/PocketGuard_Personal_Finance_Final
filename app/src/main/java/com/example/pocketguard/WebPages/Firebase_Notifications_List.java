package com.example.pocketguard.WebPages;
/**
 * Model class representing a Firebase Notification.
 *
 * <p>This class follows the JavaBeans design pattern, which includes private fields,
 * public getters and setters, and a no-argument constructor.</p>
 */
public class Firebase_Notifications_List {

    private String username;
    private String link;
    private String date;
    /**
     * Default no-argument constructor.
     */
    public Firebase_Notifications_List() {
    }
    /**
     * Gets the date of the notification.
     *
     * @return the date of the notification.
     */
    public String getDate() {
        return date;
    }
    /**
     * Sets the date of the notification.
     *
     * @param date the date to set.
     */
    public void setDate(String date) {
        this.date = date;
    }
    /**
     * Gets the username associated with the notification.
     *
     * @return the username of the notification.
     */
    public String getUsername() {
        return username;
    }
    /**
     * Sets the username associated with the notification.
     *
     * @param username the username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Gets the link associated with the notification.
     *
     * @return the link of the notification.
     */
    public String getLink() {
        return link;
    }
    /**
     * Sets the link associated with the notification.
     *
     * @param link the link to set.
     */
    public void setLink(String link) {
        this.link = link;
    }
}
