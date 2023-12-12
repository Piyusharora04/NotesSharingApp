package com.example.notessharingapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.notessharingapp.ExpertEventBus.UploadAmount
import com.example.notessharingapp.ExpertEventBus.UploadImage
import com.example.notessharingapp.ExpertEventBus.UploadNoteName
import com.example.notessharingapp.ExpertEventBus.UploadPDF
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.greenrobot.eventbus.EventBus

class upload_notes : AppCompatActivity() {

    private val STORAGE_PERMISSION_CODE = 101

    private val PICK_PDF_REQUEST_CODE = 123
    private val PICK_IMG_REQUEST_CODE = 123
    private lateinit var selectedFileUri: Uri
    private lateinit var selectedImgUri: Uri
    private lateinit var storageReference: StorageReference
    private lateinit var donldUrlPDF: String
    private lateinit var donldUrlImg: String
    private lateinit var UPI: String
    private lateinit var desc: String
    private lateinit var price: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_notes)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

//        db = FirebaseFirestore.getInstance()

//        FirebaseApp.initializeApp(this)

        val uploadPDF: Button = findViewById(R.id.btnSelectPdf)
        val uploadImage: Button = findViewById(R.id.btnSelectImage)
        val uploadAll: Button = findViewById(R.id.btnUploadAll)
        val upiId:EditText = findViewById(R.id.UPIid)
        val description:EditText = findViewById(R.id.descriptionEditText)
        val amount:EditText = findViewById(R.id.amountEditText)
        val name:EditText = findViewById(R.id.nameEditText)

        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.reference

        storageReference = FirebaseStorage.getInstance().reference

        uploadPDF.setOnClickListener {
            openFileChooser()
        }

        uploadImage.setOnClickListener {
            openImageChooser()
        }

        uploadAll.setOnClickListener {
            val myData = NoteData(
                name = name.text.toString(),
                img = donldUrlImg,
                pdf = donldUrlPDF,
                upiID = upiId.text.toString(),
                description = description.text.toString(),
                amount = amount.text.toString(),
                purchased = "0",
                verified = ""

            )

//            Log.d("newdata", "${name.text.toString()}")
//            Log.d("newdata", "${amount.text.toString()}")
//            Log.d("newdata", "$donldUrlPDF")
//            Log.d("newdata", "$donldUrlImg")


            val sharedPref = getSharedPreferences("Piyush", Context.MODE_PRIVATE)
            // Set the value of the object under a specific child node
//            databaseReference.child("data2").child(sharedPref.getString("username","").toString() + " " + System.currentTimeMillis().toString()).setValue(myData)
            databaseReference.child("data2").child(name.text.toString()).setValue(myData)


            // Sending data to the expert for verification
//            val noteNameEvent = UploadNoteName("${name.text}")
//            val pdfNameEvent = UploadPDF("$donldUrlPDF")
//            val imageEvent = UploadImage("$donldUrlImg")
//            val amountEvent = UploadAmount("${amount.text}")
//
//            EventBus.getDefault().post(noteNameEvent)
//            EventBus.getDefault().post(pdfNameEvent)
//            EventBus.getDefault().post(imageEvent)
//            EventBus.getDefault().post(amountEvent)


            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userUPI",upiId.text.toString())
            intent.putExtra("userAmount",amount.text.toString())
            intent.putExtra("userDes",description.text.toString())
            startActivity(intent)
            finish()
        }

    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*" // Specify the MIME type for images
        startActivityForResult(intent, PICK_IMG_REQUEST_CODE)
    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf" // Specify the MIME type for PDF files
        startActivityForResult(intent, PICK_PDF_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        if (requestCode == PICK_PDF_REQUEST_CODE && requestCode == PICK_IMG_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            // Handle the selected PDF file
//            selectedFileUri = data?.data ?: return
//            Log.d("debug", selectedFileUri.toString())
//
//            selectedImgUri = data?.data ?: return
//            Log.d("debug", selectedImgUri.toString())
//
//            // Upload the selected image to Firebase Storage
//            uploadImageToFirebaseStorage(selectedImgUri)
//
//            // Upload the selected PDF file to Firebase Storage
//            uploadPdfToFirebaseStorage(selectedFileUri)
//        }

//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == PICK_PDF_REQUEST_CODE) {
//                // Handle the selected PDF file
//                selectedFileUri = data?.data ?: return
//                Log.d("debug", "Selected PDF: ${selectedFileUri.toString()}")
//
//                // Upload the selected PDF file to Firebase Storage
//                uploadPdfToFirebaseStorage(selectedFileUri)
//            } else if (requestCode == PICK_IMG_REQUEST_CODE) {
//                // Handle the selected image
//                selectedImgUri = data?.data ?: return
//                Log.d("debug", "Selected Image: ${selectedImgUri.toString()}")
//
//                // Upload the selected image to Firebase Storage
//                uploadImageToFirebaseStorage(selectedImgUri)
//            }
//        }

        if (resultCode == Activity.RESULT_OK && data != null) {
            val selectedUri = data.data

            if (selectedUri != null) {
                // Determine whether it's a PDF or an image based on the MIME type
                val mimeType = contentResolver.getType(selectedUri)

                if (mimeType != null) {
                    if (mimeType == "application/pdf") {
                        // Handle PDF
                        selectedFileUri = selectedUri
                        Log.d("debug", "Selected PDF: ${selectedFileUri.toString()}")
                        uploadPdfToFirebaseStorage(selectedFileUri)
                    } else if (mimeType.startsWith("image/")) {
                        // Handle image
                        selectedImgUri = selectedUri
                        Log.d("debug", "Selected Image: ${selectedImgUri.toString()}")
                        uploadImageToFirebaseStorage(selectedImgUri)
                    } else {
                        // Handle unknown file type or show an error message
                        Toast.makeText(this, "Unsupported file type", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Handle case where MIME type is null
                    Toast.makeText(this, "Unable to determine file type", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Handle case where selectedUri is null
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadPdfToFirebaseStorage(uri: Uri) {
        val fileReference = storageReference.child("uploads/${uri.lastPathSegment}")

        val uploadTask = fileReference.putFile(uri)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            // File uploaded successfully
            // Retrieve the download URL

            fileReference.downloadUrl.addOnSuccessListener { downloadUrl ->
                donldUrlPDF = downloadUrl.toString()
                Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
                Log.i("debug", downloadUrl.toString())
            }
        }.addOnFailureListener { exception ->
            // Handle errors if the upload fails
            Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show()
            exception.printStackTrace()
            // You can display an error message to the user here
        }
    }
    private fun uploadImageToFirebaseStorage(uri: Uri) {
        val fileReference = storageReference.child("images/${uri.lastPathSegment}")

        val uploadTask = fileReference.putFile(uri)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Image uploaded successfully
            // Retrieve the download URL

            fileReference.downloadUrl.addOnSuccessListener { downloadUrl ->
                donldUrlImg = downloadUrl.toString()
                Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                Log.i("debug", downloadUrl.toString())
                // Handle the download URL as needed
            }
        }.addOnFailureListener { exception ->
            // Handle errors if the upload fails
            Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
            exception.printStackTrace()
            // You can display an error message to the user here
        }
    }
}
