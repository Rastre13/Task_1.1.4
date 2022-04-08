package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    String option;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        option = "create table UsersTable(" +
                "id bigint auto_increment primary key," +
                "name varchar(30)," +
                "lastName varchar(30)," +
                "age tinyint)";
        try {
            Util.getStatement().executeUpdate(option);
        } catch (SQLException e) {
            System.out.println("A table with the same name already exists");
        }
    }

    public void dropUsersTable() {
        option = "drop table UsersTable";
        try {
            Util.getStatement().executeUpdate(option);
        } catch (SQLException e) {
            System.out.println("The table you are trying to delete doesn't exist");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        option = "insert into UsersTable(name, lastName, age) values('" + name + "', '" + lastName + "', " + age + ")";
        try {
            Util.getStatement().executeUpdate(option);
            System.out.println("User " + name + " has been added to the database");
        } catch (SQLException e) {
            System.out.println("The table you are trying to delete doesn't exist");
        }
    }

    public void removeUserById(long id) {
        option = "delete from UsersTable where id = " + id;
        try {
            Util.getStatement().executeUpdate(option);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        option = "select * from UsersTable";
        try {
            ResultSet resultSet = Util.getStatement().executeQuery(option);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                System.out.println(user);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        option = "truncate UsersTable";
        try {
            Util.getStatement().executeUpdate(option);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
