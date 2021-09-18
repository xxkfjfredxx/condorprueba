package com.fred.prueba.ui.post.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fred.prueba.R
import com.fred.prueba.databinding.ItemPostBinding
import com.fred.prueba.interfaces.PostListener
import com.fred.prueba.models.Leagues
import com.squareup.picasso.Picasso

class PostAdapter(
    private val context: Context
):RecyclerView.Adapter<PostAdapter.PostHolder>() {

    private lateinit var listener: PostListener
    private var mData = mutableListOf<Leagues>()

    internal fun setData(data:List<Leagues>, listener: PostListener){
        this.mData = data as MutableList<Leagues>;
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.PostHolder {
        ItemPostBinding.inflate(LayoutInflater.from(context), parent, false).also {binding->
            return  PostHolder(binding);
        }
    }

    override fun onBindViewHolder(holder: PostAdapter.PostHolder, position: Int) {
        val post = this.mData[position];
        holder.bind(post, position)
    }

    override fun getItemCount() = this.mData.size

    internal fun removeItem(position: Int){
        var leagues:Leagues = this.mData[position];
        listener.onDeletePost(leagues, position)
        this.mData.removeAt(position)
        notifyItemRemoved(position)
    }

    internal fun updateItem(leagues: Leagues, position: Int){
        leagues.isRead = false;
        this.mData.add(position, leagues);
        notifyItemChanged(position)
    }

    internal fun restoreItem(leagues: Leagues, position: Int){
        leagues.isRead = false;
        this.mData.add(position, leagues)
        notifyItemInserted(position)
    }

    inner class PostHolder(val binding: ItemPostBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(leagues: Leagues, position: Int) {
            if (leagues.isRead!!){
                this.binding.textView
                    .setBackgroundColor(ContextCompat.getColor(context, R.color.blue_material_700))
            }else{
                this.binding.textView
                    .setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }
            if(leagues.isFavorite!!){
                this.binding.tvFavorite.setBackgroundResource(R.drawable.ic_star)
            }else{
                this.binding.tvFavorite.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }
            this.binding.tvName.text = leagues.strTeam.toString()
            this.binding.tvStadium.text = leagues.strStadium.toString()
            Picasso.get().load(leagues.strTeamBadge.toString()).into(this.binding.imgBadge)
            this.binding.root.setOnClickListener {
                listener.onSelectedPost(leagues, position)
            }
        }

    }
}