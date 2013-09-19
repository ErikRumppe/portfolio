package com.erumppe.portfolio

class User {

    transient springSecurityService

    String username
    String password
    String firstname
    String lastname

    static hasMany = [ purchasedSongs: Song ]

    Date dateCreated
    Date lastUpdated

    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static constraints = {
        username blank: false, nullable: false, size: 5..15, matches: /[\S]+/, unique: true
        password blank: false, nullable: false
        firstname blank: false, nullable: false
        lastname blank: false, nullable: false
    }

    static mapping = {
        password column: '`password`'
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
}