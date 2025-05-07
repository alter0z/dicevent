package com.ansorisan.dicevent.base.config

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources

class BaseContextWrapper(base: Context) : ContextWrapper(base) {
    override fun getResources(): Resources {
        val config = Configuration(super.getResources().configuration)
        config.fontScale = 1.0f

        val context = super.createConfigurationContext(config)
        val res = context.resources
        val metrics = res.displayMetrics
        metrics.scaledDensity = metrics.density * config.fontScale

        return res
    }
}