package Main;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class Main {
    public static void main(String[] args) {
        User u1 = new User();
        u1.setId(2);
        u1.setName("Quynh Anh");
        u1.setSalary(25000);

        Configuration config = new Configuration();
        config.addAnnotatedClass(User.class);
        config.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(u1);
        transaction.commit();

        session.close();
        sessionFactory.close();
    }
}
