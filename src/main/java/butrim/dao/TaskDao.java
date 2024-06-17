package butrim.dao;

import butrim.entity.Position;
import butrim.entity.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import butrim.utils.HibernateUtil;
import org.hibernate.query.Query;

import java.util.List;

public class TaskDao {
    public void saveTask(Task task) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            transaction = session.beginTransaction();
            session.save(task);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Task> getTasks() {

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            return session.createQuery("from Task", Task.class).list();
        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Task findByName(String title) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Task> query = session.createQuery("FROM Task WHERE title = :title", Task.class);
            query.setParameter("title", title);
            return query.uniqueResult();
        }
    }

    public Task findByTitle(String title) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Task> query = session.createQuery("FROM Task WHERE title = :title", Task.class);
            query.setParameter("title", title);
            return query.uniqueResult();
        }
    }

    public Task getTask(String title) {

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            return session.get(Task.class, title);
        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
