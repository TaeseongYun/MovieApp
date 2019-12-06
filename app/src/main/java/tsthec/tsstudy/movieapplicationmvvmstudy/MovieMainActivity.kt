package tsthec.tsstudy.movieapplicationmvvmstudy


import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*

class MovieMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        (getSystemService(Context.SEARCH_SERVICE) as SearchManager).run {
            (menu?.findItem(R.id.app_bar_search)?.actionView as SearchView).run {
                queryHint = getString(R.string.query_string_hint)
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        textView.text = query
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
//                        textView.text = query
                        return false
                    }

                })
            }
        }
        return true
    }
}
