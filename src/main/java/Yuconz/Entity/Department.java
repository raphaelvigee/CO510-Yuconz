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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Section> getSections()
    {
        return sections;
    }

    public void setSections(List<Section> sections)
    {
        this.sections = sections;
    }
}
