package sogou.test.kpi;

import java.io.File;

import sogou.test.agent.framework.command.RunScriptContext;
import sogou.test.kpi.database.Constants;
import sogou.test.kpi.devices.SdCard;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SaveActivity extends Activity implements OnClickListener,Constants{

	public static final String CHANGE_KEYBOARD_PATH="/KPITestDir/ChangeKeyboard/";
	public static final String INSTALLER_PATH="/KPITestDir/Installer/";
	public static final String SETTING_PATH="/KPITestDir/Setting/";
	private Button titleBtn,saveBtn,cancleBtn;
	private TextView titleText;
	private ImageView titleImage;
	
	private EditText nameEdit,packageEdit;
	private AutoCompleteTextView scriptEdit,installerEdit,changeEdit,settingEdit;
	private InputMethodInfo inputMethodInfo;
	private boolean hasEdited=false;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.setting_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.i_title);
        
        findWidget();
        initWidget();
        
        Bundle bundle=this.getIntent().getExtras();
        inputMethodInfo=new InputMethodInfo();
        if(bundle!=null){
        	inputMethodInfo.setId(bundle.getInt(_ID));
        	inputMethodInfo.setName(bundle.getString(IMI_INPUT_METHOD_NAME));
        	inputMethodInfo.setScript(bundle.getString(IMI_KEYBOARD_CHANGE_SCRIPT));
        	inputMethodInfo.setInstaller(bundle.getString(IMI_INPUT_METHOD_INSTALLER));
        	inputMethodInfo.setPackageName(bundle.getString(IMI_INPUT_METHOD_PACKAGE));
        	inputMethodInfo.setChangeInputMethodScript(bundle.getString(IMI_CHANGE_INPUT_METHOD_SCRIPT));
        	inputMethodInfo.setSettingScript(bundle.getString(IMI_INPUT_METHOD_SETTING_SCRIPT));
        	titleText.setText("编辑"+inputMethodInfo.getName());
        	nameEdit.setText(inputMethodInfo.getName());
        	scriptEdit.setText(inputMethodInfo.getScript());
        	installerEdit.setText(inputMethodInfo.getInstaller());
        	packageEdit.setText(inputMethodInfo.getPackageName());
        	changeEdit.setText(inputMethodInfo.getChangeInputMethodScript());
        	settingEdit.setText(inputMethodInfo.getSettingScript());
        	cancleBtn.setText("删除");
        	hasEdited=true;
        }
	}
	
	private void findWidget(){
		titleBtn=(Button)this.findViewById(R.id.title_right_btn);
		titleText=(TextView)this.findViewById(R.id.title);
		titleImage=(ImageView)this.findViewById(R.id.title_img);
		nameEdit=(EditText)this.findViewById(R.id.et_contact);
		scriptEdit=(AutoCompleteTextView)this.findViewById(R.id.et_message);
        saveBtn=(Button)this.findViewById(R.id.btn_save);
        cancleBtn=(Button)this.findViewById(R.id.btn_cancle);
        
        packageEdit=(EditText)this.findViewById(R.id.et_package);
        installerEdit=(AutoCompleteTextView)this.findViewById(R.id.et_installer);
        
        changeEdit=(AutoCompleteTextView)this.findViewById(R.id.et_changeInputMethod);
        settingEdit=(AutoCompleteTextView)this.findViewById(R.id.et_setting);
	}
	
	private void initWidget(){
		titleBtn.setText("返回");
		titleBtn.setVisibility(View.GONE);
		titleText.setText("新建项目");
		titleImage.setImageResource(R.drawable.settings_32);
		titleBtn.setOnClickListener(this);
        
		saveBtn.setOnClickListener(this);
		cancleBtn.setOnClickListener(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        saveBtn.setWidth(dm.widthPixels/2-5);
        cancleBtn.setWidth(dm.widthPixels/2-5);
        
        String []array=getScripts(CHANGE_KEYBOARD_PATH);
        if(array!=null){
        	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, array);
        	scriptEdit.setAdapter(adapter);
        }
        
        array=getScripts(INSTALLER_PATH);
        if(array!=null){
        	ArrayAdapter<String> adapterInstaller = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, array);
        	installerEdit.setAdapter(adapterInstaller);
        }
        
        array=getScripts(SETTING_PATH);
        if(array!=null){
        	ArrayAdapter<String> adapterSetting = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, array);
        	settingEdit.setAdapter(adapterSetting);
        }
        
        array=getScripts(RunScriptContext.CHANGE_INPUT_METHOD);
        if(array!=null){
        	ArrayAdapter<String> adapterChange = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, array);
        	changeEdit.setAdapter(adapterChange);
        }
	}
	
	private String[] getScripts(String pt){
		File file=SdCard.getSDPath();
		if(file==null) return null;
		String path=file.toString()+pt;
		File filePath=new File(path);
		Log.i("kpi", "file="+filePath.getAbsolutePath());
		if(!filePath.exists()) return null;
		Log.i("kpi", "length="+filePath.list().length);
		return filePath.list();
	}
	
	public void onClick(View view) {
		if(view==titleBtn){
			finish();
		}
		else if(view==saveBtn){
			String name=nameEdit.getText().toString().trim();
			if(stringIsNull(name)){
				Toast.makeText(this, "输入法名称不能为空", Toast.LENGTH_SHORT).show();
				return;
			}
			String script=scriptEdit.getText().toString().trim();
			if(stringIsNull(script)){
				Toast.makeText(this, "键盘切换脚本不能为空", Toast.LENGTH_SHORT).show();
				return;
			}
			inputMethodInfo.setName(name);
			inputMethodInfo.setScript(script);
			inputMethodInfo.setInstaller(installerEdit.getText().toString().trim());
			inputMethodInfo.setPackageName(packageEdit.getText().toString().trim());
			inputMethodInfo.setChangeInputMethodScript(changeEdit.getText().toString().trim());
			inputMethodInfo.setSettingScript(settingEdit.getText().toString().trim());
			saveInfo(inputMethodInfo);
			Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
			finish();
		}
		else if(view==cancleBtn){
			if(hasEdited){
				new AlertDialog.Builder(this)
                	.setIcon(R.drawable.minus_32)
                	.setTitle("您确认删除该记录吗？")
                	.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                		public void onClick(DialogInterface dialog, int whichButton) {
                			InsertInputMethodInfo insertInfo=new InsertInputMethodInfo(getContentResolver());
                			insertInfo.deleteInfo(inputMethodInfo);
                			Intent intent=new Intent();
                			setResult(START_ACTIVITY_FOR_RESULT_SAVE,intent);
                			finish();
                		}
                	})
                	.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                		public void onClick(DialogInterface dialog, int whichButton) {

                		}
                	})
                	.create().show();
			}
			else{
				finish();
			}
		}
	}
	
	
	public void saveInfo(InputMethodInfo info){
		InsertInputMethodInfo insertInfo=new InsertInputMethodInfo(getContentResolver());
		if(hasEdited){
			insertInfo.updateInfo(info);
			Intent intent=new Intent();
			Bundle bundle=new Bundle();
			bundle.putString(IMI_INPUT_METHOD_NAME, info.getName());
			bundle.putString(IMI_INPUT_METHOD_INSTALLER, info.getInstaller());
			bundle.putString(IMI_INPUT_METHOD_PACKAGE, info.getPackageName());
			bundle.putString(IMI_KEYBOARD_CHANGE_SCRIPT, info.getScript());
			bundle.putString(IMI_CHANGE_INPUT_METHOD_SCRIPT,inputMethodInfo.getChangeInputMethodScript());
			bundle.putString(IMI_INPUT_METHOD_SETTING_SCRIPT,inputMethodInfo.getSettingScript());
			intent.putExtras(bundle);
			setResult(START_ACTIVITY_FOR_RESULT_SAVE,intent);
		}
		else{
			insertInfo.insertInfo(info);
		}
		
	}
	
	@Override
	public void finish(){
		super.finish();
		overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right);
	}
	
	private boolean stringIsNull(String string){
		if(string==null||string.length()<1)
			return true;
		return false;
	}
}
