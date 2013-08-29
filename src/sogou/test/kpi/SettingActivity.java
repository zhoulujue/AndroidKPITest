package sogou.test.kpi;

import sogou.test.kpi.database.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends Activity implements OnClickListener,OnItemClickListener,Constants{
	
	private static final int GRID_BACK=0;
	private static final int GRID_EDIT=1;
	private static final int GRID_INPUT_OPTION=2;
	private static final int GRID_START=3;
	private static final int GRID_COLUMNS=4;
	private static final int[] GRID_ITEM_IMAGES ={R.drawable.back_32,R.drawable.plan_32,R.drawable.options_32,R.drawable.forward_32};    
    private static final String[] GRID_ITEM_NAMES={"返回","编辑","输入法","开始"};
	private Button title_btn;
	private TextView title_tv;
	private ImageView title_iv;
	
	private TextView etName,etScript,etInstaller,etPackage,etChangeInputMethod,etSetting;
	private GridView gridSetting;
	private InputMethodInfo inputMethodInfo;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.startposi_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.i_title);
        Bundle bundle=this.getIntent().getExtras();
        inputMethodInfo=new InputMethodInfo();
        if(bundle!=null){
        	
        	inputMethodInfo.setId(bundle.getInt(_ID));
        	inputMethodInfo.setName(bundle.getString(IMI_INPUT_METHOD_NAME));
        	inputMethodInfo.setInstaller(bundle.getString(IMI_INPUT_METHOD_INSTALLER));
        	inputMethodInfo.setPackageName(bundle.getString(IMI_INPUT_METHOD_PACKAGE));
        	inputMethodInfo.setScript(bundle.getString(IMI_KEYBOARD_CHANGE_SCRIPT));
        	inputMethodInfo.setChangeInputMethodScript(bundle.getString(IMI_CHANGE_INPUT_METHOD_SCRIPT));
        	inputMethodInfo.setSettingScript(bundle.getString(IMI_INPUT_METHOD_SETTING_SCRIPT));
        }
        findWidget();
        initWidget();
	}
	
	private void findWidget(){
		title_btn=(Button)this.findViewById(R.id.title_right_btn);
        title_tv=(TextView)this.findViewById(R.id.title);
        title_iv=(ImageView)this.findViewById(R.id.title_img);
        etName=(TextView)this.findViewById(R.id.tv_name);
        etScript=(TextView)this.findViewById(R.id.tv_script);
        etInstaller=(TextView)this.findViewById(R.id.tv_installer);
        etPackage=(TextView)this.findViewById(R.id.tv_package);
        etChangeInputMethod=(TextView)this.findViewById(R.id.tv_change_inputmethod);
        etSetting=(TextView)this.findViewById(R.id.tv_setting);
        if(inputMethodInfo!=null){
        	etName.setText("输入法      "+inputMethodInfo.getName());
        	etScript.setText("脚    本      "+inputMethodInfo.getScript());
        	etInstaller.setText("安装包      "+inputMethodInfo.getInstaller());
        	etPackage.setText("包    名      "+inputMethodInfo.getPackageName());
        	etChangeInputMethod.setText("切出脚本      "+inputMethodInfo.getChangeInputMethodScript());
        	etSetting.setText("设置脚本      "+inputMethodInfo.getSettingScript());
        }
        gridSetting=(GridView)this.findViewById(R.id.grid_setting);
	}
	
	private void initWidget(){
		title_btn.setText("返回");
		title_btn.setVisibility(View.GONE);
		title_tv.setText("测试信息");
		title_iv.setImageResource(R.drawable.settings_32);
        title_btn.setOnClickListener(this);
        ImageList adapter = new ImageList(this,GRID_ITEM_IMAGES,GRID_ITEM_NAMES);
        gridSetting.setNumColumns(GRID_COLUMNS);
		gridSetting.setAdapter(adapter);
		gridSetting.setOnItemClickListener(this);
	}
	
	public void onClick(View view) {
		if(view==title_btn){
			finish();
		}
	}
	
	public void saveInfo(InputMethodInfo info){
		InsertInputMethodInfo insertInfo=new InsertInputMethodInfo(getContentResolver());
		insertInfo.insertInfo(info);
	}
	
	@Override
	public void finish(){
		super.finish();
		overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		if(adapterView==gridSetting){
			switch(position){
			case GRID_BACK:
				finish();
				break;
			case GRID_EDIT:
				Intent i=new Intent(this,SaveActivity.class);
				Bundle b=new Bundle();
				b.putInt(_ID, inputMethodInfo.getId());
				b.putString(IMI_INPUT_METHOD_NAME, inputMethodInfo.getName());
				b.putString(IMI_INPUT_METHOD_INSTALLER, inputMethodInfo.getInstaller());
				b.putString(IMI_INPUT_METHOD_PACKAGE, inputMethodInfo.getPackageName());
				b.putString(IMI_KEYBOARD_CHANGE_SCRIPT, inputMethodInfo.getScript());
				b.putString(IMI_CHANGE_INPUT_METHOD_SCRIPT,inputMethodInfo.getChangeInputMethodScript());
				b.putString(IMI_INPUT_METHOD_SETTING_SCRIPT,inputMethodInfo.getSettingScript());
				i.putExtras(b);
				startActivityForResult(i,START_ACTIVITY_FOR_RESULT_SAVE);
				break;
			case GRID_INPUT_OPTION:
				showInputMethodPicker();
				break;
			case GRID_START:
				Intent intent=new Intent(this,TestActivity.class);
				Bundle bundle=new Bundle();
				bundle.putInt(_ID, inputMethodInfo.getId());
				bundle.putString(IMI_INPUT_METHOD_NAME, inputMethodInfo.getName());
				bundle.putString(IMI_KEYBOARD_CHANGE_SCRIPT, inputMethodInfo.getScript());
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			}
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case START_ACTIVITY_FOR_RESULT_SAVE:
			if(data==null) return;
			Bundle bundle=data.getExtras();
			if(bundle==null){
				finish();
				return;
			}
			inputMethodInfo.setName(bundle.getString(IMI_INPUT_METHOD_NAME));
			inputMethodInfo.setInstaller(bundle.getString(IMI_INPUT_METHOD_INSTALLER));
			inputMethodInfo.setPackageName(bundle.getString(IMI_INPUT_METHOD_PACKAGE));
			inputMethodInfo.setScript(bundle.getString(IMI_KEYBOARD_CHANGE_SCRIPT));
			inputMethodInfo.setChangeInputMethodScript(bundle.getString(IMI_CHANGE_INPUT_METHOD_SCRIPT));
        	inputMethodInfo.setSettingScript(bundle.getString(IMI_INPUT_METHOD_SETTING_SCRIPT));
        	etName.setText("输入法      "+inputMethodInfo.getName());
        	etScript.setText("脚    本      "+inputMethodInfo.getScript());
        	etInstaller.setText("安装包      "+inputMethodInfo.getInstaller());
        	etPackage.setText("包    名      "+inputMethodInfo.getPackageName());
        	etChangeInputMethod.setText("切出脚本      "+inputMethodInfo.getChangeInputMethodScript());
        	etSetting.setText("设置脚本      "+inputMethodInfo.getSettingScript());
        	break;
		case START_ACTIVITY_FOR_RESULT_DELETE:
			break;
		}
	}
	
	private void showInputMethodPicker() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .showInputMethodPicker();
    }
}
