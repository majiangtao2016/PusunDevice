package com.health.pusun.device;

import android.app.ListActivity;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.serialport.SerialPortFinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.health.pusun.device.application.App;

public class SerialPortPreferences extends PreferenceActivity {

    private App mApplication;
    private SerialPortFinder mSerialPortFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApplication = (App) getApplication();
        mSerialPortFinder = mApplication.mSerialPortFinder;

        addPreferencesFromResource(R.xml.serial_port_preferences);

        // Devices
        final ListPreference devices = (ListPreference) findPreference("DEVICE");
        String[] entries = mSerialPortFinder.getAllDevices();
        String[] entryValues = mSerialPortFinder.getAllDevicesPath();
        devices.setEntries(entries);
        devices.setEntryValues(entryValues);
        devices.setSummary(devices.getValue());
        devices.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String) newValue);
                return true;
            }
        });

        // Baud rates
        final ListPreference baudrates = (ListPreference) findPreference("BAUDRATE");
        baudrates.setSummary(baudrates.getValue());
        baudrates.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String) newValue);
                return true;
            }
        });
    }
}
