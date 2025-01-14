package com.example.myapplication

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class ViewModel : ViewModel() {
    private val _courseList = mutableStateListOf<GridModal?>()
    val courseList: List<GridModal?> = _courseList

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        fetchCourses()
    }

    private fun fetchCourses() {
        db.collection("Data").get().addOnSuccessListener { snapshot ->
            if (!snapshot.isEmpty) {
                val list: List<DocumentSnapshot> = snapshot.documents
                for (d in list) {
                    val dataModal: GridModal? = d.toObject(GridModal::class.java)
                    dataModal?.let {
                        _courseList.add(
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
