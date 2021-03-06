package com.jdd.powermanager.ui.mainpage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.jdd.common.utils.barcode.BarCodeHelper;
import com.jdd.common.utils.gps.GpsHelper;
import com.jdd.powermanager.R;
import com.jdd.powermanager.action.AbsCallback;
import com.jdd.powermanager.action.combing.CombingActions;
import com.jdd.powermanager.action.elimination.EliminationActions;
import com.jdd.powermanager.action.survey.SurveyActions;
import com.jdd.powermanager.basic.BaseActivity;
import com.jdd.powermanager.ui.generalsurvey.SurveyActivity;
import com.jdd.powermanager.ui.widgt.FullScreenWaitBar;
import com.jdd.powermanager.ui.abnormalelimination.EliminationActivity;
import com.jdd.powermanager.ui.boxcombing.*;

public class MainPageActivity extends BaseActivity 
{
	private TextView title;
	
	private GridView mGridview;
	
	private AlertDialog mDialog;
	
	private OnItemClickListener mOnClickLis = new OnItemClickListener()
	{
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) 
		{
			switch(arg2)
			{
				case 0:
					
					getAllDistrict(0,R.string.general_survey,SurveyActivity.class);
					
					break;
				case 1:
					
					getAllDistrict(1,R.string.area_comb,CombingActivity.class);
					
					break;
				case 2:
					
					getAllDistrict(2,R.string.exception_deal,EliminationActivity.class);
					
					break;
				default:
					break;
			}
		}
	};
	
	private void getAllDistrict(final int index,final int resid, final Class<?> classz)
	{
		FullScreenWaitBar.show(this, R.layout.full_screen_wait_bar);
		
		if( 0 == index )
		{
			SurveyActions.getAllDistrict(new AbsCallback() 
			{
				@Override
				public void onResult(Object o) 
				{
					showDistrictSelectDialog(index,resid,classz,o);
				}
			});
		}
		else if( 1 == index )
		{
			CombingActions.getAllDistrict(new AbsCallback() 
			{
				@Override
				public void onResult(Object o) 
				{
					showDistrictSelectDialog(index,resid,classz,o);
				}
			});
		}
		else
		{
			EliminationActions.getAllDistrict(new AbsCallback() 
			{
				@Override
				public void onResult(Object o) 
				{
					showDistrictSelectDialog(index,resid,classz,o);
				}
			});
		}
	}
	
	private void showDistrictSelectDialog(int index,final int resid, final Class<?> classz,Object o)
	{
		FullScreenWaitBar.hide();
		
		String title = getString(R.string.please_select_district);
		
		title = String.format(title, getString(resid));
		
		if( 1 == index )
		{
			DistrictsAdapter2.onItemClickListener lis = new DistrictsAdapter2.onItemClickListener() 
			{
				public void onClick(String id,String logo) 
				{
					if( null != mDialog )
					{
						mDialog.dismiss();
					}
					
					loadActivity(classz, id ,logo);
				}
			};
			
			DistrictsAdapter2 da = new DistrictsAdapter2(MainPageActivity.this,lis);
			
			@SuppressWarnings("unchecked")
			List<CombingActions.DistrictInfo> list = null == o ? null : (List<CombingActions.DistrictInfo>) o;
			
			if( null == list || list.size() <= 0 )
			{
				String msg = getString(R.string.no_districts);
				
				msg = String.format(msg, getString(resid));
				
				Toast.makeText(MainPageActivity.this,msg , Toast.LENGTH_LONG).show();
				
				return;
			}
			
			da.setList(list);
			
			final Builder b = new  AlertDialog.Builder(MainPageActivity.this).setCancelable(true).
					setTitle(title).setSingleChoiceItems(da, -1, null);
			
			mDialog = b.show();
		}
		else
		{
			DistrictsAdapter.onItemClickListener lis = new DistrictsAdapter.onItemClickListener() 
			{
				public void onClick(String id) 
				{
					if( null != mDialog )
					{
						mDialog.dismiss();
					}
					
					loadActivity(classz, id);
				}
			};
			
			DistrictsAdapter da = new DistrictsAdapter(MainPageActivity.this,lis);
			
			@SuppressWarnings("unchecked")
			List<SurveyActions.DistrictInfo> list = null == o ? null : (List<SurveyActions.DistrictInfo>) o;
			
			if( null == list || list.size() <= 0 )
			{
				String msg = getString(R.string.no_districts);
				
				msg = String.format(msg, getString(resid));
				
				Toast.makeText(MainPageActivity.this,msg , Toast.LENGTH_LONG).show();
				
				return;
			}
			
			da.setList(list);
			
			final Builder b = new  AlertDialog.Builder(MainPageActivity.this).setCancelable(true).
					setTitle(title).setSingleChoiceItems(da, -1, null);
			
			mDialog = b.show();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_page);
		
		// 初始化gps，刷新时间间隔15s，经度2m
		GpsHelper.init(getApplicationContext(), 15, 2);
		
		// 每次获取的地理位置有效时长为2分钟
		GpsHelper.setLocationEffectiveTime(120 * 1000);
		
		BarCodeHelper.init(getApplicationContext());
		
		initTopbar();
		
		initViews();
	}
	
	private void initViews()
	{
		mGridview = (GridView) findViewById(R.id.main_grid);
		
		// 主页选项
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("text", getString(R.string.general_survey));
		map.put("bg", R.drawable.bg1);
		
		data.add(map);
		
		map = new HashMap<String, Object>();
		map.put("text", getString(R.string.area_comb));
		map.put("bg", R.drawable.bg2);
		
		data.add(map);
		
		map = new HashMap<String, Object>();
		map.put("text", getString(R.string.exception_deal));
		map.put("bg", R.drawable.bg3);
		
		data.add(map);
		
		SimpleAdapter  adapter = new SimpleAdapter (this, 
				data, R.layout.main_page_item, 
				new String[]{"bg","text"},
				new int[]{R.id.bg,R.id.text});
		
		mGridview.setAdapter(adapter);
		
		mGridview.setOnItemClickListener(mOnClickLis);
	}
	
	private void initTopbar()
	{
		title = (TextView) findViewById(R.id.title_text);
		
		title.setText(R.string.login_title);
	}
	
	private void loadActivity(Class<?> classz, String districtId)
	{
		Intent i = new Intent();
		
		i.setClass(getApplication(), classz);
		
		i.putExtra("DistrictId", districtId);
		
		startActivity(i);
	}
	
	private void loadActivity(Class<?> classz, String districtId, String logo)
	{
		Intent i = new Intent();
		
		i.setClass(getApplication(), classz);
		
		i.putExtra("DistrictId", districtId);
		
		i.putExtra("DistrictLogo", logo);
		
		startActivity(i);
	}
	
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		
		BarCodeHelper.release();
	}
}
