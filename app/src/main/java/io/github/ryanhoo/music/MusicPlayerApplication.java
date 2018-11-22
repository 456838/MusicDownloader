package io.github.ryanhoo.music;

import com.salton123.base.ApplicationBase;
import com.salton123.config.RetrofitManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 8/31/16
 * Time: 9:32 PM
 * Desc: MusicPlayerApplication
 */
public class MusicPlayerApplication extends ApplicationBase {

    @Override
    public void onCreate() {
        super.onCreate();
        // Custom fonts
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-Monospace-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

}
