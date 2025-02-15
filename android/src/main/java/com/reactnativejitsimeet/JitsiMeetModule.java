package com.reactnativejitsimeet;

import android.content.Intent;
import android.content.IntentFilter;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.ReadableMap;

public class JitsiMeetModule extends ReactContextBaseJavaModule {
    private DeviceEventManagerModule.RCTDeviceEventEmitter eventEmitter;

    public JitsiMeetModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNJitsiMeetNavigatorManager";
    }

    @ReactMethod
    public void initialize() {
      try {
            FLog.d("JitsiMeet", "Initialize");
            ReactApplicationContext context = getReactApplicationContext();
            this.eventEmitter = context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
            JitsiBroadcastReceiver receiver = new JitsiBroadcastReceiver();
            receiver.setReactModule(this);
            IntentFilter filter = new IntentFilter();
            filter.addAction("CONFERENCE_FAILED");
            filter.addAction("CONFERENCE_JOINED");
            filter.addAction("CONFERENCE_LEFT");
            filter.addAction("CONFERENCE_WILL_JOIN");
            filter.addAction("CONFERENCE_WILL_LEAVE");
            filter.addAction("LOAD_CONFJitsiMeetIG_ERROR");
            context.getCurrentActivity().registerReceiver(receiver, filter, context.getPackageName() + ".permission.JITSI_BROADCAST", null);
        } catch (NullPointerException e) {
            FLog.e("JitsiMeet", "Initialize: NullPointerException");
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void call(String url, ReadableMap options) {
        ReactApplicationContext context = getReactApplicationContext();
        Intent intent = new Intent(context, JitsiMeetNavigatorActivity.class);
        intent.putExtra("url", url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void onEventReceived(String event, WritableMap data) {
        eventEmitter.emit(event, data);
    }
}
