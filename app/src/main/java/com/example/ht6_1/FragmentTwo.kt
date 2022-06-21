package com.example.ht6_1

import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.ht6_1.databinding.FragmentTwoBinding
import java.util.*
import kotlin.random.Random

class FragmentTwo : Fragment() {
    lateinit var binding: FragmentTwoBinding
    lateinit var viewModel: FrViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTwoBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[FrViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.start()
        binding.restartButton.setOnClickListener {
            viewModel.reset()
        }

        var isPlaying = true
        binding.startStopButton.setOnClickListener {
            if(isPlaying){
                binding.startStopButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                viewModel.pause()
            }
            else{
                binding.startStopButton.setImageResource(R.drawable.ic_baseline_pause_24)
                viewModel.run()
            }
            isPlaying = !isPlaying
        }
    }

    var secondsPassed = 0
    override fun onStart() {
        super.onStart()
        viewModel.secondsPassed.observe(activity as LifecycleOwner) {
            secondsPassed = it.toInt()
            binding.timer.text = "${it/100}.${it%100}"
            if((secondsPassed % 1000 == 0)&&(secondsPassed != 0)){
                changeBackground()
            }
            if (secondsPassed==0)
                binding.root.setBackgroundColor(Color.WHITE)
        }
    }
    companion object{
        @JvmStatic
        fun newInstance() = FragmentTwo()
    }



    fun changeBackground(){
        val color = Color.argb(255,Random.nextInt(256),Random.nextInt(256), Random.nextInt(256))
        binding.root.setBackgroundColor(color)
    }
}


