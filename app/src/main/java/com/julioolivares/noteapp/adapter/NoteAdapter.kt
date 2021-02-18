package com.julioolivares.noteapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.julioolivares.noteapp.databinding.RowNoteBinding
import com.julioolivares.noteapp.model.Note
import com.julioolivares.noteapp.viewModel.NoteViewModel

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
            binding?.tvNoteBody?.text = currentNote.noteBody
        }
    }

    override fun getItemCount() = differ.currentList.size
}