package app.itmaster.mobile.coffeemasters.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DataManager: ViewModel() {
    var menu: List<Category> by mutableStateOf(listOf())
    var cart: List<ItemInCart> by mutableStateOf(listOf())

    init {
        fetchData()
    }

    fun fetchData() {
        // Ejecuta el getMenu en una corutina (algo así como un thread)
        viewModelScope.launch {
            menu = API.menuService.getMenu()
        }
    }

    fun cartAdd(product: Product) {
        val existingItem = cart.find {it.product == product}
        if (existingItem != null){
            cart = cart.map{
                if (it.product == product){
                    ItemInCart(product,it.quantity+1)
                } else {
                    it
                }

            }
        } else {
        cart = cart + ItemInCart(product, 1)
        }
    }

    fun cartRemove(product: Product) {
        val existingItem = cart.find { it.product == product }

        if (existingItem != null) {
            // Item is in the cart, decrement the quantity
            if (existingItem.quantity > 1) {
                // If quantity is greater than 1, decrement the quantity
                cart =cart.map {
                    if (it.product == product) {
                        ItemInCart(product, it.quantity - 1)
                    } else {
                        it
                    }
                }
            } else {
                // If quantity is 1, remove the item from the cart
               cart =cart.filterNot { it.product == product }
            }
        }
    }

    fun cartClear(){
        cart= emptyList()
    }

    }
