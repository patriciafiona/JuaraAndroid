package com.patriciafiona.mycity.ui

import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import com.patriciafiona.mycity.data.MenuType
import com.patriciafiona.mycity.data.model.Data
import com.patriciafiona.mycity.data.source.DataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    val dataList: Map<MenuType, List<Data>> = emptyMap(),
    val currentMenu: MenuType = MenuType.Museum,
    val currentSelectedData: Data = DataSource.defaultData,
    val isShowingListPage: Boolean = true,
    val isShowingHomepage: Boolean = true
){
    val currentMenuData: List<Data> by lazy { dataList[currentMenu]!! }
}

class MainViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        var datas: Map<MenuType, List<Data>> =
            DataSource.getAllData().groupBy { it.type }
        _uiState.value =
            UiState(
                dataList = datas,
                currentSelectedData = datas[MenuType.Museum]?.get(0)
                    ?: DataSource.defaultData
            )
    }

    fun updateDetailsScreenStates(data: Data) {
        _uiState.update {
            it.copy(
                currentSelectedData = data,
                isShowingHomepage = false
            )
        }
    }

    fun resetHomeScreenStates() {
        _uiState.update {
            it.copy(
                currentSelectedData = it.dataList[it.currentMenu]?.get(0)
                    ?: DataSource.defaultData,
                isShowingHomepage = true
            )
        }
    }

    fun updateCurrentMenu(menuType: MenuType) {
        _uiState.update {
            it.copy(
                currentMenu = menuType
            )
        }
    }
}