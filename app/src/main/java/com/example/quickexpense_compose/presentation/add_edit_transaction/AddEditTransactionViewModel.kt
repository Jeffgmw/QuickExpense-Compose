package com.example.quickexpense_compose.presentation.add_edit_transaction

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.quickexpense_compose.domain.Transaction
import com.example.quickexpense_compose.domain.TransactionRepository
import com.example.quickexpense_compose.presentation.add_edit_transaction.util.getFormattedTime
import com.example.quickexpense_compose.utils.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTransactionViewModel @Inject constructor(
    private val userDataRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle,

    ) : ViewModel() {

    private val transactionId: Int = checkNotNull(savedStateHandle["id"])
    private val previousScreen: String = checkNotNull(savedStateHandle["previousScreen"])


    private val _title = mutableStateOf(
        AddEditTransactionTextFieldState(
            hint = "Enter a Title.."
        )
    )
    val title: State<AddEditTransactionTextFieldState> = _title

    private val _tags = mutableStateOf(
        AddEditTransactionTextFieldState(
            hint = "Tags"
        )
    )
    val tags: State<AddEditTransactionTextFieldState> = _tags

    private val _amount = mutableStateOf(
        AddEditTransactionTextFieldState(
            hint = "Enter the Amount.."
        )
    )
    val amount: State<AddEditTransactionTextFieldState> = _amount

    private val _note = mutableStateOf(
        AddEditTransactionTextFieldState(
            hint = "Type a note.."
        )
    )
    val note: State<AddEditTransactionTextFieldState> = _note

    private val _transactionType = mutableStateOf(
        AddEditTransactionDropDownMenuState(
            hint = "Transaction Type"
        )
    )
    val transactionType: State<AddEditTransactionDropDownMenuState> = _transactionType

    private val _dialogState = mutableStateOf(DialogState())
    val dialogState: State<DialogState> = _dialogState


    init {
        if (previousScreen == Screens.TransactionDetails.route) {
            fetchTransactionData()
        }
    }


    private fun fetchTransactionData() {
        viewModelScope.launch {
            userDataRepository.getTransactionById(transactionId).collect {
                _title.value = _title.value.copy(text = it.title)
                _amount.value = _amount.value.copy(text = it.amount.toString())
                _note.value = _note.value.copy(text = it.note)
                _tags.value = _tags.value.copy(text = it.tags)
                _transactionType.value = _transactionType.value.copy(selectedOption = it.transactionType)
                _dialogState.value = _dialogState.value.copy(text = "Do you want to discard changes?")
            }
        }
    }


    fun onEvent(event: AddEditTransactionEvent) {
        when (event) {
            is AddEditTransactionEvent.EnteredTitle -> updateTextField(_title, event.value)
            is AddEditTransactionEvent.EnteredAmount -> updateTextField(_amount, event.value)
            is AddEditTransactionEvent.EnteredNote -> updateTextField(_note, event.value)
            is AddEditTransactionEvent.EnteredTags -> updateTextField(_tags, event.value)
            is AddEditTransactionEvent.OnExpandedChange -> toggleDropDownExpansion()
            is AddEditTransactionEvent.OnDismissRequest -> closeDropDown()
            is AddEditTransactionEvent.ChangeSelectedOption -> changeDropDownOption(event.value)
            is AddEditTransactionEvent.SaveEditTransaction -> saveOrUpdateTransaction(event.context, event.navHostController)
            is AddEditTransactionEvent.OpenDialog -> openDialog()
            is AddEditTransactionEvent.CloseDialog -> closeDialog()
        }
    }

    private fun updateTextField(state: MutableState<AddEditTransactionTextFieldState>, value: String) {
        state.value = state.value.copy(text = value)
    }


    private fun toggleDropDownExpansion() {
        _transactionType.value = _transactionType.value.copy(isExpanded = !_transactionType.value.isExpanded)
    }
    private fun closeDropDown() {
        _transactionType.value = _transactionType.value.copy(isExpanded = false)
    }
    private fun changeDropDownOption(value: String) {
        _transactionType.value = _transactionType.value.copy(selectedOption = value)
    }


    private fun saveOrUpdateTransaction(context: Context, navHostController: NavHostController) {
        if (allFieldsFilled()) {
            viewModelScope.launch {
                val transaction = Transaction(
                    id = if (previousScreen == Screens.TransactionDetails.route) transactionId else 0,
                    title = _title.value.text,
                    amount = _amount.value.text.toLong(),
                    transactionType = _transactionType.value.selectedOption,
                    tags = _tags.value.text,
                    date = getFormattedTime(),
                    note = _note.value.text
                )

                if (previousScreen == Screens.TransactionDetails.route) {
                    userDataRepository.update(transaction)
                } else {
                    userDataRepository.insert(transaction)
                }

                navHostController.navigateUp()
            }
        } else {
            Toast.makeText(context, "Please fill-up all attributes.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun allFieldsFilled(): Boolean {
        return _title.value.text.isNotBlank() &&
                _tags.value.text.isNotBlank() &&
                _transactionType.value.selectedOption.isNotBlank() &&
                _note.value.text.isNotBlank() &&
                _amount.value.text.isNotBlank()
    }

    private fun openDialog() {
        _dialogState.value = _dialogState.value.copy(state = true)
    }

    private fun closeDialog() {
        _dialogState.value = _dialogState.value.copy(state = false)
    }
}
