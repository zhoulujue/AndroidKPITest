package sogou.test.kpi;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * @author chen
 *该类用于自定义GridView中每项的视图
 */
public class ImageList extends BaseAdapter {   
    Activity activity;   
    private int[] image;    
    private String[] name;
    public ImageList(Activity a,int i[],String n[] ) {   
        activity = a;   
        this.image=i;
        this.name=n;
    }   
       
    @Override  
    public int getCount() {   
        // TODO Auto-generated method stub   
        return image.length;   
    }   

    @Override  
    public Object getItem(int position) {   
        // TODO Auto-generated method stub   
        return image[position];   
    }   

    @Override  
    public long getItemId(int position) {   
        // TODO Auto-generated method stub   
        return position;   
    }   

    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {   
        // TODO Auto-generated method stub   
    	LinearLayout layout = new LinearLayout(activity);   
        layout.setOrientation(LinearLayout.VERTICAL);   
        layout.setGravity(Gravity.CENTER);  
        ImageView iv = new ImageView(activity);   
        
        iv.setImageResource(image[position]);  
        iv.setPadding(0, 5, 0, 0);
        
        layout.addView(iv,new LinearLayout.LayoutParams(48,48));   
        
        TextView tv = new TextView(activity); 
        tv.setWidth(110);
        tv.setGravity(Gravity.CENTER);   
        tv.setMaxLines(2);
        tv.setText(name[position]);
        tv.setPadding(0, 0, 0, 0);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(16);
        layout.addView(tv,new LinearLayout.LayoutParams(
        		LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));   
        return layout;   
    } 
    
    
}