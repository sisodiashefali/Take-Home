package com.android.github.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.github.databinding.ActivityDetailBinding

import com.android.github.models.RepositoryListItem

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var repoListItem: RepositoryListItem? = null

        // checking if the intent has extra
        if (intent.hasExtra(MainActivity.NEXT_SCREEN)) {
            // get the Serializable data model class with the details in it
            repoListItem =
                intent.getSerializableExtra(MainActivity.NEXT_SCREEN) as RepositoryListItem
        }
        if (repoListItem != null) {
            binding.textViewName.text = "Name:${repoListItem.name}"
            binding.textViewDescription.text = "Description:${repoListItem.description}"
            binding.textViewUpdatedAt.text = "Updated At:${repoListItem.updated_at}"
            binding.textViewStargazersCount.text ="Stargazers Count:${repoListItem.stargazers_count.toString()}"

            if(repoListItem.forks_count>5000){
                binding.textViewForksCount.setTextColor(Color.RED)
                binding.textViewForksCount.text ="Fork Count:${ repoListItem.forks_count.toString() } *"
            }else{
                binding.textViewForksCount.setTextColor(Color.BLACK)
                binding.textViewForksCount.text ="Fork Count:${ repoListItem.forks_count.toString() }"
            }
        }

    }
}