package com.julioolivares.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.julioolivares.noteapp.databinding.ActivityMainBinding
import com.julioolivares.noteapp.db.NoteDatabase
import com.julioolivares.noteapp.repository.NoteRepository
import com.julioolivares.noteapp.viewModel.NoteViewModel
import com.julioolivares.noteapp.viewModel.NoteViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setUpViewModel()
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.newNoteFragment,R.id.updateNoteFragment))
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController,appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return  binding.navHostFragment.findNavController().navigateUp() || super.onSupportNavigateUp()

    }

    private fun setUpViewModel(){
        val noteRepository = NoteRepository(
                NoteDatabase(this)
        )

        val viewModelProviderFactory = NoteViewModelProviderFactory(application,
                noteRepository = noteRepository)

        noteViewModel = ViewModelProvider(this,
                viewModelProviderFactory)
                .get(NoteViewModel::class.java)
    }
}