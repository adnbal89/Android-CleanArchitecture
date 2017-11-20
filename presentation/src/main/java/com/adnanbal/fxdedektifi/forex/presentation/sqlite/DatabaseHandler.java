/*
 * *
 *  * Copyright (C) 2017 Adnan BAL Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.adnanbal.fxdedektifi.forex.presentation.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.adnanbal.fxdedektifi.forex.presentation.AndroidApplication;
import com.adnanbal.fxdedektifi.forex.presentation.model.UserSignalDB;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

  // All Static variables
  // Database Version
  private static final int DATABASE_VERSION = 2;

  // Database Name
  private static final String DATABASE_NAME = "userSignalsManager_";

  // UserSignalDBs table name
  private static final String TABLE_USER_SIGNALS = "userSignals";

  // UserSignalDBs Table Columns names
  // Contacts Table Columns names
  private static final String KEY_ID = "id";
  private static final String KEY_UID = "uid";
  private static final String USER_UID = "user_uid";


  public DatabaseHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  // Creating Tables
  @Override
  public void onCreate(SQLiteDatabase db) {
    String CREATE_USERSIGNALS_TABLE = "CREATE TABLE " + TABLE_USER_SIGNALS + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_UID + " TEXT," + USER_UID + " TEXT" + ")";
    db.execSQL(CREATE_USERSIGNALS_TABLE);
  }

  // Upgrading database
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Drop older table if existed
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_SIGNALS);

    // Create tables again
    onCreate(db);
  }

  /**
   * All CRUD(Create, Read, Update, Delete) Operations
   */

  // Adding new userSignal
  public void addUserSignalDB(UserSignalDB userSignal) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(KEY_UID, userSignal.getId()); // userSignal db id
    values.put(KEY_UID, userSignal.getUid()); // userSignal db uid
    values.put(USER_UID, AndroidApplication.userUid); // user uid

    // Inserting Row
    db.insert(TABLE_USER_SIGNALS, null, values);
    db.close(); // Closing database connection
  }


  // Getting single userSignalDB
  public UserSignalDB getUserSignalDB(int id) {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.query(TABLE_USER_SIGNALS, new String[]{KEY_ID}, KEY_ID + "=?",
        new String[]{String.valueOf(id)}, null, null, null, null);
    if (cursor != null) {
      cursor.moveToFirst();
    }

    UserSignalDB userSignalDB = new UserSignalDB(cursor.getInt(0), cursor.getString(1));
    // return userSignalDB
    return userSignalDB;
  }

  public UserSignalDB getUserSignalDBbyUser_Uid(String user_uid) {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.query(TABLE_USER_SIGNALS, new String[]{USER_UID}, USER_UID + "=?",
        new String[]{user_uid}, null, null, null, null);
    if (cursor != null) {
      cursor.moveToFirst();
    }

    UserSignalDB userSignalDB = new UserSignalDB(cursor.getInt(0), cursor.getString(1));
    // return userSignalDB
    return userSignalDB;
  }

  public boolean Exists(String searchItem) {
    SQLiteDatabase db = this.getWritableDatabase();

    String[] columns = {KEY_UID};
    String selection = KEY_UID + " =?";
    String[] selectionArgs = {searchItem};
    String limit = "1";

    Cursor cursor = db
        .query(TABLE_USER_SIGNALS, columns, selection, selectionArgs, null, null, null, limit);
    boolean exists = (cursor.getCount() > 0);
    cursor.close();
    return exists;
  }

  // Getting All UserSignalDBs
  public List<UserSignalDB> getAllUserSignalDBs() {
    List<UserSignalDB> userSignalDBList = new ArrayList<UserSignalDB>();
    // Select All Query
    String selectQuery = "SELECT  * FROM " + TABLE_USER_SIGNALS;

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
      do {
        UserSignalDB userSignalDB = new UserSignalDB();
        userSignalDB.setId(cursor.getInt(0));
        userSignalDB.setUid(cursor.getString(1));

        // Adding userSignalDB to list
        userSignalDBList.add(userSignalDB);
      } while (cursor.moveToNext());
    }

    // return userSignalDB list
    return userSignalDBList;
  }

  // Getting All UserSignalDBs
  public List<UserSignalDB> getAllUserSignalDBsByUserUid() {
    List<UserSignalDB> userSignalDBList = new ArrayList<UserSignalDB>();
    // Select All Query
    String selectQuery = "SELECT  * FROM " + TABLE_USER_SIGNALS;

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
      do {
        UserSignalDB userSignalDB = new UserSignalDB();
        userSignalDB.setId(cursor.getInt(0));
        userSignalDB.setUid(cursor.getString(1));

        // Adding userSignalDB to list
        userSignalDBList.add(userSignalDB);
      } while (cursor.moveToNext());
    }

    // return userSignalDB list
    return userSignalDBList;
  }

  // Updating single userSignalDB
  public int updateUserSignalDB(UserSignalDB userSignalDB) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(KEY_ID, userSignalDB.getId());

    // updating row
    return db.update(TABLE_USER_SIGNALS, values, KEY_ID + " = ?",
        new String[]{String.valueOf(userSignalDB.getId())});
  }

  // Deleting single userSignalDB
  public void deleteUserSignalDB(UserSignalDB userSignalDB) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABLE_USER_SIGNALS, KEY_UID + " = ?",
        new String[]{String.valueOf(userSignalDB.getUid())});
    db.close();
  }

  // Deleting single userSignalDB
  public void deleteAllUserSignalDB() {
    SQLiteDatabase db = this.getWritableDatabase();

    db.delete(TABLE_USER_SIGNALS, null, null);

    db.close();
  }


  // Getting userSignalDBs Count
  public int getUserSignalDBsCount() {
    String countQuery = "SELECT  * FROM " + TABLE_USER_SIGNALS;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(countQuery, null);
    cursor.close();

    // return count
    return cursor.getCount();
  }


}
