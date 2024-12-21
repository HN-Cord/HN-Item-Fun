package heyblock0712.hn.data

import heyblock0712.hn.data.block.User

object Data {
    fun init() {
        ItemManager.registerItem(User())
    }
}