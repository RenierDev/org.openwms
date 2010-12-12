/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.common.domain.system.usermanagement;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import org.openwms.common.domain.values.ImageProvider;

/**
 * Detailed information about an <code>User</code>.
 * 
 * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.domain.system.usermanagement.User
 */
@Embeddable
public class UserDetails implements ImageProvider, Serializable {

    private static final long serialVersionUID = 664778075559767489L;

    /**
     * The <code>User</code>s sex.
     * 
     * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     * @see org.openwms.common.domain.system.usermanagement.User
     */
    public static enum SEX {
        /**
         * Male sex.
         */
        MALE,
        /**
         * Female sex.
         */
        FEMALE
    }

    /**
     * Some descriptive text of the <code>User</code>.
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Some comment text of the <code>User</code>.
     */
    @Column(name = "C_COMMENT")
    private String comment;

    /**
     * Phone number assigned to the <code>User</code>.
     */
    @Column(name = "PHONE_NO")
    private String phoneNo;

    /**
     * IM account assigned to the <code>User</code>.
     */
    @Column(name = "SKYPE_NAME")
    private String skypeName;

    /**
     * Office description assigned to the <code>User</code>.
     */
    @Column(name = "OFFICE")
    private String office;

    /**
     * Department description assigned to the <code>User</code>.
     */
    @Column(name = "DEPARTMENT")
    private String department;

    /**
     * An image of the <code>User</code>.
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "IMAGE")
    private byte[] image;

    /**
     * Sex of the <code>User</code>.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "SEX")
    private SEX sex;

    /* ----------------------------- methods ------------------- */
    /**
     * Create a new <code>UserDetails</code> instance.
     */
    public UserDetails() {}

    /**
     * Returns the <code>User</code>s current phone number.
     * 
     * @return The phone number.
     */
    public String getPhoneNo() {
        return this.phoneNo;
    }

    /**
     * Change the phone number of the <code>User</code>.
     * 
     * @param phoneNo
     *            The new phone number.
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Returns the description text of the <code>User</code>.
     * 
     * @return The description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Change the description text of the <code>User</code>.
     * 
     * @param description
     *            The new description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a comment text of the <code>User</code>.
     * 
     * @return The comment text.
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Change the comment text of the <code>User</code>.
     * 
     * @param comment
     *            The new comment text.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Returns the current office of the <code>User</code>.
     * 
     * @return The current office.
     */
    public String getOffice() {
        return this.office;
    }

    /**
     * Change the current office of the <code>User</code>.
     * 
     * @param office
     *            The new office.
     */
    public void setOffice(String office) {
        this.office = office;
    }

    /**
     * Returns the IM account name of the <code>User</code>.
     * 
     * @return The current IM account name.
     */
    public String getSkypeName() {
        return this.skypeName;
    }

    /**
     * Change the current IM account name of the <code>User</code>.
     * 
     * @param skypeName
     *            The new IM account name.
     */
    public void setSkypeName(String skypeName) {
        this.skypeName = skypeName;
    }

    /**
     * Returns the current department of the <code>User</code>.
     * 
     * @return The current department.
     */
    public String getDepartment() {
        return this.department;
    }

    /**
     * Change the current department of the <code>User</code>.
     * 
     * @param department
     *            The new department.
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] getImage() {
        return this.image;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * Returns the <code>User</code>'s sex.
     * 
     * @return The <code>User</code>'s sex.
     */
    public SEX getSex() {
        return sex;
    }

    /**
     * Change the <code>User</code>'s sex (is this generally possible?).
     * 
     * @param sex
     *            The new sex.
     */
    public void setSex(SEX sex) {
        this.sex = sex;
    }
}