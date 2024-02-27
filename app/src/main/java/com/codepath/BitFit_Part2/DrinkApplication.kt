package com.codepath.BitFit_Part2

import android.app.Application

class DrinkApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}