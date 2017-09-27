package com.meewii.mentalarithmetic.ui.nav

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meewii.mentalarithmetic.R
import kotlinx.android.synthetic.main.list_item_nav.view.*

class NavAdapter(private val navItems: Array<String>, private val listener: OnItemClickListener) : RecyclerView.Adapter<NavAdapter.NavViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NavViewHolder(layoutInflater.inflate(R.layout.list_item_nav, parent, false))
    }

    override fun onBindViewHolder(holder: NavViewHolder, position: Int) {
        holder.bind(navItems[position], listener)
    }

    override fun getItemCount(): Int = navItems.size

    class NavViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val navItem = view.navItem!!

        fun bind(title: String, listener: OnItemClickListener) {
            this.navItem.text = title
            view.setOnClickListener({
                listener.onItemClick(title)
            })
        }
    }

}