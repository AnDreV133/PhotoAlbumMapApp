package com.andrev133.photoalbummapapp.data

import android.content.Context
import android.content.SharedPreferences

//class MainSharedPref(context: Context) {
//    private val SHARED_PREF_NAME = "main_shared_pref"
//
//    val sharedPref: SharedPreferences = context.getSharedPreferences(
//        SHARED_PREF_NAME,
//        Context.MODE_PRIVATE
//    )
//
//    suspend inline fun <reified T : Any> get(key: String): T? =
//        withContext(Dispatchers.IO) {
//            async {
//                sharedPref.getValue<T>(key)
//            }.await()
//        }
//
//
//    suspend inline fun <reified T : Any> set(key: String, value: T) =
//        withContext(Dispatchers.IO) {
//            async {
//                sharedPref.setValue<T>(key, value)
//            }.await()
//        }
//}

//inline fun <reified T : Any> SharedPreferences.getValue(key: String) = when (T::class) {
//    String::class -> getString(key, null)
//    Boolean::class -> getBoolean(key, false)
//    Int::class -> getInt(key, 0)
//    Float::class -> getFloat(key, 0f)
//    else -> throw IllegalArgumentException("Invalid type")
//} as T?

enum class SharedPrefKeys(val value: String) {
    MAIN_SETTINGS("MAIN_SETTINGS")
}

fun Context.getSharedPrefByKey(sharedPrefKeys: SharedPrefKeys) = getSharedPreferences(
    sharedPrefKeys.value,
    Context.MODE_PRIVATE
)

inline fun <reified T : Any> SharedPreferences.setValue(
    key: String,
    value: T
) = changePutFunc(key, value).commit()

inline fun <reified T : Any> SharedPreferences.setValueAsync(
    key: String,
    value: T
) = changePutFunc(key, value).apply()

inline fun <reified T : Any> SharedPreferences.changePutFunc(
    key: String,
    value: T
): SharedPreferences.Editor = when (T::class) {
    String::class -> edit().putString(key, value as String)
    Boolean::class -> edit().putBoolean(key, value as Boolean)
    Int::class -> edit().putInt(key, value as Int)
    Long::class -> edit().putLong(key, value as Long)
    Float::class -> edit().putFloat(key, value as Float)
    Double::class -> edit().putFloat(key, (value as Double).toFloat())
    else -> throw IllegalArgumentException("Invalid type")
}


