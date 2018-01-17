package com.sxmoc.bq.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySql extends SQLiteOpenHelper {

	public MySql(Context context) {
        super(context, "MyData.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql="create table baogao(id integer,filepath varchar)";
//		String sql1="create table lovemusic(id integer primary key autoincrement,musicName varchar(20),singer varchar(20),imageUrl varchar(50),musicUrl varchar(50),duration integer)";
		db.execSQL(sql);
//		db.execSQL(sql1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}