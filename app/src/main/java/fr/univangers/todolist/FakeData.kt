package fr.angersuniv.mob.tp01.createlayoutandmenu

import android.content.ContentValues
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import fr.univangers.todolist.TasksDBHelper

object FakeData {
    fun insert_fake_data(db: SQLiteDatabase?) {
        if (db == null) {
            return
        }
        val tasks_list = arrayOf(
            "<1> Acheter des piles",
            "<3> Sortir les poubelles",
            "<2> Changer l'ampoule du salon",
            "<2> Inviter Seb",
            "<2> Prendre les billets d'avion",
            "<2> Réserver un hôtel",
            "<1> Payer le loyer",
            "<3> Réparer le mixeur",
            "<2> Mettre à jour l'ordinateur",
            "<1> Programmer l'enregistrement de Dr Who",
            "<2> Acheter du paracétamol",
            "<3> Prendre rendez-vous chez le coiffeur",
            "<2> Sortir le chien",
            "<2> Faire la révision de la voiture",
            "<1> Finir mon TP du jour",
            "<2> Augmenter mon niveau à GW2",
            "<3> Acheter des glaces",
            "<3> S'inscrire au repas de fin d'année",
            "<2> Lire le rapport d'activités",
            "<1> Écrire le bilan",
            "<2> Classer les factures",
            "<3> Peindre la porte de la chambre",
            "<1> Réparer le pneu crevé",
            "<3> Acheter un disque dur",
            "<2> Faire les sauvegardes mensuelles",
            "<3> Prendre des nouvelles des parents",
            "<2> Faire le ménage de mes flux RSS",
            "<1> Corriger les copies",
            "<3> Nettoyer la machine à café",
            "<3> Faire la liste de cadeaux d'anniversaire",
            "<2> Ramener le jeu prêté par David",
            "<3> Emprunter un nouveau livre à la bibliothèque",
            "<2> Renouveler mon abonnement train",
            "<1> Commander un nouveau chèquier",
            "<1> Réservez la baby sitter pour samedi",
            "<1> Appeler pour réserver le restaurant",
            "<3> Changer la voiture de place",
            "<2> Vérifier le niveau d'eau de la chaudière",
            "<1> Renouveler la carte de parking",
            "<2> Sortir le chat de la voisine"
        ).toCollection(ArrayList())

        val list: MutableList<ContentValues> = ArrayList()

        for (task in tasks_list) {
            val cv = ContentValues()
            val name = task.substring(4)
            val priority = task.substring(1,2)

            cv.put(TasksDBHelper.NAME_COL, name)
            cv.put(TasksDBHelper.PRIORITY_COL, priority)
            list.add(cv)
        }

        //insert all tasks in one transaction
        try {
            db.beginTransaction()
            //clear the table first
            db.delete(TasksDBHelper.TABLE_NAME, null, null)
            //go through the list and add one by one
            for (c in list) {
                db.insert(TasksDBHelper.TABLE_NAME, null, c)
            }
            db.setTransactionSuccessful()
        } catch (e: SQLException) {
            //too bad :(
        } finally {
            db.endTransaction()
        }
    }
}

