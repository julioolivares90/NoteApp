package com.julioolivares.noteapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.julioolivares.noteapp.databinding.RowNoteBinding
import com.julioolivares.noteapp.fragment.home.HomeFragmentDirections
import com.julioolivares.noteapp.model.Note
import com.julioolivares.noteapp.viewModel.NoteViewModel
import kotlin.random.Random

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var binding : RowNoteBinding? = null


    class NoteViewHolder(itemBinding: RowNoteBinding) : RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object :ItemCallback<Note>(){

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        binding = RowNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  NoteViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.itemView.apply {
            binding?.tvNoteTitle?.text = currentNote.noteTitle

            val random = java.util.Random()

            val color = Color.rgb(random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256))

            binding?.tvNoteTitle?.setBackgroundColor(color)

            binding?.tvNoteTitle?.setTextColor(Color.rgb(0,0,0))

            binding?.tvNoteBody?.text = currentNote.noteBody
        }.setOnClickListener {mView->
            val direction = HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(currentNote)
            Navigation.findNavController(mView).navigate(direction)
        }
    }

    override fun getItemCount() = differ.currentList.size
}