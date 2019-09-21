package com.surya.githubusersearchapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.surya.githubusersearchapp.R
import com.surya.githubusersearchapp.data.model.GitUser
import com.surya.githubusersearchapp.databinding.ListItemBinding

/**
 * Created by suryamudti on 21/09/2019.
 */
class UserViewHolder(view: ListItemBinding) : RecyclerView.ViewHolder(view.root) {

    private val name: TextView = view.titleText

    private var user: GitUser? = null

    /**
     * handles displaying the user data per item, or dummy text until it's loaded
     */
    fun bind(user: GitUser?){
        if (user == null){
            name.text = "Loading"

        }else{
            showUserData(user)
        }
    }

    /**
     * displays the full dat of the user when loaded
     */
    private fun showUserData(user: GitUser) {
        this.user = user
        name.text = user.login


    }

    companion object {
        /**
         * inflates the view through databinding and instantiates the UserViewHolder
         */
        fun create(parent: ViewGroup): UserViewHolder {
            val inflator = LayoutInflater.from(parent.context)

            val inflatedViewBinding = DataBindingUtil.inflate<ListItemBinding>(inflator,
                R.layout.list_item,
                parent, false)
            return UserViewHolder(inflatedViewBinding)
        }
    }

}