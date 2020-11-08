package com.covidchain.app

import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.os.IBinder
import com.covidchain.app.db.ContactRepository
import com.covidchain.app.db.DatabaseProvider

class MainService: Service() {
    lateinit var contactRepository: ContactRepository

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        contactRepository = ContactRepository(DatabaseProvider(this))
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}