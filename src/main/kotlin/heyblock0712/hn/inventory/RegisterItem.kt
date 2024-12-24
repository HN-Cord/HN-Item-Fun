package heyblock0712.hn.inventory

import heyblock0712.hn.inventory.item.MechanicUser

object RegisterItem {
    fun init() {
        ItemManager.registerItem(MechanicUser())
    }
}