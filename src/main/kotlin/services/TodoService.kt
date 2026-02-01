package services

import entities.Todo
import repositories.ITodoRepository

class TodoService(private val todoRepository: ITodoRepository) : ITodoService {
    override fun showTodos() {
        val todos = todoRepository.getAllTodos()

        println("Daftar Todo:")
        var counter = 0
        for (todo in todos) {
            counter++
            println(todo)
        }

        if (counter <= 0) {
            println("- Data todo belum tersedia!")
        }
    }

    override fun addTodo(title: String) {
        val newTodo = Todo(title = title)
        todoRepository.addTodo(newTodo)
    }

    override fun removeTodo(id: Int) {
        val success = todoRepository.removeTodo(id)
        if (!success) {
            println("[!] Gagal menghapus todo dengan ID: $id.")
            return
        }
    }

    override fun updateTodo(id: Int, title: String, isFinished: Boolean) {
        val success = todoRepository.updateTodo(id, title, isFinished)
        if (!success) {
            println("[!] Gagal memperbarui todo dengan ID: $id.")
        }
    }

    override fun searchTodo(keyword: String) {
        if (keyword == "x") {
            println("[x] Pencarian todo dibatalkan.")
            return
        }

        val todos = todoRepository.searchTodos(keyword)

        if (todos.isEmpty()) {
            println("- Data todo tidak ditemukan!")
        } else {
            for (todo in todos) {
                println(todo)
            }
        }
    }

    override fun sortTodo(criteria: String, isAscending: Boolean) {
        when (criteria.lowercase()) {
            "id" -> {
                // Untuk id, kita perlu track index untuk secondary sorting
                val todos = todoRepository.getAllTodos()

                // Buat list dengan index
                val indexedTodos = todos.mapIndexed { index, todo ->
                    IndexedValue(index, todo)
                }

                // Sort berdasarkan id, lalu index
                val sorted = if (isAscending) {
                    // Ascending: id kecil dulu
                    // Jika id sama, urutkan berdasarkan index descending (urutan terbalik)
                    indexedTodos.sortedWith(
                        compareBy<IndexedValue<Todo>> { it.value.id }
                            .thenByDescending { it.index }
                    )
                } else {
                    // Descending: id besar dulu
                    // Jika id sama, urutkan berdasarkan index descending (urutan terbalik)
                    indexedTodos.sortedWith(
                        compareByDescending<IndexedValue<Todo>> { it.value.id }
                            .thenByDescending { it.index }
                    )
                }

                // Terapkan hasil sorting ke repository
                val mutableTodos = todos as? MutableList<Todo>
                if (mutableTodos != null) {
                    mutableTodos.clear()
                    mutableTodos.addAll(sorted.map { it.value })
                }
            }

            "title" -> {
                // Untuk title, kita perlu track index untuk secondary sorting
                val todos = todoRepository.getAllTodos()

                // Buat list dengan index
                val indexedTodos = todos.mapIndexed { index, todo ->
                    IndexedValue(index, todo)
                }

                // Sort berdasarkan title mengikuti urutan ASCII
                val sorted = if (isAscending) {
                    // Ascending: urutan ASCII dari kecil ke besar
                    // Urutan: Angka (0-9) → Huruf Besar (A-Z) → Huruf Kecil (a-z)
                    // Contoh: "1todo", "2todo", "Apple", "Banana", "apple", "banana"
                    // Jika title sama, urutkan berdasarkan index descending (urutan terbalik)
                    indexedTodos.sortedWith(
                        compareBy<IndexedValue<Todo>> { it.value.title }
                            .thenByDescending { it.index }
                    )
                } else {
                    // Descending: urutan ASCII dari besar ke kecil
                    // Urutan: Huruf kecil (z-a) → Huruf Besar (Z-A) → Angka (9-0)
                    // Contoh: "zebra", "apple", "Zebra", "Apple", "9todo", "1todo"
                    // Jika title sama, urutkan berdasarkan index descending (urutan terbalik)
                    indexedTodos.sortedWith(
                        compareByDescending<IndexedValue<Todo>> { it.value.title }
                            .thenByDescending { it.index }
                    )
                }

                // Terapkan hasil sorting ke repository
                val mutableTodos = todos as? MutableList<Todo>
                if (mutableTodos != null) {
                    mutableTodos.clear()
                    mutableTodos.addAll(sorted.map { it.value })
                }
            }

            "finished" -> {
                val todos = todoRepository.getAllTodos()

                // Buat list dengan index untuk tracking urutan asli
                val indexedTodos = todos.mapIndexed { index, todo ->
                    IndexedValue(index, todo)
                }

                // Sort berdasarkan finished dengan index sebagai secondary sort
                val sorted = if (isAscending) {
                    // Ascending: false (Belum Selesai) dulu, lalu true (Selesai)
                    // Urutan asli dipertahankan untuk yang sama status
                    indexedTodos.sortedBy { it.value.isFinished }
                } else {
                    // Descending: true (Selesai) dulu, lalu false (Belum Selesai)
                    // Jika status sama, urutkan berdasarkan index descending (urutan terbalik)
                    indexedTodos.sortedWith(
                        compareByDescending<IndexedValue<Todo>> { it.value.isFinished }
                            .thenByDescending { it.index }
                    )
                }

                // Terapkan hasil sorting ke repository
                val mutableTodos = todos as? MutableList<Todo>
                if (mutableTodos != null) {
                    mutableTodos.clear()
                    mutableTodos.addAll(sorted.map { it.value })
                }
            }
        }
    }
}