package uz.b7.chessonline.presenter.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nasiyaapp.utils.myShortToast
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import uz.b7.chessonline.R
import uz.b7.chessonline.data.sourse.MyShar
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
     lateinit var myShar:MyShar
    private val fireStore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun isOnlineUser(){
        fireStore.collection("users")
            .document(myShar.getUserInfo().id)
            .update("isActive",true).addOnSuccessListener {
                "User Online".myShortToast(this)
            }
            .addOnFailureListener {
                "${it.message}".myShortToast(this)
            }
    }
    fun isOfflineUser(){
        fireStore.collection("users")
            .document(myShar.getUserInfo().id)
            .update("isActive",false).addOnSuccessListener {
            }
            .addOnFailureListener {
                "${it.message}".myShortToast(this)
            }
    }

    override fun onResume() {
        super.onResume()
        if (myShar.getUserInfo().id!=""){
            isOnlineUser()
        }
    }

    override fun onStop() {
        if (myShar.getUserInfo().id!=""){
            isOfflineUser()
        }
        super.onStop()

    }
}