package com.ecarx.check.config

import com.ecarx.check.bean.ValidNameBean
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.io.File

/**
 * config配置解析器
 */

class ConfigParser(configFile: File) {

    private var configJson = JsonObject()

    companion object {
        const val KEY_CONFIG = "config"
        const val KEY_HANDLE_CHECK_NAME = "handle_check_name"
    }

    init {
        if (configFile.exists() && configFile.isFile) {
            configJson = Gson().fromJson(configFile.bufferedReader(), JsonObject::class.java)
        }
    }

    fun getConfigApi(): ConfigApi {
        return Gson().fromJson(
            configJson.getAsJsonObject(KEY_CONFIG),
            ConfigApi::class.java
        ) ?: ConfigApi()
    }

    fun getHandleCheckNames(): List<ValidNameBean> {
        return Gson().fromJson(
            configJson.getAsJsonArray(KEY_HANDLE_CHECK_NAME),
            object : TypeToken<List<ValidNameBean>>() {}.type
        ) ?: listOf()
    }
}