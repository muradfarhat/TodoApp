package customview.horizontalprogress

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ScaleDrawable
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.core.content.res.ResourcesCompat
import com.example.todoapp.R

/**
 * RoundedHorizontalProgressBar - An Android custom rounded Progress Bar that supports different Colors as progress
 */
class RoundedHorizontalProgressBar : ProgressBar {
    private var mBackgroundColor = Color.GRAY
    private var mProgressColor = Color.BLUE
    private var mIsRounded = true

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init() {
        val layerDrawable: LayerDrawable = if (mIsRounded) {
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.rounded_progress_bar_horizontal,
                null
            ) as LayerDrawable
        } else {
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.progress_bar_horizontal,
                null
            ) as LayerDrawable
        }
        progressDrawable = layerDrawable
        setProgressColors(mBackgroundColor, mProgressColor)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        setUpStyleable(context, attrs)
        val layerDrawable: LayerDrawable = if (mIsRounded) {
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.rounded_progress_bar_horizontal,
                null
            ) as LayerDrawable
        } else {
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.progress_bar_horizontal,
                null
            ) as LayerDrawable
        }
        progressDrawable = layerDrawable

        setProgressColors(mBackgroundColor, mProgressColor)
    }

    private fun setUpStyleable(context: Context, attrs: AttributeSet) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.RoundedHorizontalProgress)

        mBackgroundColor =
            typedArray.getColor(R.styleable.RoundedHorizontalProgress_backgroundColor, Color.GRAY)
        mProgressColor =
            typedArray.getColor(R.styleable.RoundedHorizontalProgress_progressColor, Color.BLUE)
        mIsRounded = typedArray.getBoolean(R.styleable.RoundedHorizontalProgress_isRounded, true)

        typedArray.recycle()
    }

    fun setProgressColors(backgroundColor: Int, progressColor: Int) {
        val layerDrawable: LayerDrawable = progressDrawable as LayerDrawable
//        val gradientDrawable: GradientDrawable =
//            layerDrawable.findDrawableByLayerId(R.id.background) as GradientDrawable
//        gradientDrawable.setColor(backgroundColor)

        if (mIsRounded) {
            val scaleDrawable: ScaleDrawable =
                layerDrawable.findDrawableByLayerId(R.id.progress) as ScaleDrawable
            val progressGradientDrawable: GradientDrawable =
                scaleDrawable.getDrawable() as GradientDrawable
            progressGradientDrawable.setColor(progressColor)
            progressDrawable = layerDrawable
        } else {
            val progressDrawable: ClipDrawable =
                layerDrawable.findDrawableByLayerId(R.id.progress) as ClipDrawable
            progressDrawable.setColorFilter(progressColor, PorterDuff.Mode.MULTIPLY)
            setProgressDrawable(layerDrawable)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }


    /**
     * Method to animate the progress bar from old value to new Value
     * @param animationDuration Duration of the animation
     * @param from Old value of Progress
     * @param to New value of Progress
     */
    fun animateProgress(animationDuration: Int, from: Int, to: Int) {
        val progressBarAnimation = ProgressBarAnimation(this, from.toFloat(), to.toFloat())
        progressBarAnimation.duration = animationDuration.toLong()
        startAnimation(progressBarAnimation)
    }

    /**
     * Method to animate the progress bar from old value to new Value
     * @param animationDuration Duration of the animation
     * @param to New value of Progress
     */
    fun animateProgress(animationDuration: Int, to: Int) {
        val progressBarAnimation = ProgressBarAnimation(this, progress.toFloat(), to.toFloat())
        progressBarAnimation.duration = animationDuration.toLong()
        startAnimation(progressBarAnimation)
    }
}
