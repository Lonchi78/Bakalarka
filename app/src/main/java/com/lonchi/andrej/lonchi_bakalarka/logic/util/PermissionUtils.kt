package com.lonchi.andrej.lonchi_bakalarka.logic.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.lonchi.andrej.lonchi_bakalarka.BuildConfig


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */

fun Context.checkCameraPermissions(): Boolean =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

fun Context.checkReadExternalStoragePermissions(): Boolean =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

fun Context.checkWriteExtStoragePermissions(): Boolean =
        ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

fun Context.checkCalendarPermissions(): Boolean =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CALENDAR
        ) == PackageManager.PERMISSION_GRANTED

fun Context.checkAccountManagerPermissions(): Boolean =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCOUNT_MANAGER) == PackageManager.PERMISSION_GRANTED

fun Context.checkAccountsPermissions(): Boolean =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED

fun Context.checkPhoneStatePermissions(): Boolean =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED



fun Activity.requestCameraPermission(requestCode: Int) {
    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), requestCode)
}

fun Activity.requestWriteExtStoragePermission(requestCode: Int) {
    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
}

fun Activity.requestCalendarPermission(requestCode: Int) {
    ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR),
            requestCode
    )
}

fun Activity.requestAccountManagerPermission(requestCode: Int) {
    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCOUNT_MANAGER), requestCode)
}

fun Activity.requestGetAccountsPermission(requestCode: Int) {
    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.GET_ACCOUNTS), requestCode)
}

fun Activity.requestPhoneStatePermissions(requestCode: Int) {
    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), requestCode)
}

fun Activity.requestAppPermissionSettings() {
    val intent = Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}



/**
 * Check for coarse location permission which getting location by NETWORK provider
 * @return true if persmission was granted else false if not
 */
fun Context.checkNetworkLocationPermissions(): Boolean =
        ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

/**
 * Check for fine location permission which getting location by NETWORK provider or GPS
 * @return true if persmission was granted else false if not
 */
fun Context.checkGpsLocationPermissions(): Boolean =
        ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

/**
 * Request for coarse location which is getting from NETWORK provider
 * @param requestCode
 */
fun Activity.requestNetworkLocationPermissions(requestCode: Int) {
    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), requestCode)
}
fun Fragment.requestNetworkLocationPermissions(requestCode: Int) {
    requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), requestCode)
}

/**
 * Request for fine location which is getting from NETWORK provider or GPS
 * @param requestCode
 */
fun Activity.requestGpsLocationPermissions(requestCode: Int) {
    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestCode)
}
fun Fragment.requestGpsLocationPermissions(requestCode: Int) {
    requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestCode)
}

/**
 * Check if application can show dialog with permission request
 * @return true if request can be show
 */
fun Activity.shouldShowGpsLocationRequest(): Boolean =
        ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        )

/**
 * Request fine (GPS) location and background location only if device has Android 10 or newer
 * @param requestCode
 */
fun Activity.requestBackgroundLocationPermissions(requestCode: Int) {
    val arrayOfPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    } else {
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    ActivityCompat.requestPermissions(this, arrayOfPermissions, requestCode)
}

/**
 * Request fine (GPS) location and background location only if device has Android 10 or newer
 * @param requestCode
 */
fun Fragment.requestBackgroundLocationPermissions(requestCode: Int) {
    val arrayOfPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    } else {
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    requestPermissions(arrayOfPermissions, requestCode)
}

/**
 * Check for fine (GPS) and background location (only if Android is 10 or newer)
 * @return true if permissions are granted else false
 */
fun Context.checkBackgroundLocationPermissions(): Boolean {
    val accessFineLocation = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    var accessBackgroundLocation = true
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        accessBackgroundLocation = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    return accessFineLocation && accessBackgroundLocation
}

/**
 * Check if application can show dialog with permissions requests
 * @return true if requests can be show
 */
fun Activity.shouldShowBackgroundLocationRequest(): Boolean {
    val accessFineLocation = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
    )
    var accessBackgroundLocation = true
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        accessBackgroundLocation = ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    }
    return accessFineLocation && accessBackgroundLocation
}

fun Fragment.requestAllPermissions(requestCode: Int, permissions: Array<String>) {
    this.requestPermissions(permissions, requestCode)
}