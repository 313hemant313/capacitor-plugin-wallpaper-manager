package tech.thegamedefault.plugins.capacitorwallpapermanager;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@NativePlugin(
        permissions={
                Manifest.permission.SET_WALLPAPER,
                Manifest.permission.SET_WALLPAPER_HINTS
        }
)
public class TGDWallpaperManager extends Plugin {

    protected static final int SET_WALLPAPER = 1122;

    @Override
    protected void handleRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.handleRequestPermissionsResult(requestCode, permissions, grantResults);


        PluginCall savedCall = getSavedCall();
        if (savedCall == null) {
            Log.d("Test", "No stored plugin call for permissions request result");
            return;
        }

        for(int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                Log.d("Test", "User denied permission");
                return;
            }
        }

        if (requestCode == SET_WALLPAPER) {
            // We got the permission!
            applyWallpaperWithPermissions(savedCall);
        }
    }

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", value);
        call.success(ret);
    }

    @PluginMethod
    public void applyWallpaper(PluginCall call) {
        String wallpaperUrl = call.getString("value");
        pluginRequestAllPermissions();
        if(hasRequiredPermissions()) {
            applyWallpaperWithPermissions(call);
        }
    }

    void applyWallpaperWithPermissions(PluginCall call) {
        String wallpaperUrl = call.getString("value");
        System.out.println("### Plugin- Apply wallpaper: "+wallpaperUrl);
        WallpaperManager wpm = WallpaperManager.getInstance(this.getContext());
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        wpm.suggestDesiredDimensions(width, height);
        try(InputStream ins =  new URL(wallpaperUrl).openStream()) {
            wpm.setStream(ins);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
