package com.soi.rapidandroidapp.models.common;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

public interface IBaseModel<T>  {


	public Date getCreatedAt();

    /**
     * @return the updated date of this record
     */
    public Date getUpdated();

    /**
     * @param mId The primary key of the table
     * @return the record by the primary key
     */
    public T findOne(Long mId);

    /**
     * @return all the records for the type T
     */
    public List<? extends DBModel> findAll();

    /**
     * @param mId The primary key of the table
     * @return boolean if it's deleted successfully
     */
    public void deleteOne(Long mId);

    /**
     * @return boolean if table is deleted successfully
     */
    public void deleteAll();

    /**
     * Returns an instance of an Select ActiveRecord object
     * @return Select
     */
    public Select selectQuery();

    /**
     * Returns an instance of an Delete ActiveRecord object
     * @return Select
     */
    public Delete deleteQuery();
}