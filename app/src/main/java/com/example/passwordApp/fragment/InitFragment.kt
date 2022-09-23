package com.example.passwordApp.fragment

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import com.example.passwordApp.R
import com.example.passwordApp.activity.LoginActivity

class InitFragment : Fragment() {

    private lateinit var sceneView: View
    private lateinit var circleView: View
    private lateinit var backgroundView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_init, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sceneView = view
        circleView = view.findViewById(R.id.circle)
        backgroundView = view.findViewById(R.id.sceneLayout)
        startAnimation()
        val thread = Thread {
            run{
                Thread.sleep(3050)
            }
            requireActivity().runOnUiThread{
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        thread.start()
    }

    private fun startAnimation() {
        val dm: DisplayMetrics = this.resources.displayMetrics
        val screenHeight = dm.heightPixels.toFloat()
        val path = screenHeight / 2 - 150*dm.density
        ObjectAnimator.ofFloat(circleView, "y", path).apply {
            interpolator = DecelerateInterpolator()
            duration = 3000
            start()
        }
    }
}
