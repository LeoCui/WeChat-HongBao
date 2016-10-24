package leo.qianghongbao1.job;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.os.PowerManager;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import leo.qianghongbao1.service.MyAccessibilityService;

/**
 * Created by Leo on 16/6/13.
 */
public class WeChatAccessibilityJob {
    final String HONGBAO_KEY_TEXT="微信红包";
    final String TAG="WeChatAccessibilityJob";
    final String BUTTON_CLASSNAME="android.widget.Button";
    final String LIST_CLASSNAME="android.widget.ListView";
    final String TEXT_CLASSNAME="android.widget.TextView";
    final String FRAME_CLASSNAME="android.widget.FrameLayout";

    public static int step=0;
    public static boolean needUnlock=false;
    private static MyAccessibilityService service;

    public static  void setService(MyAccessibilityService service1){
        service=service1;
    }

    MyAccessibilityService getService(){
        return  service;
    }
    /* 监听到event后的动作 */
    public void onReceiveJob(AccessibilityEvent event){
        String ClassName=String.valueOf(event.getClassName());
        int type=event.getEventType();
        if(type==AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            if (step==1) {  //点击红包
                clickHongBao();
            }
            if (step == 2) {   //打开红包
                openHongBao();
            }
            if(step==3){    //返回桌面
                returnMainView();
            }
        }
        else{
            if(type==AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED){
                String text=String.valueOf(event.getText());
                Notification nf=(Notification)event.getParcelableData();
                step=1;
                notificationEvent(text,nf);
            }
            else {
                if (type == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
                    if (step == 3) {
                        step = 0;
                        returnMainView();
                    }
                }
            }
        }
    }

    //监听到通知栏的动作
    public void onReceiveJob(StatusBarNotification statusBarNotification){
        String PacketName=statusBarNotification.getPackageName();
        Notification nf=statusBarNotification.getNotification();
        String text=String.valueOf(nf.tickerText);
        step=1;
        notificationEvent(text, nf);
    }

    /* 通知栏事件的动作 */
    void notificationEvent(String text,Notification nf){
        Log.d(TAG, text);
        if(text.contains(HONGBAO_KEY_TEXT))
        {
            AccessibilityHelper.wakeAndUnlock(getService());
            PendingIntent intent=nf.contentIntent;
            try {
                intent.send();     //跳到微信界面
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    }

    //领取红包
    public void clickHongBao(){
        AccessibilityNodeInfo nodeInfo=getService().getRootInActiveWindow();
        if(nodeInfo==null){
            return;
        }
        List<AccessibilityNodeInfo> nodeInfos=nodeInfo.findAccessibilityNodeInfosByText("领取红包");
        if(nodeInfos!=null&&!nodeInfos.isEmpty()){
            int len=nodeInfos.size();
            Log.d(TAG, "领取红包-->" + len);
            AccessibilityNodeInfo targetInfo=nodeInfos.get(len-1);
            step++;
            AccessibilityHelper.performClick(targetInfo);    //模拟点击红包
        }
    }

    /* 拆红包 */
    void openHongBao(){
        AccessibilityNodeInfo nodeInfo=getService().getRootInActiveWindow();
        AccessibilityNodeInfo targetInfo=AccessibilityHelper.findNodeInfoByClassName(nodeInfo, BUTTON_CLASSNAME);
        if(targetInfo==null){
            return;
        }
        Log.d(TAG, "已点击红包--->");
        step++;
        AccessibilityHelper.performClick(targetInfo);     // 拆红包
        Log.d(TAG, "打开红包——->");
    }

    /* 返回主界面 */
    public void returnMainView(){
        AccessibilityHelper.performGoHome(getService());  //返回主界面
        //AccessibilityHelper.wakeAndUnlock(getService());
    }
}
