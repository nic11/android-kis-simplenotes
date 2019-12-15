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
                .replace(R.id.activity_host_note_list_container, NoteListFragment(), NoteListFragment.TAG)
                .addToBackStack(null)
                .commit()

            title = resources.getString(R.string.main_title)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
            title = resources.getString(R.string.main_title)
        }
    }

    fun onNoteSelected(id: String) {
        if (supportFragmentManager.findFragmentByTag(DetailsFragment.TAG) != null) {
            supportFragmentManager.popBackStack()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_host_details_container, DetailsFragment.build(id), DetailsFragment.TAG)
            .addToBackStack(null)
            .commit()
    }
}
