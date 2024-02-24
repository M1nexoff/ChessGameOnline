package uz.b7.chessonline.domain.impl

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import uz.b7.chessonline.data.model.LogInData
import uz.b7.chessonline.data.model.UserData
import uz.b7.chessonline.data.sourse.MyShar
import uz.b7.chessonline.domain.LogInRepository
import java.util.UUID
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(
    private val myShar: MyShar
):LogInRepository {
    private val fireStore = FirebaseFirestore.getInstance()
    override fun logIn(logInData: LogInData): Flow<Result<Unit>> = callbackFlow{
        fireStore.collection("users")
            .whereEqualTo("phone",logInData.phone)
            .whereEqualTo("password",logInData.password)
            .limit(1)
            .get().addOnSuccessListener {
                if (it.size()==0){
                    trySend(Result.failure(Throwable("Hato password yoki phone!!")))
                    channel.close()
                }else{
                    it.documents[0].apply {
                        val name=(data?.getOrDefault("name","")?:"Ali").toString()
                        val id=id
                        val rating=(data?.getOrDefault("rating",0)?:0).toString()
                        myShar.setUserInfo(UserData(name,id,true,rating.toInt()))
                        trySend(Result.success(Unit))
                        channel.close()
                    }
                }
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
                channel.close()
            }
        awaitClose()
    }

}