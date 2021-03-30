package com.minhnv.c9nvm.dropdownextension

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
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
        this.addView(textView)
        this.addView(imageView)
        context.theme.obtainStyledAttributes(attrs, R.styleable.DropDown, 0, 0).apply {
            try {
                textView.hint = getString(R.styleable.DropDown_setHint) ?: "hint"
                textView.text = getString(R.styleable.DropDown_setTextDropDown) ?: ""
                imageView.setImageResource(getResourceId(R.styleable.DropDown_setIconArrow, R.drawable.ic_baseline_keyboard_arrow_down_24))
                background = getDrawable(R.styleable.DropDown_setBackground)
                textView.setTextColor(getColor(R.styleable.DropDown_setTextColor, Color.BLACK))
                textView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    getDimension(R.styleable.DropDown_setTextSize, 50f)
                )
            } finally {
                recycle()
            }
        }
        this.orientation = HORIZONTAL
        background = ContextCompat.getDrawable(context, R.drawable.bg_dropdown)
        textView.apply {
            includeFontPadding = false
            setPadding(context.convertDpToPixel(10f))
            layoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT, 9f)
            gravity = Gravity.CENTER_VERTICAL
        }
        imageView.apply {
            layoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
        }
        initClick()
        invalidate()
        requestLayout()
    }

    private fun initClick() {
        this.setOnClickListener {
            popupWindow.width = this.width
            PopupWindowCompat.showAsDropDown(popupWindow, this, 0, 0, Gravity.CENTER)
        }
    }

    private var limitItem = 5
    private var heightPopUp : Int = 200
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
            this.context.resources.displayMetrics.densityDpi * count * (10 * limitItem) / heightPopUp,
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
        when (limit) {
            5 -> this.heightPopUp = 200
            6 -> this.heightPopUp = 240
            7 -> this.heightPopUp = 280
            8 -> this.heightPopUp = 320
            9 -> this.heightPopUp = 360
            10 -> this.heightPopUp = 400
            else -> throw Exception("limit need range in 5..10")
        }
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

    fun setHint(hint: String) {
        textView.hint = hint
    }
}