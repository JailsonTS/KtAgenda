package business

import entity.ContactEntity
import repository.ContactRepository

class ContactBusiness{

    private fun validade(name: String, phone: String) {
        if (name == "") {
            throw Exception("Nome é obrigatório!")
        }

        if (phone == "") {
            throw Exception("Telefone é obrigatório!")
        }
    }

    private fun validadeDelete(name: String, phone: String) {
        if (name == "" || phone == "") {
            throw java.lang.Exception("É necessário selecionar um contato antes de remover.")
        }
    }

    fun getContactCountDescription() : String{
        val list = getList()
        return when {
            list.isEmpty() -> "Nenhum contato"
            list.size == 1 -> "1 Contato"
            else -> "${list.size} Contatos"
        }
    }

    fun save(name: String, phone: String) {
        validade(name, phone)
        val contact = ContactEntity(name, phone)
        ContactRepository.save(contact)


    }

    fun delete(name: String, phone: String){
        validadeDelete(name, phone)
        ContactRepository.delete(name, phone)
    }

    fun getList(): List<ContactEntity> {
        return ContactRepository.getList()
    }
}