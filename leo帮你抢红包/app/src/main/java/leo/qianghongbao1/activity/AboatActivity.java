package leo.qianghongbao1.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import leo.qianghongbao1.R;

/**
 * Created by Leo on 16/6/15.
 */
public class AboatActivity extends Activity{  //关于软件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_the_app);
        findViewById(R.id.btnAboutReturnToTabbed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboatActivity.this.finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<String> arrayList=new ArrayList<String>();
        ArrayAdapter<String> adapter;
        ListView listView;
        arrayList.add("软件名称:      leo帮你抢红包");
        arrayList.add("版本号:          1.0.0");
        arrayList.add("系统要求:      安卓4.4.4以上");
        arrayList.add("开发者:          Leo崔一鸣");
        arrayList.add("说明:            程序运行时可能会影响微信正常使用");
        arrayList.add("版权所有,翻版不究");
        adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arrayList);
        listView=(ListView)findViewById(R.id.list_about);
        listView.setAdapter(adapter);
    }
}
