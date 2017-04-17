package at.ac.fh.salzburg.smartmeter.data;

import at.ac.fh.salzburg.smartmeter.data.entities.CustomerEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerManager extends DaoBase {
    public void createCustomer(CustomerEntity customer){
        try (Session session = getSession()) {
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
        }
    }

    public void updateCustomer(CustomerEntity customer){
        try (Session session = getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(customer);
            session.getTransaction().commit();
        }
    }

    public CustomerEntity getCustomer(int id){
        try (Session session = getSession()) {
            final Query query = session.createQuery("from CustomerEntity where id = :id");
            query.setParameter("id", id);
            final List<CustomerEntity> list = (List<CustomerEntity>) query.list();
            return list.get(0);
        }
    }

    public void deleteCustomer(int id)
    {
        try (Session session = getSession()) {
            final Query query = session.createQuery("delete CustomerEntity where id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
        }
    }
}
