package com.example.admin.weatherdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.AndroidException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.admin.app.MyApplication;
import com.example.admin.bean.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectCity extends Activity implements View.OnClickListener {//选择城市
    private  ImageView mBackBtn;
    private ListView mList;
    private List<City> cityList;
    private List<String> filterDateList=new ArrayList<>();//存储城市名称
    private List<String> oringinalList=new ArrayList<>();//存储城市代码
    private HashMap<String,String> map=new HashMap<>();// 查询过程中使用


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
                /*Intent i=new Intent();
                i.putExtra("cityCode","101160101");
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
        MyApplication myApplication=(MyApplication)getApplication();
        cityList=myApplication.getCityList();
        for(City city:cityList){
                filterDateList.add(city.getCity());//存储城市名称
                oringinalList.add(city.getNumber());//存储城市代码
                map.put(city.getCity(),city.getNumber());//存储到hashmap中
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                SelectCity.this,android.R.layout.simple_list_item_1,filterDateList
        );
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cityName=filterDateList.get(position);
                String cityCode=map.get(cityName);


                Intent i=new Intent();
                i.putExtra("cityCode",cityCode);
                setResult(RESULT_OK,i);
                finish();
            }
        });

    }

}
