package com.amit.nasaa.pod.ui.main

import android.app.Application
import com.amit.nasaa.pod.ui.main.database.APODRoomDb

class ApodApplication : Application() {
    val database: APODRoomDb by lazy { APODRoomDb.getDatabase(this) }
}