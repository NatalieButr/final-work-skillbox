package butrim;

import butrim.entity.Task;
import butrim.entity.Timesheet;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import butrim.utils.HibernateUtil;

import butrim.Report.Top5costTasks;
import butrim.Report.Top5employees;
import butrim.Report.Top5longTasks;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, ParseException {
        if (args.length < 2) {
            System.out.println("Provide parameters: [action] [object]");
            System.exit(1);
        }
        switch (args[0]) {
            case "import":
                System.out.println("Importing file " + args[1]);
                switch (args[1]) {
                    case "positions.csv":
                        ImportData.importPositions(args[1]);
                        break;
                    case "employees.csv":
                        ImportData.importEmployees(args[1]);
                        break;

                    case "timesheet.csv":
                        ImportData.importTasks(args[1]);
                        ImportData.importTimesheet(args[1]);
                        break;
                }
                break;
            case "get":
                System.out.println("Timesheet for employee " + args[1]);
                printTimesheet(args[1]);
                break;
            case "remove":
                System.out.println("Removing timesheet with id " + args[1]);
                removeTimesheet(Integer.valueOf(args[1]));
                break;
            case "report":
                System.out.println("Report " + args[1]);
                switch (args[1]) {
                    case "top5longTasks":
                        Top5longTasks.report();
                        break;
                    case "top5costTasks":
                        Top5costTasks.report();
                        break;
                    case "top5employees":
                        Top5employees.report();
                        break;
                }
                break;
        }
    }

    public static void printTimesheet(String employeeName) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String sql = "SELECT ti.id, ti.employee_id, ti.task_id, ti.date_start, ti.date_end " +
                    "FROM Timesheet ti " +
                    "JOIN Employee e ON ti.employee_id = e.id " +
                    "WHERE e.name = :employeeName";
            Query<Object[]> query = session.createQuery(sql, Object[].class);
            query.setParameter("employeeName", employeeName);
            List<Object[]> resultList = query.list();

            if (!resultList.isEmpty()) {
                System.out.println("+------+-------------+---------+---------------------+---------------------+");
                System.out.println("| id   | employee_id | task_id | start_time          | end_time            |");
                System.out.println("+------+-------------+---------+---------------------+---------------------+");
                for (Object[] row : resultList) {
                    System.out.printf("| %-4d | %-11d | %-7d | %-19s | %-19s |\n", row[0], row[1], row[2], row[3], row[4]);
                }
                System.out.println("+------+-------------+---------+---------------------+---------------------+");
            } else {
                System.out.println("No timesheets found for employee: " + employeeName);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void removeTimesheet(Integer timesheetId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Timesheet timesheet = session.get(Timesheet.class, timesheetId);
            if (timesheet != null) {
                session.delete(timesheet);

                String sql = "SELECT COUNT(*) FROM Timesheet ti WHERE ti.task_id = :taskId";
                Query<Long> query = session.createQuery(sql, Long.class);
                query.setParameter("taskId", timesheet.getTask().getId());
                Long count = (Long) ((org.hibernate.query.Query<?>) query).uniqueResult();


                if (count == 0) {
                    Task task = session.get(Task.class, timesheet.getTask().getId());
                    session.delete(task);
                }

                transaction.commit();
                System.out.println("Timesheet removed successfully");
            } else {
                System.out.println("Timesheet not found");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
