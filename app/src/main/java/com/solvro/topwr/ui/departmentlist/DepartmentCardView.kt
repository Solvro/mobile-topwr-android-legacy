package com.solvro.topwr.ui.departmentlist

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.solvro.topwr.R
import com.solvro.topwr.databinding.ItemDepartmentBinding

class DepartmentCardView(
    context: Context, attrs: AttributeSet
) : FrameLayout(context, attrs) {

    private var binding: ItemDepartmentBinding =
        ItemDepartmentBinding.inflate(LayoutInflater.from(context))

    private var logoImageView = binding.departmentLogo
    private var departmentAcronymTextView: TextView = binding.acronymTextView
    private var departmentNameTextView: TextView = binding.departmentNameTextView

    var acronym = R.string.empty
        set(value) {
            field = value
            departmentAcronymTextView.setText(value)
            invalidate()
        }
    var name = R.string.empty
        set(value) {
            field = value
            departmentNameTextView.setText(value)
        }

    var logoImg = R.drawable.ic_w1_bg
        set(value) {
            field = value
            ContextCompat.getDrawable(
                context,
                value
            )?.let {
                logoImageView.setImageDrawable(it)
                invalidate()
            }
        }

    var gradientStart = R.color.white
        set(value) {
            field = value
            binding.departmentCardView.setCardBackgroundColor(value)
            invalidate()
        }
    var gradientEnd = R.color.white
        set(value) {
            field = value
            binding.departmentCardView.setCardBackgroundColor(value)
            invalidate()
        }

    init {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.DepartmentCardView,
            0, 0
        ).apply {
            try {
                logoImg = getResourceId(
                    R.styleable.DepartmentCardView_logo,
                    logoImg
                )

                acronym = getResourceId(
                    R.styleable.DepartmentCardView_departmentAcronym,
                    acronym
                )

                name = getResourceId(
                    R.styleable.DepartmentCardView_departmentName,
                    name
                )

                gradientStart = getResourceId(
                    R.styleable.DepartmentCardView_gradientStart,
                    gradientStart
                )

            } finally {
                recycle()
            }
        }
    }
}