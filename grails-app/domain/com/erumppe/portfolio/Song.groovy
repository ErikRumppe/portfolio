package com.erumppe.portfolio

class Song {
    String title
    String genre
    Integer year
    Integer trackNumber
    Integer duration

    Artist artist

    static belongsTo = [ album: Album ]

    Date dateCreated
    Date lastUpdated

    static constraints = {
        title blank: false
        duration min: 1
        year range: 1900..2100
    }

    String toString() { title }
}