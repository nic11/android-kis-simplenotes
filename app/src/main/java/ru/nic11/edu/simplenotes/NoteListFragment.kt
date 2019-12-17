package ru.nic11.edu.simplenotes

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

        NoteRepository.initialize(activity)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = NoteListAdapter(activity as HostActivity)

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
    }
}
