package org.openlp.lite.domain;

/**
 * @Author : Madasamy
 * @Version : 0.1
 */
public class SongBook
{
    private String name;
    private String publisher;


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    @Override
    public String toString()
    {
        return "SongBook[name=" + name + ", publisher=" + publisher + "]";
    }
}
