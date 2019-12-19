package ru.nic11.edu.simplenotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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
        }
    }

    fun onNoteSelected(id: Long) {
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
