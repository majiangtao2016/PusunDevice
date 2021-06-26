package com.health.pusun.device.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONObject;


/**
 * 显示给用户看的,界面显示工具类,目前有确认对话框和进度对话框和土司
 */
public class ShowHelper {

    /**
     * 土司
     */
    private static Toast mToast;


    /**
     * 显示土司
     *
     * @param context 上下文
     * @param c       字符串
     */
    public static void toastShort(Context context, CharSequence c) {
        if (mToast == null) {
            mToast = Toast.makeText(context, c, Toast.LENGTH_SHORT);
        } else {
            //mToast.cancel();
            mToast.setText(c);
        }
        mToast.show();
    }

    /**
     * 显示土司
     *
     * @param context
     * @param c
     */
    public static void toastLong(Context context, CharSequence c) {
        if (mToast == null) {
            mToast = Toast.makeText(context, c, Toast.LENGTH_LONG);
        } else {
            mToast.setText(c);
        }
        mToast.show();
    }

    /**
     * 显示土司
     */
    public static void toastShort(Context context, int resId) {
        Toast.makeText(context, context.getText(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示提示
     *
     * @param context
     * @param resId
     */
    public static void toastLong(Context context, int resId) {
        toastLong(context, context.getText(resId));
    }

    /**
     * 对话框
     */
    private static ProgressDialog progressDialog;

    /**
     * 显示对话框
     *
     * @param context
     * @param message
     */
    public static void showProgressDialog(Context context, String message) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(context, "", message, true);
        } else {
            progressDialog.setMessage(message);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }

    }

    public static boolean isProgressDialogShowing(){
        if (null != progressDialog && progressDialog.isShowing()){
            return true;
        }else return false;
    }

    public static void setProgressDialogMessage(String message){
        if (progressDialog != null) {
            progressDialog.setMessage(message);
        }
    }


    /**
     * 解析提示内容,考虑到了{"message":"","msg":"","data":{"message":"","msg":""}}
     * @param jsonObject
     * @return
     */
    public static String getMessage(JSONObject jsonObject){
        if(jsonObject==null){
            return "";
        }
        String message = jsonObject.optString("message");
        if(TextUtils.isEmpty(message)){
            message = jsonObject.optString("msg");
        }
        if(TextUtils.isEmpty(message)){
            JSONObject data = jsonObject.optJSONObject("data");
            if(data!=null){
                message = data.optString("msg");
                if(TextUtils.isEmpty(message)){
                    message = data.optString("message");
                }
            }
        }
        if(message==null){
            message= jsonObject.toString();
        }
        return message;
    }

    public static boolean isResponseOperationOk(Context context, JSONObject jsonObject){
        boolean isOk = jsonObject.optString("code","2").equals("0");
        if(!isOk){
            ShowHelper.toastLong(context, getMessage(jsonObject));
        }
        return isOk;
    }

    /**
     * 隐藏对话框
     */
    public static void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.getContext() != null && progressDialog.getWindow() != null) {
            progressDialog.dismiss();
        }
        progressDialog = null;

    }

    /**
     * 显示对话框
     *
     * @param context
     * @param message
     * @param onClickListener
     */
    public static void showAlertDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton("是", onClickListener);
        builder.setNegativeButton("否", null);
        builder.show();
    }

    /**
     * 显示对话框
     *
     * @param context
     * @param message
     * @param onClickListener
     */
    public static void showAlertDialogSelect(Context context, String message, DialogInterface.OnClickListener onClickListener,DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton("左侧", onClickListener);
        builder.setNegativeButton("右侧", clickListener);
        builder.show();
    }

    /**
     * 显示对话框
     *
     * @param context
     * @param message
     * @param onClickListener
     */
    public static void showAlertDialog(Context context, String message, DialogInterface.OnClickListener onClickListener,DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton("左场练习", onClickListener);
        builder.setNegativeButton("右场练习", clickListener);
        builder.show();
    }

    /**
     * 显示对话框
     *
     * @param context
     * @param message
     * @param onClickListener
     */
    public static void showUpdateApkAlertDialog(Context context, String message, DialogInterface.OnClickListener onClickListener,DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("版本升级提示");
        builder.setMessage(message);
        builder.setPositiveButton("是", onClickListener);
        builder.setNegativeButton("否", clickListener);
        builder.setCancelable(false);
        builder.show();
    }


    /**
     * 显示对话框
     *
     * @param context
     * @param message
     *
     */
    public static void showAlertDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton("确认", null);
        builder.show();
    }

    /**
     * 显示对话框
     *
     * @param context
     * @param message
     */
    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确认", null);
        builder.show();
    }
//
//    /**
//     * 权限提示对话框
//     *
//     * @param context
//     * @param message
//     */
//    public static void showAuthorityDialog(Context context, String message){
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_authority,null);
//        TextView info = (TextView)layout.findViewById(R.id.authority_info);
//        info.setText(message);
//        builder.setView(layout);
//        builder.setPositiveButton("确认", null);
//        builder.show();
//    }
//
//    public static void showAuthorityDialog(Context context, int messageId){
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_authority,null);
//        TextView info = (TextView)layout.findViewById(R.id.authority_info);
//        info.setText(context.getText(messageId));
//        builder.setView(layout);
//        builder.setPositiveButton("确认", null);
//        builder.show();
//    }
//
//    public static void showLoadingDialog(Context context){
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_loading,null);
//        builder.setView(layout);
//        builder.show();
//    }

}
