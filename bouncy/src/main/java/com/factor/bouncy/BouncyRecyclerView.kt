package com.factor.bouncy

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.EdgeEffect
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.factor.bouncy.util.*


class BouncyRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs)
{
    private lateinit var callBack: DragDropCallBack

    var onOverPullListener: OnOverPullListener? = null

    var overscrollAnimationSize = 0.5f

    var flingAnimationSize = 0.5f

    @Suppress("MemberVisibilityCanBePrivate")
    var dampingRatio = SpringForce.DAMPING_RATIO_NO_BOUNCY
        set(value)
        {
            field = value
            this.spring.spring = SpringForce()
                .setFinalPosition(0f)
                .setDampingRatio(value)
                .setStiffness(stiffness)
        }

    @Suppress("MemberVisibilityCanBePrivate")
    var stiffness = SpringForce.STIFFNESS_LOW
        set(value)
        {
            field = value
            this.spring.spring = SpringForce()
                .setFinalPosition(0f)
                .setDampingRatio(dampingRatio)
                .setStiffness(value)
        }

    @Suppress("MemberVisibilityCanBePrivate")
    var longPressDragEnabled = false
        set(value)
        {
            field = value
            if (adapter is DragDropAdapter) callBack.setDragEnabled(value)
        }

    @Suppress("MemberVisibilityCanBePrivate")
    var itemSwipeEnabled = false
        set(value)
        {
            field = value
            if (adapter is DragDropAdapter) callBack.setSwipeEnabled(value)
        }

    val spring: SpringAnimation = SpringAnimation(this, SpringAnimation.TRANSLATION_Y)
        .setSpring(
            SpringForce()
                .setFinalPosition(0f)
                .setDampingRatio(dampingRatio)
                .setStiffness(stiffness)
        )


    override fun setAdapter(adapter: RecyclerView.Adapter<*>?)
    {
        super.setAdapter(adapter)

        if (adapter is DragDropAdapter)
        {
            callBack = DragDropCallBack(adapter, longPressDragEnabled, itemSwipeEnabled)
            val touchHelper = ItemTouchHelper(callBack)
            touchHelper.attachToRecyclerView(this)
        }
    }


    inline fun <reified T : ViewHolder> RecyclerView.forEachVisibleHolder(action: (T) -> Unit)
    {
        for (i in 0 until childCount) action(getChildViewHolder(getChildAt(i)) as T)
    }


    init {

        //read attributes
        context.theme.obtainStyledAttributes(attrs, R.styleable.BouncyRecyclerView, 0, 0)
            .apply{
                longPressDragEnabled = getBoolean(R.styleable.BouncyRecyclerView_allow_drag_reorder, false)
                itemSwipeEnabled = getBoolean(R.styleable.BouncyRecyclerView_allow_item_swipe, false)

                overscrollAnimationSize = getFloat(R.styleable.BouncyRecyclerView_recyclerview_overscroll_animation_size, 0.5f)
                flingAnimationSize = getFloat(R.styleable.BouncyRecyclerView_recyclerview_fling_animation_size, 0.5f)

                when (getInt(R.styleable.BouncyRecyclerView_recyclerview_damping_ratio, 0))
                {
                    0 -> dampingRatio = Bouncy.DAMPING_RATIO_NO_BOUNCY
                    1 -> dampingRatio = Bouncy.DAMPING_RATIO_LOW_BOUNCY
                    2 -> dampingRatio = Bouncy.DAMPING_RATIO_MEDIUM_BOUNCY
                    3 -> dampingRatio = Bouncy.DAMPING_RATIO_HIGH_BOUNCY
                }
                when (getInt(R.styleable.BouncyRecyclerView_recyclerview_stiffness, 1))
                {
                    0 -> stiffness = Bouncy.STIFFNESS_VERY_LOW
                    1 -> stiffness = Bouncy.STIFFNESS_LOW
                    2 -> stiffness = Bouncy.STIFFNESS_MEDIUM
                    3 -> stiffness = Bouncy.STIFFNESS_HIGH
                }
                recycle()
            }


        val rc = this

        //create edge effect
        this.edgeEffectFactory = object : RecyclerView.EdgeEffectFactory()
        {
            override fun createEdgeEffect(recyclerView: RecyclerView, direction: Int): EdgeEffect
            {
                return object : EdgeEffect(recyclerView.context)
                {
                    override fun onPull(deltaDistance: Float)
                    {
                        super.onPull(deltaDistance)
                        onPullAnimation(deltaDistance)
                    }

                    override fun onPull(deltaDistance: Float, displacement: Float)
                    {
                        super.onPull(deltaDistance, displacement)
                        onPullAnimation(deltaDistance)
                        if (direction == DIRECTION_BOTTOM)
                            onOverPullListener?.onOverPulledBottom(deltaDistance)
                        else if (direction == DIRECTION_TOP)
                            onOverPullListener?.onOverPulledTop(deltaDistance)
                    }

                    private fun onPullAnimation(deltaDistance: Float)
                    {

                        val delta: Float =
                            if (direction == DIRECTION_BOTTOM)
                                -1 * recyclerView.width * deltaDistance * overscrollAnimationSize
                            else
                                1 * recyclerView.width * deltaDistance * overscrollAnimationSize

                        spring.cancel()
                        rc.translationY += delta

                        forEachVisibleHolder{holder: ViewHolder? -> if (holder is BouncyViewHolder)holder.onPulled(deltaDistance)}
                    }

                    override fun onRelease()
                    {
                        super.onRelease()
                        onOverPullListener?.onRelease()
                        spring.start()

                        forEachVisibleHolder{holder: ViewHolder? -> if (holder is BouncyViewHolder)holder.onRelease()}
                    }

                    override fun onAbsorb(velocity: Int)
                    {
                        super.onAbsorb(velocity)

                        val v: Float = if (direction == DIRECTION_BOTTOM)
                            -1 * velocity * flingAnimationSize
                        else
                            1 * velocity * flingAnimationSize


                        spring.setStartVelocity(v).start()

                        forEachVisibleHolder{holder: ViewHolder? -> if (holder is BouncyViewHolder)holder.onAbsorb(velocity)}
                    }

                    override fun draw(canvas: Canvas?): Boolean
                    {
                        setSize(0, 0)
                        return super.draw(canvas)
                    }
                }
            }
        }
    }

    abstract class Adapter: RecyclerView.Adapter<ViewHolder>(), DragDropAdapter
}
