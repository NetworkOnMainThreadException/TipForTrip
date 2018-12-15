package networkonmainthreadexception.tipfortrip


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_reg.*
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuth


class RegFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reg, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonReg.setOnClickListener { onRegClick() }
    }

    private fun onRegClick() {
        val name = editTextName.text.toString().trim()
        val surname = editTextSurname.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString()
        val passwordRepeat = editTextPasswordRepeat.text.toString()

        if (name.isEmpty() ||
            surname.isEmpty() ||
            email.isEmpty() ||
            password.isEmpty() ||
            passwordRepeat.isEmpty()
        ) {
            context?.showToast("Заполните все поля")
            return
        }

        if (password != passwordRepeat) {
            context?.showToast("Пароли не совпадают")
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                db.collection("users").document(it.user.uid)
                    .set(Profile(name, surname, "[phone]", "[cityid]"))
                    .addOnSuccessListener {
                        context?.showToast("Reg OK")
                        FirebaseAuth.getInstance().signOut()
                        fragmentManager?.setFragment(AuthFragment())
                    }
                    .addOnFailureListener(MyFailureListener(context))
            }
            .addOnFailureListener(
                MyFailureListener(
                    context,
                    FirebaseAuthWeakPasswordException::class.java
                            to "Слишком короткий пароль: введите хотя бы 6 символов",
                    FirebaseAuthInvalidCredentialsException::class.java to "Некорректный email",
                    FirebaseAuthUserCollisionException::class.java to "Email уже используется"
                )
            )
    }

}
