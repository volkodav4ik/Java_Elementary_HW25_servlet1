package com.volkodav4ik.dao;

import com.google.gson.Gson;
import com.volkodav4ik.model.Picture;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;
import java.util.List;

public class PictureDao {

    private static final Gson gson = new Gson();
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static Picture addPicture(Picture picture) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(picture);
            transaction.commit();
            session.close();
            return picture;
        }
    }

    public static List getIdList() {
        try (Session session = sessionFactory.openSession()) {
            List list = session.createQuery("SELECT id FROM Picture ORDER BY id").list();
            session.close();
            return list;
        }
    }

    public static String getBase64ById(int id) {
        try (Session session = sessionFactory.openSession()) {
            String base64 = session.createQuery("SELECT base64 FROM Picture WHERE id = :id")
                    .setParameter("id", id)
                    .getSingleResult()
                    .toString();
            return base64;
        } catch (NoResultException e){
            return null;
        }
    }

    public static Picture pictureFromJson(String json) {
        return gson.fromJson(json, Picture.class);
    }
}
