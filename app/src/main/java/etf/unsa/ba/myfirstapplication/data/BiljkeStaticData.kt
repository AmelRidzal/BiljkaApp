package etf.unsa.ba.myfirstapplication.data

import etf.unsa.ba.myfirstapplication.enumClass.KlimatskiTip
import etf.unsa.ba.myfirstapplication.enumClass.MedicinskaKorist
import etf.unsa.ba.myfirstapplication.enumClass.ProfilOkusaBiljke
import etf.unsa.ba.myfirstapplication.enumClass.Zemljište

fun getBiljke(): List<Biljka> {
    return listOf(
        Biljka(
            naziv = "Bosiljak (Ocimum basilicum)",
            porodica = "Lamiaceae (usnate)",
            medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
            medicinskeKoristi = listOf(
                MedicinskaKorist.SMIRENJE,
                MedicinskaKorist.REGULACIJAPROBAVE
            ),
            profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
            jela = listOf("Salata od paradajza", "Punjene tikvice"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljište.PJESKOVITO, Zemljište.ILOVACA)
        ),
        Biljka(
            naziv = "Nana (Mentha spicata)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA),
            profilOkusa = ProfilOkusaBiljke.MENTA,
            jela = listOf("Jogurt sa voćem", "Gulaš"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
            zemljisniTipovi = listOf(Zemljište.GLINENO, Zemljište.CRNICA)
        ),
        Biljka(
            naziv = "Kamilica (Matricaria chamomilla)",
            porodica = "Asteraceae (glavočike)",
            medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Čaj od kamilice"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljište.PJESKOVITO, Zemljište.KRECNJACKO)
        ),
        Biljka(
            naziv = "Ružmarin (Rosmarinus officinalis)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
            medicinskeKoristi = listOf(
                MedicinskaKorist.PROTUUPALNO,
                MedicinskaKorist.REGULACIJAPRITISKA
            ),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Pečeno pile", "Grah", "Gulaš"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
            zemljisniTipovi = listOf(Zemljište.SLJUNOVITO, Zemljište.KRECNJACKO)
        ),
        Biljka(
            naziv = "Lavanda (Lavandula angustifolia)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine. Također, treba izbjegavati kontakt lavanda ulja sa očima.",
            medicinskeKoristi = listOf(
                MedicinskaKorist.SMIRENJE,
                MedicinskaKorist.PODRSKAIMUNITETU
            ),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Jogurt sa voćem"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
            zemljisniTipovi = listOf(Zemljište.PJESKOVITO, Zemljište.KRECNJACKO)
        ),
        Biljka(
            naziv = "Kaktus (káktos)",
            porodica = "Kaktus (Cactaceae)",
            medicinskoUpozorenje = "Moze te se ubosti, rukavice su preporuka za rad s kaktusom",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PODRSKAIMUNITETU),
            profilOkusa = ProfilOkusaBiljke.GORKO,
            jela = listOf("vocna salata"),
            klimatskiTipovi = listOf(KlimatskiTip.SUHA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljište.SLJUNOVITO)
        ),
        Biljka(
            naziv = "Jabuka",
            porodica = "Ruza (Rosaceae)",
            medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
            medicinskeKoristi = listOf(MedicinskaKorist.PODRSKAIMUNITETU, MedicinskaKorist.REGULACIJAPROBAVE),
            profilOkusa = ProfilOkusaBiljke.SLATKI,
            jela = listOf("vocna salata","jabukovaca"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SREDOZEMNA),
            zemljisniTipovi = listOf(Zemljište.ILOVACA, Zemljište.GLINENO)
        ),
        Biljka(
            naziv = "Ruza",
            porodica = "Ruza (Rosaceae)",
            medicinskoUpozorenje = "Moze te se ubosti, rukavice su preporuka za rad s ruzama",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTIVBOLOVA, MedicinskaKorist.PROTUUPALNO),
            profilOkusa = ProfilOkusaBiljke.GORKO,
            jela = listOf("pekmez"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
            zemljisniTipovi = listOf(Zemljište.CRNICA)
        ),
        Biljka(
            naziv = "Bor (Pinus)",
            porodica = "Pinaceae",
            medicinskoUpozorenje = "Vecina borova je toksicno za konzumirat, posavjetujte se s expertima prije konzumacije",
            medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPROBAVE, MedicinskaKorist.REGULACIJAPRITISKA),
            profilOkusa = ProfilOkusaBiljke.KORIJENASTO,
            jela = listOf("caj od borovih iglica"),
            klimatskiTipovi = listOf(KlimatskiTip.PLANINSKA),
            zemljisniTipovi = listOf(Zemljište.KRECNJACKO, Zemljište.SLJUNOVITO)
        ),
        Biljka(
            naziv = "Đumbir",
            porodica = "Zingiberaceae",
            medicinskoUpozorenje = "Moze izazvati alergijske reakcije.",
            medicinskeKoristi = listOf(MedicinskaKorist.PODRSKAIMUNITETU, MedicinskaKorist.SMIRENJE),
            profilOkusa = ProfilOkusaBiljke.KORIJENASTO,
            jela = listOf("curry","piva"),
            klimatskiTipovi = listOf(KlimatskiTip.TROPSKA, KlimatskiTip.SUHA),
            zemljisniTipovi = listOf(Zemljište.ILOVACA, Zemljište.CRNICA)
        )
    )

}