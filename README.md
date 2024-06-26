# Дипломный проект БД для программистов

1. Создайте файл .env в корне проекта
2. Юзера и пароль для базы данных нужно будет указать также в .env файле (MYSQL_USER, MYSQL_PASSWORD)
3. Собрать контейнеры командой docker-compose up -d --build
4. Создать базу данных и перенести данные docker exec -i mysql mysql -uuser -ppassword <schema.sql
5. Подключиться к базе данных docker exec -i mysql mysql -uuser -ppassword;

Данные для импорта в csv должны лежать в data


Реализован следующий функционал: 
1. import positions.csv — добавляет в БД указанные в файле positions.csv должности и ставки оплаты труда;
2. import employees.csv — добавляет в БД указанные в файле employees.csv должности и ставки оплаты труда;
3. import timesheet.csv — добавляет в БД указанные в файле timesheet.csv периоды работы сотрудников над задачами;
4. get [employeeName] — выводит таймшиты сотрудника по его имени;
5. remove [employeeName] — удаляет данные по сотруднику из таймшита по его имени;
6. report Top5longTasks — выводит пять задач, на которые потрачено больше всего времени;
7. report Top5costTasks — выводит пять задач, на которые потрачено больше всего денег;
8. report Top5employees — выводит пять сотрудников, отработавших наибольшее количество времени за всё время.

# Diploma project DB for programmers
1. Create a .env file in the project root.
2. Specify the database user and password in the .env file as well (MYSQL_USER, MYSQL_PASSWORD).
3. Build containers using the command docker-compose up -d --build.
4. Create the database and import data: docker exec -i mysql mysql -uuser -ppassword <schema.sql.
5. Connect to the database: docker exec -i mysql mysql -uuser -ppassword.
6. Data for CSV import should be located in the 'data' directory.

The following functionality is implemented:

1. import positions.csv — adds positions and wage rates specified in the positions.csv file to the database.
2. import employees.csv — adds positions and wage rates specified in the employees.csv file to the database.
3. import timesheet.csv — adds employee work periods on tasks specified in the timesheet.csv file to the database.
4. get [employeeName] — outputs timesheets of an employee by their name.
5. remove [employeeName] — deletes data of an employee from the timesheet by their name.
6. report Top5longTasks — outputs the top five tasks that took the most time.
7. report Top5costTasks — outputs the top five tasks that incurred the highest costs.
8. report Top5employees — outputs the top five employees who worked the most hours of all time.