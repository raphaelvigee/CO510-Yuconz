package Yuconz.Entity;

import java.util.List;

public enum Department
{
    HUMAN_RESOURCES("Human Resources"),
    DESIGN("Design"),
    FINANCE("Finance");

    private String name;

    private List<Section> sections;

    Department(String name)
    {
        this.name = name;
    }

    /**
     * Get the name.
     * @return name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Set the name.
     * @param name name.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Get the sections.
     * @return sections.
     */
    public List<Section> getSections()
    {
        return sections;
    }

    /**
     * Set the sections.
     * @param sections sections.
     */
    public void setSections(List<Section> sections)
    {
        this.sections = sections;
    }
}
