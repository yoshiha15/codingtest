/*
 * This file is generated by jOOQ.
 */
package com.example.codingtest.domain.tables.records;


import com.example.codingtest.domain.tables.Author;

import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class AuthorRecord extends TableRecordImpl<AuthorRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.author.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.author.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.author.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.author.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.author.birthday</code>.
     */
    public void setBirthday(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.author.birthday</code>.
     */
    public String getBirthday() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.author.updated</code>.
     */
    public void setUpdated(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.author.updated</code>.
     */
    public String getUpdated() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.author.created</code>.
     */
    public void setCreated(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.author.created</code>.
     */
    public String getCreated() {
        return (String) get(4);
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AuthorRecord
     */
    public AuthorRecord() {
        super(Author.AUTHOR);
    }

    /**
     * Create a detached, initialised AuthorRecord
     */
    public AuthorRecord(Integer id, String name, String birthday, String updated, String created) {
        super(Author.AUTHOR);

        setId(id);
        setName(name);
        setBirthday(birthday);
        setUpdated(updated);
        setCreated(created);
        resetChangedOnNotNull();
    }
}