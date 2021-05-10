package dev.sunnat629.autoimageslide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dev.sunnat629.autoimageslide.constants.ActionTypes
import dev.sunnat629.autoimageslide.constants.ScaleTypes
import dev.sunnat629.autoimageslide.interfaces.ItemChangeListener
import dev.sunnat629.autoimageslide.interfaces.ItemClickListener
import dev.sunnat629.autoimageslide.interfaces.TouchListener
import dev.sunnat629.autoimageslide.models.SlideModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageSlider = findViewById<ImageSlider>(R.id.image_slider) // init imageSlider

        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(
            SlideModel(
                "https://cdn2.bulbagarden.net/upload/4/49/Ash_Pikachu.png",
                "Pikachu",
                ScaleTypes.CENTER_CROP
            )
        )
        imageList.add(
            SlideModel(
                "https://cdn2.bulbagarden.net/upload/7/7e/Ash_Charmander_M20.png",
                "Charmander (Japanese: ヒトカゲ Hitokage) is a Fire-type Pokémon introduced in Generation I.",
                ScaleTypes.FIT
            )
        )
        imageList.add(
            SlideModel(
                "https://cdn2.bulbagarden.net/upload/8/87/Mewtwo_Journeys.png",
                "Mewtwo (Japanese: ミュウツー Mewtwo) is a Psychic-type Legendary Pokémon introduced in Generation I.",
                ScaleTypes.CENTER_INSIDE
            )
        )

        imageSlider.setImageList(imageList)

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                Log.w("ASDF", "Position: $position")
            }
        })

        imageSlider.setItemChangeListener(object : ItemChangeListener {
            override fun onItemChanged(position: Int) {
                //println("Pos: " + position)
            }
        })

        imageSlider.setTouchListener(object : TouchListener {
            override fun onTouched(touched: ActionTypes) {
                if (touched == ActionTypes.DOWN) {
                    imageSlider.stopSliding()
                } else if (touched == ActionTypes.UP) {
                    imageSlider.startSliding(1000)
                }
            }
        })
    }
}
