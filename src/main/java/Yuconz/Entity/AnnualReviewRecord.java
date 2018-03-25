package Yuconz.Entity;

import Yuconz.PropertyAccessor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Entity for an initial employment details record.
 */
@Entity
@Table
public class AnnualReviewRecord extends AbstractRecord
{
    @Column(nullable = false)
    private LocalDate periodStart;

    @Column(nullable = false)
    private LocalDate periodEnd;

    private String previousYearReview;

    private String previousYearTrainingMentoring;

    private String achievementOutcomesReview;

    private String futureObjectivePlans;

    private String trainingMentoringReview;

    private String reviewerSummary;

    private String employeeComments;

    @Column(nullable = false)
    private Boolean accepted = false;

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
     * Get period start.
     *
     * @return periodStart
     */
    public LocalDate getPeriodStart()
    {
        return periodStart;
    }

    /**
     * Set the period start.
     *
     * @param periodStart periodStart
     */
    public void setPeriodStart(LocalDate periodStart)
    {
        this.periodStart = periodStart;
    }

    /**
     * Get period end.
     *
     * @return periodEnd
     */
    public LocalDate getPeriodEnd()
    {
        return periodEnd;
    }

    /**
     * Set the period end.
     *
     * @param periodEnd periodEnd
     */
    public void setPeriodEnd(LocalDate periodEnd)
    {
        this.periodEnd = periodEnd;
    }

    public String getPreviousYearReview()
    {
        return previousYearReview;
    }

    public void setPreviousYearReview(String previousYearReview)
    {
        this.previousYearReview = previousYearReview;
    }

    public String getPreviousYearTrainingMentoring()
    {
        return previousYearTrainingMentoring;
    }

    public void setPreviousYearTrainingMentoring(String previousYearTrainingMentoring)
    {
        this.previousYearTrainingMentoring = previousYearTrainingMentoring;
    }

    /**
     * Get the achievement outcomes review.
     *
     * @return achievementOutcomesReview
     */
    public String getAchievementOutcomesReview()
    {
        return achievementOutcomesReview;
    }

    /**
     * Get the achievement outcomes review.
     *
     * @param achievementOutcomesReview achievementOutcomesReview
     */
    public void setAchievementOutcomesReview(String achievementOutcomesReview)
    {
        this.achievementOutcomesReview = achievementOutcomesReview;
    }

    /**
     * Get the future objective plans.
     *
     * @return futureObjectivePlans
     */
    public String getFutureObjectivePlans()
    {
        return futureObjectivePlans;
    }

    /**
     * Set the future objective plans.
     *
     * @param futureObjectivePlans futureObjectivePlans
     */
    public void setFutureObjectivePlans(String futureObjectivePlans)
    {
        this.futureObjectivePlans = futureObjectivePlans;
    }

    /**
     * Get the training mentoring review.
     *
     * @return trainingMentoringReview
     */
    public String getTrainingMentoringReview()
    {
        return trainingMentoringReview;
    }

    /**
     * Set the training mentoring review.
     *
     * @param trainingMentoringReview trainingMentoringReview
     */
    public void setTrainingMentoringReview(String trainingMentoringReview)
    {
        this.trainingMentoringReview = trainingMentoringReview;
    }

    /**
     * Get the reviewer summary.
     *
     * @return reviewerSummary
     */
    public String getReviewerSummary()
    {
        return reviewerSummary;
    }

    /**
     * Set the reviewer summary.
     *
     * @param reviewerSummary
     */
    public void setReviewerSummary(String reviewerSummary)
    {
        this.reviewerSummary = reviewerSummary;
    }

    /**
     * Get employee comments.
     *
     * @return employeeComments
     */
    public String getEmployeeComments()
    {
        return employeeComments;
    }

    /**
     * Set the employee comments.
     *
     * @param employeeComments employeeComments
     */
    public void setEmployeeComments(String employeeComments)
    {
        this.employeeComments = employeeComments;
    }

    /**
     * If has been reviewed by HR.
     *
     * @return True or false
     */
    public Boolean isAccepted()
    {
        return accepted;
    }

    /**
     * Set if HR has reviewed.
     *
     * @param accepted True or false
     */
    public void setAccepted(Boolean accepted)
    {
        this.accepted = accepted;
    }

    /**
     * Get the reviewee signature.
     *
     * @return revieweeSignature
     */
    public Signature getRevieweeSignature()
    {
        return revieweeSignature;
    }

    /**
     * Set the reviewee signature.
     *
     * @param revieweeSignature revieweeSignature
     */
    public void setRevieweeSignature(Signature revieweeSignature)
    {
        this.revieweeSignature = revieweeSignature;
    }

    /**
     * Get the first reviewer signature.
     *
     * @return reviewer1Signature
     */
    public Signature getReviewer1Signature()
    {
        return reviewer1Signature;
    }

    /**
     * Set the first reviewer signature.
     *
     * @param reviewer1Signature reviewer1Signature
     */
    public void setReviewer1Signature(Signature reviewer1Signature)
    {
        this.reviewer1Signature = reviewer1Signature;
    }

    /**
     * Get the second reviewer signature.
     *
     * @return reviewer2Signature
     */
    public Signature getReviewer2Signature()
    {
        return reviewer2Signature;
    }

    /**
     * Set the second reviewer signature.
     *
     * @param reviewer2Signature reviewer2Signature
     */
    public void setReviewer2Signature(Signature reviewer2Signature)
    {
        this.reviewer2Signature = reviewer2Signature;
    }

    /**
     * Get the moderator.
     *
     * @return moderator
     */
    public User getModerator()
    {
        return moderator;
    }

    /**
     * Set the moderator.
     *
     * @param moderator moderator
     */
    public void setModerator(User moderator)
    {
        this.moderator = moderator;
    }

    /**
     * Get the first reviewer.
     *
     * @return reviewer1
     */
    public User getReviewer1()
    {
        return reviewer1;
    }

    /**
     * Set the first reviewer.
     *
     * @param reviewer1 reviewer1
     */
    public void setReviewer1(User reviewer1)
    {
        this.reviewer1 = reviewer1;
    }

    /**
     * Get the second reviewer.
     *
     * @return reviewer2
     */
    public User getReviewer2()
    {
        return reviewer2;
    }

    /**
     * Set the second reviewer.
     *
     * @param reviewer2 reviewer2
     */
    public void setReviewer2(User reviewer2)
    {
        this.reviewer2 = reviewer2;
    }


    public User getReviewee()
    {
        return getUser();
    }

    public void setReviewee(User user)
    {
        setUser(user);
    }

    public boolean isReady()
    {
        boolean r1 = getReviewer1() != null && getReviewer1Signature() != null;
        boolean r2 = getReviewer2() != null && getReviewer2Signature() != null;
        boolean re = getReviewee() != null && getRevieweeSignature() != null;

        return r1 && r2 && re;
    }

    @Override
    public String getTitle()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return String.format("Annual Review (%s - %s)", getPeriodStart().format(formatter), getPeriodEnd().format(formatter));
    }

    public void apply(Map<String, Object> map)
    {
        map.forEach((name, value) -> {
            PropertyAccessor.set(this, name, value);
        });
    }

    @Override
    public String renderSummary()
    {
        return String.format("<b>Reviewer 1</b>: %s <br> <b>Reviewer 2</b>: %s", getReviewer1().getFullName(), getReviewer2().getFullName());
    }
}
