package com.example.contentproviderserverday10

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri

class ServerContentProvider : ContentProvider() {
    companion object {
        const val AUTHORITY = "com.dhp.server.ServerContentProvider"
        const val DATABASE_NAME = "order_tb"
        const val DATABASE_VERSION = 1
        const val ORDER_TABLE = "orders"
        const val ID_COl = "id"
        const val CUSTOMER_NAME_COL = "customername"
        const val TIMESTAMP = "timestamp"
        const val VALUE = "value"
        const val UriCode = 1
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$ORDER_TABLE")
    }

    private lateinit var dbHelper: DatabaseHelper
    private var matcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        matcher.addURI(AUTHORITY, ORDER_TABLE, UriCode)
        matcher.addURI(AUTHORITY, "$ORDER_TABLE/", 2)
    }

    //create SQLiteOpenHelper
    class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            val createDB = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT)",
                ORDER_TABLE,
                ID_COl,
                CUSTOMER_NAME_COL,
                TIMESTAMP,
                VALUE
            )
            db.execSQL(createDB)
        }

        override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
            db.execSQL("DROP TABLE IF EXISTS $ORDER_TABLE")
            onCreate(db)
        }
    }

    //delete
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db = dbHelper.writableDatabase
        val deleteCount = db.delete(ORDER_TABLE, selection, selectionArgs)
        context?.contentResolver?.notifyChange(uri, null)
        return deleteCount
    }

    //
    override fun getType(uri: Uri): String? {
        when (matcher.match(uri)) {
            UriCode -> return "$AUTHORITY/$ORDER_TABLE"
            else -> throw IllegalArgumentException("Unsupported URI:  + uri")
        }
    }

    //insert
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper.writableDatabase
        db.insert(ORDER_TABLE, "", values)
        context?.contentResolver?.notifyChange(uri, null)
        return uri

    }

    //create
    override fun onCreate(): Boolean {
        dbHelper = DatabaseHelper(context!!)
        return true
    }

    //query
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val db = dbHelper.writableDatabase
        val cursor = db.query(ORDER_TABLE, projection, selection, selectionArgs, "", "", sortOrder)
        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    //update
    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val db = dbHelper.writableDatabase
        val updateCount = db.update(ORDER_TABLE, values, selection, selectionArgs)
        context?.contentResolver?.notifyChange(uri, null)
        return updateCount
    }
}