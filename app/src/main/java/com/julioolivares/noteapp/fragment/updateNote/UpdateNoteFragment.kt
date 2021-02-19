package com.julioolivares.noteapp.fragment.updateNote

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.julioolivares.noteapp.MainActivity
import com.julioolivares.noteapp.R
import com.julioolivares.noteapp.databinding.UpdateNoteFragmentBinding
import com.julioolivares.noteapp.model.Note
import com.julioolivares.noteapp.utilities.toast
import com.julioolivares.noteapp.viewModel.NoteViewModel

class UpdateNoteFragment : Fragment(R.layout.update_note_fragment) {

    companion object {
        fun newInstance() = UpdateNoteFragment()
    }

    private lateinit var viewModel: NoteViewModel

    private var _binding : UpdateNoteFragmentBinding? = null

    private val args : UpdateNoteFragmentArgs by navArgs()

    private lateinit var currentNote : Note

    val binding get() = _binding

    lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UpdateNoteFragmentBinding.inflate(layoutInflater,
                container,
                false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).noteViewModel
        mView = view

        currentNote = args.note

        binding?.etNoteTitleUpdate?.setText(currentNote.noteTitle)

        binding?.etNoteBodyUpdate?.setText(currentNote.noteBody)

        binding?.faActualizarNota?.setOnClickListener {

            val title =  binding?.etNoteTitleUpdate?.text.toString().trim()
            val body = binding?.etNoteBodyUpdate?.text.toString()

            if (title.isNotEmpty()){
                val noteUpdate = Note(currentNote.id,title,body)
                viewModel.updateNote(note = noteUpdate)

                Snackbar.make(mView,"Actualizada con exito!!",Snackbar.LENGTH_SHORT).show()
                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }else {
                activity?.toast("Porfavor ingrese un titulo ")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.update_note_menu,menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun deleteNote(){
        AlertDialog.Builder(activity as MainActivity).apply {
            setTitle("Delete note")
            setMessage("Are you sure want to delete this note?")
            setPositiveButton("DELETE"){_,_->
                viewModel.deleteNote(currentNote)
                mView.findNavController()
                        .navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }.setNegativeButton("CANCEL",null)

        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_menu -> deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}