package com.mauro.reto.ui.movie

import android.os.Bundle
import com.mauro.reto.R
import com.mauro.reto.core.ui.base.BaseActivity

class MovieActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        initUI()
    }

    private fun initUI() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, MovieFragment())
            .commit()
    }
}

