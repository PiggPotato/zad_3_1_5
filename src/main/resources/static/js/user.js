const url = '/api/user'

const showNavbarInfoUser = (user) => {
    const navbarInfo = document.getElementById('navbarInfo')
    let result = `

                <div class="col h5 card-header">                    
                    <strong>${user.username}</strong>
                    with roles:
                    <span>${user.cleanRoles}</span>
                </div>
                <div class="col">
                    <div class="float-end">
                        <form action="/logout" method="POST">
                            <input class="btn-exit" type="submit" value="Logout">
                        </form>
                    </div>
                </div>
                `
    navbarInfo.innerHTML = result
}

fetch(url)
    .then(response => response.json())
    .then(data => showNavbarInfoUser(data))
    .catch(error => console.log(error))

let userPageInfo = ''
const showUserInfo = (user) => {
    const container = document.querySelector('tbody')
    userPageInfo += `
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.age}</td>                  
                    <td>${user.cleanRoles}</td>  
                </tr>
                `
    container.innerHTML = userPageInfo
}
fetch(url)
    .then(response => response.json())
    .then(data => showUserInfo(data))
    .catch(error => console.log(error))