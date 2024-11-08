package com.example.bankapp.ui

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Paid
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.bankapp.R
import com.example.bankapp.data.ScreenNavigation
import com.example.bankapp.data.UserData
import com.example.bankapp.ui.theme.BlueCenter
import com.example.bankapp.ui.theme.BlueEnd
import com.example.bankapp.ui.theme.DarkBlue2
import com.example.bankapp.ui.theme.DarkPurple
import kotlinx.coroutines.async


@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    context: Context
) {

    val authState = authViewModel.authState.observeAsState()
    var showLogoutDialogue by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> {
                navController.navigate(ScreenNavigation.GetStartedScreen.route) {
                    popUpTo(navController.graph.startDestinationId) { // Use the start destination to clear the entire back stack
                        inclusive = true // Clear all backstacks
                    }
                    launchSingleTop = true // Avoid multiple instances of the same destination
                }
            }
            else -> Unit
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.LightGray)
    ) {
        AccountOverview()
        AccountActions()
        PersonalInfo(authViewModel = authViewModel, context = context)
        RecentActivity()
        //Logout
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .clip(RoundedCornerShape(8.dp))
            ,
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(0.7f)),
            onClick = {
                showLogoutDialogue = true
            }
        ) {
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Logout,
                    contentDescription = "Log Out",
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "Log Out",
                    fontSize = 17.sp
                )
            }
        }
        // Alert Dialogue
        if (showLogoutDialogue) {
            AlertDialog(
                title = { Text(text = "Log Out")},
                text = { Text(text = "Are you sure you want to log out?")},
                onDismissRequest = { showLogoutDialogue = false },
                dismissButton = { Button(onClick = { showLogoutDialogue = false }) {
                    Text(text = "Cancel")
                } },
                confirmButton = {
                   Button(onClick = { authViewModel.signOut() }) {
                       Text(text = "Ok")
                   }
                }
            )
        }
    }
}

@Composable
fun AccountOverview() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .height(150.dp)
            .background(
                Brush.verticalGradient(
                    listOf(DarkPurple, BlueCenter, BlueEnd, DarkPurple)
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column {
                Text(
                    text = "Account Overview",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$12,893.84",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )

                    Text(
                        text = "Balance",
                        color = Color.LightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                        )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "4",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )

                    Text(
                        text = "Cards",
                        color = Color.LightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "8",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )

                    Text(
                        text = "Transactions",
                        color = Color.LightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
            }
        }


    }
}


@Composable
fun AccountActions() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .height(80.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(DarkBlue2)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Rounded.Wallet,
                    contentDescription = "Add Funds",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "Add Funds",
                    color = Color.White,
                    fontSize = 17.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .height(80.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(DarkBlue2)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Send,
                    contentDescription = "Transfer",
                    tint = Color.White,
                    modifier = Modifier.size(35.dp)
                )
                Text(
                    text = "Transfer",
                    color = Color.White,
                    fontSize = 17.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .height(80.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(DarkBlue2)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.CreditCard,
                    contentDescription = "Manage Cards",
                    tint = Color.White,
                    modifier = Modifier.size(35.dp)
                )
                Text(
                    text = "Manage\nCards",
                    color = Color.White,
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun PersonalInfo(
    authViewModel: AuthViewModel,
    context: Context
) {

    val userEmail by authViewModel.userEmail.observeAsState()

    // State to hold the retrieved UserData
    val userDataState = remember { mutableStateOf<UserData?>(null) }

    // Fetch user data and image when userEmail is available
    LaunchedEffect(userEmail) {
        userEmail?.let {
            // Fetch user image based on the logged-in user's email
            authViewModel.fetchUserImage(it, context)

            // Retrieve user data
            authViewModel.retrieveData(context) { userData ->
                userDataState.value = userData  // Update the state with the retrieved data
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Personal Information",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Row {
                Text(
                    text = "Full Name",
                    fontSize = 17.sp,
                    modifier = Modifier.weight(1f)
                )
                // Safely access userDataState to avoid null errors
                userDataState.value?.let { userData ->
                    Text(
                        text = "${userData.fname} ${userData.lname}",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                } ?: run {
                    Text(
                        text = "Loading...",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row {
                Text(
                    text = "Email",
                    fontSize = 17.sp,
                    modifier = Modifier.weight(1f)
                )
                userDataState.value?.let { userData ->
                    Text(
                        text = userData.email,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                } ?: run {
                    Text(
                        text = "Loading...",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row {
                Text(
                    text = "Phone",
                    fontSize = 17.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "+1234567890",
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row {
                Text(
                    text = "Address",
                    fontSize = 17.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1223 Main St, Springfield",
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }


        }
    }
}

@Composable
fun RecentActivity() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Recent Activity",
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = null,
                    tint = Color.Blue
                )
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "Amazon Purchase",
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )
                    Text(
                        text = "July 15, 2024",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 17.sp
                    )
                }
                Text(
                    text = "-$150.00",
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Paid,
                    contentDescription = null,
                    tint = Color.Green
                )
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "PayCheck Deposit",
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )
                    Text(
                        text = "July 14, 2024",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 17.sp
                    )
                }
                Text(
                    text = "+$2,500.00",
                    fontWeight = FontWeight.Bold
                )
            }


        }
    }
}

@Composable
fun ProfileScreenTopBar(
    authViewModel: AuthViewModel,
    context: Context
) {

    //Dropdown declaration variable
    var dropDownExpanded by remember {
        mutableStateOf(false)
    }

    val imageUrl by authViewModel.imageUrl.observeAsState()
    val userEmail by authViewModel.userEmail.observeAsState()

    // State to hold the retrieved UserData
    val userDataState = remember { mutableStateOf<UserData?>(null) }

    // Fetch user data and image when userEmail is available
    LaunchedEffect(userEmail) {
        userEmail?.let { email ->
            // Fetch image and data concurrently
            val fetchImageTask = async { authViewModel.fetchUserImage(email, context) }
            val fetchDataTask = async { authViewModel.retrieveData(context) { userData ->
                userDataState.value = userData
            }
            }

            // Await both tasks
            fetchImageTask.await()
            fetchDataTask.await()
        }
    }

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            // Upload the selected image to Firebase
            userEmail?.let { email ->
                authViewModel.uploadImageForCurrentUser(
                    imageUri = uri,
                    userEmail = email,
                    context = context
                )
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp, color = Color.Blue, shape = CircleShape
                )
                .padding(3.dp)
        ) {
            if (imageUrl.isNullOrEmpty()) {
                // Show the icon when there's no image
                Icon(
                    imageVector = Icons.Rounded.AddAPhoto,
                    contentDescription = "User Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable {
                            // Trigger image picker on icon click
                            imagePickerLauncher.launch(
                                PickVisualMediaRequest
                                    .Builder()
                                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    .build()
                            )
                        },
                )
            } else {
                // Show the user's image if available
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "User Image",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            // Safely access userDataState to avoid null errors
            userDataState.value?.let { userData ->
                Text(
                    text = "${userData.fname} ${userData.lname}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            } ?: run {
                Text(
                    text = "Loading...",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            // Safely access userDataState to avoid null errors
            userDataState.value?.let { userData ->
                Text(
                    text = userData.email,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            } ?: run {
                Text(
                    text = "Loading...",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        IconButton(onClick = { dropDownExpanded = !dropDownExpanded }) {
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = null
            )

            //DropDown Menu
            DropdownMenu(
                expanded = dropDownExpanded,
                onDismissRequest = { dropDownExpanded = false },
                modifier = Modifier
                    .width(200.dp)
                    .height(250.dp)
                    .padding(8.dp)
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Settings") },
                    onClick = { /*TODO*/ }
                )
                DropdownMenuItem(
                    text = { Text(text = "Site 1") },
                    onClick = { /*TODO*/ }
                )
                DropdownMenuItem(
                    text = { Text(text = "Site 2") },
                    onClick = { /*TODO*/ }
                )
                DropdownMenuItem(
                    text = { Text(text = "Site 3") },
                    onClick = { /*TODO*/ }
                )
            }
        }
    }
}