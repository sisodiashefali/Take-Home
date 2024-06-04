package com.android.github.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.android.github.CustomAdapter
import com.android.github.R
import com.android.github.databinding.ActivityMainBinding
import com.android.github.models.RepositoryListItem
import com.android.github.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        // this creates a vertical layout Manager
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.searchButton.setOnClickListener { view ->
            val userID = binding.editTextUserName.text.toString()
            hidekeyBoard()
            if (!userID.isNullOrBlank()) {
                binding.errorMessage.visibility = View.GONE
                mainViewModel.loadGitProfile(binding.editTextUserName.text.toString())
            } else {
                showError()
            }
        }

        mainViewModel.productsLiveData.observe(this, Observer {
            binding.linearLayoutImage.visibility = View.VISIBLE
            binding.textViewProfile.text = it.name
            Glide.with(this).load(it.avatar_url).into(binding.imageViewProfile)
        })
        mainViewModel.repoListLiveData.observe(this) {
            Log.d("repo", it.toString())
            var arrayList: ArrayList<RepositoryListItem>? = arrayListOf()
            if (it.size > 0) {
                it.forEach {

                    arrayList?.add(
                        it
                    )
                }
            }
            // This will pass the ArrayList to our Adapter
            val adapter = CustomAdapter(arrayList)

            // Setting the Adapter with the recyclerview
            binding.recyclerView.adapter = adapter
            val controller: LayoutAnimationController =
                AnimationUtils.loadLayoutAnimation(
                    binding.recyclerView.context,
                    R.anim.layout_animation_fall_down
                );

            binding.recyclerView.setLayoutAnimation(controller);
            binding.recyclerView.getAdapter()?.notifyDataSetChanged();
            binding.recyclerView.scheduleLayoutAnimation();

            // Applying OnClickListener to our Adapter
            adapter.setOnClickListener( object : CustomAdapter.OnClickListener {
                override fun onClick(position: Int, model: RepositoryListItem?) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    // Passing the data to the
                    // EmployeeDetails Activity
                    intent.putExtra(NEXT_SCREEN, model)
                    startActivity(intent)
                }
            })
        }


    }
    companion object{
        const val NEXT_SCREEN="details_screen"
    }
    private fun showError() {
        hidekeyBoard()
        binding.linearLayoutImage.visibility = View.GONE
        binding.errorMessage.text = getString(R.string.error_message)
        binding.errorMessage.visibility = View.VISIBLE
        binding.recyclerView.adapter = null
    }

    private fun hideProgress() {
        hidekeyBoard()
        binding.progressBar.visibility = View.GONE
    }

    private fun hidekeyBoard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val inputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

            // on below line hiding our keyboard.
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }

    }

}





















