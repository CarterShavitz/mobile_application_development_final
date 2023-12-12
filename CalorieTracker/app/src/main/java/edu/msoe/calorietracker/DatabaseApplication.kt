package edu.msoe.calorietracker

import android.app.Application

class DatabaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Repository.initialize(this)
    }
}