package Yuconz.Manager;

import Yuconz.Entity.AnnualReviewRecord;
import Yuconz.Entity.InitialEmploymentDetailsRecord;
import Yuconz.Entity.User;
import Yuconz.Model.UserRole;
import Yuconz.Service.Hibernate;
import com.sallyf.sallyf.Container.ServiceInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
interface TriFunction<A, B, C, R>
{
    R apply(A a, B b, C c);
}

/**
 * Annual Review Manager.
 */
public class AnnualReviewManager implements ServiceInterface
{
    private Hibernate hibernate;

    private RecordManager recordManager;

    public AnnualReviewManager(Hibernate hibernate, RecordManager recordManager)
    {
        this.hibernate = hibernate;
        this.recordManager = recordManager;
    }

    /**
     * Checks is an Annual Review is signed.
     *
     * @param review the review
     * @param user   the user
     * @return true or false
     */
    public boolean requiresSignature(AnnualReviewRecord review, User user)
    {
        if (review.getId() == null) {
            return false;
        }

        return Arrays.asList(review.getReviewer1(), review.getReviewer2(), review.getReviewee()).contains(user);
    }

    /**
     * Checks whether a user needs a new Annual Review.
     *
     * @param user the user
     * @return true or false
     */
    public boolean needsNew(User user)
    {
        if (user.getRole().equals(UserRole.DIRECTOR)) {
            return false;
        }

        InitialEmploymentDetailsRecord ied = recordManager.getLastRecord(user, InitialEmploymentDetailsRecord.class);

        if (ied == null) {
            return false;
        }

        LocalDate now = LocalDate.now();

        LocalDate baseDate = ied.getStartDate().withYear(now.getYear());

        LocalDate startReviewPeriod = baseDate.minusDays(14);
        LocalDate endReviewPeriod = baseDate.plusDays(14);

        if (now.toEpochDay() < ied.getStartDate().plusMonths(1).toEpochDay()) {
            return false;
        }

        TriFunction<LocalDate, LocalDate, LocalDate, Boolean> between = (d, startDate, endDate) -> d.toEpochDay() >= startDate.toEpochDay() && d.toEpochDay() <= endDate.toEpochDay();

        AnnualReviewRecord lastReview = getLast(user, null);

        if (lastReview != null) {
            return between.apply(now, startReviewPeriod, endReviewPeriod) && !between.apply(lastReview.getCreatedAt(), startReviewPeriod, endReviewPeriod);
        }

        return between.apply(now, startReviewPeriod, endReviewPeriod);
    }

    /**
     * Helper for getting a list of Annual Reviews.
     *
     * @param query query
     * @return list of annual reviews
     */
    public List<AnnualReviewRecord> getList(Supplier<Query> query)
    {
        Session session = hibernate.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        List<AnnualReviewRecord> records = query.get().getResultList();

        transaction.commit();

        return records;
    }

    /**
     * Helper for getting a list of incomplete Annual Reviews.
     *
     * @param user the user
     * @return list of annual reviews
     */
    public List<AnnualReviewRecord> getIncomplete(User user)
    {
        Session session = hibernate.getCurrentSession();

        return getList(() -> session.createQuery("from AnnualReviewRecord where (reviewer1 = :user and reviewer1Signature is null) or (reviewer2 = :user and reviewer2Signature is null) or (user = :user and revieweeSignature is null)")
                .setParameter("user", user));
    }

    /**
     * Helper for getting a list of currently active Annual Reviews.
     *
     * @param user the user
     * @return list of annual reviews
     */
    public List<AnnualReviewRecord> getUnderReview(User user)
    {
        Session session = hibernate.getCurrentSession();

        return getList(() -> session.createQuery("from AnnualReviewRecord where (reviewer1 = :user or reviewer2 = :user or user = :user) and accepted != true")
                .setParameter("user", user));
    }

    /**
     * Helper for getting a list of Annual Reviews requiring attention.
     *
     * @return list of annual reviews
     */
    public List<AnnualReviewRecord> getRequiresAttentionAsHR()
    {
        Session session = hibernate.getCurrentSession();

        return getList(() -> session.createQuery("from AnnualReviewRecord where reviewer1 is null or reviewer2 is null or user is null or accepted != true"));
    }

    /**
     * Get list of Annual Reviews.
     *
     * @param user     the user
     * @param accepted true: only accepted, false: only not accepted, null: ignore
     * @return list of annual reviews
     */
    public List<AnnualReviewRecord> getRecords(User user, Boolean accepted)
    {
        Session session = hibernate.getCurrentSession();

        String acceptedQuery = accepted == null ? "" : " and accepted = " + String.valueOf(accepted);

        return getList(() -> session.createQuery("from AnnualReviewRecord where user = :user" + acceptedQuery)
                .setParameter("user", user));
    }

    /**
     * Gets the last Annual Review.
     *
     * @param user     the user
     * @param accepted true: only accepted, false: only not accepted, null: ignore
     * @return last Annual Review
     */
    public AnnualReviewRecord getLast(User user, Boolean accepted)
    {
        Session session = hibernate.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        String acceptedQuery = accepted == null ? "" : " and accepted = " + String.valueOf(accepted);

        Query query = session.createQuery("from AnnualReviewRecord r where user = :user" + acceptedQuery)
                .setMaxResults(1)
                .setParameter("user", user);

        AnnualReviewRecord record;
        try {
            record = (AnnualReviewRecord) query.getSingleResult();
        } catch (NoResultException e) {
            record = null;
        }

        transaction.commit();

        return record;
    }

    /**
     * Get candidates for reviewer 1.
     *
     * @param user the user
     * @return list of reviewers
     */
    public List<User> getCandidatesReviewer1(User user)
    {
        Session session = hibernate.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from User where (role = 'DIRECTOR' or role = 'MANAGER') and section = :userSection")
                .setParameter("userSection", user.getSection());

        List<User> records = query.getResultList();

        transaction.commit();

        return records;
    }

    /**
     * Get all candidates for reviewer 2.
     *
     * @param reviewee the reviewee
     * @return list of reviewers
     */
    public List<User> getAllCandidatesReviewer2(User reviewee)
    {
        List<User> candidates = new ArrayList<>();
        for (User reviewer1 : getCandidatesReviewer1(reviewee)) {
            candidates.addAll(getCandidatesReviewer2(reviewer1));
        }

        return candidates;
    }

    /**
     * Get candidates for reviewer 2.
     *
     * @param review the review
     * @return list of reviewers
     */
    public List<User> getCandidatesReviewer2(AnnualReviewRecord review)
    {
        if (review == null) {
            return new ArrayList<>();
        }

        return getCandidatesReviewer2(review.getReviewer1());
    }

    /**
     * Get candidates for reviewer 2.
     *
     * @param reviewer1 the reviewer 1
     * @return list of reviewers
     */
    public List<User> getCandidatesReviewer2(User reviewer1)
    {
        if (reviewer1 == null) {
            return new ArrayList<>();
        }

        Session session = hibernate.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from User r where role = :reviewer1Role and r <> :reviewer1")
                .setParameter("reviewer1", reviewer1)
                .setParameter("reviewer1Role", reviewer1.getRole());

        List<User> records = query.getResultList();

        transaction.commit();

        return records;
    }
}
