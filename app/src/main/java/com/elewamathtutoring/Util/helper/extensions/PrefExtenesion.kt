package com.elewamathtutoring.Util.helper.extensions

import com.elewamathtutoring.Util.App


fun savePrefrence(key: String, value: Any) {
    val preference = App.application.applicationContext.getSharedPreferences("TeleMath", 0)
    val editor = preference.edit()

    when (value) {
        is String -> editor.putString(key, value as String)
        is Boolean -> editor.putBoolean(key, value as Boolean)
        is Int -> editor.putInt(key, value as Int)
    }
    editor.apply()

}
fun saveTokenPrefrence(key: String, value: Any) {
    val preference = App.application.applicationContext.getSharedPreferences("TeleMath", 0)
    val editor = preference.edit()

    when (value) {
        is String -> editor.putString(key, value as String)
        is Boolean -> editor.putBoolean(key, value as Boolean)
        is Int -> editor.putInt(key, value as Int)
    }
    editor.apply()
}


fun savePrefrencewelcome(key: String, value: Any) {
    val preference =
            App.application.applicationContext.getSharedPreferences("TeleMath", 0)
    val editor = preference.edit()

    when (value) {
        is String -> editor.putString(key, value as String)
        is Boolean -> editor.putBoolean(key, value as Boolean)
        is Int -> editor.putInt(key, value as Int)
    }
    editor.apply()
}

inline fun <reified T> getPrefrencewelcome(key: String, deafultValue: T): T {
    val preference =
            App.application.applicationContext.getSharedPreferences("TeleMath", 0)
    return when (T::class) {
        String::class -> preference.getString(key, deafultValue as String) as T
        Boolean::class -> preference.getBoolean(key, deafultValue as Boolean) as T
        Int::class -> preference.getInt(key, deafultValue as Int) as T
        else -> {
            " " as T
        }
    }

}

//fun defaultPrefs(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
fun clearPrefrences() {
    val preference = App.application.applicationContext.getSharedPreferences("TeleMath", 0)
    val editor = preference.edit()
    editor.clear()
    editor.apply()
}
//inline
inline fun <reified T> getPrefrence(key: String, deafultValue: T): T {
    val preference = App.application.applicationContext.getSharedPreferences("TeleMath", 0)
    return when (T::class) {
        String::class -> preference.getString(key, deafultValue as String) as T
        Boolean::class -> preference.getBoolean(key, deafultValue as Boolean) as T
        Int::class -> preference.getInt(key, deafultValue as Int) as T
        else -> {
            " " as T
        }
    }

}

inline fun <reified T> getTokenPrefrence(key: String, deafultValue: T): T {
    val preference = App.application.applicationContext.getSharedPreferences("TeleMath", 0)
    return when (T::class) {
        String::class -> preference.getString(key, deafultValue as String) as T
        Boolean::class -> preference.getBoolean(key, deafultValue as Boolean) as T
        Int::class -> preference.getInt(key, deafultValue as Int) as T
        else -> {
            " " as T
        }
    }

}


/*fun savePrefObject(key: String, obj: LoginResponse) {
    savePrefrence(key, Gson().toJson(obj))
}

fun saveSignUpObject(key: String, obj: SignUpResponse) {
    savePrefrence(key, Gson().toJson(obj))
}

fun getprefObject(key: String): LoginResponse {
    return Gson().fromJson(getPrefrence(key, ""), LoginResponse::class.java)
}

fun getSignUpObject(key: String): SignUpResponse {
    return Gson().fromJson(getPrefrence(key, ""), SignUpResponse::class.java)
}*/
