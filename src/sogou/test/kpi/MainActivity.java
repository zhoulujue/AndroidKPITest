package sogou.test.kpi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sogou.test.agent.framework.root.CommonFunction;
import sogou.test.kpi.database.Constants;
import sogou.test.kpi.devices.RunScript;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener,Constants,OnClickListener{
	
	private ListView inputList,insertList;
	private static final String[] insertArray = {"添加新的输入法"};
	private Button title_btn;
	private TextView title_tv;
	private ImageView title_iv;
	private List<InputMethodInfo> inputMethodList; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.i_title);
        
        findWidget();
        initWidget();
        //RunScript runScript=RunScript.getInstance(this);
        //runScript.startRunScript();
        try{
        	Runtime.getRuntime().exec("su");
        }
        catch(IOException e){}
    }
    
    public void findWidget(){
    	title_btn=(Button)this.findViewById(R.id.title_right_btn);
        title_tv=(TextView)this.findViewById(R.id.title);
        title_iv=(ImageView)this.findViewById(R.id.title_img);
    	inputList=(ListView)this.findViewById(R.id.inputList);
    	insertList=(ListView)this.findViewById(R.id.insertList);
    }
    
    public void initWidget(){  	
    	//title_btn.setVisibility(View.GONE);
    	title_btn.setText("开始");
    	title_btn.setOnClickListener(this);
		title_tv.setText("输入法Kpi测试工具");
		title_iv.setImageResource(R.drawable.plan_32);
    	inputList.setAdapter(new SquareItemAdapter(this,getInputMethodList()));
    	inputList.setDivider(null);         
    	inputList.setOnItemClickListener(this);
    	insertList.setAdapter(new InsertAdapter(this,insertArray));
    	insertList.setDivider(null);         
    	insertList.setOnItemClickListener(this);
    }
    
    private String[] getInputMethodList(){
    	Cursor cursor=this.getContentResolver().query(CONTENT_URI_TB_IMI, null, null, null, null);
    	if(cursor==null) return null;
    	String result[]=new String[cursor.getCount()];
    	if(inputMethodList==null)
    		inputMethodList=new ArrayList<InputMethodInfo>();
    	inputMethodList.clear();

    	int index=0;
    	while(cursor.moveToNext()){
    		InputMethodInfo info=new InputMethodInfo();
    		info.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
    		info.setName(cursor.getString(cursor.getColumnIndex(IMI_INPUT_METHOD_NAME)));
    		info.setInstaller(cursor.getString(cursor.getColumnIndex(IMI_INPUT_METHOD_INSTALLER)));
    		info.setPackageName(cursor.getString(cursor.getColumnIndex(IMI_INPUT_METHOD_PACKAGE)));
    		info.setScript(cursor.getString(cursor.getColumnIndex(IMI_KEYBOARD_CHANGE_SCRIPT)));
    		info.setChangeInputMethodScript(cursor.getString(cursor.getColumnIndex(IMI_CHANGE_INPUT_METHOD_SCRIPT)));
    		info.setSettingScript(cursor.getString(cursor.getColumnIndex(IMI_INPUT_METHOD_SETTING_SCRIPT)));
    		inputMethodList.add(info);
    		result[index]=info.getName();
    		index++;
    	}
    	cursor.close();
    	return result;
    }

	@Override
	public void onItemClick(AdapterView<?> viewAdapter, View view, int position, long id) {
		if(viewAdapter==insertList){
			Intent intent=new Intent(this,SaveActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_left);
		}
		else if(viewAdapter==inputList){
			Intent intent=new Intent(this,SettingActivity.class);
			Bundle bundle=new Bundle();
			InputMethodInfo info=inputMethodList.get(position);
			System.out.println("position="+position);
			bundle.putInt(_ID, info.getId());
			bundle.putString(IMI_INPUT_METHOD_NAME,info.getName());
			bundle.putString(IMI_KEYBOARD_CHANGE_SCRIPT,info.getScript());
			bundle.putString(IMI_INPUT_METHOD_INSTALLER,info.getInstaller());
			bundle.putString(IMI_INPUT_METHOD_PACKAGE,info.getPackageName());
			bundle.putString(IMI_CHANGE_INPUT_METHOD_SCRIPT,info.getChangeInputMethodScript());
			bundle.putString(IMI_INPUT_METHOD_SETTING_SCRIPT,info.getSettingScript());
			intent.putExtras(bundle);
			startActivity(intent);
			overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_left);
		}
	}
	
	@Override
	public void onRestart(){
		super.onRestart();
		inputList.setAdapter(new SquareItemAdapter(this,getInputMethodList()));
    	inputList.setDivider(null); 
	}

	@Override
	public void onClick(View view) {
		if(view==title_btn){
			KPITestApplication.getInstance().setTestNumber(0);
			new BeforeTest(this).start();
		}
	}
}
