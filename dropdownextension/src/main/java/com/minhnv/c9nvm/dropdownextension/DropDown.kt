package com.minhnv.c9nvm.dropdownextension

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.core.widget.PopupWindowCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class DropDown @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), OnSelectedChangeListeners {
    private var popupWindow: PopupWindow = PopupWindow()
    private lateinit var dropdownView: View
    private val textView: TextView = TextView(context)
    private val imageView = ImageView(context)

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.DropDown, 0, 0).apply {
            try {
                textView.hint = getString(R.styleable.DropDown_setHint) ?: ""
                textView.text = getString(R.styleable.DropDown_setTextDropDown) ?: ""
                imageView.setImageDrawable(getDrawable(R.styleable.DropDown_setIconArrow))
                background = getDrawable(R.styleable.DropDown_setBackground)
                textView.setTextColor(getColor(R.styleable.DropDown_setTextColor, Color.BLACK))
                textView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    getDimension(R.styleable.DropDown_setTextSize, 10f)
                )
            } finally {
                recycle()
            }
        }
        this.orientation = HORIZONTAL
        background = ContextCompat.getDrawable(context, R.drawable.bg_dropdown)
        textView.apply {
            hint = "hint"
            includeFontPadding = false
            setTextColor(Color.BLACK)
            setPadding(context.convertDpToPixel(10f))
            setTextSize(TypedValue.COMPLEX_UNIT_PX, 50f)
            layoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT, 9f)
            gravity = Gravity.CENTER_VERTICAL
        }

        imageView.apply {
            setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            layoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
        }
        this.addView(textView)
        this.addView(imageView)
        initClick()
        invalidate()
        requestLayout()
    }

    private fun initClick() {
        this.setOnClickListener {
            popupWindow.width = width
            PopupWindowCompat.showAsDropDown(popupWindow, this, 0, 0, Gravity.CENTER)
        }
    }

    private var limitItem = 5
    private var backgroundOrColor = ContextCompat.getColor(context, R.color.ltgray)

    fun <T : DropDownTemplate> setListData(list: MutableList<T>) {
        val count = if (list.size < limitItem) {
            list.size
        } else {
            limitItem
        }
        dropdownView = View.inflate(context, R.layout.popup_dropdown, null)
        popupWindow = PopupWindow(
            dropdownView,
            width,
            this.context.resources.displayMetrics.densityDpi * count * (10 * limitItem) / 200,
            true
        )
        val dropDownAdapter = DropDownAdapter(backgroundOrColor, this, list)
        val rycDropDown: RecyclerView = dropdownView.findViewById(R.id.rycDropdown)
        rycDropDown.layoutManager = LinearLayoutManager(context)
        rycDropDown.setHasFixedSize(true)
        rycDropDown.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        rycDropDown.adapter = dropDownAdapter
        dropdownView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
    }

    private fun Context.convertDpToPixel(dp: Float): Int {
        return (dp * (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    private var listener: OnSelectedListener? = null

    interface OnSelectedListener {
        fun onSelected(text: String, position: Int)
    }

    fun addOnSelectedChangeListener(listener: OnSelectedListener) {
        this.listener = listener
    }

    override fun changeText(value: String, position: Int) {
        textView.text = value
        listener?.onSelected(value, position)
        popupWindow.dismiss()
    }

    //function setting
    fun setLimitedItem(limit: Int) {
        limitItem = limit
    }

    val text get() = textView.text.toString()

    /**
     * change color item selected and need call before [setListData]
     * */
    fun setBackgroundItemSelected(@ColorRes backgroundOrColor: Int) {
        this.backgroundOrColor = backgroundOrColor
    }

    fun removeHint() {
        textView.hint = ""
    }
}