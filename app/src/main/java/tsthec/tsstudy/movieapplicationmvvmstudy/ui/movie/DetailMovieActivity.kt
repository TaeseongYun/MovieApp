package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_detail_movie.*
import tsthec.tsstudy.movieapplicationmvvmstudy.R

class DetailMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        movieId.text = intent.getStringExtra("movieID") ?: "영화 id 값 안들어 왔어요"


    }
}
