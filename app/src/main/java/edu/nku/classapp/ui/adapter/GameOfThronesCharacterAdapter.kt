package edu.nku.classapp.ui.adapter

//import edu.nku.classapp.model.GameOfThronesCharacter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.nku.classapp.R
import edu.nku.classapp.databinding.CharacterCardViewBinding
import edu.nku.classapp.model.GameOfThronesCharacterResponse

class GameOfThronesCharacterAdapter(
    private val onCharacterClicked: (character: GameOfThronesCharacterResponse.Character, position: Int) -> Unit,
) : RecyclerView.Adapter<GameOfThronesCharacterAdapter.GameOfThronesCharacterViewHolder>() {

    class GameOfThronesCharacterViewHolder(
        private val binding: CharacterCardViewBinding,
        private val onCharacterClicked: (position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onCharacterClicked(adapterPosition)
            }
        }

        fun bind(character: GameOfThronesCharacterResponse.Character) {
            binding.title.text =
                binding.root.context.getString(R.string.title, character.title)
            binding.family.text =
                binding.root.context.getString(
                    R.string.family,
                    character.family
                )
            binding.fullName.text =
                binding.root.context.getString(
                    R.string.full_name,
                    character.fullName
                )
//            binding.characterSpecies.text =
//                binding.root.context.getString(R.string.species, character.species)
            binding.id.text =
                binding.root.context.getString(
                    R.string.id,
                    character.id ?: 0
                )
            Glide.with(binding.root)
                .load(character.imageUrl)
                .into(binding.characterImage)
        }
    }

    private val gameOfThronesCharacters = mutableListOf<GameOfThronesCharacterResponse.Character>()

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(characters: List<GameOfThronesCharacterResponse.Character>) {
        gameOfThronesCharacters.clear()
        gameOfThronesCharacters.addAll(characters)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameOfThronesCharacterViewHolder {
        val binding =
            CharacterCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GameOfThronesCharacterViewHolder(binding) { position ->
            onCharacterClicked(gameOfThronesCharacters[position], position)
        }
    }


    override fun getItemCount() = gameOfThronesCharacters.size

    override fun onBindViewHolder(
        holder: GameOfThronesCharacterViewHolder,
        position: Int
    ) {
        val character = gameOfThronesCharacters[position]
        holder.bind(character)
    }
}