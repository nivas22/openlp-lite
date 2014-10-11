package org.openlp.lite.domain;

import java.io.Serializable;

/**
 * @Author : Madasamy
 * @Version : 0.1
 */
public class Author implements Serializable
{
    private int id;
    private String firstName;
    private String lastName;
    private String displayName;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return "Author - " + firstName + lastName;
    }
}
