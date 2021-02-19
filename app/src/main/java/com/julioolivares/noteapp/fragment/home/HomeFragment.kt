package com.julioolivares.noteapp.fragment.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.julioolivares.noteapp.MainActivity
import com.julioolivares.noteapp.R
import com.julioolivares.noteapp.adapter.NoteAdapter
import com.julioolivares.noteapp.databinding.HomeFragmentBinding
import com.julioolivares.noteapp.model.Note
import com.julioolivares.noteapp.viewModel.NoteViewModel

class HomeFragment : Fragment(R.layout.home_fragment) ,SearchView.OnQueryTextListener {

    private var _binding : HomeFragmentBinding?= null
    private val binding get() = _binding


    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: NoteViewModel

    private  lateinit var noteAdapter: NoteAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu,menu)

        val menuSearchView = menu.findItem(R.id.menu_search).actionView as SearchView
        menuSearchView.isSubmitButtonEnabled = true
        menuSearchView.setOnQueryTextListener(this)
        binding?.fbNewNote?.setOnClickListener{mView ->
            mView.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).noteViewModel

        setUpRecyclerView()

        binding?.fbNewNote?.setOnClickListener {mView->
            mView.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpRecyclerView(){
        noteAdapter = NoteAdapter()
        binding!!.rvNotes.apply {
            layoutManager = StaggeredGridLayoutManager(
                    2,
                    StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)

            adapter = noteAdapter
        }

        activity?.let {
            viewModel.getAllNotes().observe(viewLifecycleOwner,{notes->
                noteAdapter.differ.submitList(notes)
                updateUI(notes)
            })
        }
    }

    private fun updateUI(notes: List<Note>?) {
        if (notes!!.isNotEmpty()){
            binding?.rvNotes?.visibility = View.VISIBLE
            binding?.tvNoNotesAvailable?.visibility = View.GONE
        }else {
            binding?.rvNotes?.visibility = View.GONE
            binding?.tvNoNotesAvailable?.visibility = View.VISIBLE
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            searchNote(it)
        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            searchNote(it)
        }

        return true

    }

    private fun searchNote(query : String?){
        val searchQuery = "%$query%"
        viewModel.searchNote(searchQuery).observe(this,{list->
            noteAdapter.differ.submitList(list)
        })
    }
}