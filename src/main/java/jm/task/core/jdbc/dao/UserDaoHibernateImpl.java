package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    private Transaction transaction = null;
    private static String option;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        option = "create table UsersTable(" +
                "id bigint auto_increment primary key," +
                "name varchar(30)," +
                "lastName varchar(30)," +
                "age tinyint)";

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(option).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("A table with the same name already exists");
            try {
                transaction.rollback();
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        option = "drop table UsersTable";
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(option).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("The table you are trying to delete doesn't exist");
            try {
                transaction.rollback();
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        option = "insert into UsersTable(name, lastName, age) values('" + name + "', '" + lastName + "', " + age + ")";
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(option).executeUpdate();
            System.out.println("User " + name + " has been added to the database");
            transaction.commit();
        } catch (Exception e) {
            System.out.println("The table you are trying to delete doesn't exist");
            try {
                transaction.rollback();
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        option = "delete from UsersTable where id = " + id;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(option).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                transaction.rollback();
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        option = "select * from UsersTable";
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            list = session.createSQLQuery(option).addEntity(User.class).list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                transaction.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        option = "truncate UsersTable";
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(option).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                transaction.rollback();
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }
    }
}
