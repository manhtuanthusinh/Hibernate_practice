package Testing;

import Util.HibernateUtil;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;

public class TestUser {
    public static void main(String[] args) {
        /* Test Read file csv and
        write data to mysql
        using Hibernate ORM
        * */

        String csvFile = "/home/manhtuanthusinh/Documents/java_learning/Hibernate_practice/Hibernate_01/src/main/java/user.csv";
        String line;
        String csvSplitBy = ",";

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // BufferedReader ensures efficient reading of large files
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            // skip header line
            br.readLine();
            // to control batch size
            int count = 0;

            // Split the line into columns
            while ((line = br.readLine()) != null) {

                String[] data = line.split(csvSplitBy);

                // Create a new User entity
                User user = new User();

                // Assign CSV values to entity fields
                // trim() removes unwanted spaces from CSV values
                user.setName(data[0].trim());
                user.setSalary(Double.parseDouble(data[1]));
                user.setEmail(data[2].trim());

                // Persist entity (stored in Hibernate session cache first)
                session.persist(user);
                count++;

                // check the cache and clean after 20 records
                if (count % 20 == 0) {
                    session.flush();   // push data to DB
                    session.clear();   // clears Hibernate cache to avoid memory issues
                }
            }
            tx.commit();
            System.out.println("Insert CSV successfully!");

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

