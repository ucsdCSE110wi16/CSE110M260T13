package com.prodevteam.tastebud;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.gms.iid.InstanceIDListenerService;

public class ListenerService extends InstanceIDListenerService {
    public ListenerService() {
    }

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, RegistrationService.class);
        startService(intent);
    }
}
