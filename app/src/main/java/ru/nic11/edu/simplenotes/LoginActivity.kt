package ru.nic11.edu.simplenotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        val password2 = findViewById<EditText>(R.id.password_repeat)
        val register = findViewById<Button>(R.id.register_btn)

        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            MyApp.noteRepository.login(username.text.toString(), password.text.toString(), {
                MyApp.noteRepository.update {
                    finish()
                }
            }, {
                loading.visibility = View.GONE
            })
        }

        register.setOnClickListener {
            if (password.text.toString() != password2.text.toString()) {
                MyApp.toastShort("Passwords do not match!")
                return@setOnClickListener
            }

            loading.visibility = View.VISIBLE
            MyApp.noteRepository.register(username.text.toString(), password.text.toString(), {
                MyApp.noteRepository.update {
                    finish()
                }
            }, {
                loading.visibility = View.GONE
            })
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
