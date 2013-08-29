package sogou.test.kpi;

import sogou.test.kpi.database.Constants;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

public class InsertInputMethodInfo extends InsertInfo implements Constants{


	private ContentResolver myContentResolver;
	public InsertInputMethodInfo(ContentResolver resolver){
		this.myContentResolver=resolver;
	}
	
	
	
	@Override
	public void insertInfo(Object object) {
		InputMethodInfo inputMethodInfo=(InputMethodInfo)object;
		ContentValues value = new ContentValues();
		value.put(IMI_INPUT_METHOD_NAME, inputMethodInfo.getName());
		value.put(IMI_INPUT_METHOD_INSTALLER, inputMethodInfo.getInstaller());
		value.put(IMI_INPUT_METHOD_PACKAGE, inputMethodInfo.getPackageName());
		value.put(IMI_KEYBOARD_CHANGE_SCRIPT, inputMethodInfo.getScript());
		value.put(IMI_CHANGE_INPUT_METHOD_SCRIPT, inputMethodInfo.getChangeInputMethodScript());
		value.put(IMI_INPUT_METHOD_SETTING_SCRIPT, inputMethodInfo.getSettingScript());
		this.myContentResolver.insert(CONTENT_URI_TB_IMI, value);
	}
	
	public void updateInfo(Object object){
		InputMethodInfo inputMethodInfo=(InputMethodInfo)object;
		ContentValues value = new ContentValues();
		value.put(IMI_INPUT_METHOD_NAME, inputMethodInfo.getName());
		value.put(IMI_INPUT_METHOD_INSTALLER, inputMethodInfo.getInstaller());
		value.put(IMI_INPUT_METHOD_PACKAGE, inputMethodInfo.getPackageName());
		value.put(IMI_KEYBOARD_CHANGE_SCRIPT, inputMethodInfo.getScript());
		value.put(IMI_CHANGE_INPUT_METHOD_SCRIPT, inputMethodInfo.getChangeInputMethodScript());
		value.put(IMI_INPUT_METHOD_SETTING_SCRIPT, inputMethodInfo.getSettingScript());
		this.myContentResolver.update(CONTENT_URI_TB_IMI, value, 
				_ID+"="+inputMethodInfo.getId(), null);
	}
	
	public void deleteInfo(Object object){
		InputMethodInfo inputMethodInfo=(InputMethodInfo)object;
		this.myContentResolver.delete(CONTENT_URI_TB_IMI, _ID+"="+inputMethodInfo.getId(), null);
	}
	
	public InputMethodInfo getInputMethodInfo(int position){
		if(position<0) return null;
		
		Cursor cursor=this.myContentResolver.query(CONTENT_URI_TB_IMI, null, null, null, null);
    	if(cursor==null) return null;
    	if(position>=cursor.getCount()) return null;

    	int index=0;
    	InputMethodInfo info=null;
    	while(cursor.moveToNext()){
    		if(index==position){
    			info=new InputMethodInfo();
    			info.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
    			info.setName(cursor.getString(cursor.getColumnIndex(IMI_INPUT_METHOD_NAME)));
    			info.setScript(cursor.getString(cursor.getColumnIndex(IMI_KEYBOARD_CHANGE_SCRIPT)));
    			info.setInstaller(cursor.getString(cursor.getColumnIndex(IMI_INPUT_METHOD_INSTALLER)));
    			info.setPackageName(cursor.getString(cursor.getColumnIndex(IMI_INPUT_METHOD_PACKAGE)));
    			info.setChangeInputMethodScript(cursor.getString(cursor.getColumnIndex(IMI_CHANGE_INPUT_METHOD_SCRIPT)));
    			info.setSettingScript(cursor.getString(cursor.getColumnIndex(IMI_INPUT_METHOD_SETTING_SCRIPT)));
    			break;
    		}
    		index++;
    	}
    	cursor.close();
    	return info;
	}

}
