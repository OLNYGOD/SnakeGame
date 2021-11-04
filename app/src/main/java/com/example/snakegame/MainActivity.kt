package com.example.snakegame

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.snakegame.databinding.ActivityMainBinding
import com.example.snakegame.databinding.ContentMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG: String? = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private lateinit var contentbinding: ContentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        contentbinding = ContentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(contentbinding.root)
        setSupportActionBar(binding.toolbar)
        var gameView = findViewById<GameView>(R.id.game_view)
        val up = contentbinding.up
        val down = contentbinding.down
        val left = contentbinding.left
        val right = contentbinding.right
        //val game_View = contentbinding.game_view
        //傾聽SnakeViewModel身上資料及方法
        val viewModel = ViewModelProvider(this).get(SnakeViewModel::class.java)
        viewModel.body.observe(this,{
            gameView.snakeBody = it
            gameView.invalidate()
        })
        viewModel.apple.observe(this,{
            gameView.apple = it
            gameView.invalidate()
        })
        viewModel.score.observe(this,{
            contentbinding.score.setText(it.toString())
        })
        viewModel.size.observe(this,{

        })
        viewModel.gameStatus.observe(this,{
            if ( it == GameStatus.GAMEOVER){
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Game")
                    .setMessage("GameOver")
                    .setPositiveButton("OK",null)
                    .show()
            }
        })
        /*val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)*/
        //Log.d(TAG, "MainActivity" + 1)
        viewModel.start()
        up.setOnClickListener { viewModel.move(dir = Direction.UP)}
        down.setOnClickListener { viewModel.move(dir = Direction.DOWN) }
        left.setOnClickListener { viewModel.move(dir = Direction.LEFT) }
        right.setOnClickListener { viewModel.move(dir = Direction.RIGHT) }
        binding.replay.setOnClickListener { view ->
            viewModel.reset()
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