package butrim.Report;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import butrim.utils.HibernateUtil;

import java.util.List;

public class Top5employees {
    public static void report() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String sql = "SELECT e.name, SUM(TIMESTAMPDIFF(HOUR, ti.date_start, ti.date_end)) AS total_hours " +
                    "FROM timesheet ti " +
                    "JOIN employees e ON e.id = ti.id " +
                    "GROUP BY e.name " +
                    "ORDER BY total_hours DESC " +
                    "LIMIT 5";

            NativeQuery<Object[]> query = session.createNativeQuery(sql);
            List<Object[]> resultList = query.list();

            if (!resultList.isEmpty()) {
                // Print table header
                System.out.println("+-------------+--------+");
                System.out.println("| total_hours | name   |");
                System.out.println("+-------------+--------+");

                // Print table rows
                for (Object[] row : resultList) {
                    System.out.printf("| %-11s | %-6s |\n", row[1], row[0]);
                }

                System.out.println("+-------------+--------+");
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
