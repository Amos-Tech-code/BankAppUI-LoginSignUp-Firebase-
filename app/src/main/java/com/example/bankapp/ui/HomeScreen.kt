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
import androidx.compose.material.icons.automirrored.outlined.CompareArrows
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.bankapp.R
import com.example.bankapp.data.UserData
import com.example.bankapp.ui.theme.BlueCenter
import com.example.bankapp.ui.theme.BlueEnd
import com.example.bankapp.ui.theme.DarkBlue
import com.example.bankapp.ui.theme.DarkBlue2
import com.example.bankapp.ui.theme.DarkPurple
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {

    // Remember bottom sheet state
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    )
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheetContent {
                coroutineScope.launch {
                    scaffoldState.bottomSheetState.hide() // Collapse the sheet
                }
            }
        },
        sheetPeekHeight = 0.dp,  // Collapsed by default
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color(0xFFF5F5F5))
            ) {
        // Main content boxes and rows go here
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
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Your Balance",
                    color = Color.White,
                    fontSize = 20.sp
                )
                Text(
                    text = "$12,893.84",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                Text(
                    text = "**** **** **** 2004",
                    color = Color.White,
                    fontSize = 19.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .height(75.dp)
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
                        contentDescription = "Request Money",
                        tint = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { }
                    )
                    Text(
                        text = "Request",
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .height(75.dp)
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
                        imageVector = Icons.AutoMirrored.Outlined.CompareArrows,
                        contentDescription = "Transfer",
                        tint = Color.White,
                        modifier = Modifier
                            .size(35.dp)
                            .clickable { }
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
                    .height(75.dp)
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
                        imageVector = Icons.Outlined.MoreHoriz,
                        contentDescription = "Other",
                        tint = Color.White,
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                coroutineScope.launch {
                                    scaffoldState.bottomSheetState.expand() // Expand the bottom sheet
                                }
                            }
                    )
                    Text(
                        text = "Other",
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Latest People",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Box {
                    Column {
                        Icon(
                            imageVector = Icons.Outlined.PersonAdd,
                            contentDescription = "Add people",
                            modifier = Modifier
                                .size(45.dp)
                                .clickable { },
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(text = "Add", fontSize = 19.sp)
                    }
                }

                Box {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.avatar1),
                            contentDescription = "Add people",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                        Text(text = "Adnan", fontSize = 19.sp)
                    }
                }
                Box {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.avatar3),
                            contentDescription = "Add people",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                        Text(text = "Sarah", fontSize = 19.sp)
                    }
                }
                Box {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.avatar2),
                            contentDescription = "Add people",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                        Text(text = "John", fontSize = 19.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Recent Transactions",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
            )

            Box(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CreditCard,
                        contentDescription = null,
                        tint = DarkBlue,
                        modifier = Modifier.size(40.dp)
                    )
                    Column {
                        Text(
                            text = "Netflix Subscriptions",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp
                        )
                        Text(
                            text = "June 20, 2024 at 1:50 PM",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                        Text(
                            text = "-$150.00",
                            color = Color.Red,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
        //parent column
    }
})
}


@Composable
fun HomeScreenTopBar(
    authViewModel: AuthViewModel,
    context: Context
) {
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
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .border(1.dp, Color.Blue, CircleShape)
                .padding(3.dp)
                .clickable {
                    // Trigger image picker on icon click
                    imagePickerLauncher.launch(
                        PickVisualMediaRequest
                            .Builder()
                            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            .build()
                    )
                }
        ) {
            if (imageUrl.isNullOrEmpty()) {
                Icon(
                    imageVector = Icons.Rounded.AddAPhoto,
                    contentDescription = "User Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
            } else {
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

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Welcome Back \uD83D\uDC4B",
                fontSize = 16.sp,
            )

            userDataState.value?.let { userData ->
                Text(
                    text = "${userData.fname} ${userData.lname}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            } ?: Text(
                text = "Loading...",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Icon(
            imageVector = Icons.Rounded.ChatBubbleOutline,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            imageVector = Icons.Rounded.NotificationsNone,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}





@Composable
fun BottomSheetContent(
    onItemClick: () -> Unit
    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Other Options",
            fontSize = 19.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(BlueEnd)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "OPTION 1", color = Color.White, modifier = Modifier.clickable { }
                )
                Text(
                    text = "OPTION 2",color = Color.White, modifier = Modifier.clickable { }
                )
                Text(
                    text = "OPTION 3",color = Color.White, modifier = Modifier.clickable { }
                )
                Text(
                    text = "OPTION 4",color = Color.White, modifier = Modifier.clickable { }
                )
                Text(
                    text = "CLOSE",color = Color.White, modifier = Modifier.clickable {
                        //close
                        onItemClick()
                    }
                )
            }

        }
    }
}
