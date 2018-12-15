package networkonmainthreadexception.tipfortrip


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_auth.*
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth


class AuthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonAuth.setOnClickListener { onAuthClick() }
        textViewReg.setOnClickListener { fragmentManager?.setFragment(RegFragment()) }
    }

    private fun onAuthClick() {
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            context?.showToast("Заполните все поля")
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                context?.showToast("Auth OK")
                fragmentManager?.setFragment(TabsFragment())
            }
            .addOnFailureListener(MyFailureListener(context,
                    FirebaseNetworkException::class.java to "Нет подключения к интернету",
                    FirebaseAuthInvalidCredentialsException::class.java to "Неверный логин или пароль",
                    FirebaseAuthInvalidUserException::class.java to "Пользователь не найден"))
    }

}
