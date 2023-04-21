package com.github.studydistractor.sdp.friends

import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.ui.state.FriendsUiState
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FriendsViewModel(
    friendsModel: FriendsModel
) : ViewModel() {
    companion object {
        private val uidRegex = Regex("[a-zA-Z0-9]{28,255}")
    }

    private val _friendsModel: FriendsModel = friendsModel
    private val _uiState = MutableStateFlow(FriendsUiState())
    val uiState: StateFlow<FriendsUiState> = _uiState.asStateFlow()

    fun refreshFriendsList() {
        val uid = _friendsModel.getCurrentUid()!!
        val friendsList = _friendsModel.fetchAllFriends(uid)
        _uiState.update { it.copy(friendsList = friendsList) }
    }

    private fun validateUid(newFriend: String): Boolean {
        return newFriend.matches(Companion.uidRegex)
    }

    fun updateNewFriend(newFriend: String) {
        _uiState.update { it.copy(newFriend = newFriend) }
    }

    fun addFriend() : Task<Void> {
        val uid = _friendsModel.getCurrentUid()!!
        val newFriend = uiState.value.newFriend

        if(!validateUid(newFriend)) {
            return Tasks.forException(Exception("invalid new friend"))
        }

        return _friendsModel.addNewFriend(uid, newFriend)
    }

    fun removeFriend(friendUid: String) : Task<Void> {
        val uid = _friendsModel.getCurrentUid()!!

        if(!validateUid(friendUid)) {
            return Tasks.forException(Exception("invalid friend selected for deletion"))
        }

        return _friendsModel.removeFriend(uid, friendUid)
    }

    init {
        refreshFriendsList()
    }
}