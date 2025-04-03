package etf.unsa.ba.myfirstapplication.DAO

import etf.unsa.ba.myfirstapplication.BuildConfig
import etf.unsa.ba.myfirstapplication.data.BiljkaColor
import etf.unsa.ba.myfirstapplication.data.BiljkaFix
import etf.unsa.ba.myfirstapplication.data.BiljkaId
import etf.unsa.ba.myfirstapplication.data.BiljkaImage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServis {



    @GET("/api/v1/plants/{id}")
    suspend fun getImage(
        @Path("id")id:Int,
        @Query("token") token: String = BuildConfig.TREFLE_API_KEY,
    ):Response<BiljkaImage>

    @GET("/api/v1/plants/search")
    suspend fun getBiljkaByName(
        @Query("token") token: String = BuildConfig.TREFLE_API_KEY,
        @Query("q") q:String
    ):Response<BiljkaId>

    @GET("/api/v1/plants/{id}")
    suspend fun getBiljkaById(
        @Path("id") id: Int?,
        @Query("token") token: String = BuildConfig.TREFLE_API_KEY,
    ):Response<BiljkaFix>


    @GET("/api/v1/plants/{id}")
    suspend fun getBiljkaFlowerColor(
        @Path("id")id:Int,
        @Query("token") token: String = BuildConfig.TREFLE_API_KEY,
    ):Response<BiljkaColor>



}