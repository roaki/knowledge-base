package com.joywifi.knowledge.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.joywifi.knowledge.annotation.Unique;
import com.joywifi.knowledge.enums.UserStatusEnum;
import com.joywifi.knowledge.plugin.entity.LogicDeleteable;

@XmlRootElement
public class User extends BaseEntity implements LogicDeleteable {

    private static final long serialVersionUID = 6478593744952785316L;

    @Unique
    @Length(min = 3, max = 20, message = "填写合法的用户名")
    private String username;
    private String password;
    private String salt;

    @Email
    @NotBlank(message = "邮箱不能为空")
    private String email;
    @DBRef
    private List<Role> roles;
    private List<String> permissions;

    private UserStatusEnum status;
    private Boolean deleted = Boolean.FALSE;

    @Transient
    private String plainPassword;

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public UserStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UserStatusEnum status) {
        this.status = status;
    }


    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public void markDeleted() {
        this.deleted = Boolean.TRUE;
    }
}
