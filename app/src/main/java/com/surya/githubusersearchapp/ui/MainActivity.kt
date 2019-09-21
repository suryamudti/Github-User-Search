package com.surya.githubusersearchapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.surya.githubusersearchapp.R
import com.surya.githubusersearchapp.data.model.GitUser
import com.surya.githubusersearchapp.di.GithubUserSearchAppInjection
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    
    private lateinit var viewModel: MainViewModel
    private val adapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get the view model
        viewModel = ViewModelProviders.of(this,
            GithubUserSearchAppInjection.provideViewModelFactory(this))
            .get(MainViewModel::class.java)

        initAdapter()// initiate adapter and load default user list from network
        // check if the search query string
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        // initiate / update search query string livedata in vm
        viewModel.searchUser(query)
        initSearch(query)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        // keep track of last search query string
        outState.putString(LAST_SEARCH_QUERY, viewModel.lastQueryValue())
    }

    /**
     * initiate and set the rv adapter
     * observe the vm users live data for returned list of user from network call
     *
     * observes network error messages
     */
    private fun initAdapter() {
        list.adapter = adapter
        // observer list of user live data returned form network call
        viewModel.users.observe(this, Observer<PagedList<GitUser>> {
            Log.d("Activity", "list: ${it?.size}")
            showEmptyList(it?.size == 0) // hide list if size is 0, show tv with msg
            adapter.submitList(it) // Submits a new list to be diffed, and displayed.
        })
        viewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(this, "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
        })
    }

    /**
     * display query sting
     * initiate search
     */
    private fun initSearch(query: String) {
        search_user.setText(query)

        search_user.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateUserListFromInput()
                true
            } else {
                false
            }
        }
        search_user.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateUserListFromInput()
                true
            } else {
                false
            }
        }
    }

    /**
     * helper method to show or hide a textview relaying no data message
     * or the list of users
     * @param show flag indicating ui state
     */
    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            list.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            list.visibility = View.VISIBLE
        }
    }

    private fun updateUserListFromInput() {
        search_user.text.trim().let {
            if (it.isNotEmpty()) {
                list.scrollToPosition(0)// reset rv scroll
                viewModel.searchUser(it.toString()) // initiate a search for query
                adapter.submitList(null) // empty/reset the adapter
            }
        }
    }


    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query" // last saved search string
        private const val DEFAULT_QUERY = "tiket" // default git user search query string
    }
}
