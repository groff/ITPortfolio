package moura.groff.ernani.itportfolio.portfolio_app

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import moura.groff.ernani.itportfolio.entities.AppPortfolio

typealias CallbackAppPortfolio = (List<AppPortfolio>) -> Unit

class PortfolioMainModel {

    fun loadAppPortfolio(callback: CallbackAppPortfolio) {
        val database = FirebaseFirestore.getInstance()
        database.collection("profiles")
                .get()
                .addOnCompleteListener() {
                    if (it.isSuccessful) {
                        database.collection("profiles").document(it.result!!.documents[0].id)
                                .collection("app_portfolio").get()
                                .addOnCompleteListener() {
                                    if (it.isSuccessful) {
                                        val list: MutableList<AppPortfolio> = mutableListOf()
                                        for (item in it.result!!) {
                                            list.add(item.toObject(AppPortfolio::class.java))
                                        }
                                        callback(list)
                                    } else {
                                        Log.i("loadAppPortfolio", "FAILURE: " + it.exception)
                                    }
                                }
                    }
                }
    }

}