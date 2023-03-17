package anhhv.colors_picker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import anhhv.colors_picker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUi()
    }

    private fun setupUi() {
        binding.btnSingle.setOnClickListener {
            startActivity(Intent(this, SingleActivity::class.java))
        }
        binding.btnColor.setOnClickListener {
            startActivity(Intent(this, ColorActivity::class.java))
        }
    }
}
