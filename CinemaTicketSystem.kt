package com.example.singleton

import java.lang.System
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf


// A ticket office responsible for selling tickets, be it online or at the box office
// (or some other innovative channel in the future... :D who knows... )
interface TicketOffice {

    fun sellTicket(customerName: String): Unit

}

// An enum to represent the sales channel - either box office or online

/*
A Cinema ticket system implements the TicketOffice interface.
Our cinema offers both direct sales at the box office and online sales through a website.
Since we have to make sure that direct sales and online sales do not result in overbooking,
it makes sense to implement the CinemaTicketSystem as a singleton.
(Imagine a cinema that has only one screen and shows one movie a day to keep it simple :) )
*/
class CinemaTicketSystem private constructor(): TicketOffice {

    // Maximum number of seats available in the cinema
    private val maxSeats = 10

    // current count of sold seats
    private var soldSeats = 0

    // list of customers who have purchased tickets - just for demonstration purposes
    private val bookKeeping = mutableListOf<String>()

    // pretty self-explanatory method to sell a ticket
    override fun sellTicket(customerName: String): Unit {
        //if we sold all seats, we cannot sell more tickets
        if (soldSeats >= maxSeats) {
            println("Sorry $customerName, the cinema is fully booked.")

            // else we add the customer to the list and increase the sold seats count
        } else {

            bookKeeping.add(customerName)
            soldSeats++

            println("One Ticket sold to $customerName. Available seats: ${maxSeats - soldSeats}")
        }
    }

    // a companion object to manage the singleton instance
    companion object {
        // the "instance" variable holds the singleton instance
        private var instance: CinemaTicketSystem? = null

        // if the instance is null, we create it, else we return the existing one
        fun getInstance(): CinemaTicketSystem {
            if (instance == null) {
                instance = CinemaTicketSystem()
            }
            // !! ensures that "instance" is not null
            return instance!!
        }
    }

    // a method to display a summary of the cinema ticket sales
    fun showTicketSystemSummary() {
        println("Cinema Summary:")
        println("Total Seats: $maxSeats")
        println("Sold Seats: $soldSeats")
        println("Customers: ${bookKeeping.joinToString(", ")}")
    }
}

    /*
    we will simulate some ticket sales by different sales channels.
    Our cinema has two box offices and an online sales channel.
     */
    fun main() {

        //the different sales channels obtain the singleton instance...
        val boxOffice1 = CinemaTicketSystem.getInstance()
        // ... and sell some tickets.
        boxOffice1.sellTicket("Alice")

        val boxOffice2 = CinemaTicketSystem.getInstance()
        boxOffice2.sellTicket("Marie")
        boxOffice2.sellTicket("Rosalind")
        boxOffice2.sellTicket("Margaret")

        val onlineSales = CinemaTicketSystem.getInstance()
        onlineSales.sellTicket("Tinky Winky")
        onlineSales.sellTicket("Homer")
        onlineSales.sellTicket("Marge")
        onlineSales.sellTicket("Bart")
        onlineSales.sellTicket("Lisa")
        onlineSales.sellTicket("Maggie")

        println()
        // I am late to the movie and want to buy a last minute ticket at the box office!!!
        boxOffice2.sellTicket("Cemre")
        // this sale should fail as the cinema is already fully booked :(((

        // finally, we show a summary of the cinema ticket sales
        println("\nFinal Cinema Ticket Sales Summary:")
        onlineSales.showTicketSystemSummary()

        // verify that all sales channels refer to the same singleton instance
        println("\nVerifying Singleton Instance:")
        println("boxOffice1 and boxOffice2 are the same instance: ${boxOffice1 === boxOffice2}")
        println()
        println("addresses of the instances:")
        println(boxOffice1)
        println(boxOffice2)
        println(onlineSales)
        println()
        println("boxOffice1 and onlineSales are the same instance: ${boxOffice1 === onlineSales}")
    }
