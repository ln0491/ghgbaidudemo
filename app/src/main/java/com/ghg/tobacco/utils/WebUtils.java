package com.ghg.tobacco.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @author $Author
 * @version $Rev$
 * @time 2016/5/19 13:16
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class WebUtils {

    public static void openBrowser(Context context,String url){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }
}
