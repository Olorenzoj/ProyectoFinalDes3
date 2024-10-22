package Modelos;

import java.sql.Date;

public class Usuario {
    private int userId;
    private String username;
    private String passwordHash;
    private String email;
    private int roleId;
    private String roleName;
    private Date createdAt;
    private Date updatedAt;

    public Usuario(String username, String passwordHash, String email, int roleId, Date createdAt, Date updatedAt) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.roleId = roleId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public Usuario(String username, int roleId){
        this.username=username;
        this.roleId = roleId;
    }

    public Usuario(int userId, String username, int roleId, String roleName) {
        this.userId = userId;
        this.username = username;
        this.roleId = roleId;
        this.roleName = roleName;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {return roleName;}

    public void setRoleName(String roleName) {this.roleName = roleName;}

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
