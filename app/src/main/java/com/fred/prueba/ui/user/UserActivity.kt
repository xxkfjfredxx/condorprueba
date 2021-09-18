package com.fred.prueba.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fred.prueba.R
import com.fred.prueba.interfaces.ResponseListener
import com.fred.prueba.models.Event
import com.fred.prueba.models.Leagues
import com.fred.prueba.utils.ApiServiceException
import com.fred.prueba.utils.Coroutines
import com.fred.prueba.utils.NoInternetException
import com.squareup.picasso.Picasso
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.lang.Exception
import android.content.Intent
import android.net.Uri
import com.fred.prueba.databinding.ActivityUserBinding


class UserActivity : AppCompatActivity(R.layout.activity_user), KodeinAware, ResponseListener,
    View.OnClickListener {

    override val kodein by kodein()

    private val userViewModelFactory: UserViewModelFactory by instance()

    private lateinit var userViewModel: UserViewModel

    private val binding: ActivityUserBinding by viewBinding()

    private var leagues: Leagues? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onStarted()

        this.userViewModel =
            ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)

        intent.extras.also { bundle ->
            if (bundle != null) {
                leagues = bundle.getSerializable("post") as Leagues
                isFavoriteChecked(leagues)
                getUser(leagues!!)
            }
        }

        this.binding.tvFavorite.setOnClickListener(this)
        this.binding.tvFavoriteMessage.setOnClickListener(this)


    }

    private fun isFavoriteChecked(leagues: Leagues?) {
        if (leagues?.isFavorite!!) {
            this.binding.tvFavoriteMessage.text = "Favorite post";
            this.binding.tvFavorite.setBackgroundResource(R.drawable.ic_favorite_checked)
        } else {
            this.binding.tvFavoriteMessage.text = "Add to favorite posts";
            this.binding.tvFavorite.setBackgroundResource(R.drawable.ic_favorite_unchecked)
        }
    }


    private fun getUser(leagues: Leagues) = Coroutines.main {
        try {
            userViewModel.getEvents(leagues.idTeam!!).also { user ->
                if (user != null) {
                    bindUI(leagues, user.results)
                }
            }
        } catch (e: ApiServiceException) {
            onFailure(e.message!!)
        } catch (e: NoInternetException) {
            onFailure(e.message!!)
        } catch (e: Exception) {
            onFailure(e.message!!)
        }
    }

    private fun setFavoritePost() = Coroutines.main {
        if (leagues!!.isFavorite!!) {
            this.userViewModel.setFavorite(false, leagues!!.id!!)
            bindFavoritePost(
                R.drawable.ic_favorite_unchecked,
                "The post was removed from favorite",
                "Add to favorite posts"
            )

        } else {
            this.userViewModel.setFavorite(true, leagues!!.id!!)
            bindFavoritePost(
                R.drawable.ic_favorite_checked,
                "The post was added to favorite",
                "Favorite post"
            )
        }
        leagues!!.isFavorite = !leagues!!.isFavorite!!
    }

    private fun bindFavoritePost(icon: Int, message: String, label: String) {
        this.binding.tvFavorite.setBackgroundResource(icon)
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        this.binding.tvFavoriteMessage.text = label
    }

    private fun bindUI(infoTeams: Leagues, listEvents: List<Event>) {
        this.binding.tvName.text = infoTeams.strTeam
        this.binding.tvDescription.text = infoTeams.strDescriptionES
        this.binding.tvFoundationYear.text = infoTeams.intFormedYear
        this.binding.tvInstagram.text = infoTeams.strInstagram
        this.binding.tvTwiter.text = infoTeams.strTwitter
        this.binding.tvFacebook.text = infoTeams.strFacebook
        this.binding.tvYoutube.text = infoTeams.strYoutube
        this.binding.tvInstagram.setOnClickListener({
            openNav(infoTeams.strInstagram!!)
        })
        this.binding.tvTwiter.setOnClickListener({
            openNav(infoTeams.strTwitter!!)
        })
        this.binding.tvFacebook.setOnClickListener({
            openNav(infoTeams.strFacebook!!)
        })
        this.binding.tvYoutube.setOnClickListener({
            openNav(infoTeams.strYoutube!!)
        })
        this.binding.tvInstagram.setOnClickListener({
            openNav(infoTeams.strInstagram!!)
        })
        var evento = "Eventos : \n"
        listEvents.forEachIndexed { index, event ->
            evento += event.strEvent + "\n"
        }
        this.binding.tvNextEvents.text = evento
        Picasso.get().load(infoTeams.strTeamBadge).into(this.binding.imBadge)
        onSuccess()
    }

    override fun onStarted() {
        this.binding.progressBar.visibility = View.VISIBLE
    }

    override fun onFailure(message: String) = Coroutines.main {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess() {
        this.binding.progressBar.visibility = View.GONE
        this.binding.containerUser.visibility = View.VISIBLE
    }

    override fun onClick(v: View?) {
        when (v) {
            this.binding.tvFavorite -> {
                setFavoritePost()
            }
            this.binding.tvFavoriteMessage -> {
                setFavoritePost()
            }
        }
    }

    fun openNav(url: String) {
        val uri: Uri = Uri.parse("http://"+url)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}