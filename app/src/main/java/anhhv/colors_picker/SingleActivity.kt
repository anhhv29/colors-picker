package anhhv.colors_picker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import anhhv.colors_picker.databinding.ActivitySingleBinding

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
    }
}
