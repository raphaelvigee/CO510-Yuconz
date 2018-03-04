package Yuconz.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Entity for an initial employment details record.
 */
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
    private Signature reviewer2Signature;

    @ManyToOne
    private User moderator;

    @ManyToOne(optional = false)
    private User reviewer1;

    @ManyToOne
    private User reviewer2;

    /**
     * Get the Interviewers.
     * @return interviewers
     */
    public List<User> getInterviewers()
    {
        return interviewers;
    }

    /**
     * Set the Interviewers.
     * @param interviewers interviewers
     */
    public void setInterviewers(List<User> interviewers)
    {
        this.interviewers = interviewers;
    }

    /**
     * Get period start.
     * @return periodStart
     */
    public Date getPeriodStart()
    {
        return periodStart;
    }

    /**
     * Set the period start.
     * @param periodStart periodStart
     */
    public void setPeriodStart(Date periodStart)
    {
        this.periodStart = periodStart;
    }

    /**
     * Get period end.
     * @return periodEnd
     */
    public Date getPeriodEnd()
    {
        return periodEnd;
    }

    /**
     * Set the period end.
     * @param periodEnd periodEnd
     */
    public void setPeriodEnd(Date periodEnd)
    {
        this.periodEnd = periodEnd;
    }

    /**
     * Get the achievement outcomes review.
     * @return achievementOutcomesReview
     */
    public String getAchievementOutcomesReview()
    {
        return achievementOutcomesReview;
    }

    /**
     * Get the achievement outcomes review.
     * @param achievementOutcomesReview achievementOutcomesReview
     */
    public void setAchievementOutcomesReview(String achievementOutcomesReview)
    {
        this.achievementOutcomesReview = achievementOutcomesReview;
    }

    /**
     * Get the future objective plans.
     * @return futureObjectivePlans
     */
    public String getFutureObjectivePlans()
    {
        return futureObjectivePlans;
    }

    /**
     * Set the future objective plans.
     * @param futureObjectivePlans futureObjectivePlans
     */
    public void setFutureObjectivePlans(String futureObjectivePlans)
    {
        this.futureObjectivePlans = futureObjectivePlans;
    }

    /**
     * Get the training mentoring review.
     * @return trainingMentoringReview
     */
    public String getTrainingMentoringReview()
    {
        return trainingMentoringReview;
    }

    /**
     * Set the training mentoring review.
     * @param trainingMentoringReview trainingMentoringReview
     */
    public void setTrainingMentoringReview(String trainingMentoringReview)
    {
        this.trainingMentoringReview = trainingMentoringReview;
    }

    /**
     * Get the reviewer summary.
     * @return reviewerSummary
     */
    public String getReviewerSummary()
    {
        return reviewerSummary;
    }

    /**
     * Set the reviewer summary.
     * @param reviewerSummary
     */
    public void setReviewerSummary(String reviewerSummary)
    {
        this.reviewerSummary = reviewerSummary;
    }

    /**
     * Get employee comments.
     * @return employeeComments
     */
    public String getEmployeeComments()
    {
        return employeeComments;
    }

    /**
     * Set the employee comments.
     * @param employeeComments employeeComments
     */
    public void setEmployeeComments(String employeeComments)
    {
        this.employeeComments = employeeComments;
    }

    /**
     * Get the training mentoring development.
     * @return trainingMentoringDevelopment
     */
    public String getTrainingMentoringDevelopment()
    {
        return trainingMentoringDevelopment;
    }

    /**
     * Set the training mentoring development.
     * @param trainingMentoringDevelopment trainingMentoringDevelopment
     */
    public void setTrainingMentoringDevelopment(String trainingMentoringDevelopment)
    {
        this.trainingMentoringDevelopment = trainingMentoringDevelopment;
    }

    /**
     * If has been reviewed by HR.
     * @return True or false
     */
    public boolean isAccepted()
    {
        return accepted;
    }

    /**
     * Set if HR has reviewed.
     * @param accepted True or false
     */
    public void setAccepted(boolean accepted)
    {
        this.accepted = accepted;
    }

    /**
     * Get the reviewee signature.
     * @return revieweeSignature
     */
    public Signature getRevieweeSignature()
    {
        return revieweeSignature;
    }

    /**
     * Set the reviewee signature.
     * @param revieweeSignature revieweeSignature
     */
    public void setRevieweeSignature(Signature revieweeSignature)
    {
        this.revieweeSignature = revieweeSignature;
    }

    /**
     * Get the first reviewer signature.
     * @return reviewer1Signature
     */
    public Signature getReviewer1Signature()
    {
        return reviewer1Signature;
    }

    /**
     * Set the first reviewer signature.
     * @param reviewer1Signature reviewer1Signature
     */
    public void setReviewer1Signature(Signature reviewer1Signature)
    {
        this.reviewer1Signature = reviewer1Signature;
    }

    /**
     * Get the second reviewer signature.
     * @return reviewer2Signature
     */
    public Signature getReviewer2Signature()
    {
        return reviewer2Signature;
    }

    /**
     * Set the second reviewer signature.
     * @param reviewer2Signature reviewer2Signature
     */
    public void setRevieweer2ignature(Signature reviewer2Signature)
    {
        this.reviewer2Signature = reviewer2Signature;
    }

    /**
     * Get the moderator.
     * @return moderator
     */
    public User getModerator()
    {
        return moderator;
    }

    /**
     * Set the moderator.
     * @param moderator moderator
     */
    public void setModerator(User moderator)
    {
        this.moderator = moderator;
    }

    /**
     * Get the first reviewer.
     * @return reviewer1
     */
    public User getReviewer1()
    {
        return reviewer1;
    }

    /**
     * Set the first reviewer.
     * @param reviewer1 reviewer1
     */
    public void setReviewer1(User reviewer1)
    {
        this.reviewer1 = reviewer1;
    }

    /**
     * Get the second reviewer.
     * @return reviewer2
     */
    public User getReviewer2()
    {
        return reviewer2;
    }

    /**
     * Set the second reviewer.
     * @param reviewer2 reviewer2
     */
    public void setReviewer2(User reviewer2)
    {
        this.reviewer2 = reviewer2;
    }
}
