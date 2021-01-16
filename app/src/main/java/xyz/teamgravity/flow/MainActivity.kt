package xyz.teamgravity.flow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import xyz.teamgravity.flow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get data from imaginary server -> producer flow
        val flow = flow<Int> {
            for (i in 1..10) {
                emit(i)
                delay(1000L)
            }
        }

        // update ui when data ready -> consumer flow
        lifecycleScope.launch {
            // runs different coroutine from producer
            flow.buffer().filter {
                it % 2 == 0
            }.map {
                it * it
            }.collect {
                println("debug: $it")
                delay(2000L)
            }
        }
    }
}