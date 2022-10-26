package mx.com.satoritech.satoritickets.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.com.satoritech.delega.colono.Repository.LocalUserRepositoryImp
import mx.com.satoritech.satoritickets.repository.*

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepository(authRepositoryImp: AuthRepositoryImp): AuthRepository
    @Binds
    abstract fun bindLocalUserRepository(localUserRepositoryImp: LocalUserRepositoryImp): LocalUserRepository
    @Binds
    abstract fun bindTicketRepository(ticketRepositoryImp: TicketRepositoryImp): TicketRepository
    @Binds
    abstract fun bindChatRepository(chatRepositoryImp: ChatRepositoryImp): ChatRepository
    @Binds
    abstract fun bindMessageRepository(messageRepositoryImp: MessageRepositoryImp): MessageRepository
}