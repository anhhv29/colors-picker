package anhhv.colors_picker

import android.app.Activity
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import anhhv.colors_picker.databinding.ActivitySingleBinding
import java.io.File
import java.io.FileOutputStream
import java.util.*

@Suppress("DEPRECATION")
class SingleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleBinding

    private val colors by lazy {
        resources.getIntArray(R.array.default_single)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
    }

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
            val v1: View = binding.defaultQuatroGradeView
            v1.isDrawingCacheEnabled = true
            val bitmap: Bitmap = Bitmap.createBitmap(v1.drawingCache)
            v1.isDrawingCacheEnabled = false

            val wallpaperManager = WallpaperManager.getInstance(applicationContext)
            val out = FileOutputStream("/sdcard/mypicture")
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.close()
            val pngFile = File("/sdcard/mypicture")
            wallpaperManager.setBitmap(BitmapFactory.decodeFile(pngFile.absolutePath))
//            WallpaperManager.getInstance(applicationContext).setBitmap(bitmap)
            setResult(Activity.RESULT_OK)
            finish()
        })
    }
}
