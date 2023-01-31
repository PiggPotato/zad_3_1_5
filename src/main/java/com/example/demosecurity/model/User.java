package com.example.demosecurity.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "age")
    private int age;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(String username, String password, int age, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.roles = roles;
    }

    public User(Long id, String username, String password, int age, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Set<Role> getRoles() {
        return roles;
    }

    @Transient
    public String getCleanRoles() {
        Set<Role> cleanRoles = new HashSet<>(roles);
        cleanRoles = cleanRoles.stream()
                .toList().stream()
                .map(r -> new Role(r.getName().replaceFirst("ROLE_", "")))
                .collect(Collectors.toSet());
        List<Role> newRoles = new ArrayList<>(cleanRoles){
            @Override
            public String toString() {
                Iterator<Role> it = iterator();
                if (! it.hasNext())
                    return "[]";

                StringBuilder sb = new StringBuilder();
                for (;;) {
                    Role e = it.next();
                    sb.append(e.getName());
                    if (! it.hasNext())
                        return sb.toString();
                    sb.append(' ');
                }
            }
        };
        return newRoles.toString();
    }



    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());

    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, age, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", clean_roles=" + roles +
                '}';
    }
}
