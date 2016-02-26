package com.prodevteam.tastebud;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.gcm.GcmListenerService;

public class GCMListenerService extends GcmListenerService {
    public GCMListenerService() {
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {

    }
}
