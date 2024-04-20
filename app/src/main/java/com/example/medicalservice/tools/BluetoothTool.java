package com.example.medicalservice.tools;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.Context;

public class BluetoothTool {
    BluetoothHeadset bluetoothHeadset;

    // Get the default adapter
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private BluetoothProfile.ServiceListener profileListener = new BluetoothProfile.ServiceListener() {
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            if (profile == BluetoothProfile.HEADSET) {
                bluetoothHeadset = (BluetoothHeadset) proxy;
            }
        }
        public void onServiceDisconnected(int profile) {
            if (profile == BluetoothProfile.HEADSET) {
                bluetoothHeadset = null;
            }
        }
    };

    public void fun(Context context){

    }
}
