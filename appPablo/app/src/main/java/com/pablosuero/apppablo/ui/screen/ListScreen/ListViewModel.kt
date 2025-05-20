import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.pablosuero.apppablo.data.MealItem
import com.pablosuero.apppablo.data.repositories.RemoteConnection
import com.pablosuero.apppablo.data.repositories.models.Meal

class ListViewModel : ViewModel() {
    private val _lista = MutableLiveData<List<MealItem>>()
    val lista: LiveData<List<MealItem>> = _lista

    private val _progressBar: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressBar: LiveData<Boolean> = _progressBar

    private val mealMap = mutableMapOf<Int, Meal>()  // NUEVO
    fun getMealById(id: Int): Meal? = mealMap[id]    // NUEVO

    init {
        _progressBar.value = true
        viewModelScope.launch {
            _progressBar.value = true
            val result = mutableListOf<MealItem>()
            repeat(10) {
                try {
                    val response = RemoteConnection.service.getRandomMeal()
                    response.meals?.firstOrNull()?.let { meal ->
                        val id = meal.idMeal.toIntOrNull() ?: 0
                        mealMap[id] = meal  // NUEVO
                        result.add(
                            MealItem(
                                id = id,
                                name = meal.strMeal,
                                imageURL = meal.strMealThumb ?: ""
                            )
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            _lista.value = result
            _progressBar.value = false
        }
    }
}
