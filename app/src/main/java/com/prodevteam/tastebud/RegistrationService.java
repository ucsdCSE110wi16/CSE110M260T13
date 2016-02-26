package com.prodevteam.tastebud;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;

import java.io.IOException;

public class RegistrationService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RegistrationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            String token = instanceID.getToken("364282103429", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            sendRegistrationTokenToServer(token);
            App.token = token;
        } catch (IOException e) {
            Log.e("GCM:", "Error:", e);
        }
    }

    private void sendRegistrationTokenToServer(String token) {
    }
}
