package com.example.thez.main.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.thez.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding= ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR_URL)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        viewModel.loading.observe(this,{
            if (it)
                showLoading(true)
            else{
                showLoading(false)
            }
        })

        username?.let { viewModel.setUserDetail(it) }
        viewModel.getUserDetail().observe(this ) {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(tvProfile)
                }
            }
        }

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null){
                    if (count>0){
                        binding.toggleButton.isChecked = true
                        _isChecked = true
                    }else{
                        binding.toggleButton.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleButton.setOnClickListener{
            _isChecked = ! _isChecked
            if (_isChecked){
                if (username != null) {
                    if (avatarUrl != null) {
                        viewModel.addToFavorite(username, id, avatarUrl)
                    }
                }
            }else {
                viewModel.removeFromFavorite(id)
            }
            binding.toggleButton.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)

        }
    }
    private fun showLoading(state: Boolean){
        if (state){
            binding.progressbarUser.visibility = View.VISIBLE
            binding.detailUser.visibility = View.INVISIBLE
        }else{
            binding.progressbarUser.visibility = View.INVISIBLE
            binding.detailUser.visibility = View.VISIBLE
        }
    }
}

