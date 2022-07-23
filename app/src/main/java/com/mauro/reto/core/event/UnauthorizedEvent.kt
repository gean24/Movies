package com.mauro.reto.core.event

class UnauthorizedEvent {
    companion object{
        private val INSTANCE: UnauthorizedEvent = UnauthorizedEvent()
        fun instance(): UnauthorizedEvent {
            return INSTANCE
        }
    }
}