import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.CurrencyBitcoin
import androidx.compose.material.icons.rounded.CurrencyPound
import androidx.compose.material.icons.rounded.CurrencyYen
import androidx.compose.material.icons.rounded.Euro
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.bankapp.data.ScreenNavigation
import com.example.bankapp.ui.theme.BlueCenter
import com.example.bankapp.ui.theme.BlueEnd
import com.example.bankapp.ui.theme.DarkBlue
import com.example.bankapp.ui.theme.DarkPurple
import com.example.bankapp.ui.theme.SkyBlue


//@Preview(showBackground = true)
@Composable
fun GetStartedScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()

    ) {
        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(DarkPurple, BlueCenter, BlueEnd, SkyBlue)
                    )
                )
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                // Create references for each icon
                val (dollar, bitcoin, euro, yen, pound, rupee) = createRefs()

                // Place each icon with irregular constraints
                Icon(
                    imageVector = Icons.Rounded.AttachMoney,
                    contentDescription = "Dollar",
                    tint = Color.White,
                    modifier = Modifier
                        .size(50.dp)
                        .constrainAs(dollar) {
                            top.linkTo(parent.top, 30.dp)
                            start.linkTo(parent.start, 30.dp)
                        }
                )

                Icon(
                    imageVector = Icons.Rounded.CurrencyBitcoin,
                    contentDescription = "Bitcoin",
                    tint = Color.White,
                    modifier = Modifier
                        .size(50.dp)
                        .constrainAs(bitcoin) {
                            top.linkTo(dollar.bottom, 20.dp)
                            start.linkTo(dollar.end, 8.dp)
                        }
                )

                Icon(
                    imageVector = Icons.Rounded.Euro,
                    contentDescription = "Euro",
                    tint = Color.White,
                    modifier = Modifier
                        .size(50.dp)
                        .constrainAs(euro) {
                            top.linkTo(dollar.top, 5.dp)
                            start.linkTo(bitcoin.end, 1.dp)
                        }
                )

                Icon(
                    imageVector = Icons.Rounded.CurrencyYen,
                    contentDescription = "Yen",
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
                        .constrainAs(yen) {
                            bottom.linkTo(parent.bottom, 40.dp)
                            start.linkTo(parent.start, 50.dp)
                        }
                )

                Icon(
                    imageVector = Icons.Rounded.CurrencyPound,
                    contentDescription = "Pound",
                    tint = Color.White,
                    modifier = Modifier
                        .size(50.dp)
                        .constrainAs(pound) {
                            top.linkTo(yen.top)
                            start.linkTo(euro.end, 8.dp)
                        }
                )

                Icon(
                    imageVector = Icons.Rounded.AttachMoney,
                    contentDescription = "Dollar",
                    tint = Color.White,
                    modifier = Modifier
                        .size(50.dp)
                        .constrainAs(rupee) {
                            bottom.linkTo(pound.top, 30.dp)
                            end.linkTo(parent.end, 70.dp)
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(60.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Innovative Digital App Financial Planner",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Muhoro helps you manage your financial problems easily and efficiently",
                color = Color.Blue,
                textAlign = TextAlign.Center,
                fontSize = 17.sp,
                modifier = Modifier
                    .padding(horizontal = 30.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    navController.navigate(ScreenNavigation.LoginScreen.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkBlue
                )
            ) {
                Text(text = "Get Started")
            }
        }
    }
}
