import com.github.tophatcroat.config.AppConfig
import com.github.tophatcroat.data.DatabaseConnectionFactory
import com.github.tophatcroat.data.DatabaseConnectionFactoryImpl
import com.github.tophatcroat.feature.todo.data.TodoDataSourceImpl
import com.github.tophatcroat.feature.todo.domain.TodoService
import com.github.tophatcroat.feature.todo.domain.TodoServiceImpl
import com.github.tophatcroat.feature.todo.domain.TodoDataSource
import org.koin.core.annotation.KoinReflectAPI
import org.koin.dsl.module
import org.koin.dsl.single

@OptIn(KoinReflectAPI::class)
val appModule = module {
    // Backend Config
    single<AppConfig>()
    single<DatabaseConnectionFactory> { DatabaseConnectionFactoryImpl(get()) }
    single<TodoDataSource> { TodoDataSourceImpl() }
    single<TodoService> { TodoServiceImpl(get()) }
}