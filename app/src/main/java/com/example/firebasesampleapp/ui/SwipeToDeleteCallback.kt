package com.example.firebasesampleapp.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasesampleapp.R

abstract class SwipeToDeleteCallback(context: Context?):ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {


    val icon = ContextCompat.getDrawable(context!!,R.drawable.baseline_delete_black_18dp)
    val background = ColorDrawable(Color.RED)
    private val intrinsicWidth = icon?.intrinsicWidth
    private val intrinsicHeight = icon?.intrinsicHeight


    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }
//Implemented in AuhtorFragment
//    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//
//    }

    override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        // Draw the red delete background
        background.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        background.draw(canvas)

        // Calculate position of delete icon
        val iconTop = itemView.top + (itemHeight - this!!.intrinsicHeight!!) / 2
        val iconMargin = (itemHeight - this!!.intrinsicHeight!!) / 2
        val iconLeft = itemView.right - iconMargin - this!!.intrinsicWidth!!
        val iconRight = itemView.right - iconMargin
        val iconBottom = iconTop + this!!.intrinsicHeight!!

        // Draw the delete icon
        icon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        icon?.draw(canvas)
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

    }
}