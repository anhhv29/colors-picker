package anhhv.colors_picker

import android.app.Activity
import android.view.View
import androidx.annotation.ColorInt
import anhhv.colors_picker.databinding.LayoutColorPickerBinding
import codes.side.andcolorpicker.converter.setFromColorInt
import codes.side.andcolorpicker.converter.toColorInt
import codes.side.andcolorpicker.group.PickerGroup
import codes.side.andcolorpicker.group.registerPickers
import codes.side.andcolorpicker.model.IntegerHSLColor
import codes.side.andcolorpicker.view.picker.ColorSeekBar
import codes.side.andcolorpicker.view.picker.OnIntegerHSLColorPickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Activity.showColorPickerBottomSheet(
    @ColorInt color: Int,
    onColorChanged: (color: Int) -> Unit
) {
    val binding = LayoutColorPickerBinding.inflate(layoutInflater)

    val bottomSheetDialog = BottomSheetDialog(this)
    bottomSheetDialog.setContentView(binding.root)

    val group = PickerGroup<IntegerHSLColor>().also {
        it.registerPickers(
            binding.colorPickerHueSeekBar,
            binding.colorPickerSaturationSeekBar,
            binding.colorPickerLightnessSeekBar,
            binding.colorPickerAlphaSeekBar
        )
    }
    group.addListener(
        object : OnIntegerHSLColorPickListener() {
            override fun onColorChanged(
                picker: ColorSeekBar<IntegerHSLColor>,
                color: IntegerHSLColor,
                value: Int
            ) {
                super.onColorChanged(picker, color, value)
                onColorChanged(color.toColorInt())
            }
        }
    )
    group.setColor(IntegerHSLColor().apply { setFromColorInt(color) })

    binding.colorPickerButtonRandomColor.setOnClickListener {
        group.setColor(randomHSLColor())
    }
    bottomSheetDialog.behavior.maxWidth = window.decorView.width
    bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    bottomSheetDialog.show()

}


fun Activity.showSingleColorPickerBottomSheet(
    @ColorInt color: Int,
    onColorChanged: (color: Int) -> Unit
) {
    val binding = LayoutColorPickerBinding.inflate(layoutInflater)

    val bottomSheetDialog = BottomSheetDialog(this)
    bottomSheetDialog.setContentView(binding.root)

    binding.colorPickerSaturation.visibility = View.GONE
    binding.colorPickerLightness.visibility = View.GONE
    binding.colorPickerButtonRandomColor.visibility = View.GONE
    val group = PickerGroup<IntegerHSLColor>().also {
        it.registerPickers(
            binding.colorPickerHueSeekBar,
            binding.colorPickerSaturationSeekBar,
            binding.colorPickerLightnessSeekBar,
            binding.colorPickerAlphaSeekBar
        )
    }
    group.addListener(
        object : OnIntegerHSLColorPickListener() {
            override fun onColorChanged(
                picker: ColorSeekBar<IntegerHSLColor>,
                color: IntegerHSLColor,
                value: Int
            ) {
                super.onColorChanged(picker, color, value)
                onColorChanged(color.toColorInt())
            }
        }
    )
    group.setColor(IntegerHSLColor().apply { setFromColorInt(color) })
    bottomSheetDialog.behavior.maxWidth = window.decorView.width
    bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    bottomSheetDialog.show()
}

fun randomHSLColor(pure: Boolean = false) = IntegerHSLColor.createRandomColor(pure)