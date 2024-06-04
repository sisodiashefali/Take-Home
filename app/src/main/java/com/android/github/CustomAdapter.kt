package com.android.github

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.github.models.RepositoryListItem

class CustomAdapter(private val mList: List<RepositoryListItem>?) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycle_view, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList?.get(position)
        // sets the image to the imageview from our itemHolder class

        // sets the text to the textview from our itemHolder class
        holder.textName.text = item?.name ?: ""
        holder.textDesc.text = item?.description ?: ""
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item )
            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }
    // A function to bind the onclickListener.
    public fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textName: TextView = itemView.findViewById(R.id.textName)
        val textDesc: TextView = itemView.findViewById(R.id.textDesc)
    }
    interface OnClickListener {
        fun onClick(position: Int, model: RepositoryListItem?)    }
}