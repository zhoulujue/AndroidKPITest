package sogou.test.kpi.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyDataProvider extends ContentProvider implements Constants{
	
	private DatabaseHelper helper;

	@Override
	public boolean onCreate() {
		helper = new DatabaseHelper(this.getContext());
		return true;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		String tableName = uri.toString().split("/")[3];
		helper.getWritableDatabase().insert(tableName, "", values);
		return null;
	}
	
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String tableName = uri.toString().split("/")[3];
		helper.getWritableDatabase().delete(tableName, selection, selectionArgs);
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		String tableName = uri.toString().split("/")[3];
		helper.getWritableDatabase().update(tableName, values, selection, selectionArgs);
		return 0;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		String tableName = uri.toString().split("/")[3];
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
		return cursor;
	}

	
	@Override
	public String getType(Uri uri) {
		return null;
	}

}
