package br.com.mirabilis.sqlite_menuhead.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import br.com.mirabilis.sqlite_menuhead.master_barang.BarangModel
import br.com.mirabilis.sqlite_menuhead.db.DBContract.KategoriEntry.Companion.TABLE_KATEGORI
import br.com.mirabilis.sqlite_menuhead.db.DBContract.UserEntry.Companion.TABLE_NAME
import br.com.mirabilis.sqlite_menuhead.master_kategori.mKategoriModel

import java.util.ArrayList

class UsersDBHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
        db.execSQL(SQL_CREATE_KATEGORIS)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        db.execSQL(SQL_DELETE_KATEGORIS)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }



    fun cariKategori(id: Int): String {
        val mod = ArrayList<mKategoriModel>()
        var hasilpencarian = ""
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select kategori from " + DBContract.KategoriEntry.TABLE_KATEGORI + " WHERE " + DBContract.KategoriEntry.COLUMN_ID_KATEGORI + "='" + id + "'", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
                //return ArrayList()
        }

        var kategori: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {

                kategori = cursor.getString(cursor.getColumnIndex(DBContract.KategoriEntry.COLUMN_KATEGORI))

                hasilpencarian = mKategoriModel(
                    id,
                    kategori
                ).toString()
                cursor.moveToNext()
            }
        }
        return hasilpencarian
    }


    fun updateKategori(id: Int, kategori:String): Int {
        val db = this.writableDatabase

        // Creating content values
        val values = ContentValues()
        values.put(DBContract.KategoriEntry.COLUMN_ID_KATEGORI, id)
        values.put(DBContract.KategoriEntry.COLUMN_KATEGORI, kategori)

        // update row in students table base on students.is value
        return db.update(
            TABLE_KATEGORI, values, "${DBContract.KategoriEntry.COLUMN_ID_KATEGORI} = ?",
            arrayOf(id.toString())
        )
    }



    fun updateBarang(id: Int, id_kategori:String, qrid: String, barang: String, harga_jual: String,
                     promo:String,harga_grosir:String,harga_grosir_promo:String,harga_beli:String,
                     stok:String,satuan:String,tax:String,nonstok:String,catatan:String): Int {
        val db = this.writableDatabase

        // Creating content values
        val values = ContentValues()
        values.put(DBContract.UserEntry.COLUMN_IDKATEGORI, id_kategori)
        values.put(DBContract.UserEntry.COLUMN_QRID, qrid)
        values.put(DBContract.UserEntry.COLUMN_BARANG, barang)
        values.put(DBContract.UserEntry.COLUMN_HARGA_JUAL, harga_jual)
        values.put(DBContract.UserEntry.COLUMN_PROMO, promo)
        values.put(DBContract.UserEntry.COLUMN_HARGA_GROSIR, harga_grosir)
        values.put(DBContract.UserEntry.COLUMN_HARGA_GROSIR_PROMO, harga_grosir_promo)
        values.put(DBContract.UserEntry.COLUMN_HARGA_BELI, harga_beli)
        values.put(DBContract.UserEntry.COLUMN_STOK, stok)
        values.put(DBContract.UserEntry.COLUMN_SATUAN, satuan)
        values.put(DBContract.UserEntry.COLUMN_TAX, tax)
        values.put(DBContract.UserEntry.COLUMN_NONSTOK, nonstok)
        values.put(DBContract.UserEntry.COLUMN_CATATAN, catatan)

        // update row in students table base on students.is value
        return db.update(
            TABLE_NAME, values, "${DBContract.UserEntry.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }


    @Throws(SQLiteConstraintException::class)
    fun insertUser(id_kategori:String, qrid: String, barang: String, harga_jual: String,
                   promo:String,harga_grosir:String,harga_grosir_promo:String,harga_beli:String,
                   stok:String,satuan:String,tax:String,nonstok:String,catatan:String): Boolean {

        val db = writableDatabase
        val values = ContentValues()

        values.put(DBContract.UserEntry.COLUMN_IDKATEGORI, id_kategori)
        values.put(DBContract.UserEntry.COLUMN_QRID, qrid)
        values.put(DBContract.UserEntry.COLUMN_BARANG, barang)
        values.put(DBContract.UserEntry.COLUMN_HARGA_JUAL, harga_jual)
        values.put(DBContract.UserEntry.COLUMN_PROMO, promo)
        values.put(DBContract.UserEntry.COLUMN_HARGA_GROSIR, harga_grosir)
        values.put(DBContract.UserEntry.COLUMN_HARGA_GROSIR_PROMO, harga_grosir_promo)
        values.put(DBContract.UserEntry.COLUMN_HARGA_BELI, harga_beli)
        values.put(DBContract.UserEntry.COLUMN_STOK, stok)
        values.put(DBContract.UserEntry.COLUMN_SATUAN, satuan)
        values.put(DBContract.UserEntry.COLUMN_TAX, tax)
        values.put(DBContract.UserEntry.COLUMN_NONSTOK, nonstok)
        values.put(DBContract.UserEntry.COLUMN_CATATAN, catatan)

        val newRowId = db.insert(DBContract.UserEntry.TABLE_NAME, null, values)

        return true
    }


    @Throws(SQLiteConstraintException::class)
    fun insertKategori(kategori: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.KategoriEntry.COLUMN_KATEGORI, kategori)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.KategoriEntry.TABLE_KATEGORI, null, values)

        return true
    }


    //@Throws(SQLiteConstraintException::class)
    fun deleteKategori(id: String): Boolean {
        val db = writableDatabase

        val selection = DBContract.KategoriEntry.COLUMN_ID_KATEGORI + " LIKE ?"
        val selectionArgs = arrayOf(id)

        db.delete(DBContract.KategoriEntry.TABLE_KATEGORI, selection, selectionArgs)

        return true
    }


    //@Throws(SQLiteConstraintException::class)
    fun deleteBarang(id: String): Boolean {
        val db = writableDatabase

        val selection = DBContract.UserEntry.COLUMN_ID + " LIKE ?"
        val selectionArgs = arrayOf(id)

        db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }


    fun readAllUsers(): ArrayList<BarangModel> {

        val users = ArrayList<BarangModel>()
        val db = writableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var id: Int
        var id_kategori: String
        var qrid: String
        var barang: String
        var harga_jual: String
        var promo: String
        var harga_grosir: String
        var harga_grosir_promo: String
        var harga_beli: String
        var stok: String
        var satuan: String
        var tax: String
        var nonstok: String
        var catatan: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {

                id = cursor.getInt(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_ID))
                id_kategori = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_IDKATEGORI))
                qrid = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_QRID))
                barang = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_BARANG))
                harga_jual = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_HARGA_JUAL))
                promo = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_PROMO))
                harga_grosir = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_HARGA_GROSIR))
                harga_grosir_promo = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_HARGA_GROSIR_PROMO))
                harga_beli = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_HARGA_BELI))
                stok = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_STOK))
                satuan = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_SATUAN))
                tax = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_TAX))
                nonstok = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NONSTOK))
                catatan = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_CATATAN))


                users.add(
                    BarangModel(
                        id,
                        id_kategori,
                        qrid,
                        barang,
                        harga_jual,
                        promo, harga_grosir, harga_grosir_promo, harga_beli, stok, satuan, tax, nonstok, catatan
                    )
                )
                cursor.moveToNext()
            }
        }
        return users
    }


    fun readAllKategoris(): ArrayList<mKategoriModel> {

        val kategori_model = ArrayList<mKategoriModel>()
        val db = writableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("select * from " + DBContract.KategoriEntry.TABLE_KATEGORI, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var id: Int
        var kategori: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {

                id = cursor.getInt(cursor.getColumnIndex(DBContract.KategoriEntry.COLUMN_ID_KATEGORI))
                kategori = cursor.getString(cursor.getColumnIndex(DBContract.KategoriEntry.COLUMN_KATEGORI))

                kategori_model.add(
                    mKategoriModel(
                        id,
                        kategori
                    )
                )
                cursor.moveToNext()
            }
        }
        return kategori_model
    }


    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FeedReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                    DBContract.UserEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBContract.UserEntry.COLUMN_IDKATEGORI + " TEXT," +
                    DBContract.UserEntry.COLUMN_QRID + " TEXT," +
                    DBContract.UserEntry.COLUMN_BARANG + " TEXT," +
                    DBContract.UserEntry.COLUMN_HARGA_JUAL + " TEXT," +

                    DBContract.UserEntry.COLUMN_PROMO + " TEXT," +
                    DBContract.UserEntry.COLUMN_HARGA_GROSIR + " TEXT," +
                    DBContract.UserEntry.COLUMN_HARGA_GROSIR_PROMO + " TEXT," +
                    DBContract.UserEntry.COLUMN_HARGA_BELI + " TEXT," +
                    DBContract.UserEntry.COLUMN_STOK + " TEXT," +
                    DBContract.UserEntry.COLUMN_SATUAN + " TEXT," +
                    DBContract.UserEntry.COLUMN_TAX + " TEXT," +
                    DBContract.UserEntry.COLUMN_NONSTOK + " TEXT," +

                    DBContract.UserEntry.COLUMN_CATATAN + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME


        private val SQL_CREATE_KATEGORIS =
            "CREATE TABLE " + DBContract.KategoriEntry.TABLE_KATEGORI + " (" +
                    DBContract.KategoriEntry.COLUMN_ID_KATEGORI + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBContract.KategoriEntry.COLUMN_KATEGORI + " TEXT)"

        private val SQL_DELETE_KATEGORIS = "DROP TABLE IF EXISTS " + DBContract.KategoriEntry.TABLE_KATEGORI

    }

}