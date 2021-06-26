package com.health.pusun.device.utils;

import android.content.Context;
import android.content.pm.PackageManager;


public class SelfPermissionUtil {

    private static final String CAMERA_PERMISSION = "android.permission.CAMERA";
    private static final String READ_STORAGE_PERMISSION = "android.permission.READ_EXTERNAL_STORAGE";
    public static boolean checkCameraPermission(Context context){
        int perm = context.checkCallingOrSelfPermission(CAMERA_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
    public static boolean checkStoragePermission(Context context){
        int perm = context.checkCallingOrSelfPermission(READ_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

}
