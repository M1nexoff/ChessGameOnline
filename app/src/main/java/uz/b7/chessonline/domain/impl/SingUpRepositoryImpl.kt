package uz.b7.chessonline.domain.impl

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.b7.chessonline.data.model.SingUpData
import uz.b7.chessonline.data.model.UserData
import uz.b7.chessonline.data.sourse.MyShar
import uz.b7.chessonline.domain.SingUpRepository
import java.util.UUID
import javax.inject.Inject

class SingUpRepositoryImpl @Inject constructor(
    private val myShar: MyShar
):SingUpRepository {
    private val fireStore = FirebaseFirestore.getInstance()
    override fun singUpUser(singUpData: SingUpData): Flow<Result<Unit>> = callbackFlow{
        val docId=UUID.randomUUID().toString()
        myShar.setUserInfo(UserData(singUpData.name,docId,true,0))
        fireStore.collection("users")
            .document(docId)
            .set(singUpData).addOnSuccessListener {
                trySend(Result.success(Unit))
                channel.close()
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
                channel.close()
            }
        awaitClose()
    }
}