package sogou.test.kpi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class SquareItemAdapter extends BaseAdapter{
	private String[] arrayList = null;
	private Context context;
	//private TextView tvName;

	public SquareItemAdapter(Context context,String []list){
		this.context = context;
		this.arrayList=list;
	}

	public int getCount(){
		if(arrayList==null) return 0;
		return arrayList.length;
	}

	public Object getItem(int paramInt){
		return Integer.valueOf(paramInt);
	}

	public long getItemId(int paramInt){
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup){
		View localView = ((LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
			.inflate(R.layout.square_item_view, null);
		ViewHolder mViewHolder = new ViewHolder();
		mViewHolder.tv = (TextView)localView.findViewById(R.id.tvItemName);

		if(arrayList.length==1){
			String str1 = arrayList[0];
			mViewHolder.tv.setText(str1);
			localView.setBackgroundResource(R.drawable.circle_list_new_top);
		}
		else if (paramInt == 0){
			String str1 = arrayList[0];
			mViewHolder.tv.setText(str1);
			localView.setBackgroundResource(R.drawable.circle_list_top);
		}
		else if (paramInt> 0&paramInt<(arrayList.length-1)){
			String str2 = arrayList[paramInt];
			mViewHolder.tv.setText(str2);
			localView.setBackgroundResource(R.drawable.circle_list_middle);
		}			
		else if(paramInt == (arrayList.length-1)){
			String str3 = arrayList[paramInt];
			mViewHolder.tv.setText(str3);
			localView.setBackgroundResource(R.drawable.circle_list_bottom);	
		}
		return localView;
	}
}

class ViewHolder
{
	TextView tv;
	ViewHolder()
	{
	}
}
