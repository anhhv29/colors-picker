package anhhv.colors_picker

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import anhhv.colors_picker.databinding.ActivitySingleBinding
import java.io.File
import java.io.FileOutputStream
import java.util.Random

@Suppress("DEPRECATION")
class SingleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleBinding

    private val colors by lazy {
        resources.getIntArray(R.array.default_single)
    }

    private val PERMISSION_REQUEST_CODE = 1

    private var sharedPreference: SharedPreferences? = null
    private var longScreen = false
    private var portScreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createFolder(this)

//        checkScreen()

        binding = ActivitySingleBinding.inflate(layoutInflater)

        sharedPreference = getSharedPreferences("Color", Context.MODE_PRIVATE)!!

        setContentView(binding.root)

        setupUi()
    }

//    private fun checkScreen() {
//        val orientation = resources.configuration.orientation
//        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//            portScreen = true
//            return
//        } else {
//            val displayMetrics = DisplayMetrics()
//            windowManager.defaultDisplay.getMetrics(displayMetrics)
//            val height = displayMetrics.heightPixels
//            val width = displayMetrics.widthPixels
//
//            if ((width / height * 1f) >= 2.0) {
//                longScreen = true
//                return
//            }
//        }
//    }

    private fun setupUi() {
        binding.colorPickerButtonRandomColor.setOnClickListener {
            val rnd = Random()
            val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            val color1: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            val color2: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            val color3: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            val colors: IntArray = intArrayOf(color, color1, color2, color3)
            binding.defaultQuatroGradeView.setColors(colors)
        }

        binding.btnChange.setOnClickListener {
            showSingleColorPickerBottomSheet(colors[0]) { color ->
                colors[0] = color
                colors[1] = color
                colors[2] = color
                colors[3] = color
                binding.defaultQuatroGradeView.setColors(colors)
            }
        }

        binding.defaultQuatroGradeView.setColors(colors)

        binding.btnColor.setOnClickListener(View.OnClickListener {

            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermission()) {
                    setWallpaper()
                } else {
                    requestPermission()
                }
            } else {
                setWallpaper()
            }
        })
    }

    private fun setWallpaper() {
        val v1: View = binding.defaultQuatroGradeView
        v1.isDrawingCacheEnabled = true
        val bitmap: Bitmap = Bitmap.createBitmap(v1.drawingCache)
        v1.isDrawingCacheEnabled = false

        val wallpaperManager = WallpaperManager.getInstance(applicationContext)
        val out = FileOutputStream("${commonDocumentDirPath(this)}/single.png")
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        out.close()
        val pngFile = File("${commonDocumentDirPath(this)}/single.png")
        wallpaperManager.setBitmap(BitmapFactory.decodeFile(pngFile.absolutePath))
        WallpaperManager.getInstance(applicationContext).setBitmap(bitmap)
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun checkPermission(): Boolean {
        val result: Int = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                this,
                "Vui lòng cho phép quyền bộ nhớ ngoài trong Cài đặt ứng dụng",
                Toast.LENGTH_LONG
            ).show()
            alertDialog()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun alertDialog() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Thông báo")
        alertDialog.setMessage("Để sử dụng tính năng vui lòng cấp quyền Bộ nhớ trong")

        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "Cài đặt",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                startActivity(Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", packageName, null)
                })
            })

        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, "Đóng",
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        alertDialog.show()
    }

    private fun createFolder(context: Context): Boolean {
        val folder = commonDocumentDirPath(context)
        return if (!folder.exists()) {
            folder.mkdir()
        } else false
    }

    private fun commonDocumentDirPath(context: Context): File {
        val dir: File = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            File(
                context.getExternalFilesDir(null).toString() + "/.GalleryColor"
            )
        } else {
            File(
                Environment.getExternalStorageDirectory().absolutePath + "/.GalleryColor"
            )
        }
        return dir
    }
}
