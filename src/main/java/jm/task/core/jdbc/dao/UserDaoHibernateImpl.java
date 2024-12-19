package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

import static jm.task.core.jdbc.util.HibernateUtil.getSessionFactory;


public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction tx = null;

        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS users (Id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    " Name VARCHAR(20), LastName VARCHAR(20), Age TINYINT)";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tx = null;

        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction tx = null;

        try ( Session session = getSessionFactory().openSession() ) {
            tx = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tx.commit();
        }catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;

        try ( Session session = getSessionFactory().openSession() ) {
            tx = session.beginTransaction();
            session.delete(session.get(User.class, id));
            tx.commit();
        }catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
       try (Session session = getSessionFactory().openSession()) {
           return session.createQuery("from User", User.class).list();
       }
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;

        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createSQLQuery("DELETE FROM users").executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
