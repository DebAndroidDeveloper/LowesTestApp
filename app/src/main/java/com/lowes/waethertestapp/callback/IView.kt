package com.lowes.waethertestapp.callback

interface IView {
    fun displayNoResultsFound(noResults: Boolean)

    fun showError(s: String)

    fun showErrorDialog(message: String)

    fun showProgressBar()

    fun hideProgressBar()
}