package com.wlg.Util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by wlg on 2016/6/14.
 */
@Component
public class HiSession {
    /**
     * 注入一个sessionFactory属性,并注入到父类(HibernateDaoSupport)
     * **/
    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    private Session session;
    public Session getSession() {
        session = sessionFactory.getCurrentSession();
        return session;
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }


}
