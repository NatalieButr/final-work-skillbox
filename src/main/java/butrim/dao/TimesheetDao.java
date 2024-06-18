package butrim.dao;

import butrim.entity.Employee;
import butrim.entity.Task;
import butrim.entity.Timesheet;
import org.hibernate.Session;
import org.hibernate.Transaction;
import butrim.utils.HibernateUtil;

import java.util.Collections;
import java.util.List;

public class TimesheetDao {
    public void saveTimesheet(Timesheet timesheet) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            transaction = session.beginTransaction();
            session.save(timesheet);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Timesheet removeTimesheet(Integer id) {

        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            Timesheet timesheet = session.get(Timesheet.class, id);

            if (timesheet != null) {
                session.delete(timesheet);
                transaction.commit();
            } else {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
            return timesheet;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public List<Timesheet> getTimesheet(Employee employee) {

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            return Collections.singletonList(session.get(Timesheet.class, employee.getId()));
        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
