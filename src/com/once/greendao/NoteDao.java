package com.once.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.once.greendao.Note;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table NOTE.
*/
public class NoteDao extends AbstractDao<Note, Long> {

    public static final String TABLENAME = "NOTE";

    /**
     * Properties of entity Note.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Notecontent = new Property(2, String.class, "notecontent", false, "NOTECONTENT");
        public final static Property NotecreateDate = new Property(3, java.util.Date.class, "notecreateDate", false, "NOTECREATE_DATE");
        public final static Property NoteupdateDate = new Property(4, java.util.Date.class, "noteupdateDate", false, "NOTEUPDATE_DATE");
        public final static Property Notepictrue = new Property(5, String.class, "notepictrue", false, "NOTEPICTRUE");
    };

    private DaoSession daoSession;


    public NoteDao(DaoConfig config) {
        super(config);
    }
    
    public NoteDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'NOTE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'TITLE' TEXT," + // 1: title
                "'NOTECONTENT' TEXT," + // 2: notecontent
                "'NOTECREATE_DATE' INTEGER," + // 3: notecreateDate
                "'NOTEUPDATE_DATE' INTEGER," + // 4: noteupdateDate
                "'NOTEPICTRUE' TEXT);"); // 5: notepictrue
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'NOTE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Note entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String notecontent = entity.getNotecontent();
        if (notecontent != null) {
            stmt.bindString(3, notecontent);
        }
 
        java.util.Date notecreateDate = entity.getNotecreateDate();
        if (notecreateDate != null) {
            stmt.bindLong(4, notecreateDate.getTime());
        }
 
        java.util.Date noteupdateDate = entity.getNoteupdateDate();
        if (noteupdateDate != null) {
            stmt.bindLong(5, noteupdateDate.getTime());
        }
 
        String notepictrue = entity.getNotepictrue();
        if (notepictrue != null) {
            stmt.bindString(6, notepictrue);
        }
    }

    @Override
    protected void attachEntity(Note entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Note readEntity(Cursor cursor, int offset) {
        Note entity = new Note( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // notecontent
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)), // notecreateDate
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // noteupdateDate
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // notepictrue
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Note entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNotecontent(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNotecreateDate(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
        entity.setNoteupdateDate(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setNotepictrue(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Note entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Note entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
