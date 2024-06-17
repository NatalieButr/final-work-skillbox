package butrim;

import butrim.dao.PositionDao;
import butrim.dao.TaskDao;
import butrim.dao.TimesheetDao;
import butrim.entity.Position;
import butrim.dao.EmployeeDao;
import butrim.entity.Employee;
import butrim.entity.Task;
import butrim.entity.Timesheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ImportData {
    private static final String DATA_PATH = "data";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public static void importPositions(String filename) throws FileNotFoundException {
        PositionDao positionDao = new PositionDao();
        Scanner scanner = new Scanner(new File(DATA_PATH + File.separatorChar + filename));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Scanner s = new Scanner(line).useDelimiter(",");
            String name = s.next();
            Integer rate = s.nextInt();
            Position position = new Position();
            position.setName(name);
            position.setRate(rate);

            positionDao.savePosition(position);

            System.out.println(line);
        }
        scanner.close();
    }

    public static void importEmployees(String filename) throws FileNotFoundException {
        EmployeeDao employeeDao = new EmployeeDao();
        Scanner scanner = new Scanner(new File(DATA_PATH + File.separatorChar + filename));
        PositionDao positionDao = new PositionDao();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Scanner s = new Scanner(line).useDelimiter(",");
            String name = s.next();
            String positionName = s.next();

            Position position = positionDao.findByName(positionName);
            if (position == null) {
                System.err.println("Position not found: " + positionName);
                continue;
            }

            Employee employee = new Employee();
            employee.setName(name);
            employee.setPosition_id(position.getPosition_id());
            employeeDao.saveEmployee(employee);

            System.out.println(line);
        }

        scanner.close();
    }

    public static void importTasks(String filename) throws FileNotFoundException, ParseException {
        TaskDao taskDao = new TaskDao();
        Scanner scanner = new Scanner(new File(DATA_PATH + File.separatorChar + filename));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Scanner s = new Scanner(line).useDelimiter(",");
            String title = s.next();

            if (taskDao.findByTitle(title) == null) {
                Task task = new Task();
                task.setTitle(title);
                taskDao.saveTask(task);
                System.out.println("Saved task: " + line);
            } else {
                System.out.println("Task with title '" + title + "' already exists. Skipping: " + line);
            }

        }

        scanner.close();
    }

    public static void importTimesheet(String filename) throws FileNotFoundException, ParseException {

        Scanner scanner = new Scanner(new File(DATA_PATH + File.separatorChar + filename));
        TimesheetDao timesheetDao = new TimesheetDao();
        EmployeeDao employeeDao = new EmployeeDao();
        TaskDao taskDao = new TaskDao();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Scanner s = new Scanner(line).useDelimiter(",");
            String name = s.next();
            String employeeName = s.next();
            Date startTime = formatter.parse(s.next());
            Date endTime = formatter.parse(s.next());

            Task task  = taskDao.findByName(name);

            if (task == null) {
                System.err.println("Task not found: " + name);
                continue;
            }

            Employee employee  = employeeDao.findByName(employeeName);

            if (employee == null) {
                System.err.println("Employee not found: " + employeeName);
                continue;
            }


            Timesheet timesheet = new Timesheet();
            timesheet.setName(name);
            timesheet.setDate_start(startTime);
            timesheet.setDate_end(endTime);
            timesheet.setEmployee_id(employee.getId());
            timesheet.setTask_id(task.getId());

            timesheetDao.saveTimesheet(timesheet);

            System.out.println(line);
        }

        scanner.close();
    }


}
