package app.itmaster.mobile.coffeemasters.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.lifecycle.viewmodel.compose.viewModel
import app.itmaster.mobile.coffeemasters.data.DataManager
import app.itmaster.mobile.coffeemasters.ui.theme.Alternative1
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderPage(dataManager: DataManager) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Column() {
        Box(
            modifier =
            Modifier
                .shadow(
                    elevation = 10.dp,
                    spotColor = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                )
                .fillMaxWidth()
                .background(colorScheme.surfaceVariant)

        ) {
            Column() {
                Text(
                    text = "ITEMS",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF976336),

                    modifier = Modifier
                        .background(colorScheme.surfaceVariant)
                        .padding(10.dp)
                )
                LazyColumn {
                    items(dataManager.cart) {
                        val total = it.product.price * it.quantity
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .drawBehind {
                                    drawLine(
                                        Color(0xFFECD0BA),
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = 2.dp.toPx()
                                    )
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Text(
                                "${it.quantity}x",
                                style = MaterialTheme.typography.bodySmall,
                                color = colorScheme.primary,
                                modifier = Modifier
                                    .padding(8.dp)
                            )


                            Text(
                                " ${it.product.name}",
                                modifier = Modifier
                                    .padding(16.dp)

                            )
                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                "$${total.toDecimalString()}",
                                modifier = Modifier


                            )
                            IconButton(onClick = { dataManager.cartRemove(it.product) }) {
                                Icon(
                                    Icons.Outlined.Delete,
                                    contentDescription = "Delete Icon"
                                )


                            }
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }

                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier =
            Modifier
                .shadow(
                    elevation = 10.dp,
                    spotColor = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                )
                .fillMaxWidth()
                .background(colorScheme.surfaceVariant)

        ) {
            Column {
                Text(
                    text = "NAME",
                    style = MaterialTheme.typography.bodyMedium,
                    color =  Color(0xFF976336),

                    modifier = Modifier
                        .background(colorScheme.surfaceVariant)
                        .padding(10.dp)
                )
                Row(modifier=Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically

                    ) {
                    SimpleOutlinedTextField()
                    }

                }
                Spacer(modifier = Modifier.height(10.dp))

            }
        Spacer(modifier = Modifier.height(10.dp))
Box(modifier = Modifier.align(Alignment.End)){
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Alternative1,
                contentColor = Color.White
            ),
            onClick = {
                dataManager.cartClear()
                val orderNumber = (1..1000).random()
                scope.launch {
                    snackbarHostState.showSnackbar("Order ${orderNumber} will be ready in a few minutes")
                }
            },
        ) {
            Text("Send Order")
        }
        SnackbarHost(hostState = snackbarHostState)

    }} }



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SimpleOutlinedTextField() {
        val vm:OrderViewModel = viewModel()

    OutlinedTextField(
        value = vm.order,
        onValueChange = {vm.update(it)},
        label = { Text("Name for order") },
        shape = AbsoluteRoundedCornerShape(50.dp)
        )


}
@OptIn(SavedStateHandleSaveableApi::class)
class OrderViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var order by savedStateHandle.saveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
        private set

    fun update(newMessage: TextFieldValue) {
        order = newMessage
    }
}




