package com.lonchi.andrej.lonchi_bakalarka.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.ActivityOnboardingBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class OnboardingActivity : FragmentActivity() {

    companion object {
        fun getStartIntent(context: Context, extras: Bundle? = null): Intent =
            Intent(context, OnboardingActivity::class.java)
                .apply { if (extras != null) this.putExtras(extras) }

        private const val NUM_PAGES = 4
    }

    private var binding: ActivityOnboardingBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(requireNotNull(binding).root)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        binding?.viewPager?.adapter = pagerAdapter
        binding?.viewPager?.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handlePositionOfPage(position)
            }
        })

        binding?.buttonNext?.setOnClickListener {
            binding?.viewPager?.currentItem?.let {
                if (it == (NUM_PAGES - 1)) {
                    startActivity(MainActivity.getStartIntent(this, intent.extras))
                    finishAffinity()
                } else {
                    binding?.viewPager?.currentItem = it + 1
                }
            }
        }
    }

    private fun handlePositionOfPage(position: Int) {
        binding?.progressIndicator1?.background?.setTint(
            ContextCompat.getColor(
                this,
                if (0 <= position) R.color.green500 else R.color.gray200
            )
        )
        binding?.progressIndicator2?.background?.setTint(
            ContextCompat.getColor(
                this,
                if (1 <= position) R.color.green500 else R.color.gray200
            )
        )
        binding?.progressIndicator3?.background?.setTint(
            ContextCompat.getColor(
                this,
                if (2 <= position) R.color.green500 else R.color.gray200
            )
        )
        binding?.progressIndicator4?.background?.setTint(
            ContextCompat.getColor(
                this,
                if (3 <= position) R.color.green500 else R.color.gray200
            )
        )
    }

    override fun onBackPressed() {
        if (binding?.viewPager?.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            binding?.viewPager?.currentItem?.let {
                binding?.viewPager?.currentItem = it - 1
            }
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES
        override fun createFragment(position: Int): Fragment =
            OnboardingFragment.newInstance(position)
    }
}