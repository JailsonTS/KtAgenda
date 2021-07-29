package repository

import entity.ContactEntity

class ContactRepository {

    companion object {
        private val contactList = mutableListOf<ContactEntity>()

        fun save(contact: ContactEntity) {
            contactList.add(contact)
        }

        fun delete(name: String, phone: String) {

            val list = getList()
            var index = -1
            for (item in list.withIndex()) {
                if (item.value.name == name && item.value.phone == phone) {
                    index = item.index
                    break
                }
            }
            contactList.removeAt(index)
        }

        fun getList(): List<ContactEntity> {
            return contactList
        }

    }


}