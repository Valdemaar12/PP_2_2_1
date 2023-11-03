package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

//todo начинаем привыкать к hotKeys: например горячими клавишами убираем - лишние импорты

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    //todo повтор по коду - .getCurrentSession(). А можем? реализовать где-то и заинжектить сразу Session
    // todo - в обязательном порядке - используем try_with_resources. это рабочий паттерн.

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserFromCar(String model, int series) {
        TypedQuery<User> query = sessionFactory.getCurrentSession().
                createQuery("SELECT u FROM User u WHERE u.car.model=:model and u.car.series=:series", User.class);
        query.setParameter("model", model).setParameter("series", series);
        return query.getSingleResult();
    }

}
