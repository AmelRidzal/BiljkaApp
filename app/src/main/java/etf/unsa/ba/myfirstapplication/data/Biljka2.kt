package etf.unsa.ba.myfirstapplication.data

import com.google.gson.annotations.SerializedName

data class BiljkaFix(
    var data: Data?
)


data class Data(
    var family: Family?,
    var main_species: Main_Species?,

    )

data class Family(

    var name: String?,
)


data class Main_Species(

    var specifications: Specifications?,
    var edible: Boolean?,
    var growth: Growth?,
)


data class Specifications(
    var toxicity: String?,

)

data class Growth(

    var soil_texture: Int?,
    var light: Int?,
    var atmospheric_humidity: Int?,
    )


data class BiljkaId(
    var data: ArrayList<DataId>?,
)


data class DataId(
    var id:Int?=null,
    var scientific_name:String?=null,
)
data class BiljkaImage(
    var data: DataImage?
)

data class DataImage(
    var image_url:String?
)

data class BiljkaColor(
    var data: DataColor?
)

data class DataColor(
    var main_species: Main_SpeciesColor?,
)

data class Main_SpeciesColor(

    var flower:Flower?,
)

data class Flower(
    var color:ArrayList<String>?,
)




