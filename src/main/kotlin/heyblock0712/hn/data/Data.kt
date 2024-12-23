package heyblock0712.hn.data

import heyblock0712.hn.data.block.MechanicUser

object Data {
    fun init() {
        ItemManager.registerItem(MechanicUser())
    }
}