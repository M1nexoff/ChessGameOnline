package uz.b7.chessonline.domain.impl

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.b7.chessonline.data.model.UserData
import uz.b7.chessonline.data.sourse.MyShar
import uz.b7.chessonline.domain.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val myShar: MyShar
):SearchRepository {
    private val fireStore = FirebaseFirestore.getInstance()
    override fun getAllActiveUser(): Flow<Result<List<UserData>>> = callbackFlow{
        fireStore.collection("users")
            .whereEqualTo("isActive",true)
            .get().addOnSuccessListener {
                val data=ArrayList<UserData>()
                val size=it.size()
                var index=0
                it.forEach {
                    index++
                    if (myShar.getUserInfo().id!=it.id){
                        val name=(it.data.getOrDefault("name","")?:"User").toString()
                        val rating=(it.data.getOrDefault("rating",0)?:0).toString()
                        data.add(UserData(name,it.id,true, rating = rating.toInt()))
                    }
                    if (index==size){
                        trySend(Result.success(data))
                    }
                }
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }


}