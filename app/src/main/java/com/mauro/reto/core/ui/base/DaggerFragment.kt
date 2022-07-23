package com.mauro.reto.core.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.mauro.reto.MainActivity
import com.mauro.reto.R
import com.mauro.reto.ui.login.LoginActivity
import com.mauro.reto.ui.movie.MovieActivity
import dagger.hilt.android.AndroidEntryPoint

typealias BaseFragment = DaggerFragment

/**
 * Base activity providing Dagger support and [ViewModel] support
 */
@AndroidEntryPoint
abstract class DaggerFragment : Fragment(){
    protected fun isNetworkAvailable(): Boolean {
        return (activity as MovieActivity).isNetworkAvailable()
    }
    fun addFragmentArg(newFragment: Fragment, nameBackStack:String, args: Bundle) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        newFragment.arguments = args
        transaction.replace(R.id.fragment_container, newFragment)
        transaction.addToBackStack(nameBackStack)
        transaction.commit()
    }
}

