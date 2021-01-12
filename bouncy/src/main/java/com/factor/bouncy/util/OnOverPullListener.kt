package com.factor.bouncy.util

interface OnOverPullListener
{
    fun onOverPulledTop(deltaDistance: Float)

    fun onOverPulledBottom(deltaDistance: Float)

    fun onRelease()
}