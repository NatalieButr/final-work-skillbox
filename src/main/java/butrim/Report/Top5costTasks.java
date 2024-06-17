package butrim.Report;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import butrim.utils.HibernateUtil;
import java.util.List;


public class Top5costTasks {
    public static void report() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String sql = "WITH raiting as (SELECT ti.task_id, ta.title as title_task, " +
                    "ROUND(COUNT(TIMESTAMPDIFF(HOUR, ti.date_start, ti.date_end)) * AVG(p.rate)) as total_cost " +
                    "FROM timesheet ti " +
                    "JOIN tasks ta on ta.id = ti.task_id " +
                    "JOIN employees e on e.id = ti.id " +
                    "JOIN positions p on p.position_id = e.position_id " +
                    "GROUP BY ti.task_id " +
                    "ORDER BY total_cost DESC " +
                    "LIMIT 5) " +
                    "SELECT row_number() over() as position, title_task, total_cost from raiting";

            NativeQuery<Object[]> query = session.createNativeQuery(sql);
            List<Object[]> resultList = query.list();

            if (!resultList.isEmpty()) {
                // Print table header
                System.out.println(String.format("%-10s %-30s %-10s", "Position", "Title Task", "Total Cost"));
                System.out.println("------------------------------------------------------------");

                // Print table rows
                for (Object[] row : resultList) {
                    System.out.println(String.format("%-10s %-30s %-10s", row[0], row[1], row[2]));
                }
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
