package com.julioolivares.noteapp.fragment.newFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.julioolivares.noteapp.MainActivity
import com.julioolivares.noteapp.R
import com.julioolivares.noteapp.databinding.NewNoteFragmentBinding
import com.julioolivares.noteapp.model.Note
import com.julioolivares.noteapp.utilities.toast
import com.julioolivares.noteapp.viewModel.NoteViewModel

class NewNoteFragment : Fragment(R.layout.new_note_fragment) {

    private var _binding : NewNoteFragmentBinding? = null

    val binding  get() = _binding


    companion object {
        fun newInstance() = NewNoteFragment()
    }

    private lateinit var viewModel: NoteViewModel

    private lateinit var mView : View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = NewNoteFragmentBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).noteViewModel
        mView = view

        binding?.faCrearNota?.setOnClickListener {
            saveNote(mView)
        }
    }

    private fun saveNote(view: View){
        val noteTitle = binding?.etNoteTitle?.text.toString().trim()

        val noteBody = binding?.etNoteBody?.text.toString().trim()

        if (noteTitle.isNotEmpty()){
            val note = Note(0,noteTitle,noteBody)

            viewModel.addNote(note = note)
            Snackbar.make(view,"Note Saved successfully",Snackbar.LENGTH_SHORT).show()

            view.findNavController().navigate(
                    R.id.action_newNoteFragment_to_homeFragment
            )
        }else {
            activity?.toast("Please enter note title!")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save -> {
                saveNote(mView)
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.new_note_menu,menu)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}