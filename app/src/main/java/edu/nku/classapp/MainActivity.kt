package edu.nku.classapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import edu.nku.classapp.databinding.ActivityMainBinding
import edu.nku.classapp.ui.GameOfThronesCharacterListFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContentView(binding.root)
        //Either Or for commit
//        supportFragmentManager
//            .beginTransaction()
//            .add(R.id.fragment_container_view, GameOfThronesCharacterListFragment())
//            .setReorderingAllowed(true)
//            .commit()

        supportFragmentManager.commit {
            add(R.id.fragment_container_view, GameOfThronesCharacterListFragment())
            setReorderingAllowed(true)
        }

    }


}