package dev.sunnat629.autoimageslide.adapters

import android.content.Context
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import dev.sunnat629.autoimageslide.R
import dev.sunnat629.autoimageslide.constants.ActionTypes
import dev.sunnat629.autoimageslide.constants.ScaleTypes
import dev.sunnat629.autoimageslide.interfaces.ItemClickListener
import dev.sunnat629.autoimageslide.interfaces.TouchListener
import dev.sunnat629.autoimageslide.models.SlideModel

/**
 * @author  S M Mohi Us Sunnat
 * @date    10/5/21
 * Copyright ©2021. All rights reserved.
 */

class ViewPagerAdapter(
    private val context: Context?,
    private val imageList: List<SlideModel>,
    private val radius: Int,
    private val errorImage: Int,
    private val placeholder: Int,
    private val titleBackground: Int,
    private val scaleType: ScaleTypes?,
    private val textAlign: String
) : PagerAdapter() {

    constructor(
        context: Context,
        imageList: List<SlideModel>,
        radius: Int,
        errorImage: Int,
        placeholder: Int,
        titleBackground: Int,
        textAlign: String
    ) :
            this(
                context,
                imageList,
                radius,
                errorImage,
                placeholder,
                titleBackground,
                null,
                textAlign
            )

    private var layoutInflater: LayoutInflater? =
        context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?

    private var itemClickListener: ItemClickListener? = null
    private var touchListener: TouchListener? = null

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val itemView = layoutInflater!!.inflate(R.layout.pager_row, container, false)

        val imageView = itemView.findViewById<ImageView>(R.id.image_view)
        val linearLayout = itemView.findViewById<LinearLayout>(R.id.linear_layout)
        val textView = itemView.findViewById<TextView>(R.id.text_view)

        if (imageList[position].title != null) {
            textView.text = imageList[position].title
            linearLayout.setBackgroundResource(titleBackground)
            textView.gravity = getGravityFromAlign(textAlign)
            linearLayout.gravity = getGravityFromAlign(textAlign)
        } else {
            linearLayout.visibility = View.INVISIBLE
        }

        // Image from url or local path check.
        val glide = Glide.with(imageView)
            .load(imageList[position].imageUrl ?: imageList[position].imagePath)
        if ((scaleType != null && scaleType == ScaleTypes.CENTER_CROP) || imageList[position].scaleType == ScaleTypes.CENTER_CROP) {
            glide.centerCrop()
        } else if ((scaleType != null && scaleType == ScaleTypes.CENTER_INSIDE) || imageList[position].scaleType == ScaleTypes.CENTER_INSIDE) {
            glide.centerInside()
        } else if ((scaleType != null && scaleType == ScaleTypes.FIT) || imageList[position].scaleType == ScaleTypes.FIT) {
            glide.fitCenter()
        }
        glide.placeholder(placeholder).into(imageView)

        container.addView(itemView)

        imageView.setOnClickListener { itemClickListener?.onItemSelected(position) }

        if (touchListener != null) {
            imageView!!.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> touchListener!!.onTouched(ActionTypes.MOVE)
                    MotionEvent.ACTION_DOWN -> touchListener!!.onTouched(ActionTypes.DOWN)
                    MotionEvent.ACTION_UP -> touchListener!!.onTouched(ActionTypes.UP)
                }
                false
            }
        }


        return itemView
    }

    /**
     * Get layout gravity value from textAlign variable
     *
     * @param  textAlign  text align by user
     */
    fun getGravityFromAlign(textAlign: String): Int {
        return when (textAlign) {
            "RIGHT", "END" -> Gravity.END
            "CENTER" -> Gravity.CENTER
            else -> Gravity.START
        }
    }

    /**
     * Set item click listener
     *
     * @param  itemClickListener  callback by user
     */
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    /**
     * Set touch listener for listen to image touch
     *
     * @param  touchListener  interface callback
     */
    fun setTouchListener(touchListener: TouchListener) {
        this.touchListener = touchListener
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

}