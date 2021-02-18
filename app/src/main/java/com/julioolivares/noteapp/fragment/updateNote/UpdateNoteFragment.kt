package com.julioolivares.noteapp.fragment.updateNote

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.julioolivares.noteapp.R

class UpdateNoteFragment : Fragment() {

    companion object {
        fun newInstance() = UpdateNoteFragment()
    }

    private lateinit var viewModel: UpdateNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.update_note_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UpdateNoteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}