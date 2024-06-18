package butrim.dao;

import butrim.entity.Position;

import java.util.List;
import butrim.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class PositionDao {
    public void savePosition(Position position) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(position);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Position findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Position> query = session.createQuery("FROM Position WHERE name = :name", Position.class);
            query.setParameter("name", name);
            return query.uniqueResult();
        }
    }
}