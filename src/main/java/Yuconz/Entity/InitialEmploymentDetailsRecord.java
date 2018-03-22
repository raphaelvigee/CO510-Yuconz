package Yuconz.Entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table
public class InitialEmploymentDetailsRecord extends AbstractRecord
{
    private String interviewNotes;

    private LocalDate startDate;

    public String getInterviewNotes()
    {
        return interviewNotes;
    }

    public void setInterviewNotes(String interviewNotes)
    {
        this.interviewNotes = interviewNotes;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    /**
     * Generates a hashmap of all the User's data.
     *
     * @return User data hashmap
     */
    public Map<String, Object> toHashMap()
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("interviewNotes", getInterviewNotes());
        map.put("startDate", getStartDate());

        return map;
    }

    /**
     * Updates User properties to those from provided hashmap.
     *
     * @param map Hashmap of User properties
     */
    public void applyHashMap(Map<String, Object> map)
    {
        setInterviewNotes((String) map.get("interviewNotes"));
        setStartDate((LocalDate) map.get("startDate"));
    }

    @Override
    public String getTitle()
    {
        return "Initial Employment Details";
    }

    @Override
    public String renderSummary()
    {
        return "";
    }
}
