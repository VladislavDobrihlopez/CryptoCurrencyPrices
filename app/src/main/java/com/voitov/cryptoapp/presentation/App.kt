package com.voitov.cryptoapp.presentation

import android.app.Application
import com.voitov.cryptoapp.di.DaggerApplicationComponent

class App: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}