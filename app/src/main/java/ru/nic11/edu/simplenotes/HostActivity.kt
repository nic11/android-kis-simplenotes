package ru.nic11.edu.simplenotes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class HostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_host_note_list_container, NoteListFragment(), NoteListFragment.TAG)
                .addToBackStack(null)
                .commit()

            title = resources.getString(R.string.main_title)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
//        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                MyApp.noteRepository.logout()
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        if (!MyApp.noteRepository.loggedIn()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
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
        if (supportFragmentManager.findFragmentByTag(DetailsFragment.TAG) != null) {
            supportFragmentManager.popBackStack()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_host_details_container, DetailsFragment.build(id), DetailsFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    fun notifyNotesUpdated() {
        val noteListFrag = supportFragmentManager.findFragmentByTag(NoteListFragment.TAG)
                as NoteListFragment
        noteListFrag.notifyViewAdapter()
    }
}
