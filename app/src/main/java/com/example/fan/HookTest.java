package com.example.fan;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findClass;

public class HookTest implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        //com.tencent.mm
        if (lpparam.packageName.equals("com.tencent.mobileqq"))
        {
            try{
                XposedHelpers.findAndHookMethod(Application.class,
                        "attach",
                        Context.class,
                        new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                Context context = (Context)param.args[0];
                                ClassLoader classLoader = context.getClassLoader();
                                HookLocation(classLoader);
                            }
                        });
            }catch (Throwable e){
//                XposedBridge.log(e);
            }
        }
    }

    private static void HookLocation(ClassLoader classLoader) throws ClassNotFoundException {
        Class<?> runnable = findClass("java.lang.Runnable", classLoader);
         hook com.tencent.mm.sdk.platformtools.MMHandler
        XposedHelpers.findAndHookMethod(
                "com.tencent.mm.sdk.platformtools.MMHandler", classLoader, "post", runnable,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {
                        boolean flag2 = false;
                        long threadId = 00000;
                        for (Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()) {
                            Thread thread = (Thread) stackTrace.getKey();
                            StackTraceElement[] stack = (StackTraceElement[]) stackTrace.getValue();

                            if (!thread.equals(Thread.currentThread())) {
                                continue;
                            }

                            for (StackTraceElement stackTraceElement : stack) {
                                Log.d("stackTraceElement.getClassName()", stackTraceElement.getClassName());
                                Log.d("stackTraceElement.getClassName()", stackTraceElement.getMethodName());
                                if (stackTraceElement.getClassName().equals("com.tencent.mm.plugin.appbrand.jsapi.h") && stackTraceElement.getMethodName().equals("Q")) {
                                    flag2 = true;
                                    break;
                                }
                            }
                            Log.d("flag2", String.valueOf(flag2));
                            if (!flag2) {
                                break;
                            }
                            threadId = thread.getId();
                            Log.d("[hookMMHandlerPost]", String.valueOf(param.args[0]) + "*******" + thread.getName() + "-------" + threadId);
                            Log.d("param.getResult()", String.valueOf(param.getResult()));
                        }
//                        if (flag2) {
//                            Message m = (Message) param.getResult();
//                            m.what = (int) threadId;
//                            param.setResult(m);
//                        }
                    }
                });

         hook com.tencent.mm.sdk.platformtools.MMHandler
        Class<?> message = findClass("android.os.Message", classLoader);
        XposedHelpers.findAndHookMethod(
                "android.os.Handler", classLoader, "dispatchMessage", message,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {
                        Message m = (Message) param.args[0];
                        Runnable r = m.getCallback();
//                        Log.i("m.getCallback()", String.valueOf(m.getCallback()));
                        int relatedThreadId = m.what;
                        if (r == null) {
                            return;
                        }
                        String tag = "[dispatchMessage]";
                        String msg = "runnable:  " + String.valueOf(r) + "*******" + "relatedThreadid: " + String.valueOf(relatedThreadId) + "-------" + "currentId: ";
                        printLog(tag, msg);

                    }
                });

        // hook invokeHandler Wechat-api
        XposedHelpers.findAndHookMethod(
                //Class : d Method : y
                "com.tencent.mm.plugin.appbrand.jsapi.h", classLoader, "Q", String.class, String.class, String.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {
                        String tag = "[MiniAppApi]";
                        String msg = String.valueOf(param.args[0]) + "*******";
                        printLog(tag,msg);

                    }
                });

        //hook  Android APi

        hookMethods(com.example.fan.Method.methodListPart1, classLoader);
        hookMethods(com.example.fan.Method.methodListPart2, classLoader);
        hookMethods(com.example.fan.Method.methodListPart3, classLoader);
        hookMethods(com.example.fan.Method.methodListPart4, classLoader);
        hookMethods(com.example.fan.Method.methodListPart5, classLoader);
        hookMethods(com.example.fan.Method.methodListPart6, classLoader);
        hookMethods(com.example.fan.Method.methodListPart7, classLoader);
        hookMethods(com.example.fan.Method.methodListPart8, classLoader);
        hookMethods(com.example.fan.Method.methodListPart10, classLoader);
        hookMethods(com.example.fan.Method.methodListPart11, classLoader);
        hookMethods(com.example.fan.Method.methodListPart12, classLoader);
        hookMethods(com.example.fan.Method.methodListPart13, classLoader);
        hookMethods(com.example.fan.Method.methodListPart14, classLoader);
        hookMethods(com.example.fan.Method.methodListPart15, classLoader);
        hookMethods(com.example.fan.Method.methodListPart16, classLoader);
        hookMethods(com.example.fan.Method.methodListPart17, classLoader);
        hookMethods(com.example.fan.Method.methodListPart18, classLoader);
        hookMethods(com.example.fan.Method.methodListPart19, classLoader);
        hookMethods(com.example.fan.Method.methodListPart20, classLoader);
        hookMethods(com.example.fan.Method.methodListPart21, classLoader);
        hookMethods(com.example.fan.Method.methodListPart22, classLoader);
        hookMethods(com.example.fan.Method.methodListPart24, classLoader);
        hookMethods(com.example.fan.Method.methodListPart25, classLoader);
        hookMethods(com.example.fan.Method.methodListPart26, classLoader);
        hookMethods(com.example.fan.Method.methodListPart27, classLoader);
        hookMethods(com.example.fan.Method.methodListPart28, classLoader);
        hookMethods(com.example.fan.Method.methodListPart29, classLoader);
        hookMethods(com.example.fan.Method.methodListPart30, classLoader);
        hookMethods(com.example.fan.Method.methodListPart31, classLoader);
        hookMethods(com.example.fan.Method.methodListPart32, classLoader);
    }

    private static void hookMethods(String[][] methods, ClassLoader classLoader) {
        for (int i = 0; i < methods.length; i++) {
            final String className = methods[i][0];
            Class<?> clazz = findClass(methods[i][0], classLoader);
            for (Method method : clazz.getDeclaredMethods()) {
                final String methodName = method.getName();
                if (!methodName.equals(methods[i][1])) {
                    continue;
                }
                if (!Modifier.isAbstract(method.getModifiers())) {
                    XposedBridge.hookMethod(method, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            for (Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()) {
                                Thread thread = stackTrace.getKey();
                                if (!thread.equals(Thread.currentThread())) {
                                    continue;
                                }

                                StringBuilder argsStr = new StringBuilder();
                                if (param.args != null) {
                                    for (Object arg : param.args) {
                                        if (arg != null) {
                                            argsStr.append(arg.getClass().getSimpleName()).append(": ").append(arg).append(", ");
                                        } else {
                                            argsStr.append("null, ");
                                        }
                                    }

                                    if (argsStr.length() > 0) {
                                        argsStr.setLength(argsStr.length() - 2);
                                    }
                                }

                                Log.i("[AndroidAPI]", "Thread ID: " + thread.getId() + " | Class: " + className + " | Method: " + methodName + " | Args: " + argsStr);
                            }
                        }
                    });
                }
            }
        }
    }



    public static void printLog(String tag, String msg) {
        for (Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()) {
            Thread thread = (Thread) stackTrace.getKey();
            StackTraceElement[] stack = (StackTraceElement[]) stackTrace.getValue();
            // 进行过滤
            if (!thread.equals(Thread.currentThread())) {
                continue;
            }
            String threadName = thread.getName();
            long threadId = thread.getId();
            Log.d(tag, threadName+"-----"+msg + "-----"+ threadId);

        }
    }
}

