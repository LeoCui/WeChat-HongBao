package leo.qianghongbao1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import leo.qianghongbao1.R;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {  //入口

    final String openAcessibilityService="android.settings.ACCESSIBILITY_SETTINGS";
    final String openNotificationService="android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnAcessibility=(Button)findViewById(R.id.btnAcessibility);
        Button btnNotification=(Button)findViewById(R.id.btnNotification);
        Button btnGuide=(Button)findViewById(R.id.btnUseGuide);
        Button btnAbout=(Button)findViewById(R.id.btnAbout);
        btnAcessibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(openAcessibilityService);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "打开[leo帮你抢红包]", LENGTH_SHORT).show();
            }
        });

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(openNotificationService);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"打开[leo帮你抢红包]",LENGTH_SHORT).show();
            }
        });

        btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,GuideActivity.class);
                startActivity(intent);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AboatActivity.class);
                startActivity(intent);
            }
        });
    }
}
