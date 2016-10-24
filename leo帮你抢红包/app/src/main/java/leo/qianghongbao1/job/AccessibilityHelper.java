package leo.qianghongbao1.job;

import android.accessibilityservice.AccessibilityService;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import leo.qianghongbao1.service.MyAccessibilityService;

/**
 * Created by Leo on 16/6/13.
 */
public class AccessibilityHelper {
    static final String TAG="AccessibilityHelper";
    static boolean unlocked=false;
    /* 模拟点击*/
    static void performClick(AccessibilityNodeInfo nodeInfo){
        if(nodeInfo==null){
            return;
        }
        if(nodeInfo.isClickable()){
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        else{
            performClick(nodeInfo.getParent());
        }
    }

    //模拟返回桌面
    static void performGoHome(AccessibilityService service){
        if(service==null){
            return;
        }
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
    }


    //根据text找到相关的node
    static AccessibilityNodeInfo findNodeInfoByText(AccessibilityNodeInfo nodeInfo,String text){
        List<AccessibilityNodeInfo> nodeInfos=nodeInfo.findAccessibilityNodeInfosByText(text);
        if(nodeInfos==null||nodeInfos.isEmpty()){
            return null;
        }
        return nodeInfos.get(0);
    }

    // 根据ID找到相关的node
    static AccessibilityNodeInfo findNodeInfoById(AccessibilityNodeInfo nodeInfo,String Id){
        List<AccessibilityNodeInfo> NodeInfos=nodeInfo.findAccessibilityNodeInfosByViewId(Id);
        if(NodeInfos.size()==0){
            return null;
        }
        return nodeInfo.findAccessibilityNodeInfosByViewId(Id).get(0);
    }

    // 根据类名找到相关的node
    static AccessibilityNodeInfo findNodeInfoByClassName(AccessibilityNodeInfo nodeInfo,String ClassName){
        if(nodeInfo==null){
            return null;
        }
        int count=nodeInfo.getChildCount();
        AccessibilityNodeInfo targetInfo;
        for(int i=0;i<count;i++){
            targetInfo=nodeInfo.getChild(i);
            if(targetInfo.getClassName().equals(ClassName)){
                return targetInfo;
            }
        }
        return null;
    }

    static public void  wakeAndUnlock(AccessibilityService service){  //解锁相关
        PowerManager pm=(PowerManager)service.getSystemService(Context.POWER_SERVICE); //
        KeyguardManager km=(KeyguardManager)service.getSystemService(Context.KEYGUARD_SERVICE);
        PowerManager.WakeLock wl=null;
        KeyguardManager.KeyguardLock kl=null;
        if(!pm.isScreenOn()){  //点亮屏幕
            wl=pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP, "点亮屏幕");
            wl.acquire();
            Log.d(TAG, "点亮屏幕");
        }
        if(wl!=null){
            wl.release();
            wl=null;
            Log.d(TAG,"关灯");
        }
        if(km.inKeyguardRestrictedInputMode()){  //解锁
            kl=km.newKeyguardLock("解锁");
            unlocked=true;
            kl.disableKeyguard();
            Log.d(TAG,"解锁");
        }
        else {
            if (unlocked == true) {
                kl = km.newKeyguardLock("加锁");
                kl.reenableKeyguard();
                Log.d(TAG, "加锁");
            }
        }
    }

}
