# PokemonUniverse

Application uses [PokeAPI](https://pokeapi.co/) to load information about pokemons. Retrofit 2.5.0 is used to send requests to the server. RecyclerView is used to display the data.
This app is build using the architecture pattern MVP. For asynchronous communications with different layers uses RxJava 3.0.

The application consists of two activities:
* First activity that displays the list of pokemons itself. Also it contains the button to re-initialize the list with the random seed from the server's base 
and the panel with flags attack/hp/defense to sort the current list by these flags.
* Second activity contains detailed information about each pokemon such as image, name, height, weight, types and stats. To open this activity you need to click on list item
from the first activity

The list of pokemons loads by 30 items. If user scrolls to the end of the list, the new portion will load. New data requests by interface method 'getPokemonsList' 
which requires the limit of items and the offset of the first item in the parameters

To sort pokemons needs to check one or more checkBoxes (attack/hp/defense) on the bottom panel and press 'sort' button. If more then one CheckBox is checked, the 
list will be sorted first by attack, then by hp and then by defense. The list will be sorted in descending order and the more powerful pokemon will placed at the first place.
Background of the first list item will be painted in a different color from another items and recyclerView will scroll smooth the list to this item.  

If re-initialization button is clicked, the random integer value from 0 to max count of base's items will be chosen. Loading new pokemons will be done with the offset with chosen
value from the beginning of the base. If the end of the base is reached, loading of new pokemons will be done from 0 to the chosen value (offset).


### Screenshots
![Main Activity](https://user-images.githubusercontent.com/25653655/92344741-fce53e00-f0cf-11ea-9091-dd67584a9e50.jpg)
![notMain](https://user-images.githubusercontent.com/25653655/92344848-38800800-f0d0-11ea-9fe1-7fbdbef0140d.jpg)
