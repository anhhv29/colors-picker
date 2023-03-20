package anhhv.colors_picker

import android.app.Activity
import android.graphics.drawable.ColorDrawable
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
import com.google.android.material.button.MaterialButton
import de.hdodenhof.circleimageview.CircleImageView


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

    binding.btnConfirm.setOnClickListener {
        bottomSheetDialog.hide()
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

    binding.colorPickerLightness.visibility = View.GONE
    binding.colorPickerAlpha.visibility = View.GONE
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

    binding.btnConfirm.setOnClickListener {
        bottomSheetDialog.hide()
    }

    group.setColor(IntegerHSLColor().apply { setFromColorInt(color) })
    bottomSheetDialog.behavior.maxWidth = window.decorView.width
    bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    bottomSheetDialog.show()
}

fun MaterialButton.setBackgroundAndContrastColors(@ColorInt color: Int) {
    setBackgroundColor(color)
//    setTextColor(
//        IntegerHSLColor().apply {
//            setFromColorInt(color)
//        }.toContrastColor(ContrastColorAlphaMode.LIGHT_BACKGROUND)
//    )
}

fun CircleImageView.setBackgroundAndContrastColors(@ColorInt color: Int) {
    val cd = ColorDrawable(color)
    setImageDrawable(cd)
}

fun randomHSLColor(pure: Boolean = false) = IntegerHSLColor.createRandomColor(pure)