package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem.TeacherProblemResponse
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import kotlinx.android.synthetic.main.activity_video_play.*

class VideoPlayActivity : AppCompatActivity() {

    private val playbackStateListener: Player.EventListener = playbackStateListener()
    private var mPlayer: SimpleExoPlayer? = null
    private var fullscreen = false
//    private var absPlayerInternal: SimpleExoPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)

        getIntentData()
    }


    private fun getIntentData() {
        if (intent.getSerializableExtra("teacherProblem") != null) {
            val teacherResponse = intent.getSerializableExtra("teacherProblem")
                    as TeacherProblemResponse.Body

            if (teacherResponse.type == 1) {
                ivPostImage.visibility = View.VISIBLE
                ivPostImage.loadImage(Constants.documents_URL + teacherResponse.document)

            } else if (teacherResponse.type == 2) {
                playerView.visibility = View.VISIBLE
                setPlayer(Constants.documents_URL + teacherResponse.document)

                Log.e("videoUrl", Constants.documents_URL + teacherResponse.document)
            }


        }
    }


    override fun onPause() {
        super.onPause()
        //Make sure the player stops playing if the user presses the home button .
        mPlayer?.playWhenReady = false
//        stopPlayer(exoPlayer,absPlayerInternal)
    }

    override fun onStop() {
        super.onStop()
        mPlayer?.playWhenReady = false
    }


    override fun onDestroy() {
        super.onDestroy()
        mPlayer?.release()
        mPlayer?.removeListener(playbackStateListener)
    }


    private fun setPlayer(url: String) {

        val dataSourceFactory = DefaultHttpDataSource.Factory()
// val mediaItem = MediaItem.fromUri(AppConstants.IMAGE_BASE_URL+item.media.original)
        val mediaItem =
            MediaItem.fromUri(url)
        val source = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)

        mPlayer = SimpleExoPlayer.Builder(this).build()
        mPlayer!!.setMediaSource(source)
        mPlayer!!.prepare()
        mPlayer!!.addListener(playbackStateListener)

        mPlayer!!.repeatMode = Player.REPEAT_MODE_OFF


        playerView.player = mPlayer
        mPlayer!!.playWhenReady = true

        val fullscreenButton = playerView.findViewById<ImageView>(R.id.exo_fullscreen_icon)
        fullscreenButton.setOnClickListener {

            if (fullscreen) {
                playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                fullscreenButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_fullscreen_expand
                    )
                )
                window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                val params: LinearLayout.LayoutParams =
                    playerView.layoutParams as LinearLayout.LayoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                playerView.layoutParams = params
                fullscreen = false;
            } else {
                playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                fullscreenButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_fullscreen_skrink
                    )
                )
                window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                val params: LinearLayout.LayoutParams =
                    playerView.layoutParams as LinearLayout.LayoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                playerView.layoutParams = params
                fullscreen = true
            }
        }

    }


    private fun playbackStateListener() = object : Player.EventListener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                ExoPlayer.STATE_IDLE -> {
                    Log.d("state", "idle")
                }
                ExoPlayer.STATE_BUFFERING -> {
                    Log.d("state", "buffering")
//                    progressBar.visibility = View.VISIBLE
                }
                ExoPlayer.STATE_READY -> {
                    Log.d("state", "ready")
//                    videoThumbnail.visibility = View.GONE
//                    progressBar.visibility = View.GONE

                }
                ExoPlayer.STATE_ENDED -> {
                    Log.d("state", "ended")
                }
            }
        }
    }

}