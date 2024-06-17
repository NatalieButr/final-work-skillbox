package butrim.Report;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import butrim.utils.HibernateUtil;

import java.util.List;

public class Top5longTasks {
    public static void report() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String sql = "SELECT ta.title, SUM(TIMESTAMPDIFF(HOUR, ti.date_start, ti.date_end)) AS spent_hours " +
                    "FROM timesheet ti " +
                    "JOIN tasks ta ON ta.id = ti.task_id " +
                    "GROUP BY ta.title " +
                    "ORDER BY spent_hours DESC " +
                    "LIMIT 5";

            NativeQuery<Object[]> query = session.createNativeQuery(sql);
            List<Object[]> resultList = query.list();


            if (!resultList.isEmpty()) {
                System.out.println("+-------------+---------------+");
                System.out.println("| spent_hours | title         |");
                System.out.println("+-------------+---------------+");

                for (Object[] row : resultList) {
                    System.out.printf("| %-13s | %-13s |\n", row[0], row[1]);
                }

                System.out.println("+-------------+---------------+");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
