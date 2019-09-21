package com.surya.githubusersearchapp.ui

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.surya.githubusersearchapp.data.model.GitUser

/**
 * Created by suryamudti on 21/09/2019.
 */


/**
 * Adapter for the list of users.
 */
class UserAdapter : PagedListAdapter<GitUser, RecyclerView.ViewHolder>(COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val userItem = getItem(position)
        if (userItem != null) (holder as UserViewHolder).bind(userItem)
    }


    /**
     * diffUtils implementation for comparator
     */
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<GitUser>() {
            override fun areItemsTheSame(oldItem: GitUser, newItem: GitUser): Boolean =
                oldItem.login == newItem.login

            override fun areContentsTheSame(oldItem: GitUser, newItem: GitUser): Boolean =
                oldItem == newItem
        }
    }
}