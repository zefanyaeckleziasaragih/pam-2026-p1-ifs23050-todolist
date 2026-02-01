package views

import services.ITodoService
import utils.InputUtil

class TodoView(private val todoService: ITodoService) {

    /**
     * Menampilkan view todo
     */
    fun showTodos() {
        while (true) {
            todoService.showTodos()

            println("Menu:")
            println("1. Tambah")
            println("2. Ubah")
            println("3. Cari")
            println("4. Urutkan")
            println("5. Hapus")
            println("x. Keluar")

            val input = InputUtil.input("Pilih")
            val stop = when (input) {
                "1" -> {
                    addTodo()
                    false
                }
                "2" -> {
                    updateTodo()
                    false
                }
                "3" -> {
                    searchTodo()
                    false
                }
                "4" -> {
                    sortTodo()
                    false
                }
                "5" -> {
                    removeTodo()
                    false
                }
                "x" -> true
                else -> {
                    println("[!] Pilihan tidak dimengerti.")
                    false
                }
            }

            if (stop) {
                break
            }

            println()
        }
    }

    /**
     * Menampilkan view add todo
     */
    fun addTodo() {
        println("[Menambah Todo]")
        val title = InputUtil.input("Judul (x Jika Batal)")

        if (title != "x") {
            todoService.addTodo(title)
        }
    }

    /**
     * Menampilkan view remove todo
     */
    fun removeTodo() {
        println("[Menghapus Todo]")

        val strIdTodo = InputUtil.input("[ID Todo] yang dihapus (x Jika Batal)")

        if (strIdTodo != "x") {
            try {
                val idTodo = strIdTodo.toInt()
                todoService.removeTodo(idTodo)
            } catch (e: NumberFormatException) {
                println("[!] ID harus berupa angka.")
            }

        }
    }

    /**
     * Menampilkan view update todo
     */
    fun updateTodo() {
        println()
        println("[Memperbarui Todo]")
        val strIdTodo = InputUtil.input("[ID Todo] yang diubah (x Jika Batal)")

        if (strIdTodo == "x") {
            println("[x] Pembaruan todo dibatalkan.")
            return
        }

        try {
            val idTodo = strIdTodo.toInt()
            val newTitle = InputUtil.input("Judul Baru (x Jika Batal)")

            if (newTitle == "x") {
                println("[x] Pembaruan todo dibatalkan.")
                return
            }

            val strFinished = InputUtil.input("Apakah todo sudah selesai? (y/n)")
            val isFinished = strFinished.lowercase() == "y"

            todoService.updateTodo(idTodo, newTitle, isFinished)
        } catch (e: NumberFormatException) {
            println("[!] ID harus berupa angka.")
        }
    }

    /**
     * Menampilkan view search todo
     */
    fun searchTodo() {
        println()
        println("[Mencari Todo]")
        val keyword = InputUtil.input("Kata kunci (x Jika Batal)")
        todoService.searchTodo(keyword)
    }

    /**
     * Menampilkan view sort todo
     */
    fun sortTodo() {
        println()
        println("[Mengurutkan Todo]")
        val criteria = InputUtil.input("Urutkan berdasarkan (id/title/finished) (x Jika Batal)")

        if (criteria == "x") {
            println("[x] Pengurutan todo dibatalkan.")
            return
        }

        val ascending = InputUtil.input("Urutkan secara ascending? (y/n)")
        val isAscending = ascending.lowercase() == "y"

        todoService.sortTodo(criteria, isAscending)
    }
}

