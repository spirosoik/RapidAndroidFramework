package com.soi.rapidandroidapp.models.common;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is Abstract Model for the app's database.
 * Supports the ActiveRecord for android
 * @param <T> It's the type of the current Object you want to be used as a Database Model
 */
public class DBModel<T> extends Model implements IBaseModel<DBModel>,Comparable<T>, Serializable {

    private static final DBModel instance = new DBModel();

    private Map<Class,Object> singletonHolder = new HashMap<Class,Object>();

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updated_at = new Date();

    /**
     * Singletton pattern for each model which extends this DBModel class
     * @param classOf
     * @param <T>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> T getInstance(Class<T> classOf) throws InstantiationException, IllegalAccessException
    {
        synchronized(instance){
            if(!instance.singletonHolder.containsKey(classOf)){
                T obj = classOf.newInstance();
                instance.singletonHolder.put(classOf, obj);
            }
            return (T)instance.singletonHolder.get(classOf);
        }
    }

    @Override
    public Date getCreatedAt() {
        return new Date();
    }

    @Override
    public Date getUpdated() {
        return this.updated_at;
    }

    @Override
    public DBModel findOne(Long mId) {
        return load(getClass(), mId);
    }

    @Override
    public List<? extends DBModel> findAll() {
        return selectQuery().from(getClass()).execute();
    }

    @Override
    public void deleteOne(Long mId) {
        deleteQuery().from(getClass()).where("Id = ?", mId).execute();
    }

    @Override
    public void deleteAll() {
        deleteQuery().from(getClass()).execute();
    }

    @Override
    public int compareTo(T another) {
        return this.compareTo(another);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Select selectQuery() {
        return new Select();
    }

    @Override
    public Delete deleteQuery() {
        return new Delete();
    }

    public T clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}