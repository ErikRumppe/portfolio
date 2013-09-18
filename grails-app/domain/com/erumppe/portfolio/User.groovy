package com.erumppe.portfolio

class User {
	String username
    String password
    String firstname
    String lastname

    static hasMany = [ purchasedSongs: Song ]

    Date dateCreated
    Date lastUpdated

    static constraints = {
        username blank: false, nullable: false, size: 5..15, matches: /[\S]+/, unique: true
        password blank: false, nullable: false, size: 5..15, matches: /[\S]+/
        firstname blank: false, nullable: false
        lastname blank: false, nullable: false
    }
}