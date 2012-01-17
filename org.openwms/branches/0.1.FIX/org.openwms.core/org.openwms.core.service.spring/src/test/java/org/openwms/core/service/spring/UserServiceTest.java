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
package org.openwms.core.service.spring;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import javax.persistence.NoResultException;

import org.junit.Before;
import org.junit.Test;
import org.openwms.core.domain.system.usermanagement.SystemUser;
import org.openwms.core.domain.system.usermanagement.User;
import org.openwms.core.domain.system.usermanagement.UserPassword;
import org.openwms.core.domain.system.usermanagement.UserPreference;
import org.openwms.core.exception.InvalidPasswordException;
import org.openwms.core.service.UserService;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.openwms.core.service.exception.UserNotFoundException;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * A UserServiceTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@ContextConfiguration("classpath:/org/openwms/core/service/spring/Test-context.xml")
public class UserServiceTest extends AbstractJpaSpringContextTests {

    @Autowired
    private UserService srv;

    /**
     * Setting up some test users.
     */
    @Before
    public void onBefore() {
        entityManager.persist(new User("KNOWN"));
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * Test to save a byte array as image file.
     */
    @Test
    public final void testUploadImage() {
        try {
            srv.uploadImageFile("UNKNOWN", new byte[222]);
            fail("Should throw an exception when calling with unknown user");
        } catch (ServiceRuntimeException sre) {
            if (!(sre instanceof UserNotFoundException)) {
                fail("Should throw a nested UserNotFoundException when calling with unknown user");
            }
            logger.debug("OK: User unknown" + sre.getMessage());
        }
        srv.uploadImageFile("KNOWN", new byte[222]);
        User user = findUser("KNOWN");
        assertTrue(user.getUserDetails().getImage().length == 222);
    }

    /**
     * Test to save a NULL user.
     */
    @Test
    public final void testSaveWithNull() {
        try {
            srv.save(null);
            fail("Should throw an exception when calling with null");
        } catch (ServiceRuntimeException sre) {
            logger.debug("OK: null user:" + sre.getMessage());
        }
    }

    /**
     * Test to save a transient user.
     */
    @Test
    public final void testSaveTransient() {
        User user = srv.save(new User("UNKNOWN"));
        assertFalse("User must be persisted and has a primary key", user.isNew());
    }

    /**
     * Test to save a existing detached user.
     */
    @Test
    public final void testSaveDetached() {
        User user = findUser("KNOWN");
        assertFalse("User must be persisted before", user.isNew());
        entityManager.clear();
        user.setFullname("Mr. Hudson");
        user = srv.save(user);
        entityManager.flush();
        entityManager.clear();
        user = findUser("KNOWN");
        assertEquals("Changes must be saved", "Mr. Hudson", user.getFullname());
    }

    /**
     * Test to call remove with null.
     */
    @Test
    public final void testRemoveWithNull() {
        try {
            srv.remove(null);
            fail("Should throw an exception when calling with null");
        } catch (ServiceRuntimeException sre) {
            logger.debug("OK: null user:" + sre.getMessage());
        }
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserServiceImpl#remove(User)}.
     */
    @Test
    public final void testRemove() {
        User user = findUser("KNOWN");
        assertFalse("User must be persisted before", user.isNew());
        entityManager.clear();
        srv.remove(user);
        entityManager.flush();
        entityManager.clear();
        try {
            findUser("KNOWN");
            fail("Must be removed before and throw an exception");
        } catch (NoResultException nre) {
            logger.debug("OK: Exception when searching for a removed entity");
        }
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserServiceImpl#changeUserPassword(UserPassword)}
     * .
     * 
     * Test to call with null.
     */
    @Test
    public final void testChangePasswordWithNull() {
        try {
            srv.changeUserPassword(null);
            fail("Should throw an exception when calling with null");
        } catch (ServiceRuntimeException sre) {
            logger.debug("OK: null:" + sre.getMessage());
        }
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserServiceImpl#changeUserPassword(UserPassword)}
     * .
     * 
     * Test to change it for an unknown user.
     */
    @Test
    public final void testChangePasswordUnknown() {
        try {
            srv.changeUserPassword(new UserPassword(new User("UNKNOWN"), "password"));
            fail("Should throw an exception when calling with an unknown user");
        } catch (ServiceRuntimeException sre) {
            if (!(sre instanceof UserNotFoundException)) {
                fail("Should throw an UserNotFoundException when calling with an unknown user");
            }
            logger.debug("OK: UserNotFoundException:" + sre.getMessage());
        }
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserServiceImpl#changeUserPassword(UserPassword)}
     * .
     * 
     * Test to change the password of an User.
     */
    @Test
    public final void testChangePassword() {
        srv.changeUserPassword(new UserPassword(new User("KNOWN"), "password"));
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserServiceImpl#changeUserPassword(UserPassword)}
     * .
     * 
     * Test to change password to an invalid one.
     */
    @Test
    public final void testChangePasswordInvalidPassword() {
        srv.changeUserPassword(new UserPassword(new User("KNOWN"), "password"));
        srv.changeUserPassword(new UserPassword(new User("KNOWN"), "password1"));
        try {
            srv.changeUserPassword(new UserPassword(new User("KNOWN"), "password"));
            fail("Should throw an exception when calling with an invalid password");
        } catch (ServiceRuntimeException sre) {
            if (!(sre.getCause() instanceof InvalidPasswordException)) {
                fail("Should throw a nested InvalidPasswordException when calling with an invalid password");
            }
            logger.debug("OK: InvalidPasswordException:" + sre.getMessage());
        }
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserServiceImpl#findAll()}.
     */
    @Test
    public final void testFindAll() {
        assertEquals("1 User is expected", 1, srv.findAll().size());
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserServiceImpl#getTemplate(String)}
     * .
     */
    @Test
    public final void testGetTemplate() {
        User user = srv.getTemplate("TEST_USER");
        assertTrue("Must be a new User", user.isNew());
        assertEquals("Expected to get an User instance with the same username", "TEST_USER", user.getUsername());
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserServiceImpl#createSystemUser()}
     * .
     */
    @Test
    public final void testCreateSystemUser() {
        User user = srv.createSystemUser();
        assertTrue("Must be a new User", user.isNew());
        assertTrue("Must be a SystemUser", user instanceof SystemUser);
        assertEquals("Expected one Role", 1, user.getRoles().size());
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserServiceImpl#saveUserProfile(User, UserPassword, org.openwms.core.domain.system.usermanagement.UserPreference...)}
     * .
     */
    @Test
    public final void testSaveUserProfileUserNull() {
        try {
            srv.saveUserProfile(null, new UserPassword(new User("TEST"), "TEST"));
            fail("Must throw an exception when invoking with null argument");
        } catch (ServiceRuntimeException sre) {
            if (!(sre.getCause() instanceof IllegalArgumentException)) {
                fail("Expected to wrap an IllegalArgumentException when the user argument is null");
            }
        }
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserServiceImpl#saveUserProfile(User, UserPassword, org.openwms.core.domain.system.usermanagement.UserPreference...)}
     * .
     */
    @Test
    public final void testSaveUserProfileUserPreferencePasswordNull() {
        User user = srv.saveUserProfile(new User("TEST"), null);
        assertEquals("Must return the saved user", user, new User("TEST"));
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserServiceImpl#saveUserProfile(User, UserPassword, org.openwms.core.domain.system.usermanagement.UserPreference...)}
     * .
     */
    @Test
    public final void testSaveUserProfileUserWithPassword() {
        User user = new User("TEST");
        User u = srv.saveUserProfile(user, new UserPassword(user, "password"));
        assertEquals("Must return the saved user", u, user);
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserServiceImpl#saveUserProfile(User, UserPassword, org.openwms.core.domain.system.usermanagement.UserPreference...)}
     * .
     */
    @Test
    public final void testSaveUserProfileUserWithInvalidPassword() {
        User user = new User("TEST");
        User u = srv.saveUserProfile(user, new UserPassword(user, "password"));
        u = srv.saveUserProfile(u, new UserPassword(u, "password1"));
        try {
            u = srv.saveUserProfile(u, new UserPassword(user, "password"));
            fail("Expected to catch an ServiceRuntimeException when the password is invalid");
        } catch (ServiceRuntimeException sre) {
            if (!(sre.getCause() instanceof InvalidPasswordException)) {
                fail("Expected to catch an InvalidPasswordException when the password is invalid");
            }
        }
        assertEquals("Must return the saved user", u, user);
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserServiceImpl#saveUserProfile(User, UserPassword, org.openwms.core.domain.system.usermanagement.UserPreference...)}
     * .
     */
    @Test
    public final void testSaveUserProfileWithPreference() {
        User user = new User("TEST");
        User u = srv.saveUserProfile(user, new UserPassword(user, "password"), new UserPreference(user.getUsername(),
                "TEST"));
        entityManager.flush();
        entityManager.clear();
        assertEquals("Must return the saved user", u, user);
        assertEquals("Number of UserPreferences must be 1", 1, entityManager.find(User.class, u.getId())
                .getPreferences().size());
    }

    private User findUser(String userName) {
        return (User) entityManager.createNamedQuery(User.NQ_FIND_BY_USERNAME).setParameter(1, userName)
                .getSingleResult();
    }
}