package com.example.myapplication

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class ViewModel : ViewModel() {
    private val _dataList = mutableStateListOf<GridModal?>()
    val data: List<GridModal?> = _dataList

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        fetchDB()
    }

    private fun fetchDB() {
        db.collection("Data").get().addOnSuccessListener { snapshot ->
            if (!snapshot.isEmpty) {
                val list: List<DocumentSnapshot> = snapshot.documents
                for (d in list) {
                    val dataModal: GridModal? = d.toObject(GridModal::class.java)
                    dataModal?.let {
                        _dataList.add(
                            GridModal(
                                location = it.location,
                                description = it.description,
                                imageUrl = it.imageUrl
                            )
                        )
                    }
                }
            }
        }
    }
}

data class GridModal(
    val location: String? = null,
    val description: String? = null,
    val imageUrl: String? = null
)
