package Yuconz.Entity;

/**
 * Enum for employment sections.
 * Sections are parts of a department.
 */
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

    /**
     * get the name of the section.
     * @return name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Set the name of the section.
     * @param name name.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Get the department name.
     * @return department.
     */
    public Department getDepartment()
    {
        return department;
    }

    /**
     * Custom toString method.
     * @return The String to be returned.
     */
    @Override
    public String toString()
    {
        return String.format("%s - %s", getDepartment().getName(), getName());
    }

    /**
     * Gives a random section.
     * @return The selected section.
     */
    public static Section random()
    {
        return Section.values()[(int) (Math.random() * Section.values().length)];
    }
}
