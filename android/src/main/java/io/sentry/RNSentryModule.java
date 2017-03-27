package io.sentry;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.getsentry.raven.Raven;
import com.getsentry.raven.dsn.Dsn;
import com.getsentry.raven.event.Event;
import com.getsentry.raven.event.EventBuilder;

public class RNSentryModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private Raven raven = null;
    final static Logger logger = Logger.getLogger("react-native-sentry");

    public RNSentryModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNSentry";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("nativeClientAvailable", true);
        return constants;
    }

    @ReactMethod
    public void startWithDsnString(String dsnString) {
        Dsn dsn = new Dsn(dsnString);
        this.raven = RNSentryRavenFactory.ravenInstance(dsn);
        logger.info(String.format("startWithDsnString '%s'", dsn));
    }

    @ReactMethod
    public void captureMessage(String message, int level) {
        logger.info(String.format("captureMessage '%s'", message));
        Event.Level eventLevel = null;
        switch (level) {
            case 0:
                eventLevel = Event.Level.FATAL;
                break;
            case 2:
                eventLevel = Event.Level.WARNING;
                break;
            case 3:
                eventLevel = Event.Level.INFO;
                break;
            case 4:
                eventLevel = Event.Level.DEBUG;
                break;
            default:
                eventLevel = Event.Level.ERROR;
                break;
        }
        EventBuilder eventBuilder = new EventBuilder().withMessage(message)
                .withLevel(eventLevel);
        this.raven.runBuilderHelpers(eventBuilder);
        Event event = eventBuilder.build();
        this.raven.sendEvent(event);
    }

    @ReactMethod
    public void setLogLevel(int level) {
        logger.info("TODO: implement setLogLevel");
    }

    @ReactMethod
    public void setExtras(ReadableMap extras) {
        logger.info("TODO: implement setExtras");
    }

    @ReactMethod
    public void setTags(ReadableMap tags) {
        logger.info("TODO: implement setTags");
    }

    @ReactMethod
    public void setUser(ReadableMap user) {
        logger.info("TODO: implement setUser");
    }

    @ReactMethod
    public void crash() {
        logger.info("TODO: implement crash");
    }

    @ReactMethod
    public void activateStacktraceMerging(Promise promise) {
        logger.info("TODO: implement activateStacktraceMerging");
//        try {
            promise.resolve(true);
//        } catch (IllegalViewOperationException e) {
//            promise.reject(E_LAYOUT_ERROR, e);
//        }
    }
}
