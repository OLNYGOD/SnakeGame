package com.example.snakegame

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.example.snakegame.databinding.ActivityMainBinding
import com.example.snakegame.databinding.ContentMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contentbinding: ContentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        contentbinding = ContentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(contentbinding.root)
        setSupportActionBar(binding.toolbar)
        val gameView = findViewById<GameView>(R.id.game_view)
        //val game_View = contentbinding.game_view
        //傾聽SnakeViewModel身上資料及方法
        val viewModel = ViewModelProvider(this).get(SnakeViewModel::class.java)
        viewModel.body.observe(this,{
            gameView.snakebody = it
            gameView.invalidate()
        })
        viewModel.apple.observe(this,{

        })
        viewModel.score.observe(this,{

        })
        /*val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)*/

        viewModel.start()

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }*/
}