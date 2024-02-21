package com.green.comma.ui.fairytale

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import com.green.comma.R
import com.green.comma.data.response.fairytale.ResponseFairytaleDetailDto
import com.green.comma.databinding.ActivityFairytaleDetailBinding
import com.green.comma.ui.compose.FairytaleTagElem
import com.green.comma.ui.compose.PreviewFairytaleListItem
import com.green.comma.ui.compose.theme.Green200
import com.green.comma.ui.compose.theme.Green500
import com.green.comma.ui.compose.theme.Orange200
import com.green.comma.ui.compose.theme.Orange500
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class FairytaleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFairytaleDetailBinding
    private val fairytaleViewModel: FairytaleViewModel by viewModels { FairytaleViewModelFactory(applicationContext) }
    private lateinit var fairytaleData: FairytaleData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fairytale_detail)
        binding = ActivityFairytaleDetailBinding.inflate(layoutInflater)

        val extras = intent.extras

        if(extras != null){
            fairytaleData = FairytaleData(
                extras.getLong("id"),
                extras.getString("title"),
                extras.getString("time"),
                extras.getString("videoId"),
                extras.getBoolean("subtitleTag"),
                extras.getBoolean("signTag"),
            )

            if(!fairytaleData.id?.equals(-1)!!){
                fairytaleViewModel.loadFairytaleDetail(fairytaleData.id!!)
            }
        }

        // Lifecycle의 상태에 따라 적절하게 동작할 수 있도록
        lifecycle.addObserver(binding.youtubePlayerView)

        fairytaleViewModel.itemDetail.observe(this) {
            setDetail(it)
        }

        setContentView(binding.root)
    }

    private fun setDetail(detailData: ResponseFairytaleDetailDto){
        println(fairytaleData)
        binding.tvFairytaleTitle.text = fairytaleData.title
        binding.tvFairytaleTime.text = fairytaleData.time + "분"
        binding.tvFairytaleDescription.text = detailData.description
        binding.youtubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                if(fairytaleData.videoId != null)
                    youTubePlayer.cueVideo(fairytaleData.videoId!!, 0f)
            }
        })
        binding.composeViewType.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Row(modifier = Modifier.padding(top = 10.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    if(fairytaleData.subtitleTag == true)
                        FairytaleTagElem("자막", Orange200, Orange500)
                    if(fairytaleData.signTag == true)
                        FairytaleTagElem("수화", Green200, Green500)
                }
            }
        }
        binding.composeViewFairytaleRecommend.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                PreviewFairytaleListItem(imgUrl = detailData.recommendImageUrl, 330.dp, 0.dp)
            }
        }
    }

    data class FairytaleData(
        val id: Long?,
        val title: String?,
        val time: String?,
        val videoId: String?,
        val subtitleTag: Boolean?,
        val signTag: Boolean?,
    )
}