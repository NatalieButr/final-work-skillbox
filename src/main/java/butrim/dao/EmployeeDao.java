package butrim.dao;
import butrim.entity.Employee;
import butrim.utils.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.List;

public class EmployeeDao {
    public void saveEmployee(Employee employee) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            transaction = session.beginTransaction();
            session.save(employee);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Employee> getEmployees() {

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            return session.createQuery("from Employee", Employee.class).list();
        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employee findByName(String name) {
        System.out.println(name);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery("FROM Employee WHERE name = :name", Employee.class);
            query.setParameter("name", name);
            return query.uniqueResult();
        }
    }


}