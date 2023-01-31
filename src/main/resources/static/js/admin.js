const url = '/api/admin/all_users'
const userUrl = '/api/user'
const dbRoles = [{id: 1, name: "ROLE_USER"}, {id: 2, name: "ROLE_ADMIN"}]

let roleArray = (options) => {
    let array = []
    for (let i = 0; i < options.length; i++) {
        if (options[i].selected) {
            let role = {id: options[i].value}
            array.push(role)
        }
    }
    return array;
}

// Navbar
const showNavbarInfo = (user) => {
    const navbarInfo = document.getElementById('navbarInfo')
    let result = `

                <div class="col h5 card-header">                    
                    <strong>${user.username}</strong>
                    with roles:
                    <span>${user.cleanRoles}</span>
                </div>
                <div class="col">
                    <div class="float-end">
                        <form action="/logout" th:method="POST">
                            <input class="btn-exit" type="submit" value="Logout">
                        </form>
                    </div>
                </div>
                `
    navbarInfo.innerHTML = result
}

fetch(userUrl)
    .then(response => response.json())
    .then(data => showNavbarInfo(data))
    .catch(error => console.log(error))


// Users Table
let usersInfo = ''
const showUsers = (users) => {
    const container = document.querySelectorAll('tbody')[0]
    users.forEach(user => {
        usersInfo += `
                <tr>
                    <td>${user.id}</td>   
                    <td>${user.username}</td>
                    <td>${user.age}</td>
                    <td>${user.cleanRoles}</td>   
                    <td class="text text-white">
                        <a class="btnEdit btn btn-info">Edit</a>
                    </td>  
                    <td class="text text-white"><a class="btnDelete btn btn-danger">Delete</a></td>
                </tr>
                `
    })
    container.innerHTML = usersInfo
}
fetch(url)
    .then(response => response.json())
    .then(data => showUsers(data))
    .catch(error => console.log(error))

// Обновление таблицы
const reloadShowUsers = () => {
    fetch(url)
        .then(response => response.json())
        .then(data => {
            usersInfo = ''
            showUsers(data)
        })
}


// New User
const navNewUser = document.getElementById("nav-home-tab")
const formNew = document.getElementById('newUser')
const usernameNew = document.getElementById('newUsername')
const passwordNew = document.getElementById('newPassword')
const ageNew = document.getElementById('newAge')
const rolesNew = document.getElementById('newRole')
let option = ''

navNewUser.addEventListener('click', () => {
    console.log('btnNewUser click')
    usernameNew.value = ''
    passwordNew.value = ''
    ageNew.value = ''
    rolesNew.innerHTML = `
                        <option value="${dbRoles[0].id}">${dbRoles[0].name}</option>
                        <option value="${dbRoles[1].id}">${dbRoles[1].name}</option>
                        `
    option = 'newUser'
})

formNew.addEventListener('submit', (e) => {
    e.preventDefault()
    let listRoles = roleArray(document.getElementById('newRole'))
    console.log(
        usernameNew.value, passwordNew.value, ageNew.value, listRoles
    )
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({
            username: usernameNew.value,
            password: passwordNew.value,
            age: ageNew.value,
            roles: listRoles
        })
    })
        .then(res => res.json())
        .then(data => showUsers(data))
        .catch(error => console.log(error))
        .then(reloadShowUsers)

    usernameNew.value = ''
    passwordNew.value = ''
    ageNew.value = ''
    rolesNew.innerHTML = `
                        <option value="${dbRoles[0].id}">${dbRoles[0].name}</option>
                        <option value="${dbRoles[1].id}">${dbRoles[1].name}</option>
                        `
    option = 'newUser'
})

//Edit User
const modalEdit = new bootstrap.Modal(document.getElementById('edit_user'))
const editForm = document.getElementById('edit_user')
const idEdit = document.getElementById('edit_id')
const usernameEdit = document.getElementById('edit_username')
const passwordEdit = document.getElementById('edit_password')
const ageEdit = document.getElementById('edit_age')
const rolesEdit = document.getElementById('edit_new_role')


const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}

let idForm = 0
on(document, 'click', '.btnEdit', e => {
    const row = e.target.parentNode.parentNode
    idForm = row.firstElementChild.innerHTML
    fetch(url + '/' + idForm, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(data => getUserById(data))
        .catch(error => console.log(error))
    const getUserById = (user) => {
        idEdit.value = user.id
        usernameEdit.value = user.username
        passwordEdit.value = ''
        ageEdit.value = user.age
        rolesEdit.innerHTML = `
                                <option value="${dbRoles[0].id}">${dbRoles[0].name}</option>
                                <option value="${dbRoles[1].id}">${dbRoles[1].name}</option>
                                `
        Array.from(rolesEdit.options).forEach(opt => {
            user.roles.forEach(role => {
                if (role.name === opt.text) {
                    opt.selected = true
                }
            })
        })
        modalEdit.show()
    }
})

editForm.addEventListener('submit', (e) => {
    e.preventDefault()
    let listRoles = roleArray(document.getElementById('edit_new_role'))
    fetch(url, {
        method: 'PATCH',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({
            id: idForm,
            username: usernameEdit.value,
            password: passwordEdit.value,
            age: ageEdit.value,
            roles: listRoles
        })
    })
        .then(res => res.json())
        .then(data => showUsers(data))
        .catch(error => console.log(error))
        .then(reloadShowUsers)
    modalEdit.hide()
})


// Delete User
const modalDelete = new bootstrap.Modal(document.getElementById('delete_user'))
const deleteForm = document.getElementById('delete_user')
const idDelete = document.getElementById('delete_id')
const usernameDelete = document.getElementById('delete_username')
const passwordDelete = document.getElementById('delete_password')
const ageDelete = document.getElementById('delete_age')
const rolesDelete = document.getElementById('delete_new_role')


on(document, 'click', '.btnDelete', e => {
    const row = e.target.parentNode.parentNode
    idForm = row.firstElementChild.innerHTML
    fetch(url + '/' + idForm, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(data => getUserById(data))
        .catch(error => console.log(error))
    const getUserById = (user) => {
        idDelete.value = user.id
        usernameDelete.value = user.username
        passwordDelete.value = ''
        ageDelete.value = user.age
        rolesDelete.innerHTML = `
                                <option value="${dbRoles[0].id}">${dbRoles[0].name}</option>
                                <option value="${dbRoles[1].id}">${dbRoles[1].name}</option>
                                `
        Array.from(rolesDelete.options).forEach(opt => {
            user.roles.forEach(role => {
                if (role.name === opt.text) {
                    opt.selected = true
                }
            })
        })
        modalDelete.show()
    }
})

deleteForm.addEventListener('submit', (e) => {
    e.preventDefault()
    let listRoles = roleArray(document.getElementById('delete_new_role'))
    fetch(url + '/' + idForm, {
        method: 'DELETE'
    })
        .then(data => showUsers(data))
        .catch(error => console.log(error))
        .then(reloadShowUsers)
    modalDelete.hide()
})