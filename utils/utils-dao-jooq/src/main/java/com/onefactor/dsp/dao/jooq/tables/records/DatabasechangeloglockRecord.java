/*
 * This file is generated by jOOQ.
*/
package com.onefactor.dsp.dao.jooq.tables.records;


import com.onefactor.dsp.dao.jooq.tables.Databasechangeloglock;

import java.time.LocalDateTime;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DatabasechangeloglockRecord extends UpdatableRecordImpl<DatabasechangeloglockRecord> implements Record4<Integer, Boolean, LocalDateTime, String> {

    private static final long serialVersionUID = -956707396;

    /**
     * Setter for <code>public.databasechangeloglock.id</code>.
     */
    public DatabasechangeloglockRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.databasechangeloglock.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.databasechangeloglock.locked</code>.
     */
    public DatabasechangeloglockRecord setLocked(Boolean value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.databasechangeloglock.locked</code>.
     */
    public Boolean getLocked() {
        return (Boolean) get(1);
    }

    /**
     * Setter for <code>public.databasechangeloglock.lockgranted</code>.
     */
    public DatabasechangeloglockRecord setLockgranted(LocalDateTime value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.databasechangeloglock.lockgranted</code>.
     */
    public LocalDateTime getLockgranted() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.databasechangeloglock.lockedby</code>.
     */
    public DatabasechangeloglockRecord setLockedby(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.databasechangeloglock.lockedby</code>.
     */
    public String getLockedby() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Boolean, LocalDateTime, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Boolean, LocalDateTime, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Databasechangeloglock.DATABASECHANGELOGLOCK.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field2() {
        return Databasechangeloglock.DATABASECHANGELOGLOCK.LOCKED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field3() {
        return Databasechangeloglock.DATABASECHANGELOGLOCK.LOCKGRANTED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Databasechangeloglock.DATABASECHANGELOGLOCK.LOCKEDBY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value2() {
        return getLocked();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value3() {
        return getLockgranted();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getLockedby();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabasechangeloglockRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabasechangeloglockRecord value2(Boolean value) {
        setLocked(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabasechangeloglockRecord value3(LocalDateTime value) {
        setLockgranted(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabasechangeloglockRecord value4(String value) {
        setLockedby(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabasechangeloglockRecord values(Integer value1, Boolean value2, LocalDateTime value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DatabasechangeloglockRecord
     */
    public DatabasechangeloglockRecord() {
        super(Databasechangeloglock.DATABASECHANGELOGLOCK);
    }

    /**
     * Create a detached, initialised DatabasechangeloglockRecord
     */
    public DatabasechangeloglockRecord(Integer id, Boolean locked, LocalDateTime lockgranted, String lockedby) {
        super(Databasechangeloglock.DATABASECHANGELOGLOCK);

        set(0, id);
        set(1, locked);
        set(2, lockgranted);
        set(3, lockedby);
    }
}