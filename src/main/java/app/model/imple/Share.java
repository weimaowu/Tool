package app.model.imple;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * Created by HSAEE on 2017-03-29.
 */

public class Share {
    Activity activity;
    public void Share(Activity activity1){
        activity=activity1;
    }
    public boolean checkInstallation(String packageName){
        try {
            activity.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }
    public void install(){
        Uri uri = Uri.parse("market://details?id=应用包名");
        Intent it = new Intent(Intent.ACTION_VIEW,uri);
        activity.startActivity(it);
    }
}
