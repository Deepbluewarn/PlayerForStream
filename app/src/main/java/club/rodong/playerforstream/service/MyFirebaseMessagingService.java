package club.rodong.playerforstream.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.i("onNewToken", ": " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i("onMessageReceived", remoteMessage.getData().get("Twitch_response"));

        Bundle bundle = new Bundle();
        bundle.putString("msgBody", remoteMessage.getData().get("Twitch_response"));

        Intent new_intent = new Intent();
        new_intent.setAction("ACTION_STRING_ACTIVITY");
        new_intent.putExtra("msg", bundle);

        sendBroadcast(new_intent);
    }
}
