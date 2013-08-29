package sogou.test.kpi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class InsertAdapter extends BaseAdapter{
	private String[] arrayList = null;
	private Context context;

	public InsertAdapter(Context context,String []list){
		this.context = context;
		this.arrayList=list;
	}

	public int getCount(){
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
			.inflate(R.layout.insert_item_view, null);
		ViewHolder mViewHolder = new ViewHolder();
		mViewHolder.tv = (TextView)localView.findViewById(R.id.tvItemName);
		mViewHolder.tv.setText(arrayList[paramInt]);
		localView.setBackgroundResource(R.drawable.circle_list_new_top);	
		return localView;
	}
}
