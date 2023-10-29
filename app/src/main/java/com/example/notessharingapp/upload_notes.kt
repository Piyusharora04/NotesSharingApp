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
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

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
        val upiID:EditText = findViewById(R.id.UPIid)
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
                upiID = upiID.text.toString(),
                description = description.text.toString(),
                amount = amount.text.toString()
            )

            val sharedPref = getSharedPreferences("Piyush", Context.MODE_PRIVATE)
// Set the value of the object under a specific child node
            databaseReference.child("data").child(sharedPref.getString("username","").toString() + System.currentTimeMillis().toString()).setValue(myData)


            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userUPI",upiID.text.toString())
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

        if (requestCode == PICK_PDF_REQUEST_CODE && requestCode == PICK_IMG_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Handle the selected PDF file
            selectedFileUri = data?.data ?: return
            Log.i("debug", selectedFileUri.toString())

            selectedImgUri = data?.data ?: return
            Log.i("debug", selectedImgUri.toString())

            // Upload the selected image to Firebase Storage
            uploadImageToFirebaseStorage(selectedImgUri)

            // Upload the selected PDF file to Firebase Storage
            uploadPdfToFirebaseStorage(selectedFileUri)
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

//    private fun displayPdf(fileUrl: String) {
//        // Load and display the PDF using a PDF viewer library or WebView
//        // For example, if you're using a WebView:
//        val webView: WebView = findViewById(R.id.webView)
//        webView.settings.javaScriptEnabled = true
//        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=$fileUrl")
//    }


//    private fun uploadPdfToFirebaseStorage(uri: Uri) {
//        // Create a reference to the file you want to upload
//        val fileRef = storageReference.child("uploads/${uri.lastPathSegment}")
//
//        try {
//            // Put the file to Firebase Storage
//            val uploadTask = fileRef.putFile(uri)
//
//            uploadTask.addOnSuccessListener { taskSnapshot ->
//                // File uploaded successfully
//                // Retrieve the download URL
//                fileRef.downloadUrl.addOnSuccessListener { downloadUrl ->
//                    val fileUrl = downloadUrl.toString()
//                    // Display the PDF file or handle it as needed
//                }
//            }.addOnFailureListener { exception ->
//                // Handle errors if the upload fails
//                exception.printStackTrace()
//                // You can display an error message to the user here
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }


//Please ensure that you have the Firebase SDK set up correctly in your project, and the Firebase Storage rules are configured to allow read and write access. If the file is still not being uploaded, please double-check your Firebase setup and ensure that you have a working internet connection on your device.


//        private fun displayPdf(fileUrl: String) {
//            // Load and display the PDF using a PDF viewer library or WebView
//            // For example, if you're using a WebView:
//            val webView: WebView = findViewById(R.id.webView)
//            webView.settings.javaScriptEnabled = true
//            webView.loadUrl("https://docs.google.com/gview?embedded=true&url=$fileUrl")
//        }private fun isStoragePermissionGranted(): Boolean {
////        val readPermission = ContextCompat.checkSelfPermission(
////            this,
////            android.Manifest.permission.READ_EXTERNAL_STORAGE
////        )
////        val writePermission = ContextCompat.checkSelfPermission(
////            this,
////            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
////        )
////        return readPermission == PackageManager.PERMISSION_GRANTED &&
////                writePermission == PackageManager.PERMISSION_GRANTED
////    }
////
////    private fun requestStoragePermission() {
////        ActivityCompat.requestPermissions(
////            this,
////            arrayOf(
////                android.Manifest.permission.READ_EXTERNAL_STORAGE,
////                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
////            ),
////            STORAGE_PERMISSION_CODE
////        )
////    }
