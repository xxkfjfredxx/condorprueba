package com.fred.prueba.ui.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fred.prueba.R
import com.fred.prueba.databinding.FragmentFilterFavoriteBinding
import com.fred.prueba.interfaces.PostListener
import com.fred.prueba.models.Leagues
import com.fred.prueba.ui.post.adapters.PostAdapter
import com.fred.prueba.utils.Coroutines
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class FilterFavoriteFragment : Fragment(R.layout.fragment_filter_favorite), KodeinAware , PostListener, View.OnClickListener{

    override val kodein by kodein()

    private val binding:FragmentFilterFavoriteBinding by viewBinding()

    private val postViewModelFactory:PostViewModelFactory by instance()

    private lateinit var postViewModel: PostViewModel

    private var allPost = listOf<Leagues>()

    private var postFavorites = listOf<Leagues>()


    private var postAdapter:PostAdapter?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(this.binding.toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true);
        activity.supportActionBar?.setDisplayShowTitleEnabled(false);
        this.binding.toolbar.setNavigationIcon(R.drawable.ic_back);


        postViewModel = ViewModelProvider(this, postViewModelFactory).get(PostViewModel::class.java);

        postViewModel.getAllPosts().observe(viewLifecycleOwner, {posts->
            initRecycler(posts)
            allPost = posts
        })

        postViewModel.getPostFavorites().observe(viewLifecycleOwner, {posts->
            postFavorites = posts
        })

        this.binding.toolbar.setNavigationOnClickListener {
            activity.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }


        this.binding.btnAll.setOnClickListener(this)
        this.binding.btnFavorites.setOnClickListener(this)

    }


    private fun initRecycler(leagues:List<Leagues>){
        postAdapter = PostAdapter(requireContext());
        this.binding.rcvFilter.setHasFixedSize(true);
        this.binding.rcvFilter.layoutManager = LinearLayoutManager(requireContext());
        this.postAdapter?.setData(leagues, this)
        this.binding.rcvFilter.adapter = postAdapter

    }

    override fun onSelectedPost(leagues: Leagues, position: Int) {

    }

    override fun onDeletePost(leagues: Leagues, position: Int) = Coroutines.main {

    }

    override fun onClick(v: View?) {
        when(v){
            this.binding.btnAll->{
                initRecycler(allPost)
            }
            this.binding.btnFavorites->{
                initRecycler(postFavorites)
            }
        }
    }
}