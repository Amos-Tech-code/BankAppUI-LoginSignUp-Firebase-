package com.example.bankapp.ui

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {

    // Firebase Auth
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    // LiveData to store user email
    private val _userEmail = MutableLiveData<String?>()
    val userEmail: LiveData<String?> get() = _userEmail

    init {
        val currentUserEmail = auth.currentUser?.email
        _userEmail.value = currentUserEmail
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        _authState.value = if (auth.currentUser == null) {
            AuthState.Unauthenticated
        } else {
            AuthState.Authenticated
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or Password can't be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun signUp(email: String, password: String, cPassword: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or Password can't be empty")
            return
        }
        if (password != cPassword) {
            _authState.value = AuthState.Error("Both passwords must match")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    // FireStore: Save user data
    fun saveData(
        userData: UserData,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val fireStoreRef = Firebase.firestore.collection("users")
                    val documentReference = fireStoreRef.add(userData).await()  // Wait for the Firestore operation
                    val autoGeneratedId = documentReference.id
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Successfully Signed In", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    // FireStore: Retrieve user data based on email or another field
    fun retrieveData(context: Context, data: (UserData) -> Unit) {
        val currentUserEmail = auth.currentUser?.email
        if (currentUserEmail != null) {
            viewModelScope.launch {
                try {
                    withContext(Dispatchers.IO) {
                        val fireStoreRef = Firebase.firestore.collection("users")
                        val querySnapshot = fireStoreRef.whereEqualTo("email", currentUserEmail).get().await()

                        if (!querySnapshot.isEmpty) {
                            val document = querySnapshot.documents[0]
                            val userData = document.toObject<UserData>()
                            if (userData != null) {
                                withContext(Dispatchers.Main) {
                                    data(userData)
                                    //Toast.makeText(context, "User data retrieved: ${userData.fname}", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Failed to parse user data", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "No User Data Found", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(context, "No current user email found", Toast.LENGTH_SHORT).show()
        }
    }


    // Image Upload
    // LiveData to observe and display the image URL
    private val _imageUrl = MutableLiveData<String?>()
    val imageUrl: LiveData<String?> get() = _imageUrl

    // Function to handle the image picker result and upload the image
    fun uploadImageForCurrentUser(
        imageUri: Uri,    // Image URI picked from the Image Picker
        userEmail: String,  // Use email to identify the user since ID is auto-generated
        context: Context
    ) {
        // Retrieve the user document based on the email
        getUserDocumentByEmail(
            
            email = userEmail,
            context = context,
            onSuccess = { documentId ->
                // Upload the image and store the URL in Firestore for the specific user document
                uploadImage(
                    imageUri = imageUri,
                    userId = documentId,
                    context = context,
                    onSuccess = { downloadUrl ->

                        // Save the download URL to Firestore
                        saveImageUrlToFirestore(documentId, downloadUrl, context)
                    },
                    onFailure = { errorMessage ->
                        // Handle failure
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                )
            },
            onFailure = { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        )
    }

    // Function to get the user document ID based on their email
    private fun getUserDocumentByEmail(
        email: String,
        context: Context,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val fireStoreRef = Firebase.firestore.collection("users")

        fireStoreRef.whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    onSuccess(document.id)  // Return the document ID
                } else {
                    onFailure("User document not found")
                }
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Error retrieving user document")
            }
    }

    // Function to upload the image to Firebase Storage
    private fun uploadImage(
        imageUri: Uri,
        userId: String,   // Using the Firestore document ID now
        context: Context,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val storageRef = FirebaseStorage.getInstance().reference
            .child("user_images/$userId.jpg")

        storageRef.putFile(imageUri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    onSuccess(downloadUrl)  // Pass the URL to the success callback
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception.message ?: "Image upload failed")
            }
    }

    // Function to save the image URL to Firestore in the user's document
    private fun saveImageUrlToFirestore(
        userId: String,   // Document ID from Firestore
        imageUrl: String,
        context: Context
    ) {
        val fireStoreRef = Firebase.firestore.collection("users").document(userId)

        fireStoreRef.update("imageUrl", imageUrl)
            .addOnSuccessListener {
                // Update LiveData to display the image in the UI
                _imageUrl.postValue(imageUrl)
                Toast.makeText(context, "Image URL successfully added to Firestore", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
    }

    fun fetchUserImage(userEmail: String, context: Context) {
        // Ensure the email is unique in Firestore.
        getUserDocumentByEmail(
            email = userEmail,
            context = context,
            onSuccess = { documentId ->
                // Fetch the document associated with the user's email
                val fireStoreRef = Firebase.firestore.collection("users").document(documentId)
                fireStoreRef.get()
                    .addOnSuccessListener { documentSnapshot ->
                        if (documentSnapshot.exists()) {
                            // Get the imageUrl field from Firestore
                            val imageUrl = documentSnapshot.getString("imageUrl")
                            // Update the LiveData with the retrieved image URL
                            _imageUrl.postValue(imageUrl)
                        } else {
                            Toast.makeText(context, "No image found for user", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
            },
            onFailure = { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        )
    }

}






sealed class AuthState {
    data object Authenticated: AuthState()
    data object Unauthenticated: AuthState()
    data object Loading: AuthState()
    data class Error(val message: String): AuthState()

}