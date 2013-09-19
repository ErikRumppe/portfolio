package com.erumppe.portfolio

import org.springframework.dao.DataIntegrityViolationException

class UserController {

    static allowedMethods = [save: "POST", login: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

    def show(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def edit(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def update(Long id, Long version) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'user.label', default: 'User')] as Object[],
                          "Another user has updated this User while you were editing")
                render(view: "edit", model: [userInstance: userInstance])
                return
            }
        }

        userInstance.properties = params

        if (!userInstance.save(flush: true)) {
            render(view: "edit", model: [userInstance: userInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    def delete(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        try {
            userInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "show", id: id)
        }
    }

    def register() {
        [ user: new UserRegisterCommand() ]
    }

    def save(UserRegisterCommand urc) {
        if (urc.hasErrors()) {
            render view: 'register', model: [ user: urc ]
        }
        else {
            def user = new User()
            user.properties['username', 'password', 'firstname', 'lastname'] = params

            if (user.validate() && user.save()) {
                session.user = user
                flash.message = "Welcome aboard ${user.firstname} ${user.lastname}!"
                redirect controller:"store"
            }
            else {
                // User did not pass validation
                // Set urc errors to user validation errors and return to the view
                urc.setErrors(user.getErrors())
                render view: 'register', model: [ user: urc ]
            }
        }
    }
}

class UserRegisterCommand {
    String username
    String password
    String confirm
    String firstname
    String lastname

    static constraints = {
        username blank: false, nullable: false, size: 5..15, matches: /[\S]+/, unique: true
        password blank: false, nullable: false, size: 5..15, matches: /[\S]+/
        confirm nullable: false, blank: false, validator: { passwd2, urc ->
            return passwd2 == urc.password
        }
        firstname blank: false, nullable: false
        lastname blank: false, nullable: false
    }
}
