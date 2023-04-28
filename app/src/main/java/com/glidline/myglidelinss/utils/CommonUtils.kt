package com.glidline.myglidelinss.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.app.ProgressDialog
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.database.Cursor
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.loader.content.CursorLoader
import androidx.test.core.app.ApplicationProvider
import com.glidline.myglidelinss.R
import java.io.File


fun isEmailValid(edit: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(edit).matches()

}

fun isPhoneValid(edit: EditText): Boolean {
    return Patterns.PHONE.matcher(edit.text).matches()

}


fun isIntenet(context: Activity?): Boolean {

    val connectivityManager =
        context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    return (connectivityManager!!.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED ||
            connectivityManager!!.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED
            )

}

fun showProgressBar(activity: Activity?) {

    App.progressDialog = ProgressDialog(activity)
    App.progressDialog.setTitle(activity?.resources?.getString(R.string.loading))
    App.progressDialog.setMessage(activity?.resources?.getString(R.string.please_wait))
    App.progressDialog.show()


}

fun showProgressBarClose() {

    App.progressDialog.dismiss()


}



fun getCurrentDateTime(): String {

    return System.currentTimeMillis().toString()
}

@SuppressLint("Range")
fun downloadByDownloadManager(context: Context, title:String, url: String?, outputFileName: String?): String? {

    val request = DownloadManager.Request(Uri.parse(url))
//    request.setDescription("A zip package with some files")
    request.setTitle("$title Document")
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.allowScanningByMediaScanner()
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, outputFileName)

    Log.d(
        "download folder>>>>",
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
    )
//    Handler(Looper.getMainLooper()).postDelayed({
//        UnzipFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + "/" + outputFileName)
//
//    }, 5000)
    // get download service and enqueue file

    val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
    manager!!.enqueue(request)
  var  downloadID = manager.enqueue(request);// enqueue puts the download request in the queue.


    var finishDownload = false
    var progress: Int
    while (!finishDownload) {
        val cursor: Cursor =
            manager.query(DownloadManager.Query().setFilterById(downloadID))
        if (cursor.moveToFirst()) {
            val status: Int = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            when (status) {
                DownloadManager.STATUS_FAILED -> {
                    finishDownload = true
                }
                DownloadManager.STATUS_PAUSED -> {}
                DownloadManager.STATUS_PENDING -> {}
                DownloadManager.STATUS_RUNNING -> {
                    val total: Long =
                        cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                    if (total >= 0) {
                        val downloaded: Long =
                            cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                        progress = (downloaded * 100L / total).toInt()
                        // if you use downloadmanger in async task, here you can use like this to display progress.
                        // Don't forget to do the division in long to get more digits rather than double.
                        //  publishProgress((int) ((downloaded * 100L) / total));
                    }
                }
                DownloadManager.STATUS_SUCCESSFUL -> {
                    progress = 100
                    // if you use aysnc task
                    // publishProgress(100);
                    finishDownload = true
                    Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT)
                        .show()
//                    opendialogWeb(url.toString(),context,title)

                }
            }
        }
    }
    return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + "/" + outputFileName
}


fun setProjectStatusColor(context: Context, it: TextView, leadStatusName: String) {
    if (leadStatusName == "In Quoting") {

        it.setTextColor(context.resources.getColor(R.color.red_bold))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.red_bold
                    )
                )
            )
        )
    }else if (leadStatusName == "Proposal Sent") {
        it.setTextColor(context.resources.getColor(R.color.yellowLight))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.yellowLight
                    )
                )
            )
        )
    }
    else if (leadStatusName == "Invoice Sent") {
        it.setTextColor(context.resources.getColor(R.color.yellow))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.yellow
                    )
                )
            )
        )
    }

    else if (leadStatusName == "Invoice Paid") {
        it.setTextColor(context.resources.getColor(R.color.darkGreen))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.darkGreen
                    )
                )
            )
        )
    }
    else if (leadStatusName == "In Survey") {
        it.setTextColor(context.resources.getColor(R.color.blue))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.blue
                    )
                )
            )
        )
    }

    else if (leadStatusName == "In Production") {
        it.setTextColor(context.resources.getColor(R.color.greenLight))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.greenLight
                    )
                )
            )
        )
    }



    else if (leadStatusName == "In Installation") {
        it.setTextColor(context.resources.getColor(R.color.skyblue))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.skyblue
                    )
                )
            )
        )
    }


    else if (leadStatusName == "In Review") {
        it.setTextColor(context.resources.getColor(R.color.yellowBold))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.yellowBold
                    )
                )
            )
        )
    }

    else if (leadStatusName == "Completed") {
        it.setTextColor(context.resources.getColor(R.color.greenBold))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.greenBold
                    )
                )
            )
        )
    }

    else if (leadStatusName == "Cancelled") {
        it.setTextColor(context.resources.getColor(R.color.red))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.red
                    )
                )
            )
        )
    }
}

fun setOrderStatusColor(context: Context, it: TextView, leadStatusName: String) {
  if (leadStatusName == "Initiated") {
        it.setTextColor(context.resources.getColor(R.color.yellow))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.yellow
                    )
                )
            )
        )
    }
    else if (leadStatusName == "In Review") {
        it.setTextColor(context.resources.getColor(R.color.yellowBold))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.yellowBold
                    )
                )
            )
        )
    }

    else if (leadStatusName == "In Progress") {
        it.setTextColor(context.resources.getColor(R.color.darkGreen))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.darkGreen
                    )
                )
            )
        )
    }
    else if (leadStatusName == "Completed") {
        it.setTextColor(context.resources.getColor(R.color.green))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.green
                    )
                )
            )
        )
    }


}

public fun requestPermission(requireContext: Activity):Boolean{
    var status =false
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R&&Build.VERSION.SDK_INT < Build.VERSION_CODES.S_V2) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        try {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.addCategory("android.intent.category.DEFAULT")
            intent.data = Uri.parse(
                String.format(
                    "package:%s",
                    ApplicationProvider.getApplicationContext<Context>().getPackageName()
                )
            )
            status= true
        } catch (e: Exception) {
            val intent = Intent()
            intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
            status= true
        }
    } else {
        //below android 11
        ActivityCompat.requestPermissions(
            requireContext,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            2296
        )

        if (ActivityCompat.checkSelfPermission(
                requireContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) !=
            PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                requireContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireContext, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
                ),
                1
            )
            status
        } else {

        }
    }
    return status
}

public fun checkPermission(requireContext: Activity): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Environment.isExternalStorageManager()
    } else {
        val result =
            ContextCompat.checkSelfPermission(
                requireContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        val result1 =
            ContextCompat.checkSelfPermission(
                requireContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        val result2 =
            ContextCompat.checkSelfPermission(
                requireContext,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            )
        result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    }
}
public fun uploadCheckPermission(requireContext: Activity): Boolean {
    var status =false
    if (!checkPermission(requireContext)
    ) {
        requestPermission(requireContext)
    } else {
        if (ActivityCompat.checkSelfPermission(
                requireContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) !=
            PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                requireContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requireContext.requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 1
            )
             status =false
        } else {
            var status =true

        }
    }
    return status
}


fun getRealPathFromUri(context: Context, fileUri: Uri): String? {
    // SDK >= 11 && SDK < 19
    return if (Build.VERSION.SDK_INT < 19) {
        getRealPathFromURIAPI11to18(context, fileUri)
    } else {
        getRealPathFromURIAPI19(context, fileUri)
    }// SDK > 19 (Android 4.4) and up
}


fun getRealPathFromURIAPI11to18(context: Context, contentUri: Uri): String? {
    val proj = arrayOf(MediaStore.Images.Media.DATA)
    var result: String? = null

    val cursorLoader = CursorLoader(context, contentUri, proj, null, null, null)
    val cursor = cursorLoader.loadInBackground()

    if (cursor != null) {
        val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        result = cursor.getString(columnIndex)
        cursor.close()
    }
    return result
}

/**
 * Get a file path from a Uri. This will get the the path for Storage Access
 * Framework Documents, as well as the _data field for the MediaStore and
 * other file-based ContentProviders.
 *
 * @param context The context.
 * @param uri     The Uri to query.
 * @author Niks
 */
fun getRealPathFromURIAPI19(context: Context, uri: Uri): String? {

    val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

    // DocumentProvider
    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
        // ExternalStorageProvider
        if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split =
                docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]

            if ("primary".equals(type, ignoreCase = true)) {
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            }
        } else if (isDownloadsDocument(uri)) {
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver.query(
                    uri,
                    arrayOf(MediaStore.MediaColumns.DISPLAY_NAME),
                    null,
                    null,
                    null
                )
                cursor!!.moveToNext()
                val fileName = cursor.getString(0)
                val path = Environment.getExternalStorageDirectory()
                    .toString() + "/Download/" + fileName
                if (!TextUtils.isEmpty(path)) {
                    return path
                }
            } finally {
                cursor?.close()
            }
            val id = DocumentsContract.getDocumentId(uri)
            if (id.startsWith("raw:")) {
                return id.replaceFirst("raw:".toRegex(), "")
            }
            val contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads"),
                java.lang.Long.valueOf(id)
            )

            return getDataColumn(context, contentUri, null, null)
        } else if (isMediaDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split =
                docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]

            var contentUri: Uri? = null
            when (type) {
                "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }

            val selection = "_id=?"
            val selectionArgs = arrayOf(split[1])

            return getDataColumn(context, contentUri, selection, selectionArgs)
        }// MediaProvider
        // DownloadsProvider
    } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {

        // Return the remote address
        return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
            context,
            uri,
            null,
            null
        )
    } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
        return uri.path
    }// File
    // MediaStore (and general)

    return null
}

/**
 * Get the value of the data column for this Uri. This is useful for
 * MediaStore Uris, and other file-based ContentProviders.
 *
 * @param context       The context.
 * @param uri           The Uri to query.
 * @param selection     (Optional) Filter used in the query.
 * @param selectionArgs (Optional) Selection arguments used in the query.
 * @return The value of the _data column, which is typically a file path.
 * @author Niks
 */
 fun getDataColumn(
    context: Context, uri: Uri?, selection: String?,
    selectionArgs: Array<String>?
): String? {

    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(column)

    try {
        cursor =
            context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
        if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(index)
        }
    } finally {
        cursor?.close()
    }
    return null
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is ExternalStorageProvider.
 */
 fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is DownloadsProvider.
 */
 fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is MediaProvider.
 */
 fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is Google Photos.
 */
 fun isGooglePhotosUri(uri: Uri): Boolean {
    return "com.google.android.apps.photos.content" == uri.authority
}


@SuppressLint("ResourceType")
fun setInvoicesStatusColor(context: Context, it: TextView, leadStatusName: String) {
    if (leadStatusName == "Invoice sent") {
        it.setTextColor(context.resources.getColor(R.color.yellow))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.yellow
                    )
                )
            )
        )
    }
    else if (leadStatusName == "Invoice paid") {
        it.setTextColor(context.resources.getColor(R.color.green))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.green
                    )
                )
            )
        )
    }

    else if (leadStatusName == "Cancelled") {
        it.setTextColor(context.resources.getColor(R.color.red))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.red
                    )
                )
            )
        )
    }



}

fun setSurvayStatusColor(context: Context, it: TextView, leadStatusName: String) {
    if (leadStatusName == "Assigned") {

        it.setTextColor(context.resources.getColor(R.color.greenBold))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.greenBold
                    )
                )
            )
        )
    }else if (leadStatusName == "In Process") {
        it.setTextColor(context.resources.getColor(R.color.yellow))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.yellow
                    )
                )
            )
        )
    }
    else if (leadStatusName == "Completed") {
        it.setTextColor(context.resources.getColor(R.color.green))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.green
                    )
                )
            )
        )
    }


}

public fun delteFile() {

    try {
        val fileDirectory = File(
            Environment.getExternalStorageDirectory()
                .toString() + "/Android/data/com.example.doorwindowapp/files/Download/"
        )

        val dirFiles: Array<File> = fileDirectory.listFiles()
        if (dirFiles.size != 0) {
            for (ii in dirFiles.indices) {

                dirFiles[ii].delete()

            }
        }

    } catch (e: Exception) {

    }


}
public  fun setInstallationStatusColor(context: Context, it: TextView, leadStatusName: String) {
    if (leadStatusName == "Assigned") {
        it.setTextColor(context.resources.getColor(R.color.yellow))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.yellow
                    )
                )
            )
        )
    }
    else if (leadStatusName == "In Progress") {
        it.setTextColor(context.resources.getColor(R.color.yellowBold))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.yellowBold
                    )
                )
            )
        )
    }

    else if (leadStatusName == "In Review") {
        it.setTextColor(context.resources.getColor(R.color.darkGreen))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.darkGreen
                    )
                )
            )
        )
    }
    else if (leadStatusName == "Completed") {
        it.setTextColor(context.resources.getColor(R.color.green))

        it.setCompoundDrawableTintList(
            ColorStateList.valueOf(
                Color.parseColor(
                    context.resources.getString(
                        R.color.green
                    )
                )
            )
        )
    }


}

