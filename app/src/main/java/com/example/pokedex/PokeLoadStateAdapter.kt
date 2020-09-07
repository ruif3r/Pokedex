package com.example.pokedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

class PokeLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<StateViewHolder>() {

    override fun onBindViewHolder(holder: StateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): StateViewHolder {
        return StateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_footer_pokemon_items, parent, false), retry
        )
    }
}

class StateViewHolder(itemView: View, retry: () -> Unit) : RecyclerView.ViewHolder(itemView) {

    private val errorText = itemView.findViewById<TextView>(R.id.footer_error_message)
    private val footerProgressBar = itemView.findViewById<ProgressBar>(R.id.footer_progress_bar)
    private val retryButton = itemView.findViewById<Button>(R.id.retry_button).also {
        it.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            errorText.text = loadState.error.localizedMessage
        }
        footerProgressBar.isVisible = loadState is LoadState.Loading
        errorText.isVisible = loadState is LoadState.Error
        retryButton.isVisible = loadState is LoadState.Error
    }
}
