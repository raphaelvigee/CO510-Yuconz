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

    /**
     * get interview notes
     *
     * @return interviewNotes the interview notes
     */
    public String getInterviewNotes()
    {
        return interviewNotes;
    }

    /**
     * set the interview notes
     *
     * @param interviewNotes the notes made in the interview
     */
    public void setInterviewNotes(String interviewNotes)
    {
        this.interviewNotes = interviewNotes;
    }

    /**
     * get employment start date
     *
     * @return startDate start date
     */
    public LocalDate getStartDate()
    {
        return startDate;
    }

    /**
     * set employment start date
     *
     * @param startDate start date
     */
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

    /**
     * get title
     *
     * @return string initial employment details
     */
    @Override
    public String getTitle()
    {
        return "Initial Employment Details";
    }

    /**
     * get summary
     *
     * @return string ""
     */
    @Override
    public String renderSummary()
    {
        return "";
    }
}
