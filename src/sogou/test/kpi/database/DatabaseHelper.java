package sogou.test.kpi.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper implements Constants{
	
	private static final String DATABASE_NAME = "sogoukpitest.db";
	private static final int VERSION = 1;//数据库版本号

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}
		
	@Override
	public void onCreate(SQLiteDatabase db) {
		Cursor cur=db.query("SQLITE_MASTER", null, null, null, null, null, null);
		int n = cur.getCount();
		if(n==1){
			db.execSQL("CREATE TABLE " + TB_INPUT_METHOD_INFO + " (" + 
					_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+ 
					IMI_INPUT_METHOD_NAME + " TEXT, " +
					IMI_INPUT_METHOD_INSTALLER + " TEXT, " +
					IMI_INPUT_METHOD_PACKAGE + " TEXT, " +
					IMI_CHANGE_INPUT_METHOD_SCRIPT+ " TEXT, " +
					IMI_INPUT_METHOD_SETTING_SCRIPT + " TEXT, " +
					IMI_KEYBOARD_CHANGE_SCRIPT + " TEXT "+");");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+ TB_INPUT_METHOD_INFO);
		onCreate(db);
	}

}
