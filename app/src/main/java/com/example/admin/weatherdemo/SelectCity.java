package com.example.admin.weatherdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.admin.app.MyApplication;
import com.example.admin.bean.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectCity extends Activity implements View.OnClickListener {//选择城市
    private ImageView mBackBtn;
    private ListView mList;
    private List<City> cityList;
    private List citynameList=new ArrayList();
    private EditText Searchcity;
    private List<City> filterDateList=new ArrayList<>();
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);
        initViews();

    }
    @Override
    public  void onClick(View v){
        switch (v.getId()){
            case R.id.title_back:
                /*SharedPreferences pref = getSharedPreferences("config", MODE_PRIVATE);
                String cityCode = pref.getString("cityCode", "101010100");
                Intent i=new Intent();
                i.putExtra("cityCode",cityCode);
                setResult(RESULT_OK,i);*/
                finish();
                break;
             default:
                 break;
        }
    }
    private void initViews(){
        mBackBtn=(ImageView)findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);
        mList=(ListView)findViewById(R.id.title_list);
        mList.setTextFilterEnabled(true);//开启listview过滤
        MyApplication myApplication=(MyApplication)getApplication();
        cityList=myApplication.getCityList();
        for(City city:cityList) {
            if (city != null) {
                filterDateList.add(city);//存储城市名
            }
        }
        for (City city : cityList) {
            if (city != null) {
                citynameList.add(city.getCity());
            }
        }
        adapter=new ArrayAdapter<String>(
                SelectCity.this,android.R.layout.simple_list_item_1,citynameList
        );
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city=filterDateList.get(position);
                Intent i=new Intent();
                i.putExtra("cityCode",city.getNumber());
                setResult(RESULT_OK,i);
                finish();
            }
        });
        Searchcity=(EditText)findViewById(R.id.searchcity);
        Searchcity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
                mList.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //这个函数是自定义的过滤器，可以返回后的列表
    private List<String> getFiltList(List<String> list, String filtValue){
        List<String> filtList = new ArrayList<>();
        for(String cityName : list){
            if (cityName.contains(filtValue)){
                filtList.add(cityName);
            }
        }
        return filtList;
    }
    private void filterData(String filterStr){
        filterDateList = new ArrayList<City>();
        if(TextUtils.isEmpty(filterStr)){
            for(City city:cityList){
                filterDateList.add(city);
                citynameList.add(city.getCity());
            }
        }else{
            filterDateList.clear();
            citynameList.clear();
            for(City city:cityList){
                if(city.getCity().indexOf(filterStr.toString())!= -1){
                    filterDateList.add(city);
                    citynameList.add(city.getCity());
                }
            }
        }
        //更新适配器中的内容
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,citynameList);
    }

}
