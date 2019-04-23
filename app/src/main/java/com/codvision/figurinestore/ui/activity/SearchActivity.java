package com.codvision.figurinestore.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.codvision.figurinestore.R;
import com.codvision.figurinestore.adapter.SGAdapter;
import com.codvision.figurinestore.bean.SanGuoBean;
import com.codvision.figurinestore.utils.CommolySearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    /**
     * TAG
     */
    public static final String TAG = "SearchActivity";
    /**
     * 数据显示listview
     */
    private ListView mListView;

    /**
     * 三国通用搜索框
     */
    private CommolySearchView<SanGuoBean> mSGCommolySearchView;
    /**
     * 三国数据源
     */
    private List<SanGuoBean> mSGDatas;
    /**
     * 三国适配器
     */
    private SGAdapter sgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initDataI();
        initViewI();
    }

    /**
     * 初始化数据
     */
    private void initDataI() {
        mSGDatas = new ArrayList<SanGuoBean>();
        SanGuoBean sgbean1 = new SanGuoBean();
        sgbean1.setSgName("刘备");
        sgbean1.setSgPetName("玄德");
        sgbean1.setSgHeadBp(R.drawable.head);
        sgbean1.setSgDescribe("刘备（161年－223年6月10日），字玄德，东汉末年幽州涿郡涿县（今河北省涿州市）人");
        SanGuoBean sgbean2 = new SanGuoBean();
        sgbean2.setSgName("关羽");
        sgbean2.setSgPetName("云长");
        sgbean2.setSgHeadBp(R.drawable.head);
        sgbean2.setSgDescribe("关羽（？－220年），本字长生，后改字云长，河东郡解良（今山西运城）人");
        SanGuoBean sgbean3 = new SanGuoBean();
        sgbean3.setSgName("张飞");
        sgbean3.setSgPetName("翼德");
        sgbean3.setSgHeadBp(R.drawable.head);
        sgbean3.setSgDescribe("张飞（？－221年），字益德[1] ，幽州涿郡（今河北省保定市涿州市）人氏");
        SanGuoBean sgbean4 = new SanGuoBean();
        sgbean4.setSgName("赵云");
        sgbean4.setSgPetName("子龙");
        sgbean4.setSgHeadBp(R.drawable.head);
        sgbean4.setSgDescribe("赵云（？－229年），字子龙，常山真定（今河北省正定）人");
        SanGuoBean sgbean5 = new SanGuoBean();
        sgbean5.setSgName("马超");
        sgbean5.setSgPetName("孟起");
        sgbean5.setSgHeadBp(R.drawable.head);
        sgbean5.setSgDescribe("马超（176年－222年），字孟起，司隶部扶风郡茂陵（今陕西兴平）人");
        SanGuoBean sgbean6 = new SanGuoBean();
        sgbean6.setSgName("黄忠");
        sgbean6.setSgPetName("汉升");
        sgbean6.setSgHeadBp(R.drawable.head);
        sgbean6.setSgDescribe("黄忠（？－220年），字汉升（一作“汉叔”[1] ），南阳（今河南南阳）人");
        SanGuoBean sgbean7 = new SanGuoBean();
        sgbean7.setSgName("张辽");
        sgbean7.setSgPetName("文远");
        sgbean7.setSgHeadBp(R.drawable.head);
        sgbean7.setSgDescribe("张辽（169年－222年），字文远，雁门马邑（今山西朔州）人");
        mSGDatas.add(sgbean1);
        mSGDatas.add(sgbean2);
        mSGDatas.add(sgbean3);
        mSGDatas.add(sgbean4);
        mSGDatas.add(sgbean5);
        mSGDatas.add(sgbean6);
        mSGDatas.add(sgbean7);

    }

    /**
     * 初始化控件
     */
    private void initViewI() {
        mSGCommolySearchView = (CommolySearchView) findViewById(R.id.csv_show);
        mListView = (ListView) findViewById(R.id.lv_show);
        sgAdapter = new SGAdapter(this, mSGDatas);
        mListView.setAdapter(sgAdapter);
        // 设置数据源
        mSGCommolySearchView.setDatas(mSGDatas);
        // 设置适配器
        mSGCommolySearchView.setAdapter(sgAdapter);
        // 设置筛选数据
        mSGCommolySearchView.setSearchDataListener(new CommolySearchView.SearchDatas<SanGuoBean>() {
            @Override
            public List<SanGuoBean> filterDatas(List<SanGuoBean> datas, List<SanGuoBean> filterdatas, String inputstr) {
                for (int i = 0; i < datas.size(); i++) {
                    // 筛选条件
                    if ((datas.get(i).getSgDescribe()).contains(inputstr) || datas.get(i).getSgName().contains(inputstr) || datas.get(i).getSgPetName().contains(inputstr)) {
                        filterdatas.add(datas.get(i));
                    }
                }
                return filterdatas;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SearchActivity.this, mSGCommolySearchView.getFilterDatas().get(i).getSgName() + "字" + mSGCommolySearchView.getFilterDatas().get(i).getSgPetName() + "\n" + mSGCommolySearchView.getFilterDatas().get(i).getSgDescribe(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
