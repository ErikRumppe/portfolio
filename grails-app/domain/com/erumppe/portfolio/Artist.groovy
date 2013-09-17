package com.erumppe.portfolio

class Artist {
    String name

    static hasMany = [ albums: Album, songs: Song ]

    Date dateCreated
    Date lastUpdated

    static constraints = {
    }

    String toString() { name }
}