package com.kotlindemo.main

import android.annotation.SuppressLint
import android.app.*
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.gson.Gson
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import com.kotlindemo.api.RetroClient
import com.kotlindemo.common.AlarmReceiver
import com.kotlindemo.common.ExampleJobService
import com.kotlindemo.common.MyWork
import com.kotlindemo.common.showToast
import com.kotlindemo.model.api.GetResponse
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = HomeActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        setContentView(R.layout.activity_home)
        initListnear()

        intent.let {
            val ids = it.extras?.getInt("ID")
            Log.e(TAG, "onCreate: ids = $ids")
        }

    }

    private fun showNotification(message: String) {
        val CHANNEL_ID = "channel_id"
        val intent = Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            this.putExtra("ID", 7768)
        }

        var pendingIntent: PendingIntent = PendingIntent.getActivities(this, 0, arrayOf(intent), 0)

        var builder =
            NotificationCompat.Builder(this, CHANNEL_ID).setSmallIcon(R.drawable.placeholder1)
                .setContentTitle(getString(R.string.notification)).setContentText(message)
                .setContentIntent(pendingIntent).setDefaults(Notification.DEFAULT_VIBRATE)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification)
            val descriptionText = getString(R.string.notification)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(this)) {
            val ID = 11
            notify(ID, builder.build())
        }
    }

    private fun initListnear() {
        btnImgSelection.setOnClickListener(this)
        btnRecycleView.setOnClickListener(this)
        btnTabLayout.setOnClickListener(this)
        btnNotification.setOnClickListener(this)
        btnPdf.setOnClickListener(this)
        btnPlayMusic.setOnClickListener(this)
        btnUpdateApp.setOnClickListener(this)
        btnFingerprintAuth.setOnClickListener(this)
        btnCustomView.setOnClickListener(this)
        btnCreateZip.setOnClickListener(this)
        btnCreateUnZip.setOnClickListener(this)
        btnJobScheduler.setOnClickListener(this)
        btnWorkmanager.setOnClickListener(this)
        btnAlarm.setOnClickListener(this)
        btnWordlClock.setOnClickListener(this)

        //set system mode
//       AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        //set switch base on dark mode
        switchDarkMode.isChecked =
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        switchDarkMode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    @SuppressLint("NewApi")
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnImgSelection -> {
                gotoActivity(ImageSelectionActivity::class.java)
            }
            R.id.btnRecycleView -> {
                gotoActivity(RecycleActivity::class.java)
            }
            R.id.btnTabLayout -> {
                gotoActivity(TabActivity::class.java)
            }
            R.id.btnNotification -> {
                showNotification("Kotlin demo app notification...")
            }
            R.id.btnPdf -> {
                if (initPermission()) showPdfDialog()
            }
            R.id.btnPlayMusic -> {
                gotoActivity(PlayMusicActivity::class.java)
            }
            R.id.btnUpdateApp -> {
                showToast(this, "App update successfully")
                //   updateApp()

            }
            R.id.btnFingerprintAuth -> {
                chackBiometric()
            }

            R.id.btnCustomView -> {
                gotoActivity(CustomActivity::class.java)
            }

            R.id.btnCreateZip -> {

                try {

//                val file = File(filesDir.absolutePath, "newDir");
//                if (!file.exists())
//                    file.mkdir()
//
//                showToast(this, "Create Files")
//                val txtFile = File(file, "textFile.txt")
//                if (!txtFile.exists())
//                    txtFile.createNewFile()
//
//                val txtFile3 = File(file, "textFile2.json")
//                if (!txtFile3.exists())
//                    txtFile3.createNewFile()
//
//                val dirFile = File(file, "testDir")
//                if (!dirFile.exists()) dirFile.mkdir()
//
//                val dirTxt = File(dirFile, "test.txt")
//                if (!dirTxt.exists()) dirTxt.createNewFile()

//                val path = this.getDatabasePath("word_database")
//                Log.e(TAG, "onClick: path => " + path)
//                val dbFile = File(path, "/")
//                val zipF = File(filesDir, "new.zip");
//                ZipManager.createZipfile(dbFile, zipF.absolutePath)
//                showToast(this, "zip Files at: " + zipF.absolutePath)

                    val db = getDatabasePath("word_database")
                    val dbShm = File(db.parent, "word_database-shm")
                    val dbWal = File(db.parent, "word_database-wal")

                    val dbRoot = File(Environment.getExternalStorageDirectory(), "aaDb")
//                dbRoot.mkdir()
//                val db2 = File(dbRoot, "word_database")
//                val dbShm2 = File(db2.parent, "word_database-shm")
//                val dbWal2 = File(db2.parent, "word_database-wal")
//                try {
//                    db.copyTo(db2,true)
//                    dbShm.copyTo(dbShm2,true)
//                    dbWal.copyTo(dbWal2,true)
//                    showToast(this, "save DB at: " + dbRoot.absolutePath)
//                } catch (e: Exception) {
//                    Log.e("SAVEDB", e.toString())
//                }

//                create zip
                    val zipDir = File(Environment.getExternalStorageDirectory(), "aaDbZip")
                    if (!zipDir.exists()) zipDir.mkdir()
                    val dbZip = File(zipDir, "backup.zip")
//              ZipManager.createZipfile(dbRoot, dbZip.absolutePath)

                    //encrypt zip
                    val file1: File = File(Environment.getExternalStorageDirectory(), "aaEncrypt")
                    if (!file1.exists()) file1.mkdir()
                    val file3: File = File(file1, "aaDoc_.zip")
//                ZipManager.encryptAndClose(FileInputStream(dbZip), FileOutputStream(file3))

                    //decrypt zip
                    val decrytZip = File(zipDir, "decrypt.zip")
//                ZipManager.decryptAndClose(FileInputStream(file3),FileOutputStream(decrytZip))
//                ZipManager.unzipFile(decrytZip,zipDir)

                    var list = zipDir.listFiles()
                    var db1: File? = null
                    var db2: File? = null
                    var db3: File? = null
                    for (f in list) {
                        Log.e(TAG, "onClick: files = " + f.absolutePath)
                        if (f.name.toString().endsWith("database")) {
                            db1 = f;
                        }
                        if (f.name.toString().endsWith("shm")) {
                            db2 = f;
                        }
                        if (f.name.toString().endsWith("wal")) {
                            db3 = f;
                        }
                    }
                    try {
                        db1?.copyTo(db, true)
                        db2?.copyTo(dbShm, true)
                        db3?.copyTo(dbWal, true)
                        showToast(this, "save DB at: " + dbRoot.absolutePath)
                    } catch (e: Exception) {
                        Log.e("SAVEDB", e.toString())
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "onClick: zip exe = " + e.message)
                }
            }
            R.id.btnCreateUnZip -> {

                try {

//                val unZip = File(filesDir.absolutePath, "unZip");
//                if (!unZip.exists()) unZip.mkdir()
//                val fileZip = File(filesDir.absolutePath, "new.zip");
//                if (fileZip.exists()) {
//                    ZipManager.unzipFile(fileZip, unZip)
//                    showToast(this, "unzip files at: " + unZip.absolutePath)
//                } else {
//                    showToast(this, "file not found")
//                }

                    // save file to drive
                    val file1 = File(Environment.getExternalStorageDirectory(), "aaEncrypt")
                    if (!file1.exists()) file1.mkdir()
                    val file3 = File(file1, "aaDoc_.zip")
                    val uriForFile: Uri =
                        FileProvider.getUriForFile(this, getString(R.string.fileProvider), file3)
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.putExtra("android.intent.extra.STREAM", uriForFile)
                    intent.setPackage("com.google.android.apps.docs")
                    intent.type = "application/zip"
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    val chooser = Intent.createChooser(intent, "Save backup")
                    startActivity(chooser)

                } catch (e: java.lang.Exception) {
                    Log.e(TAG, "onClick: unzip exe " + e.message)
                }

            }
            R.id.btnJobScheduler -> {
                val componentName = ComponentName(this, ExampleJobService::class.java)
                val info: JobInfo = JobInfo.Builder(123, componentName).setRequiresCharging(true)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED).setPersisted(true)
                    .setPeriodic(60 * 1000).build()
                val scheduler: JobScheduler =
                    getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
                val resultCode: Int = scheduler.schedule(info)
                if (resultCode == JobScheduler.RESULT_SUCCESS) {
                    Log.e(TAG, "Job scheduled")
                } else {
                    Log.e(TAG, "Job scheduling failed")
                }
            }
            R.id.btnWorkmanager -> {
                //one time wokr
                val workmanager = WorkManager.getInstance(application)
//                workmanager.enqueue(OneTimeWorkRequest.from(MyWork::class.java))

                //periodic work
                val repeatWork = PeriodicWorkRequestBuilder<MyWork>(1, TimeUnit.MINUTES).build()
                workmanager.enqueue(repeatWork)
            }
            R.id.btnAlarm -> {
                setAlarmToApp()
            }
            R.id.btnWordlClock -> {
                callWsGettime()
            }
        }
    }

    fun callWsGettime() {
        val apiService = RetroClient.apiService
        val call = apiService.getCurrentDate

        call.enqueue(object : Callback<GetResponse> {
            override fun onResponse(call: Call<GetResponse>, response: Response<GetResponse>) {
                Log.e(TAG, "onResponse: " + Gson().toJson(response.body()))
                if (response.isSuccessful) {
                    onResponseSuccess(response.body()?.getCurrentDateTime())
                }
            }

            override fun onFailure(call: Call<GetResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: error - " + t.message)
                showToast(this@HomeActivity, "Error: " + t.message.toString())
            }
        })

    }

    private fun onResponseSuccess(response: String?) {
        Log.e(TAG, "onResponseSuccess: " + response)
        //        2020-10-09T10:15Z = utc
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ")
        var date: Date? = null
        try {
            date = df.parse(response)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val formatter: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm z")
        df.timeZone = TimeZone.getTimeZone("Asia/Kolkata") // Or whatever IST is supposed to be
        if (date != null) {
            val ist = formatter.format(date)
            Log.e(TAG, "onResponseSuccess: ist = $ist")
            showToast(this, "DateTime : $ist")
        }

    }

    private fun setAlarmToApp() {
        Log.e(TAG, "setAlarmToApp: ")
        val alarmMgr = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, 20)
        val date = SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").format(calendar.time)
        showToast(this, "Set Alarm at : " + date)
        Log.e(TAG, "setAlarmToApp: at : " + date)
        val delayInMill = calendar.timeInMillis


        if (Build.VERSION.SDK_INT >= 23) {
            alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, delayInMill, alarmIntent)
        } else {
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, delayInMill, alarmIntent)
        }

    }

//    //zip file
//    fun zip(_files: Array<String>, zipFileName: String?) {
//        try {
//            val BUFFER = 6*1024
//            var origin: BufferedInputStream? = null
//            val dest = FileOutputStream(zipFileName)
//            val out = ZipOutputStream(
//                BufferedOutputStream(
//                    dest
//                )
//            )
//            val data = ByteArray(BUFFER)
//            for (i in _files.indices) {
//                Log.v("Compress", "Adding: " + _files[i])
//                val fi = FileInputStream(_files[i])
//                origin = BufferedInputStream(fi, BUFFER)
//                val entry = ZipEntry(_files[i].substring(_files[i].lastIndexOf("/") + 1))
//                out.putNextEntry(entry)
//                var count: Int
//                while (origin.read(data, 0, BUFFER).also { count = it } != -1) {
//                    out.write(data, 0, count)
//                }
//                origin.close()
//            }
//            out.close()
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    fun unzip(_zipFile: String?, _targetLocation: String) {
//
//        //create target location folder if not exist
//        dirChecker(_targetLocatioan)
//        try {
//            val fin = FileInputStream(_zipFile)
//            val zin = ZipInputStream(fin)
//            var ze: ZipEntry? = null
//            while (zin.getNextEntry().also({ ze = it }) != null) {
//
//                //create dir if required while unzipping
//                if (ze!!.isDirectory) {
//                    dirChecker(ze!!.name)
//                } else {
//                    val fout = FileOutputStream(_targetLocation + ze!!.name)
//                    var c: Int = zin.read()
//                    while (c != -1) {
//                        fout.write(c)
//                        c = zin.read()
//                    }
//                    zin.closeEntry()
//                    fout.close()
//                }
//            }
//            zin.close()
//        } catch (e: java.lang.Exception) {
//            println(e)
//        }
//    }

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    fun chackBiometric() {
        Log.e(TAG, "App can authenticate using biometrics.")
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.e(TAG, "App can authenticate using biometrics.")
                showFingerprintPrompt()
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.e(TAG, "No biometric features available on this device.")
                showToast(this, "No biometric features available")
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.e(TAG, "Biometric features are currently unavailable.")
                showToast(this, "Biometric features are currently unavailable.")
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Log.e(TAG, "Biometric not set to your phone")
                showToast(this, "Fingerprint not set yet!")
                // Prompts the user to create credentials that your app accepts.
//                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
//                    putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
//                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
//                }
//                startActivityForResult(enrollIntent, 110)
            }
        }
    }

    private fun showFingerprintPrompt() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
//                    Toast.makeText(applicationContext,
//                        "Authentication error: $errString", Toast.LENGTH_SHORT)
//                        .show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        applicationContext,
                        "Authentication succeeded!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle("App is Locked")
            .setSubtitle("Use fingerprint to unlock the app").setNegativeButtonText("USE PIN >")
            .build()

        // Prompt appears
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
        biometricPrompt.authenticate(promptInfo)
    }

    private fun updateApp() {
        Log.e(TAG, "updateApp: call")
        val logDir = File(Environment.getExternalStorageDirectory(), "update")
        val apkFile = File(logDir.absolutePath, "test.apk")

        if (!apkFile.exists()) {
            showToast(this, "File not found!")
            return
        }

        val data: Uri = FileProvider.getUriForFile(this, getString(R.string.fileProvider), apkFile)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(data, "application/vnd.android.package-archive")
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
        startActivity(intent)
    }

    private fun showPdfDialog() {
        val dialog: Dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_pdf)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.show()

        val btnCreatePdf = dialog.findViewById<Button>(R.id.btnCreatePdf)
        val btnViewPdf = dialog.findViewById<Button>(R.id.btnViewPdf)
        val etTitle = dialog.findViewById<EditText>(R.id.etTitle)
        val etDesc = dialog.findViewById<EditText>(R.id.etDesc)

        dialog.findViewById<ImageView>(R.id.imgClose).setOnClickListener {
            dialog.dismiss()
        }

        btnCreatePdf.setOnClickListener {
            val title = etTitle.text.toString()
            val desc = etDesc.text.toString()
            if (TextUtils.isEmpty(title)) {
                showToast(this, "Enter Title")
                return@setOnClickListener
            }
            createPDF(title, desc)
        }
        btnViewPdf.setOnClickListener {
//            val file: File = File(Environment.getExternalStorageDirectory() , "pdf/invoice.pdf")
            val file: File = File(filesDir.absoluteFile.toString() + "/invoice.pdf")
            Log.e(TAG, "showPdfDialog: file path = " + file.absolutePath)

            if (!file.exists()) {
                showToast(this, "File not found!")
                return@setOnClickListener
            }

            val intent = Intent(Intent.ACTION_VIEW)
            val data: Uri = FileProvider.getUriForFile(this, getString(R.string.fileProvider), file)
            intent.setDataAndType(data, "application/pdf")
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val chooser = Intent.createChooser(intent, "Open Pdf")
            try {
                startActivity(chooser)
            } catch (e: Exception) {
                showToast(this, "Error : ${e.message}")
                Log.e(TAG, "showPdfDialog: err : " + e.message)
            }
        }
    }

    private fun createPDF(title: String, desc: String) {

        /*
        *Ref link: https://medium.com/android-school/exploring-itext-to-create-pdf-in-android-5577881998c8
        * */

        //* Creating Document object
        val document = Document()

        // Location to save
        val file: File = File(filesDir.absoluteFile.toString() + "/invoice.pdf")
        //create new pdf file
        if (!file.exists()) file.createNewFile() else file.delete(); file.createNewFile()

        PdfWriter.getInstance(document, FileOutputStream(file))

        document.open() //Open to write

        //*Document Settings
        document.setPageSize(PageSize.A4);
        document.addCreationDate();
        document.addAuthor("Android App");
        document.addCreator("Admin");

        //*Setting up Fonts from Assets:
        //  Variables for further use....
        val mColorAccent = BaseColor(0, 153, 204, 255)
        val mHeadingFontSize = 20.0f

        //* How to USE FONT....
        val urName =
            BaseFont.createFont("assets/fonts/sofia_medium.otf", "UTF-8", BaseFont.EMBEDDED)

        //*Creating Line Separator:
        val lineSeparator = LineSeparator()
        lineSeparator.setLineColor(BaseColor(0, 0, 0, 68))

        // * Adding Heading:

        // Title Order Details...,  Adding Title
        val mOrderDetailsTitleFont = Font(urName, 28.0f, Font.NORMAL, mColorAccent)
        // Creating Chunk
        val mOrderDetailsTitleChunk = Chunk(title, mOrderDetailsTitleFont)
        // Creating Paragraph to add
        val mOrderDetailsTitleParagraph = Paragraph(mOrderDetailsTitleChunk)
        // Setting Alignment for Heading
        mOrderDetailsTitleParagraph.alignment = Element.ALIGN_CENTER
        // Finally Adding that Chunk
        document.add(mOrderDetailsTitleParagraph)

        document.add(Chunk(lineSeparator));

        //* Adding Title - Value pair:
//        val mOrderIdFont = Font(urName, mHeadingFontSize, Font.NORMAL, BaseColor.BLACK)
//        val mOrderIdChunk = Chunk("Order No: 8707", mOrderIdFont)
//        val mOrderIdParagraph = Paragraph(mOrderIdChunk)
//        document.add(mOrderIdParagraph)

//        Break line or add Line Separator:
        document.add(Paragraph(""));
        document.add(Paragraph(desc));

//        document.add(Paragraph("Amount: 5100 INR"));
//        document.add(Paragraph("GST: 200 INR"));

//        document.add(Chunk(lineSeparator));

        //Add table
//        val table = PdfPTable(6)
//        for (i in 1..6) {
//            table.addCell(" Cell-1,$i ")
//        }
//        document.add(table)

//      Finally closing document:
        document.close();
        showToast(this, "Pdf generated success!")

//        val newFile: File = File(Environment.getExternalStorageDirectory() , "pdf")
//        copyFile(file.toString(),newFile.absolutePath,"invoice.pdf")
    }

    private fun gotoActivity(java: Class<*>) {
        startActivity(Intent(this, java))
    }

    val CODE_PERMISSION = 1
    private fun initPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true
            } else {
                val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, CODE_PERMISSION)
                return false
            }
        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CODE_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showPdfDialog()
                } else {
                    showToast(this, "Permission denied")
                }
            }
        }
    }
}


