package kejuntong.com.samplemoduleapp.UtilClasses;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by kejuntong on 2018-04-29.
 */

public class UtilMethods {

    public static int dpToPx(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px){
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static void saveBitmapToStorage(Context context, Bitmap bitmap, String url) {
        File file = new File(context.getCacheDir(), url);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap getBitmapFromStorage(Context context, String url){

        Bitmap bitmap = null;
        File file = new File(context.getCacheDir(), url);
        if (file.exists()){
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 2;
//            bitmap = BitmapFactory.decodeFile(filePath, options);
            bitmap = BitmapFactory.decodeFile(file.getPath());

        }
        return bitmap;
    }

    public static Uri getFileUriFromStorage(Context context, String url){
        File file = new File(context.getCacheDir(), url);
        if (file.exists()){
            return Uri.fromFile(file);
        } else {
            return null;
        }
    }

}
