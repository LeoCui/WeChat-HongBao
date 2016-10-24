package leo.qianghongbao1.service;

import android.accessibilityservice.AccessibilityService;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import leo.qianghongbao1.job.AccessibilityHelper;
import leo.qianghongbao1.job.WeChatAccessibilityJob;

/**
 * Created by Leo on 16/6/13.
 */
public class MyNotificationService extends NotificationListenerService {
    final String TAG="MyNotificationService";
    final String WECHAT_PACKET_NAME="com.tencent.mm";
    boolean flag=false;
    private static AccessibilityService service;
    AccessibilityService getService(){
        return  service;
    }
    public static void setService(AccessibilityService service1){
        service=service1;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        try {
            super.onNotificationPosted(sbn);
            Log.d(TAG, "通知栏" + sbn);
            if (sbn.getPackageName().equals(WECHAT_PACKET_NAME)) {
                new WeChatAccessibilityJob().onReceiveJob(sbn);
            } else {
//            if(WeChatAccessibilityJob.needUnlock==true){
//                WeChatAccessibilityJob.needUnlock=false;
//                new WeChatAccessibilityJob().returnMainView();
//            }
                Log.d(TAG, "step=" + WeChatAccessibilityJob.step);
                if (WeChatAccessibilityJob.step == 1) {
//                AccessibilityHelper.wakeAndUnlock(getService());
//                new WeChatAccessibilityJob().clickHongBao();
                }
            }
        }
        catch (Exception e){
            Log.d(TAG,"事件-->没有notification服务");
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        try {
            super.onNotificationRemoved(sbn);
            Log.d(TAG, "通知栏--->" + sbn);
        }
        catch(Exception e){
            Log.d(TAG,"事件--->没有notification服务");
        }
    }
}
