package Yuconz.Entity;

public enum Section
{
    RECRUITMENT("Recruitment", Department.HUMAN_RESOURCES),
    INTERNAL("Internal", Department.HUMAN_RESOURCES),
    FRONT_END("Front end", Department.DESIGN),
    PUBLICATIONS("Publications", Department.DESIGN),
    INSURANCE_OFFICE("Insurance Office", Department.FINANCE),
    TAX_TEAM("Tax Team", Department.FINANCE),
    DIRECTORATE("Directorate", Department.FINANCE);

    private String name;

    private Department department;

    Section(String name, Department department)
    {
        this.name = name;
        this.department = department;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Department getDepartment()
    {
        return department;
    }

    @Override
    public String toString()
    {
        return String.format("%s - %s", getDepartment().getName(), getName());
    }

    public static Section random()
    {
        return Section.values()[(int) (Math.random() * Section.values().length)];
    }
}
