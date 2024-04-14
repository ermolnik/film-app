package ru.ermolnik.filmapp

import android.app.Application
import ru.ermolnik.filmapp.di.AppComponent
import ru.ermolnik.filmapp.di.DaggerAppComponent
import timber.log.Timber

internal class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .factory()
            .create(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}