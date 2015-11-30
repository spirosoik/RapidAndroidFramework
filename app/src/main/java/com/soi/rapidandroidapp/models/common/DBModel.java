package com.soi.rapidandroidapp.models.common;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ollie.Model;
import ollie.annotation.Column;
import ollie.query.Delete;
import ollie.query.Select;

/**
 * This is Abstract Model for the app's database.
 * Supports the ActiveRecord for android
 *
 * @param <T> It's the type of the current Object you want to be used as a Database Model
 */
public class DBModel<T> extends Model implements IBaseModel<DBModel>, Comparable<T>, Serializable {

    private static final DBModel instance = new DBModel();

    private Map<Class, Object> singletonHolder = new HashMap<Class, Object>();

    /**
     * Singletton pattern for each model which extends this DBModel class
     *
     * @param classOf
     * @param <T>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> T getInstance(Class<T> classOf) throws InstantiationException, IllegalAccessException {
        synchronized (instance) {
            if (!instance.singletonHolder.containsKey(classOf)) {
                T obj = classOf.newInstance();
                instance.singletonHolder.put(classOf, obj);
            }
            return (T) instance.singletonHolder.get(classOf);
        }
    }

    @Override
    public DBModel findOne(Long mId) {
        return find(getClass(), mId);
    }

    @Override
    public List<? extends DBModel> findAll() {
        return Select.from(getClass()).fetch();
    }

    @Override
    public void deleteOne(Long mId) {
        Delete.from(getClass()).where("_id = ?", mId).execute();
    }

    @Override
    public void deleteAll() {
        Delete.from(getClass()).execute();
    }

    @Override
    public int compareTo(T another) {
        return this.compareTo(another);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}