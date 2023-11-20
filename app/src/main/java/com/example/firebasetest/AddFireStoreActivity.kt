package com.example.firebasetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.firebasetest.MyApplication.Companion.db
import com.example.firebasetest.Utils.MyUtil
import com.example.firebasetest.databinding.ActivityAddFireStoreBinding
import java.util.Date

class AddFireStoreActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddFireStoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFireStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // FireStore crud 확인.
        // 아이디(기본값), 게시글 내용, 날짜(기본값),
        binding.insertBtn.setOnClickListener {
            val data = mapOf(
//                "email" to MyApplication.email,
                "content" to binding.inputEdt.text.toString(),
                "date" to MyUtil.dateToString(Date())
            )
            // 스토어에 넣기. NoSQL 기반, JSON과 비슷함.
            MyApplication.db.collection("TestBoard")
                // 데이터 추가
                .add(data)
                // 데이터 추가 성공후 실행할 콜백 함수
                .addOnCompleteListener{
                    Log.d("scb","글쓰기 성공")
                    Toast.makeText(this,"글쓰기 성공",Toast.LENGTH_SHORT).show()
                }
                // 데이터 추가 실패후 실행할 콜백 함수
                .addOnFailureListener {
                    Log.d("scb","글쓰기 실패")
                    Toast.makeText(this,"글쓰기 실패",Toast.LENGTH_SHORT).show()
                }

        }
        //불러오기 ,리사이클러뷰 이용해서, 조금있다가하고
        // 현재 한개만 샘플로 불러오기.
        // 공식문서 :
        // https://firebase.google.com/docs/firestore/query-data/get-data?hl=ko#kotlin+ktx

        val docRef = db.collection("TestBoard").document("4opNOO7LJ89u5e7L75cZ")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("scb", "DocumentSnapshot data: ${document.data}")
                    Toast.makeText(this,"DocumentSnapshot data: ${document.data}",Toast.LENGTH_SHORT).show()
                    binding.resultStoreView.text = document.data.toString()
                } else {
                    Log.d("scb", "No such document")
                    Toast.makeText(this,"No such document",Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("scb", "get failed with ", exception)
            }

        // 글 하나 삭제하기.
        // 공식 문서
        // https://firebase.google.com/docs/firestore/manage-data/delete-data?hl=ko
        binding.deleteBtn.setOnClickListener {
            db.collection("TestBoard").document("QLRXBVb0LLZG2GtWJOp4")
                .delete()
                .addOnSuccessListener {
                    Log.d("scb", "DocumentSnapshot successfully deleted!")
                    Toast.makeText(this,"삭제 성공",Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener { e ->
                    Log.w("scb", "Error deleting document", e)
                    Toast.makeText(this,"삭제 실패",Toast.LENGTH_SHORT).show()
                }
        }

        // 하나  업데이트
        // 공식문서
        // https://firebase.google.com/docs/firestore/manage-data/add-data?hl=ko
        binding.updateBtn.setOnClickListener {
            val washingtonRef = db.collection("TestBoard").document("TRshibbM8GYfXOdCenBw")

            // Set the "isCapital" field of the city 'DC'
            washingtonRef
                .update("capital", true)
                .addOnSuccessListener { Log.d("scb", "DocumentSnapshot successfully updated!")
                    Toast.makeText(this,"수정 성공",Toast.LENGTH_SHORT).show()}
                .addOnFailureListener { e -> Log.w("scb", "Error updating document", e)
                    Toast.makeText(this,"수정 실패",Toast.LENGTH_SHORT).show()}
        }

    } // onCreate
}