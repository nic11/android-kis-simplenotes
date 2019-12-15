package ru.nic11.edu.simplenotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_host_container, NoteListFragment(), NoteListFragment.TAG)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    fun onNoteSelected(id: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_host_container, DetailsFragment.build(id), DetailsFragment.TAG)
            .addToBackStack(null)
            .commit()
    }
}
