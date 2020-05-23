package ru.nic11.edu.simplenotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.util.Base64
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nic11.edu.simplenotes.CameraActivity.Companion.KEY_SAVED_PATH
import ru.nic11.edu.simplenotes.db.Note
import java.io.File
import java.lang.RuntimeException
import java.util.*

const val CAMERA_REQUEST_CODE = 2077

class NoteListFragment : Fragment() {
    companion object {
        const val TAG = "NoteListFragment"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_list, container,  false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity!!

        MyApp.noteRepository.update {
            Log.i(LOG_TAG, "fetched notes")
            activity.runOnUiThread {
                Toast.makeText(activity, "fetched notes", Toast.LENGTH_SHORT).show()
                viewAdapter.notifyDataSetChanged()
            }
        }

        viewManager = LinearLayoutManager(activity)
        viewAdapter = NoteListAdapter(MyApp.noteRepository, activity as HostActivity)

        recyclerView = view.findViewById(R.id.notes_list_recycler_view)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val isLandscapeOrientation = resources.getBoolean(R.bool.isLandscapeOrientation)
        val deviceIsTablet = resources.getBoolean(R.bool.deviceIsTablet)

        if (isLandscapeOrientation xor deviceIsTablet) {
            recyclerView.layoutManager = GridLayoutManager(activity, 2)
        }

        val fab: View = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(context, CameraActivity::class.java)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    Toast.makeText(context, "wtf data is null", Toast.LENGTH_LONG).show()
                    return
                }

                val path = data.getStringExtra(KEY_SAVED_PATH) ?:
                    throw RuntimeException("camera activity didn't return path")

                val b64 = Base64.encodeToString(File(path).readBytes(), Base64.NO_WRAP)
                Log.i(LOG_TAG, "base64: $b64")
                Log.i(LOG_TAG, "b64.len: ${b64.length}")

                MyApp.noteRepository.create(
                    "Enter some text here",
                    b64
                ) {
                    activity!!.runOnUiThread {
                        Toast.makeText(context, "created note id=$it", Toast.LENGTH_SHORT).show()
                        viewAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun notifyViewAdapter() {
        viewAdapter.notifyDataSetChanged()
    }
}
