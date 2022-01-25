package com.vpr.server.dao.implementation;

import com.vpr.server.dao.interfaces.UserDAO;
import com.vpr.server.data.Event;
import com.vpr.server.data.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDAOImplementation implements UserDAO {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<User> getAllUser() {
        return manager.createNamedQuery("getAllUser", User.class).getResultList();
    }
}
