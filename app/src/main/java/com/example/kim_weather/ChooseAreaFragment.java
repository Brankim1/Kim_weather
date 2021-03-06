package com.example.kim_weather;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.example.kim_weather.db.cityId;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

public class ChooseAreaFragment extends Fragment {

    private EditText textCity;
    private Button query;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList=new ArrayList<>();

    private String cityName;

    private List<cityId> city1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.choose_area,container,false);
        textCity=(EditText)view.findViewById(R.id.textCity);
        query=(Button)view.findViewById(R.id.query);
        listView=(ListView)view.findViewById(R.id.list_view);
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //关闭键盘
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getView() != null && imm != null){
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                }
                //查询城市
                cityName=textCity.getText().toString();
                city1=LitePal.select("cityEn","cityid","provinceEn")
                        .where("cityEn like ?","%"+cityName+"%")//模糊查询
                        .find(cityId.class);
                if(city1.size()>0){
                    //添加数据进listview
                    dataList.clear();
                    for(cityId city:city1){
                        dataList.add(city.getCityEn()+" , "+city.getProvinceEn());//把数据加进listview
                    }
                    adapter.notifyDataSetChanged();
                    listView.setSelection(0);

                textCity.setText("");
                } else{//查询不匹配
                    Toast.makeText(getContext(),"city name wrong, try again",Toast.LENGTH_LONG).show();
                    textCity.setText("");
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //选择城市
                String weatherId=city1.get(position).getId();
                Log.d("city","id id "+weatherId);
                    if(getActivity() instanceof MainActivity){
                        Intent intent=new Intent(getActivity(),WeatherActivity.class);
                        intent.putExtra("weather_id",weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    }else if(getActivity() instanceof WeatherActivity){
                        WeatherActivity activity=(WeatherActivity)getActivity();
                        activity.drawerLayout.closeDrawers();
                        activity.swipeRefresh.setRefreshing(true);
                        activity.requestWeather(weatherId);
                    }

                }

        });

}}
