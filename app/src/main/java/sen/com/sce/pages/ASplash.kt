package sen.com.sce.pages

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

/**
 * Created by korneliussendy on 02/08/20,
 * at 21.19.
 * SCE
 */
class ASplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            delay(1500L)
            withContext(Dispatchers.Main) {
                toMain()
                finish()
            }
        }

    }

    private fun toMain() {
        startActivity(Intent(this, AMain::class.java))
    }
}