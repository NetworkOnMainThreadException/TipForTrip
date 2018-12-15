package networkonmainthreadexception.tipfortrip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (FirebaseAuth.getInstance().currentUser == null) {
            supportFragmentManager.setFragment(AuthFragment())
        } else {
            supportFragmentManager.setFragment(TabsFragment())
        }
    }
}
