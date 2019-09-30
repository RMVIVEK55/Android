package com.asow.astan.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.Preference
import android.util.Log


import java.util.Locale

class AppPreference(context: Context) : Preference(context) {
 var sharedPreferencesx:SharedPreferences

    init {
        sharedPreferencesx= context.getSharedPreferences(
            "Preferences",
            Context.MODE_PRIVATE
        )

    }
    companion object {

        private val TAG = "AppPreference"

        /**
         * Singleton instance for AppPreference
         */

        var instance: AppPreference? = null

        fun getInstance(context: Context): AppPreference? {

            return if (instance == null)
            {

                AppPreference(context)
            }


            else instance
        }



    }
    /**
     * @param key
     * key constant from AppPreference
     * @param value
     * String value to be stored associated with the key
     *
     *
     * Stores the String value in preference with the key
     *
     */
    fun putString(key: StringKeys, value: String) {

        sharedPreferencesx.edit().putString(key.toString(), value).commit()
        Log.i(TAG, "Put String - key:$key value:$value")
    }

    /**
     * @param key
     * key constant from AppPreference
     * @return String value associated with key or null
     */
    fun getString(key: StringKeys): String? {
        return sharedPreferencesx.getString(key.toString(), "")
    }

    /**
     * @param key
     * key constant from AppPreference
     * @param value
     * integer value to be stored associated with the key
     *
     *
     * Stores the integer value in preference with the key
     *
     */
    fun putInt(key: IntKeys, value: Int) {
        sharedPreferencesx.edit().putInt(key.toString(), value).commit()
        Log.i(TAG, "Put String - key:$key value:$value")
    }

    /**
     * @param key
     * key constant from AppPreference
     * @return integer value associated with key or Integer.MIN_VALUE
     */
    fun getInt(key: IntKeys): Int {
        return sharedPreferencesx.getInt(key.toString(), 0)
    }

    /**
     * @param key
     * key constant from AppPreference
     * @param value
     * boolean value to be stored associated with the key
     *
     *
     * Stores the boolean value in preference with the key
     *
     */

    fun putBoolean(key: BooleanKeys, value: Boolean) {
        sharedPreferencesx.edit().putBoolean(key.toString(), value).commit()
        Log.i(TAG, "Put String - key:$key value:$value")
    }

    /**
     * @param key
     * key constant from AppPreference
     * @return boolean value associated with key or false
     */
    fun getBoolean(key: BooleanKeys): Boolean {
        return sharedPreferencesx.getBoolean(key.toString(), false)
    }

    /**
     * @param key
     * key constant from AppPreference
     * @param value
     * float value to be stored associated with the key
     *
     *
     * Stores the float value in preference with the key
     *
     */
    fun putDouble(key: DoubleKeys, value: Float) {
        sharedPreferencesx.edit().putFloat(key.toString(), value).commit()
    }

    /**
     * @param key
     * key constant from AppPreference
     * @return float value associated with key or Float.MIN_VALUE
     */
    operator fun get(key: DoubleKeys): Float? {
        return sharedPreferencesx.getFloat(key.toString(), java.lang.Float.MIN_VALUE)
    }

    /**
     * This enum values are used to manage String values in preference
     *
     */
    enum class StringKeys {

        /**
         * user name
         */
        USER_NAME,
        DEVICE_ID, SONG_ID_URI, DEVICE_TOKEN, PD_USER_ID;

        /**
         * Profile picture
         */


        override fun toString(): String {
            return this.name.toLowerCase(Locale.ENGLISH)
        }
    }

    /**
     * This enum values are used to manage Boolean values in preference
     *
     */

    enum class BooleanKeys {
        /**
         * True for user already logged in
         */
        LOGGED_IN,
        POPUP_BOOL, SKIP, BOOL_DIALOGUE_UPDATE, LOGIN_CHECK;

        override fun toString(): String {
            return this.name.toLowerCase(Locale.ENGLISH)
        }
    }

    /**
     * This enum values are used to manage Integer values in preference
     *
     */

    enum class IntKeys {

        /**
         * Key for storing the app_version
         */
        APP_VERSION,
        STORE_ID, USER_ID, CART_ITEM_COUNTS;

        override fun toString(): String {
            return this.name.toLowerCase(Locale.ENGLISH)
        }
    }

    /**
     * This enum values are used to manage Float values in preference
     *
     */

    enum class DoubleKeys {

        /**
         * Latitude of the user's current location
         */
        INITIAL_LONG,
        TAX_PERCENT;

        override fun toString(): String {
            return  this.name.toLowerCase(Locale.ENGLISH)
        }
    }



}
