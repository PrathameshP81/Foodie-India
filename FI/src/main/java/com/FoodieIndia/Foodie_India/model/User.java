package com.FoodieIndia.Foodie_India.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.FoodieIndia.Foodie_India.Enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String email;

    private String password;

    private String gender;

    private String phone;

    // List of Purchased Planes (If Role is Normal User)
    @ManyToMany
    @JoinTable(name = "user_purchased_plans", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "plan_id"))
    List<Plan> purchased_plans = new ArrayList<>();

    // Role of the User
    @Enumerated(EnumType.STRING)
    Role role;

    // If Role is Admin

    // List of Created Plans
    @OneToMany(mappedBy = "createdBy") // refers to 'createdBy' field in Plan
    List<Plan> createdPlans = new ArrayList<>();

    // List of Created Recepies
    @OneToMany(mappedBy = "createdByUser") // refers to 'createdByUser' field in Recepie
    List<Recepie> createdRecepies = new ArrayList<>();

    // List of Subscritptions Putchase By Admin
    @OneToMany(mappedBy = "user")
    List<UserSubscriptions> purchased_subscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "createdByUser")
    List<Subscription> createdSubscriptions = new ArrayList<>();

    // Spring Security Auth Methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return email; // or name
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

}
