package Yuconz.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class InitialEmploymentDetailsRecord extends AbstractRecord
{
    @ManyToMany
    private List<User> interviewers;

    @Temporal(TemporalType.DATE)
    private Date periodStart;

    @Temporal(TemporalType.DATE)
    private Date periodEnd;

    private String achievementOutcomesReview;

    private String futureObjectivePlans;

    private String trainingMentoringReview;

    private String reviewerSummary;

    private String employeeComments;

    private String trainingMentoringDevelopment;

    private boolean accepted;

    @OneToOne
    private Signature revieweeSignature;

    @OneToOne
    private Signature reviewer1Signature;

    @OneToOne
    private Signature revieweer2ignature;

    @ManyToOne
    private User moderator;

    @ManyToOne(optional = false)
    private User reviewer1;

    @ManyToOne
    private User reviewer2;

    public List<User> getInterviewers()
    {
        return interviewers;
    }

    public void setInterviewers(List<User> interviewers)
    {
        this.interviewers = interviewers;
    }

    public Date getPeriodStart()
    {
        return periodStart;
    }

    public void setPeriodStart(Date periodStart)
    {
        this.periodStart = periodStart;
    }

    public Date getPeriodEnd()
    {
        return periodEnd;
    }

    public void setPeriodEnd(Date periodEnd)
    {
        this.periodEnd = periodEnd;
    }

    public String getAchievementOutcomesReview()
    {
        return achievementOutcomesReview;
    }

    public void setAchievementOutcomesReview(String achievementOutcomesReview)
    {
        this.achievementOutcomesReview = achievementOutcomesReview;
    }

    public String getFutureObjectivePlans()
    {
        return futureObjectivePlans;
    }

    public void setFutureObjectivePlans(String futureObjectivePlans)
    {
        this.futureObjectivePlans = futureObjectivePlans;
    }

    public String getTrainingMentoringReview()
    {
        return trainingMentoringReview;
    }

    public void setTrainingMentoringReview(String trainingMentoringReview)
    {
        this.trainingMentoringReview = trainingMentoringReview;
    }

    public String getReviewerSummary()
    {
        return reviewerSummary;
    }

    public void setReviewerSummary(String reviewerSummary)
    {
        this.reviewerSummary = reviewerSummary;
    }

    public String getEmployeeComments()
    {
        return employeeComments;
    }

    public void setEmployeeComments(String employeeComments)
    {
        this.employeeComments = employeeComments;
    }

    public String getTrainingMentoringDevelopment()
    {
        return trainingMentoringDevelopment;
    }

    public void setTrainingMentoringDevelopment(String trainingMentoringDevelopment)
    {
        this.trainingMentoringDevelopment = trainingMentoringDevelopment;
    }

    public boolean isAccepted()
    {
        return accepted;
    }

    public void setAccepted(boolean accepted)
    {
        this.accepted = accepted;
    }

    public Signature getRevieweeSignature()
    {
        return revieweeSignature;
    }

    public void setRevieweeSignature(Signature revieweeSignature)
    {
        this.revieweeSignature = revieweeSignature;
    }

    public Signature getReviewer1Signature()
    {
        return reviewer1Signature;
    }

    public void setReviewer1Signature(Signature reviewer1Signature)
    {
        this.reviewer1Signature = reviewer1Signature;
    }

    public Signature getRevieweer2ignature()
    {
        return revieweer2ignature;
    }

    public void setRevieweer2ignature(Signature revieweer2ignature)
    {
        this.revieweer2ignature = revieweer2ignature;
    }

    public User getModerator()
    {
        return moderator;
    }

    public void setModerator(User moderator)
    {
        this.moderator = moderator;
    }

    public User getReviewer1()
    {
        return reviewer1;
    }

    public void setReviewer1(User reviewer1)
    {
        this.reviewer1 = reviewer1;
    }

    public User getReviewer2()
    {
        return reviewer2;
    }

    public void setReviewer2(User reviewer2)
    {
        this.reviewer2 = reviewer2;
    }
}
