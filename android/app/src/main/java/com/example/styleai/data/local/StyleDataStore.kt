package com.example.styleai.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "styleai_settings")

object StyleDataStoreKeys {
    val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")
    val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    val CONSENT_PHOTO_PERMISSION = booleanPreferencesKey("consent_photo_permission")
    val CONSENT_UNDERSTANDS_DISCLAIMER = booleanPreferencesKey("consent_understands_disclaimer")
    val CONSENT_RAW_PHOTOS_NOT_STORED = booleanPreferencesKey("consent_raw_photos_not_stored")
    val MOCK_CREDITS = intPreferencesKey("mock_credits")
    val IS_PRO_USER = booleanPreferencesKey("is_pro_user")
    val IS_REPORT_UNLOCKED = booleanPreferencesKey("is_report_unlocked")
}
