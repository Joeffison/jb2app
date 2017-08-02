package com.joeffison.jardimbotanico2.util.monetization.interfaces;

import android.content.Context;

/**
 * Created by Joeffison on 02/08/2017.
 */

public interface IGenericAd {

    public void loadAd();

    public void showAd();

    public void pause(Context context);

    public void resume(Context context);

    public void destroy(Context context);
}
