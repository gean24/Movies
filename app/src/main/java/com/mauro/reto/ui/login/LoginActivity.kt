package com.mauro.reto.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.mauro.reto.R
import com.mauro.reto.core.ui.base.BaseActivity
import com.mauro.reto.core.utils.visible
import com.mauro.reto.ui.movie.MovieActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_login.setOnClickListener {
            //cntLoader.visible()

            val user = inputEditText_user.text.toString()
            val password = inputEditText_password.text.toString()
            if (user.isNotEmpty() && password.isNotEmpty()) {
                startActivity(Intent(this, MovieActivity::class.java))
            } else {
                Toast.makeText(
                    this,
                    (getString(R.string.enter_complete_data)),
                    Toast.LENGTH_SHORT
                ).show()

            }

        }
    }
}

