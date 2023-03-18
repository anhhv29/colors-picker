package anhhv.colors_picker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import anhhv.colors_picker.databinding.ActivityColorBinding

class ColorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityColorBinding

    private val colors by lazy {
        resources.getIntArray(R.array.default_colors)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityColorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupUi() {
        setupButton(binding.btnTopLeft, 0)
        setupButton(binding.btnTopRight, 1)
        setupButton(binding.btnBottomLeft, 2)
        setupButton(binding.btnBottomRight, 3)
        invalidateColors()
    }

    private fun setupButton(button: AppCompatButton, index: Int) {
        button.setOnClickListener {
            showColorPickerBottomSheet(colors[index]) { color ->
                colors[index] = color
                invalidateColors()
            }
        }
    }

    private fun invalidateColors() {
        binding.defaultQuatroGradeView.setColors(colors)

        binding.btnTopLeft.setBackgroundAndContrastColors(colors[0])
        binding.btnTopRight.setBackgroundAndContrastColors(colors[1])
        binding.btnBottomLeft.setBackgroundAndContrastColors(colors[2])
        binding.btnBottomRight.setBackgroundAndContrastColors(colors[3])
    }
}
