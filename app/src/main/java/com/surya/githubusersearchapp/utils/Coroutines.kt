package com.surya.githubusersearchapp.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by suryamudti on 21/09/2019.
 */

object Coroutines {

    fun main(work: suspend(()-> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }

    fun io(work: suspend(()-> Unit)) =
        CoroutineScope(Dispatchers.IO).launch {
            work()
        }

}