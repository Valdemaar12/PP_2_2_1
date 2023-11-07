package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;


@Repository
@Transactional
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.save(user);
        } catch (Exception e) {
            System.err.println("Ошибка! - " + e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<User> query = session.createQuery("from User");
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Ошибка! - " + e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserFromCar(String model, int series) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<User> query = session.
                    createQuery("SELECT u FROM User u WHERE u.car.model=:model and u.car.series=:series", User.class);
            query.setParameter("model", model).setParameter("series", series);
            return query.getSingleResult();
        } catch (Exception e) {
            System.err.println("Ошибка! - " + e);
            throw e;
        }
    }

}
